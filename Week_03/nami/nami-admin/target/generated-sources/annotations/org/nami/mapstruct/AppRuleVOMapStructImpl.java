package org.nami.mapstruct;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.nami.dto.AppRuleDTO;
import org.nami.entity.RouteRule;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-02-01T11:04:17+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_271 (Oracle Corporation)"
)
public class AppRuleVOMapStructImpl implements AppRuleVOMapStruct {

    @Override
    public AppRuleDTO mapToVO(RouteRule routeRule) {
        if ( routeRule == null ) {
            return null;
        }

        AppRuleDTO appRuleDTO = new AppRuleDTO();

        appRuleDTO.setId( routeRule.getId() );
        appRuleDTO.setAppId( routeRule.getAppId() );
        appRuleDTO.setVersion( routeRule.getVersion() );
        appRuleDTO.setMatchObject( routeRule.getMatchObject() );
        appRuleDTO.setMatchKey( routeRule.getMatchKey() );
        appRuleDTO.setMatchMethod( routeRule.getMatchMethod() );
        appRuleDTO.setMatchRule( routeRule.getMatchRule() );

        return appRuleDTO;
    }

    @Override
    public List<AppRuleDTO> mapToVOList(List<RouteRule> routeRules) {
        if ( routeRules == null ) {
            return null;
        }

        List<AppRuleDTO> list = new ArrayList<AppRuleDTO>( routeRules.size() );
        for ( RouteRule routeRule : routeRules ) {
            list.add( mapToVO( routeRule ) );
        }

        return list;
    }
}
