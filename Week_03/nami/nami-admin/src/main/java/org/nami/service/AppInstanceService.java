package org.nami.service;

import org.nami.dto.UpdateWeightDTO;
import org.nami.vo.InstanceVO;

import java.util.List;

/**
 * AppInstanceService
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
public interface AppInstanceService {

    /**
     * query instances by appId
     */
    List<InstanceVO> queryList(Integer appId);

    void updateWeight(UpdateWeightDTO updateWeightDTO);
}
