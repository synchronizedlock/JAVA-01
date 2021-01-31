package org.nami.spi.balance;

import org.nami.annotation.EnableLoadBalance;
import org.nami.constants.LoadBalanceConstants;
import org.nami.entity.ServiceInstance;
import org.nami.spi.LoadBalance;

import java.security.SecureRandom;
import java.util.List;

/**
 * RANDOM
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@EnableLoadBalance(LoadBalanceConstants.RANDOM)
public class RandomBalance implements LoadBalance {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    @Override
    public ServiceInstance chooseOne(List<ServiceInstance> instances) {
        return instances.get(SECURE_RANDOM.nextInt(instances.size()));
    }
}
