package org.nami.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.nami.entity.RouteRule;
import org.nami.vo.RuleVO;

import java.util.List;

/**
 * RuleVOMapStruct
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@Mapper
public interface RuleVOMapStruct {

    RuleVOMapStruct INSTANCE = Mappers.getMapper(RuleVOMapStruct.class);

    @Mappings({
            @Mapping(target = "createTime", expression = "java(org.nami.utils.DateUtils.formatToYYYYMMDDHHmmss(rule.getCreateTime()))"),
            @Mapping(target = "updateTime", expression = "java(org.nami.utils.DateUtils.formatToYYYYMMDDHHmmss(rule.getUpdateTime()))")
    })
    RuleVO mapToVO(RouteRule rule);

    List<RuleVO> mapToVOList(List<RouteRule> rules);
}
