package com.carryjey.social.service.inf;

import com.carryjey.social.model.Code;

/**
 * Created by CarryJey on 2019-04-11.
 */
public interface CodeService {

    Code selectByCode(String _code);

    // 查询没有用过的code
    Code selectNotUsedCode(long userId, String email);

    // 创建一条验证码记录
    Code createCode(long userId, String email);

    // 验证邮箱验证码
    Code validateCode(long userId, String email, String _code);

    // 发送邮件
    boolean sendEmail(long userId, String email);

    void update(Code code);

    //生成邮箱验证码内容页
    String createContent(Code code);
}
