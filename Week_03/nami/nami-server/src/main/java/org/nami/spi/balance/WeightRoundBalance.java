package org.nami.spi.balance;

import org.nami.annotation.EnableLoadBalance;
import org.nami.constants.LoadBalanceConstants;
import org.nami.entity.ServiceInstance;
import org.nami.spi.LoadBalance;

import java.util.List;

/**
 * Weight
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@EnableLoadBalance(LoadBalanceConstants.WEIGHT_ROUND)
public class WeightRoundBalance implements LoadBalance {

    private volatile int index;

    @Override
    public synchronized ServiceInstance chooseOne(List<ServiceInstance> instances) {
        int allWeight = instances.stream().mapToInt(ServiceInstance::getWeight).sum();
        int number = (index++) % allWeight;
        for (ServiceInstance instance : instances) {
            if (instance.getWeight() > number) {
                return instance;
            }
            number -= instance.getWeight();
        }
        return null;
    }
}
