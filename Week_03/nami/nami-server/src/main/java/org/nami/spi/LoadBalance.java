package org.nami.spi;

import org.nami.entity.ServiceInstance;

import java.util.List;

/**
 * LoadBalance
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
public interface LoadBalance {

    /**
     * Select an instance based on the load balancing algorithm
     *
     * @author Easley
     * @date 2021/1/31
     * @since 1.0
     */
    ServiceInstance chooseOne(List<ServiceInstance> instances);
}
