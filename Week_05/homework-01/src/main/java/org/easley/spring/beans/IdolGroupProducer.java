package org.easley.spring.beans;

import lombok.Data;

/**
 * 偶像团队经纪人
 *
 * @author Easley
 * @date 2021/2/20
 * @since 1.0
 */
@Data
public class IdolGroupProducer {

    private String name;

    /**
     * 经纪人自我介绍
     *
     * @author Easley
     * @date 2021/2/20
     * @since 1.0
     */
    public void selfIntroduction() {
        System.out.println("--> 大家好，我是团队的经纪人：" + name + "。");
    }
}
