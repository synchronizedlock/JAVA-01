package org.nami.service;

import org.nami.dto.AppRuleDTO;
import org.nami.dto.ChangeStatusDTO;
import org.nami.dto.RuleDTO;
import org.nami.vo.RuleVO;

import java.util.List;

/**
 * RuleService
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
public interface RuleService {

    List<AppRuleDTO> getEnabledRule();

    void add(RuleDTO ruleDTO);

    void delete(Integer id);

    List<RuleVO> queryList(String appName);

    void changeStatus(ChangeStatusDTO statusDTO);
}
