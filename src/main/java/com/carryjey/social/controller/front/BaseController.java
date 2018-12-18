package com.carryjey.social.controller.front;

import com.carryjey.social.model.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author CarryJey
 * @since 2018/12/18
 */
public class BaseController {

    protected String redirect(String path) {
        return "redirect:" + path;
    }

    protected User getUser() {
        HttpServletRequest request =
            ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getRequest();
        HttpSession session = request.getSession();
        return (User) session.getAttribute("_user");
    }
}
