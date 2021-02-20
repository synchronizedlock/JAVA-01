package org.easley.spring.beans;

import lombok.Data;

import java.util.List;

/**
 * 偶像团体
 *
 * @author Easley
 * @date 2021/2/20
 * @since 1.0
 */
@Data
public class IdolGroup {

    /**
     * 团队名称
     */
    private String name;

    /**
     * 团队经纪人
     */
    private IdolGroupProducer producer;

    /**
     * 成员列表
     */
    private List<MoeGirl> members;

    @Override
    public String toString() {
        return "IdolGroup{" +
                "name='" + name + '\'' +
                ", producer=" + producer +
                ", members=" + members +
                '}';
    }
}
