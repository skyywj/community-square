package com.carryjey.social.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author CarryJey
 * @since 2018/12/18
 */
@Controller
@RequestMapping("/oauth")
public class OAuthController extends BaseController {

    // 使用github联合登录
    @GetMapping("/github")
    public String github() {
        // TODO
        return null;
    }

    // github联合登录后的回调
    @GetMapping("/github/callback")
    public String callback() {
        // TODO
        return null;
    }
}
