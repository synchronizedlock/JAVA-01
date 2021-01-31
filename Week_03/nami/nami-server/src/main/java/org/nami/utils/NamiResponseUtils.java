package org.nami.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * NamiResponseUtils
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
public class NamiResponseUtils {

    private static final Gson GSON = new GsonBuilder().create();

    public static Mono<Void> doResponse(ServerWebExchange exchange, String resp) {
        Assert.notNull(resp, "response object can't be null");
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse()
                .bufferFactory().wrap(resp.getBytes())));
    }
}
