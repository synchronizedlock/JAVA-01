package org.nami.event;

import org.nami.dto.AppRuleDTO;
import org.springframework.context.ApplicationEvent;

/**
 * RuleDeleteEvent
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
public class RuleDeleteEvent extends ApplicationEvent {

    private AppRuleDTO appRuleDTO;

    public RuleDeleteEvent(Object source, AppRuleDTO appRuleDTO) {
        super(source);
        this.appRuleDTO = appRuleDTO;
    }

    public AppRuleDTO getAppRuleDTO() {
        return appRuleDTO;
    }
}
