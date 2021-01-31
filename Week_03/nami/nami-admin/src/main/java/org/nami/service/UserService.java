package org.nami.service;

import org.nami.dto.UserDTO;

import javax.servlet.http.HttpServletResponse;

/**
 * UserService
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
public interface UserService {

    void add(UserDTO userDTO);

    void login(UserDTO userDTO, HttpServletResponse response);
}
