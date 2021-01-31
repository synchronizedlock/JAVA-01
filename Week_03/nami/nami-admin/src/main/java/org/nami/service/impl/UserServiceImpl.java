package org.nami.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.nami.constants.AdminConstants;
import org.nami.dto.Payload;
import org.nami.dto.UserDTO;
import org.nami.entity.User;
import org.nami.enums.NamiExceptionEnum;
import org.nami.exception.NamiException;
import org.nami.mapper.UserMapper;
import org.nami.service.UserService;
import org.nami.utils.JwtUtils;
import org.nami.utils.StringTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * UserServiceImpl
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserMapper userMapper;

    @Value("${nami.user-password-salt}")
    private String salt;

    @Override
    public void add(UserDTO userDTO) {
        User oldOne = queryByName(userDTO.getUserName());
        if (oldOne != null) {
            throw new NamiException("the userName already exist");
        }

        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setPassword(StringTools.md5Digest(userDTO.getPassword(), salt));
        user.setCreateTime(LocalDateTime.now());
        userMapper.insert(user);
    }

    @Override
    public void login(UserDTO userDTO, HttpServletResponse response) {
        User user = queryByName(userDTO.getUserName());
        if (user == null) {
            throw new NamiException(NamiExceptionEnum.LOGIN_ERROR);
        }

        String pwd = StringTools.md5Digest(userDTO.getPassword(), salt);
        if (!pwd.equals(user.getPassword())) {
            throw new NamiException(NamiExceptionEnum.LOGIN_ERROR);
        }

        Payload payLoad = new Payload(user.getId(), user.getUserName());
        try {
            String token = JwtUtils.generateToken(payLoad);
            Cookie cookie = new Cookie(AdminConstants.TOKEN_NAME, token);
            cookie.setHttpOnly(true);
            // 30min
            cookie.setMaxAge(30 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (Exception e) {
            LOGGER.error("login error", e);
        }
    }

    private User queryByName(String userName) {
        QueryWrapper<User> wrapper = Wrappers.query();
        wrapper.lambda().eq(User::getUserName, userName);
        return userMapper.selectOne(wrapper);
    }
}
