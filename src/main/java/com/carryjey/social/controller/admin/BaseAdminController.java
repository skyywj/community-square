package com.carryjey.social.controller.admin;

import com.carryjey.social.controller.api.BaseApiController;
import com.carryjey.social.model.AdminUser;
import com.carryjey.social.service.inf.AdminUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author CarryJey
 * @since 2018/12/18
 */
public class BaseAdminController extends BaseApiController {

    @Autowired
    private AdminUserService adminUserService;

    // 可以将传递到controller里的参数中Date类型的从String转成Date类型的对象
    // 这货没有想象中的好用，还不如我手动控制String转Date了
    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }

    protected AdminUser getAdminUser() {
        Subject subject = SecurityUtils.getSubject();
        String principal = (String) subject.getPrincipal();
        return adminUserService.selectByUsername(principal);
    }
}
