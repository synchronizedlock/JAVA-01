package org.nami.sync;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.enums.ReadyState;
import org.java_websocket.handshake.ServerHandshake;
import org.nami.dto.AppRuleDTO;
import org.nami.dto.RouteRuleOperationDTO;
import org.nami.enums.NamiExceptionEnum;
import org.nami.enums.OperationTypeEnum;
import org.nami.exception.NamiException;
import org.nami.service.RuleService;
import org.nami.utils.NamiThreadFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * WebsocketSyncCacheClient
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@Slf4j
@Component
public class WebsocketSyncCacheClient {

    private WebSocketClient client;

    private RuleService ruleService;

    private Gson gson = new GsonBuilder().create();

    public WebsocketSyncCacheClient(@Value("${nami.server-web-socket-url}") String serverWebSocketUrl,
                                    RuleService ruleService) {
        if (StringUtils.isEmpty(serverWebSocketUrl)) {
            throw new NamiException(NamiExceptionEnum.CONFIG_ERROR);
        }
        this.ruleService = ruleService;
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1,
                new NamiThreadFactory("websocket-connect", true).create());
        try {
            client = new WebSocketClient(new URI(serverWebSocketUrl)) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    log.info("client is open");
                    List<AppRuleDTO> list = ruleService.getEnabledRule();
                    String msg = gson.toJson(new RouteRuleOperationDTO(OperationTypeEnum.INSERT, list));
                    send(msg);
                }

                @Override
                public void onMessage(String s) {
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                }

                @Override
                public void onError(Exception e) {
                    log.error("websocket client error", e);
                }
            };

            client.connectBlocking();
            //使用调度线程池进行断线重连，30秒进行一次
            executor.scheduleAtFixedRate(() -> {
                if (client != null && client.isClosed()) {
                    try {
                        client.reconnectBlocking();
                    } catch (InterruptedException e) {
                        log.error("reconnect server fail", e);
                    }
                }
            }, 10, 30, TimeUnit.SECONDS);

        } catch (Exception e) {
            log.error("websocket sync cache exception", e);
            throw new NamiException(e.getMessage());
        }
    }

    public <T> void send(T t) {
        while (!client.getReadyState().equals(ReadyState.OPEN)) {
            throw new NamiException("Please check if the nami-server is started！");
        }
        client.send(gson.toJson(t));
    }
}
