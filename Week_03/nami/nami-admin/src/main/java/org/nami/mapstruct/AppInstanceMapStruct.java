package org.nami.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.nami.entity.AppInstance;
import org.nami.entity.ServiceInstance;

import java.util.List;

/**
 * AppInstanceMapStruct
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@Mapper
public interface AppInstanceMapStruct {

    AppInstanceMapStruct INSTANCE = Mappers.getMapper(AppInstanceMapStruct.class);

    ServiceInstance mapToService(AppInstance appInstance);

    List<ServiceInstance> mapToServiceList(List<AppInstance> appInstances);
}
