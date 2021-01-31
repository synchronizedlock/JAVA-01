package org.nami.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * UpdateWeightDTO
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@Data
public class UpdateWeightDTO {

    @NotNull(message = "实例id不能为空")
    private Integer id;

    @NotNull(message = "权重不能为空")
    private Integer weight;
}
