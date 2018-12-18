package com.carryjey.social.controller.admin;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author CarryJey
 * @since 2018/12/18
 */
@Controller
@RequestMapping("/admin")
public class IndexAdminController extends BaseAdminController {

    @RequiresAuthentication
    @GetMapping({"/", "/index"})
    public String index() {
        return "admin/index";
    }
}
