package org.nami.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.nami.dto.AppRuleDTO;
import org.nami.entity.RouteRule;

import java.util.List;

/**
 * AppRuleVOMapStruct
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@Mapper
public interface AppRuleVOMapStruct {

    AppRuleVOMapStruct INSTANCE = Mappers.getMapper(AppRuleVOMapStruct.class);

    AppRuleDTO mapToVO(RouteRule routeRule);

    List<AppRuleDTO> mapToVOList(List<RouteRule> routeRules);
}
