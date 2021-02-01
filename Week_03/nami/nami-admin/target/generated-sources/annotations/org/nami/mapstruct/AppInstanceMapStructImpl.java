package org.nami.mapstruct;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.nami.entity.AppInstance;
import org.nami.entity.ServiceInstance;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-02-01T11:04:17+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_271 (Oracle Corporation)"
)
public class AppInstanceMapStructImpl implements AppInstanceMapStruct {

    @Override
    public ServiceInstance mapToService(AppInstance appInstance) {
        if ( appInstance == null ) {
            return null;
        }

        ServiceInstance serviceInstance = new ServiceInstance();

        serviceInstance.setWeight( appInstance.getWeight() );
        serviceInstance.setIp( appInstance.getIp() );
        serviceInstance.setPort( appInstance.getPort() );
        serviceInstance.setVersion( appInstance.getVersion() );

        return serviceInstance;
    }

    @Override
    public List<ServiceInstance> mapToServiceList(List<AppInstance> appInstances) {
        if ( appInstances == null ) {
            return null;
        }

        List<ServiceInstance> list = new ArrayList<ServiceInstance>( appInstances.size() );
        for ( AppInstance appInstance : appInstances ) {
            list.add( mapToService( appInstance ) );
        }

        return list;
    }
}
