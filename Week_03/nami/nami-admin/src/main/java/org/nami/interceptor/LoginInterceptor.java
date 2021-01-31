package org.nami.interceptor;

import org.nami.constants.AdminConstants;
import org.nami.dto.Payload;
import org.nami.enums.NamiExceptionEnum;
import org.nami.exception.NamiException;
import org.nami.utils.JwtUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * LoginInterceptor
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            response.sendRedirect("/user/login/page");
            return false;
        }

        String token = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(AdminConstants.TOKEN_NAME)) {
                token = cookie.getValue();
            }
        }
        if (StringUtils.isEmpty(token)) {
            response.sendRedirect("/user/login/page");
            return false;
        }

        boolean result = JwtUtils.checkSignature(token);
        if (!result) {
            throw new NamiException(NamiExceptionEnum.TOKEN_ERROR);
        }
        Payload payLoad = JwtUtils.getPayload(token);
        request.setAttribute("currUser", payLoad.getName());
        return true;
    }
}
