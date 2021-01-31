package org.nami.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.nami.entity.AppInstance;
import org.nami.vo.InstanceVO;

import java.util.List;

/**
 * InstanceVOMapStruct
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@Mapper
public interface InstanceVOMapStruct {

    InstanceVOMapStruct INSTANCE = Mappers.getMapper(InstanceVOMapStruct.class);

    @Mappings({
            @Mapping(target = "createTime", expression = "java(cn.sp.utils.DateUtils.formatToYYYYMMDDHHmmss(appInstance.getCreateTime()))"),
            @Mapping(target = "updateTime", expression = "java(cn.sp.utils.DateUtils.formatToYYYYMMDDHHmmss(appInstance.getUpdateTime()))")
    })
    InstanceVO mapToVO(AppInstance appInstance);

    List<InstanceVO> mapToVOS(List<AppInstance> appInstances);
}
