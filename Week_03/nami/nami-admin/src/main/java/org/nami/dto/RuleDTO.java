package org.nami.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * RuleDTO
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@Data
public class RuleDTO {

    @NotNull(message = "appId不能为空")
    private Integer appId;

    @NotEmpty(message = "name不能为空")
    private String name;

    @NotEmpty(message = "version不能为空")
    private String version;

    @NotEmpty(message = "matchObject不能为空")
    private String matchObject;

    private String matchKey;

    private Byte matchMethod;

    private String matchRule;

    @NotNull(message = "priority不能为空")
    private Integer priority;

    private Byte enabled;
}
