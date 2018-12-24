package com.carryjey.social.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.carryjey.social.mapper.CodeMapper;
import com.carryjey.social.model.Code;
import com.carryjey.social.util.DateUtil;
import com.carryjey.social.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author CarryJey
 * @since 2018/12/17
 */
@Service
@Transactional
public class CodeService {

    @Autowired
    private CodeMapper codeMapper;

    @Autowired
    private EmailService emailUtil;

    // 递归生成code，防止code重复
    private String generateToken() {
        String _code = StringUtil.randomString(6);
        Code code = this.selectByCode(_code);
        if (code != null) {
            return this.generateToken();
        }
        return _code;
    }

    public Code selectByCode(String _code) {
        QueryWrapper<Code> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Code::getCode, _code);
        return codeMapper.selectOne(wrapper);
    }

    // 查询没有用过的code
    public Code selectNotUsedCode(long userId, String email) {
        QueryWrapper<Code> wrapper = new QueryWrapper<>();
        wrapper
            .lambda()
            .eq(Code::getEmail, email)
            .eq(Code::getUserId, userId)
            .eq(Code::getUsed, false)
            .gt(Code::getExpireTime, new Date());
        return codeMapper.selectOne(wrapper);
    }

    // 创建一条验证码记录
    public Code createCode(long userId, String email) {
        Code code = this.selectNotUsedCode(userId, email);
        if (code == null) {
            code = new Code();
            String _code = generateToken();
            code.setUserId(userId);
            code.setCode(_code);
            code.setEmail(email);
            code.setInTime(new Date());
            code.setExpireTime(DateUtil.getMinuteAfter(new Date(), 30));
            codeMapper.insert(code);
        }
        return code;
    }

    // 验证邮箱验证码
    public Code validateCode(long userId, String email, String _code) {
        QueryWrapper<Code> wrapper = new QueryWrapper<>();
        wrapper
            .lambda()
            .eq(Code::getCode, _code)
            .eq(Code::getEmail, email)
            .eq(Code::getUserId, userId)
            .eq(Code::getUsed, false)
            .gt(Code::getExpireTime, new Date());
        return codeMapper.selectOne(wrapper);
    }

    // 发送邮件
    public boolean sendEmail(long userId, String email) {
        Code code = this.createCode(userId, email);
        // 发送邮件
        return emailUtil.sendEmail(email, "邮箱验证码-CarryJey", createContent(code));
    }

    public void update(Code code) {
        codeMapper.updateById(code);
    }

    //生成邮箱验证码内容页
    public String createContent(Code code) {
        StringBuilder content =
            new StringBuilder(
                "<div class=\"wrap\">\n"
                    + "     <p>亲爱的朋友，您好：</p>\n"
                    + "     <p>您的验证码是：<h2 class=\"code\">");
        content.append(code.getCode());
        content.append("</h2>\n" + " </p>\n" + " </div>\n" + " <p>验证码有效期为30min，请在30min时间内使用！</p>\n" + "</div>\n");
        return content.toString();
    }
}
