package org.nami.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.nami.entity.App;
import org.nami.vo.AppVO;

import java.util.List;

/**
 * AppVOMapStruct
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@Mapper
public interface AppVOMapStruct {

    AppVOMapStruct INSTANCE = Mappers.getMapper(AppVOMapStruct.class);

    @Mappings({
            @Mapping(target = "createTime", expression = "java(cn.sp.utils.DateUtils.formatToYYYYMMDDHHmmss(app.getCreateTime()))"),
            @Mapping(target = "updateTime", expression = "java(cn.sp.utils.DateUtils.formatToYYYYMMDDHHmmss(app.getUpdateTime()))")
    })
    AppVO mapToVO(App app);

    List<AppVO> mapToVOList(List<App> appList);
}
