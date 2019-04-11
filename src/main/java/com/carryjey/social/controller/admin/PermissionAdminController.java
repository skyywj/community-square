package com.carryjey.social.controller.admin;

import com.carryjey.social.model.Permission;
import com.carryjey.social.service.inf.PermissionService;
import com.carryjey.social.util.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author CarryJey
 * @since 2018/12/18
 */
@Controller
@RequestMapping("/admin/permission")
public class PermissionAdminController extends BaseAdminController {

    @Autowired
    private PermissionService permissionService;

    @RequiresPermissions("permission:list")
    @GetMapping("/list")
    public String list(Integer pid, Model model) {
        List<Permission> permissions = permissionService.selectByPid(0);
        model.addAttribute("permissions", permissions);
        List<Permission> childPermissions;
        if (pid == null || pid == 0) {
            childPermissions = permissionService.selectByPid(permissions.get(0).getId());
        } else {
            childPermissions = permissionService.selectByPid(pid);
        }
        model.addAttribute("childPermissions", childPermissions);
        model.addAttribute("pid", pid);
        return "admin/permission/list";
    }

    @RequiresPermissions("permission:add")
    @PostMapping("/add")
    @ResponseBody
    public Result add(Permission permission) {
        permission = permissionService.insert(permission);
        return success(permission.getPid() == 0 ? permission.getId() : permission.getPid());
    }

    @RequiresPermissions("permission:edit")
    @PostMapping("/edit")
    @ResponseBody
    public Result edit(Permission permission) {
        permission = permissionService.update(permission);
        return success(permission.getPid() == 0 ? permission.getId() : permission.getPid());
    }

    @RequiresPermissions("permission:delete")
    @GetMapping("/delete")
    @ResponseBody
    public Result delete(Integer id) {
        permissionService.delete(id);
        return success();
    }
}
