package com.carryjey.social.controller.api;

import com.carryjey.social.exception.ApiAssert;
import com.carryjey.social.model.User;
import com.carryjey.social.service.SystemConfigService;
import com.carryjey.social.service.UserService;
import com.carryjey.social.util.CookieUtil;
import com.carryjey.social.util.FileUtil;
import com.carryjey.social.util.Result;
import com.carryjey.social.util.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author CarryJey
 * @since 2018/12/18
 */
@RestController
@RequestMapping("/api")
public class IndexApiController extends BaseApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private CookieUtil cookieUtil;

    @Autowired
    private FileUtil fileUtil;

    // 处理登录的接口
    @PostMapping("/login")
    public Result login(String username, String password, HttpSession session) {
        ApiAssert.notEmpty(username, "请输入用户名");
        ApiAssert.notEmpty(password, "请输入密码");
        User user = userService.selectByUsername(username);
        ApiAssert.notNull(user, "用户不存在");
        ApiAssert.isTrue(new BCryptPasswordEncoder().matches(password, user.getPassword()), "用户名或密码不正确");
        // 将用户信息写session
        if (session != null) {
            session.setAttribute("_user", user);
        }
        // 将用户token写cookie
        cookieUtil.setCookie(systemConfigService.selectAllConfig().get("cookie.name").toString(), user.getToken());
        return success(user);
    }

    // 处理注册的接口
    @PostMapping("/register")
    public Result register(String username, String password, HttpSession session) {
        ApiAssert.notEmpty(username, "请输入用户名");
        ApiAssert.notEmpty(password, "请输入密码");
        User user = userService.selectByUsername(username);
        ApiAssert.isNull(user, "用户名已存在");
        user = userService.addUser(username, password);
        // 将用户信息写session
        if (session != null) {
            session.setAttribute("_user", user);
        }
        // 将用户token写cookie
        cookieUtil.setCookie(systemConfigService.selectAllConfig().get("cookie.name").toString(), user.getToken());
        return success(user);
    }
}
