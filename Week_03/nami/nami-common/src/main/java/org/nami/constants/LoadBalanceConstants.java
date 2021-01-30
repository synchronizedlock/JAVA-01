package org.nami.constants;

/**
 * LoadBalanceConstants
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
public interface LoadBalanceConstants {

    /**
     * 随机
     */
    String RANDOM = "random";

    /**
     * 轮询
     */
    String ROUND = "round";

    /**
     * 加权轮询
     */
    String WEIGHT_ROUND = "weightRound";

    /**
     * 平滑加权轮询
     */
     String SMOOTH_WEIGHT_ROUND = "smoothWeightRound";
}
