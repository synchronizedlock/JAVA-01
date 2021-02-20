package org.easley.spring.beans;

/**
 * Girl
 *
 * @author Easley
 * @date 2021/2/19
 * @since 1.0
 */
public interface Girl {

    /**
     * 获取妹子的名字
     *
     * @return 妹子的名字
     * @author Easley
     * @date 2021/2/19
     * @since 1.0
     */
    String getGirlName();

    /**
     * 获取妹子的微信号
     *
     * @return 妹子的微信号
     * @author Easley
     * @date 2021/2/19
     * @since 1.0
     */
    String getWeChat();

    /**
     * 聊天获取亲密度
     *
     * @param name 名字
     * @param wechat 微信号
     * @param gently 是否礼貌温柔
     * @return 亲密度
     * @author Easley
     * @date 2021/2/20
     * @since 1.0
     */
    int chat(String name, String wechat, boolean gently);

    /**
     * 求婚
     *
     * @param annualSalary 年薪
     * @param houseNum 房子套数
     * @param carNum 车子数
     * @param bankAccountBalance 银行账户余额
     * @param intimacy 亲密度
     * @return 彳亍不彳亍
     * @author Easley
     * @date 2021/2/20
     * @since 1.0
     */
    boolean makeAProposal(int annualSalary, int houseNum, int carNum, int bankAccountBalance, int intimacy);
}
