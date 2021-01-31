package org.nami.spi.balance;

import org.nami.annotation.EnableLoadBalance;
import org.nami.constants.LoadBalanceConstants;
import org.nami.entity.ServiceInstance;
import org.nami.spi.LoadBalance;

import java.util.List;

/**
 * Round
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@EnableLoadBalance(LoadBalanceConstants.ROUND)
public class FullRoundBalance implements LoadBalance {

    private volatile int index;

    @Override
    public synchronized ServiceInstance chooseOne(List<ServiceInstance> instances) {
        if (index == instances.size()) {
            index = 0;
        }
        return instances.get(index++);
    }
}
