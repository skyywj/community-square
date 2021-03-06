package com.carryjey.social.controller.admin;

import com.carryjey.social.service.inf.SystemConfigService;
import com.carryjey.social.util.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * @author CarryJey
 * @since 2018/12/18
 */
@Controller
@RequestMapping("/admin/system")
public class SystemConfigAdminController extends BaseAdminController {

    @Autowired
    private SystemConfigService systemConfigService;

    @RequiresPermissions("system:edit")
    @GetMapping("/edit")
    public String edit(Model model) {
        model.addAttribute("systems", systemConfigService.selectAll());
        return "admin/system/edit";
    }

    @RequiresPermissions("system:edit")
    @PostMapping("/edit")
    @ResponseBody
    public Result edit(String[] key, String[] value) throws IOException {
        if (key.length != value.length) {
            return error("数据异常");
        }
        systemConfigService.update(key, value);
        return success();
    }
}
