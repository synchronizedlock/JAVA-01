package org.nami.mapstruct;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.nami.entity.AppInstance;
import org.nami.vo.InstanceVO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-02-01T11:04:17+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_271 (Oracle Corporation)"
)
public class InstanceVOMapStructImpl implements InstanceVOMapStruct {

    @Override
    public InstanceVO mapToVO(AppInstance appInstance) {
        if ( appInstance == null ) {
            return null;
        }

        InstanceVO instanceVO = new InstanceVO();

        instanceVO.setId( appInstance.getId() );
        instanceVO.setVersion( appInstance.getVersion() );
        instanceVO.setIp( appInstance.getIp() );
        instanceVO.setPort( appInstance.getPort() );
        instanceVO.setWeight( appInstance.getWeight() );

        instanceVO.setCreateTime( org.nami.utils.DateUtils.formatToYYYYMMDDHHmmss(appInstance.getCreateTime()) );
        instanceVO.setUpdateTime( org.nami.utils.DateUtils.formatToYYYYMMDDHHmmss(appInstance.getUpdateTime()) );

        return instanceVO;
    }

    @Override
    public List<InstanceVO> mapToVOS(List<AppInstance> appInstances) {
        if ( appInstances == null ) {
            return null;
        }

        List<InstanceVO> list = new ArrayList<InstanceVO>( appInstances.size() );
        for ( AppInstance appInstance : appInstances ) {
            list.add( mapToVO( appInstance ) );
        }

        return list;
    }
}
