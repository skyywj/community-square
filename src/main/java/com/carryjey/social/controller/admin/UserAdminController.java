package com.carryjey.social.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.carryjey.social.model.User;
import com.carryjey.social.service.UserService;
import com.carryjey.social.util.Result;
import com.carryjey.social.util.StringUtil;
import com.carryjey.social.util.bcrypt.BCryptPasswordEncoder;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author CarryJey
 * @since 2018/12/18
 */
@Controller
@RequestMapping("/admin/user")
public class UserAdminController extends BaseAdminController {

    @Autowired
    private UserService userService;

    // 前台用户管理
    @RequiresPermissions("user:list")
    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "1") Integer pageNo, Model model) {
        IPage<User> iPage = userService.selectAll(pageNo);
        model.addAttribute("page", iPage);
        return "admin/user/list";
    }

    // 编辑用户
    @RequiresPermissions("user:edit")
    @GetMapping("/edit")
    public String edit(Integer id, Model model) {
        model.addAttribute("user", userService.selectById(id));
        return "admin/user/edit";
    }

    // 保存编辑后的用户信息
    @RequiresPermissions("user:edit")
    @PostMapping("/edit")
    @ResponseBody
    public Result update(User user) {
        // 如果密码不为空，给加密一下再保存
        if (!StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        } else {
            user.setPassword(null);
        }
        // 如果邮件接收通知没有勾选，user对象里的属性就是null，手动设置成false
        if (user.getEmailNotification() == null) {
            user.setEmailNotification(false);
        }
        userService.update(user);
        return success();
    }

    // 删除用户
    @RequiresPermissions("user:delete")
    @GetMapping("/delete")
    @ResponseBody
    public Result delete(Integer id) {
        userService.deleteUser(id);
        return success();
    }

    // 刷新token
    @RequiresPermissions("user:refresh_token")
    @GetMapping("/refreshToken")
    @ResponseBody
    public Result refreshToken(Integer id) {
        User user = userService.selectById(id);
        user.setToken(StringUtil.uuid());
        userService.update(user);
        return success(user);
    }
}
