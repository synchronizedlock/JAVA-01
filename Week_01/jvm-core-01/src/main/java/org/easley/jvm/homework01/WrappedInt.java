package org.easley.jvm.homework01;

/**
 * 封装了基础运算的int包装类
 * 参照了{@link java.math.BigDecimal}的设计结构
 *
 * @author Easley
 * @date 2021/1/13
 * @since 1.0
 */
public class WrappedInt {

    private final int val;

    public WrappedInt(int val) {
        this.val = val;
    }

    public WrappedInt add(WrappedInt augend) {
        return new WrappedInt(val + augend.val);
    }

    public WrappedInt subtract(WrappedInt subtrahend) {
        return new WrappedInt(val - subtrahend.val);
    }

    public WrappedInt multiply(WrappedInt multiplicand) {
        return new WrappedInt(val * multiplicand.val);
    }

    public WrappedInt divide(WrappedInt divisor) {
        return new WrappedInt(val / divisor.val);
    }

    public WrappedInt remainder(WrappedInt divisor) {
        return new WrappedInt(val % divisor.val);
    }

    public int getVal() {
        return val;
    }
}
