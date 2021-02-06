package org.easley.solutions;

import org.springframework.util.StopWatch;

/**
 * 抽象的解决方案，由于没引入SpringAOP，
 * 用模版设计模式来分离计时的逻辑
 *
 * @author Easley
 * @date 2021/2/7
 * @since 1.0
 */
public abstract class AbstractSolution {

    protected int result;

    /**
     * 用于生成result的值
     * 其中执行的是业务逻辑
     *
     * @author Easley
     * @date 2021/2/7
     * @since 1.0
     */
    protected abstract void generateResult();

    protected void solute() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        generateResult();

        stopWatch.stop();
        System.out.println("计算结果为：" + result);
        System.out.println("总计耗时：" + stopWatch.getTotalTimeMillis() + " ms.");
    }
}
