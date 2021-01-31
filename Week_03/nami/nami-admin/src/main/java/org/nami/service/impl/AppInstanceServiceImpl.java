package org.nami.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import org.nami.dto.UpdateWeightDTO;
import org.nami.entity.App;
import org.nami.entity.AppInstance;
import org.nami.mapper.AppInstanceMapper;
import org.nami.mapper.AppMapper;
import org.nami.mapstruct.InstanceVOMapStruct;
import org.nami.service.AppInstanceService;
import org.nami.vo.InstanceVO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * AppInstanceServiceImpl
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@Service
public class AppInstanceServiceImpl implements AppInstanceService {

    @Resource
    private AppInstanceMapper instanceMapper;

    @Resource
    private AppMapper appMapper;

    @Override
    public List<InstanceVO> queryList(Integer appId) {
        App app = appMapper.selectById(appId);

        QueryWrapper<AppInstance> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(AppInstance::getAppId, appId);
        List<AppInstance> instanceList = instanceMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(instanceList)) {
            return Lists.newArrayList();
        }

        List<InstanceVO> voList = InstanceVOMapStruct.INSTANCE.mapToVOS(instanceList);
        voList.forEach(vo -> vo.setAppName(app.getAppName()));
        return voList;
    }

    @Override
    public void updateWeight(UpdateWeightDTO updateWeightDTO) {
        AppInstance appInstance = new AppInstance();
        appInstance.setId(updateWeightDTO.getId());
        appInstance.setWeight(updateWeightDTO.getWeight());
        instanceMapper.updateById(appInstance);
    }
}
