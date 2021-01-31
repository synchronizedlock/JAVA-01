package org.nami.plugin;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.nami.cache.LoadBalanceFactory;
import org.nami.cache.RouteRuleCache;
import org.nami.cache.ServiceCache;
import org.nami.utils.NamiResponseUtils;
import org.nami.chain.PluginChain;
import org.nami.config.ServerConfigProperties;
import org.nami.dto.AppRuleDTO;
import org.nami.entity.ServiceInstance;
import org.nami.enums.MatchObjectEnum;
import org.nami.enums.NamiExceptionEnum;
import org.nami.enums.NamiPluginEnum;
import org.nami.exception.NamiException;
import org.nami.spi.LoadBalance;
import org.nami.utils.StringTools;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Slf4j
public class DynamicRoutePlugin extends AbstractNamiPlugin {

    private static final WebClient WEB_CLIENT = buildDefaultWebClient();

    public DynamicRoutePlugin(ServerConfigProperties properties) {
        super(properties);
    }

    @Override
    public Integer order() {
        return NamiPluginEnum.DYNAMIC_ROUTE.getOrder();
    }

    @Override
    public String name() {
        return NamiPluginEnum.DYNAMIC_ROUTE.getName();
    }

    @Override
    public Mono<Void> execute(ServerWebExchange exchange, PluginChain pluginChain) {
        String appName = pluginChain.getAppName();
        ServiceInstance serviceInstance = chooseInstance(appName, exchange.getRequest());
        String url = buildUrl(exchange, serviceInstance);
        return forward(exchange, url);
    }

    /**
     * forward request to backend service
     */
    private Mono<Void> forward(ServerWebExchange exchange, String url) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        HttpMethod method = request.getMethod();

        WebClient.RequestBodySpec requestBodySpec = WEB_CLIENT.method(method).uri(url).headers((headers) -> {
            headers.addAll(request.getHeaders());
        });

        WebClient.RequestHeadersSpec<?> reqHeadersSpec;
        if (requireHttpBody(method)) {
            reqHeadersSpec = requestBodySpec.body(BodyInserters.fromDataBuffers(request.getBody()));
        } else {
            reqHeadersSpec = requestBodySpec;
        }
        // nio->callback->nio
        return reqHeadersSpec.exchange().timeout(Duration.ofMillis(properties.getTimeOutMillis()))
                .onErrorResume(ex -> {
                    return Mono.defer(() -> {
                        String errorResultJson = "";
                        if (ex instanceof TimeoutException) {
                            errorResultJson = "{\"code\":5001,\"message\":\"network timeout\"}";
                        } else {
                            errorResultJson = "{\"code\":5000,\"message\":\"system error\"}";
                        }
                        return NamiResponseUtils.doResponse(exchange, errorResultJson);
                    }).then(Mono.empty());
                }).flatMap(backendResponse -> {
                    response.setStatusCode(backendResponse.statusCode());
                    response.getHeaders().putAll(backendResponse.headers().asHttpHeaders());
                    return response.writeWith(backendResponse.bodyToFlux(DataBuffer.class));
                });
    }

    /**
     * weather the http method need http body
     */
    private boolean requireHttpBody(HttpMethod method) {
        if (method.equals(HttpMethod.POST) || method.equals(HttpMethod.PUT) || method.equals(HttpMethod.PATCH)) {
            return true;
        }
        return false;
    }

    private String buildUrl(ServerWebExchange exchange, ServiceInstance serviceInstance) {
        ServerHttpRequest request = exchange.getRequest();
        String query = request.getURI().getQuery();
        String path = request.getPath().value().replaceFirst("/" + serviceInstance.getAppName(), "");
        String url = "http://" + serviceInstance.getIp() + ":" + serviceInstance.getPort() + path;
        if (!StringUtils.isEmpty(query)) {
            url = url + "?" + query;
        }
        return url;
    }


    /**
     * choose a ServiceInstance according to route rule config and load balancing algorithm
     */
    private ServiceInstance chooseInstance(String appName, ServerHttpRequest request) {
        List<ServiceInstance> serviceInstances = ServiceCache.getAllInstances(appName);
        if (CollectionUtils.isEmpty(serviceInstances)) {
            log.error("service instance of {} not find", appName);
            throw new NamiException(NamiExceptionEnum.SERVICE_NOT_FIND);
        }
        String version = matchAppVersion(appName, request);
        if (StringUtils.isEmpty(version)) {
            throw new NamiException("match app version error");
        }
        // filter serviceInstances by version
        List<ServiceInstance> instances = serviceInstances.stream().filter(i -> i.getVersion().equals(version)).collect(Collectors.toList());
        //Select an instance based on the load balancing algorithm
        LoadBalance loadBalance = LoadBalanceFactory.getInstance(properties.getLoadBalance(), appName, version);
        return loadBalance.chooseOne(instances);
    }


    private String matchAppVersion(String appName, ServerHttpRequest request) {
        List<AppRuleDTO> rules = RouteRuleCache.getRules(appName);
        rules.sort(Comparator.comparing(AppRuleDTO::getPriority).reversed());
        for (AppRuleDTO rule : rules) {
            if (match(rule, request)) {
                return rule.getVersion();
            }
        }
        return null;
    }


    private boolean match(AppRuleDTO rule, ServerHttpRequest request) {
        String matchObject = rule.getMatchObject();
        String matchKey = rule.getMatchKey();
        String matchRule = rule.getMatchRule();
        Byte matchMethod = rule.getMatchMethod();

        if (MatchObjectEnum.DEFAULT.getCode().equals(matchObject)) {
            return true;
        } else if (MatchObjectEnum.QUERY.getCode().equals(matchObject)) {
            String param = request.getQueryParams().getFirst(matchKey);
            if (!StringUtils.isEmpty(param)) {
                return StringTools.match(param, matchMethod, matchRule);
            }
        } else if (MatchObjectEnum.HEADER.getCode().equals(matchObject)) {
            HttpHeaders headers = request.getHeaders();
            String headerValue = headers.getFirst(matchKey);
            if (!StringUtils.isEmpty(headerValue)) {
                return StringTools.match(headerValue, matchMethod, matchRule);
            }
        }
        return false;
    }

    private static WebClient buildDefaultWebClient() {
        HttpClient httpClient = HttpClient.create()
                .tcpConfiguration(client ->
                        client.doOnConnected(conn ->
                                conn.addHandlerLast(new ReadTimeoutHandler(3))
                                        .addHandlerLast(new WriteTimeoutHandler(3)))
                                .option(ChannelOption.TCP_NODELAY, true)
                );
        return WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
