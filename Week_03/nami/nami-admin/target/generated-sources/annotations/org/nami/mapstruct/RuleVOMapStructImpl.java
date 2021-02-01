package org.nami.mapstruct;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.nami.entity.RouteRule;
import org.nami.vo.RuleVO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-02-01T11:04:17+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_271 (Oracle Corporation)"
)
public class RuleVOMapStructImpl implements RuleVOMapStruct {

    @Override
    public RuleVO mapToVO(RouteRule rule) {
        if ( rule == null ) {
            return null;
        }

        RuleVO ruleVO = new RuleVO();

        ruleVO.setId( rule.getId() );
        ruleVO.setName( rule.getName() );
        ruleVO.setAppId( rule.getAppId() );
        ruleVO.setVersion( rule.getVersion() );
        ruleVO.setMatchObject( rule.getMatchObject() );
        ruleVO.setMatchKey( rule.getMatchKey() );
        ruleVO.setMatchMethod( rule.getMatchMethod() );
        ruleVO.setMatchRule( rule.getMatchRule() );
        ruleVO.setEnabled( rule.getEnabled() );

        ruleVO.setCreateTime( org.nami.utils.DateUtils.formatToYYYYMMDDHHmmss(rule.getCreateTime()) );
        ruleVO.setUpdateTime( org.nami.utils.DateUtils.formatToYYYYMMDDHHmmss(rule.getUpdateTime()) );

        return ruleVO;
    }

    @Override
    public List<RuleVO> mapToVOList(List<RouteRule> rules) {
        if ( rules == null ) {
            return null;
        }

        List<RuleVO> list = new ArrayList<RuleVO>( rules.size() );
        for ( RouteRule routeRule : rules ) {
            list.add( mapToVO( routeRule ) );
        }

        return list;
    }
}
