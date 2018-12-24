package com.carryjey.social.controller.api;

import com.carryjey.social.exception.ApiAssert;
import com.carryjey.social.model.Code;
import com.carryjey.social.model.User;
import com.carryjey.social.service.CodeService;
import com.carryjey.social.service.SystemConfigService;
import com.carryjey.social.service.UserService;
import com.carryjey.social.util.FileUtil;
import com.carryjey.social.util.Result;
import com.carryjey.social.util.StringUtil;
import com.carryjey.social.util.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
/**
 * @author yanwenjie
 * @since 2018/12/17
 */
@RestController
@RequestMapping("/api/settings")
public class SettingsApiController extends BaseApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private CodeService codeService;

    @Autowired
    private FileUtil fileUtil;

    // 更新用户个人信息
    @PostMapping("/update")
    public Result update(
        String githubName,
        String telegramName,
        String website,
        String bio,
        @RequestParam(defaultValue = "0") Boolean emailNotification,
        HttpSession session) {
        // 查询当前用户的最新信息
        User user = userService.selectByUserId(getUser().getUserId());
        user.setGithubName(githubName);
        user.setTelegramName(telegramName);
        user.setWebsite(website);
        user.setBio(bio);
        user.setEmailNotification(emailNotification);
        userService.update(user);
        //更新session里用户信息
        if (session != null) {
            session.setAttribute("_user", user);
        }
        return success();
    }

    // 发送邮箱验证码
    @GetMapping("/sendEmailCode")
    public Result sendEmailCode(String email) {
        ApiAssert.notEmpty(email, "请输入邮箱 ");
        ApiAssert.isTrue(StringUtil.check(email, StringUtil.emailRegex), "邮箱格式不正确");
        if (codeService.sendEmail(getUser().getUserId(), email)) {
            return success();
        } else {
            return error("邮件发送失败，也可能是站长没有配置邮箱");
        }
    }

    // 更新用户邮箱
    @PostMapping("/updateEmail")
    public Result updateEmail(String email, String code, HttpSession session) {
        ApiAssert.notEmpty(email, "请输入邮箱 ");
        ApiAssert.isTrue(StringUtil.check(email, StringUtil.emailRegex), "邮箱格式不正确");
        Code code1 = codeService.validateCode(getUser().getUserId(), email, code);
        if (code1 == null) {
            return error("验证码错误");
        }
        // 将code的状态置为已用
        code1.setUsed(true);
        codeService.update(code1);
        // 查询当前用户的最新信息
        User user = userService.selectByUserId(getUser().getUserId());
        user.setEmail(email);
        userService.update(user);
        if (session != null) {
            session.setAttribute("_user", user);
        }
        return success();
    }

    // 上传头像
    @PostMapping("/uploadAvatar")
    public Result uploadAvatar(@RequestParam("file") MultipartFile file, HttpSession session) {
        long size = file.getSize();
        int uploadAvatarSizeLimit =
            Integer.parseInt(systemConfigService.selectAllConfig().get("uploadAvatarSizeLimit").toString());
        if (size > uploadAvatarSizeLimit * 1024 * 1024) {
            return error("文件太大了，请上传文件大小在 " + uploadAvatarSizeLimit + "MB 以内");
        }
        // 拿到上传后访问的url
        String url = fileUtil.upload(file, "avatar", "avatar/" + getUser().getUsername());
        if (url == null) {
            return error("上传的文件不存在或者上传过程发生了错误，请重试一下");
        }
        // 查询当前用户的最新信息
        User user = userService.selectByUserId(getUser().getUserId());
        user.setAvatar(url);
        // 保存用户新的头像
        userService.updateAvatar(user);
        // 将最新的用户信息更新在session里
        if (session != null) {
            session.removeAttribute("_user");
            session.setAttribute("_user", user);
        }
        return success(url);
    }

    // 修改密码
    @PostMapping("/updatePassword")
    public Result updatePassword(String oldPassword, String newPassword, HttpSession session) {
        ApiAssert.notEmpty(oldPassword, "请输入旧密码");
        ApiAssert.notEmpty(newPassword, "请输入新密码");
        ApiAssert.notTrue(oldPassword.equals(newPassword), "新密码怎么还是旧的？");
        ApiAssert.isTrue(new BCryptPasswordEncoder().matches(oldPassword, getUser().getPassword()), "旧密码不正确");
        // 查询当前用户的最新信息
        User user = userService.selectByUserId(getUser().getUserId());
        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        userService.update(user);
        // 将最新的用户信息更新在session里
        if (session != null) {
            session.setAttribute("_user", user);
        }
        return success();
    }
}
