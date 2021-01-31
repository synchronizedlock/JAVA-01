package org.nami.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * UserDTO
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@Data
public class UserDTO {

    @NotEmpty(message = "用户名不能为空")
    private String userName;

    @NotEmpty(message = "密码不能为空")
    private String password;
}
