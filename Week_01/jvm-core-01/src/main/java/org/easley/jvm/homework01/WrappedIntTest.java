package org.easley.jvm.homework01;

/**
 * WrappedInt测试类
 *
 * @author Easley
 * @date 2021/1/13
 * @since 1.0
 */
public class WrappedIntTest {
    private static final WrappedInt[] NUMS = {new WrappedInt(1), new WrappedInt(2), new WrappedInt(3)};

    public static void main(String[] args) {
        WrappedInt sum = new WrappedInt(0);
        for (WrappedInt num : NUMS) {
            // 计算和
            sum = sum.add(num);
        }

        // 作为后续运算参数的操作数
        WrappedInt operand = new WrappedInt(4);
        if (sum.getVal() > operand.getVal()) {
            // 差值
            WrappedInt difference = sum.subtract(operand);
        }
        // 乘积
        WrappedInt product = sum.multiply(operand);
        // 商
        WrappedInt quotient = sum.divide(operand);
        // 余数
        WrappedInt remainder = sum.remainder(operand);
    }
}
