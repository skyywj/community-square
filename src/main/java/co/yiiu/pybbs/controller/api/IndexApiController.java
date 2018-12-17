package co.yiiu.pybbs.controller.api;

import co.yiiu.pybbs.exception.ApiAssert;
import co.yiiu.pybbs.model.User;
import co.yiiu.pybbs.service.SystemConfigService;
import co.yiiu.pybbs.service.UserService;
import co.yiiu.pybbs.util.CookieUtil;
import co.yiiu.pybbs.util.FileUtil;
import co.yiiu.pybbs.util.Result;
import co.yiiu.pybbs.util.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
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
    if (session != null) session.setAttribute("_user", user);
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
    if (session != null) session.setAttribute("_user", user);
    // 将用户token写cookie
    cookieUtil.setCookie(systemConfigService.selectAllConfig().get("cookie.name").toString(), user.getToken());
    return success(user);
  }

}
