package org.nami.vo;

import org.nami.dto.AppRuleDTO;

import java.util.List;

/**
 * AppRuleListVO
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
public class AppRuleListVO {

    private List<AppRuleDTO> list;

    public List<AppRuleDTO> getList() {
        return list;
    }

    public void setList(List<AppRuleDTO> list) {
        this.list = list;
    }
}
