package org.nami.event.listener;

import com.google.common.collect.Lists;
import org.nami.dto.AppRuleDTO;
import org.nami.dto.RouteRuleOperationDTO;
import org.nami.enums.OperationTypeEnum;
import org.nami.event.RuleAddEvent;
import org.nami.event.RuleDeleteEvent;
import org.nami.sync.WebsocketSyncCacheClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * RuleEventListener
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@Component
public class RuleEventListener {

    @Autowired
    private WebsocketSyncCacheClient client;

    @EventListener
    public void onAdd(RuleAddEvent ruleAddEvent) {
        RouteRuleOperationDTO operationDTO = new RouteRuleOperationDTO(OperationTypeEnum.INSERT, Lists.newArrayList(ruleAddEvent.getAppRuleDTO()));
        client.send(operationDTO);
    }

    @EventListener
    public void onDelete(RuleDeleteEvent ruleDeleteEvent) {
        List<AppRuleDTO> list = Lists.newArrayList(ruleDeleteEvent.getAppRuleDTO());
        RouteRuleOperationDTO operationDTO = new RouteRuleOperationDTO(OperationTypeEnum.DELETE, list);
        client.send(operationDTO);
    }
}
