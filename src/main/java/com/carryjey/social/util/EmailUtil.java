package com.carryjey.social.util;

import com.carryjey.social.service.SystemConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

/**
 * Created by tomoya. Copyright (c) 2018, All Rights Reserved. https://yiiu.co
 */
// 这个工具类来自博客：https://www.cnblogs.com/whgk/p/6506027.html
@Component
public class EmailUtil {

    @Autowired
    private SystemConfigService systemConfigService;

    private Session session;
    private Logger log = LoggerFactory.getLogger(EmailUtil.class);
    private String fromMailAddress = null;
    private String authCode = null;
    private String host = null;

    private Session instance() {
        // 如果session已经存在了，就不执行了，直接返回对象
        if (session != null) {
            return session;
        }
        // session为空，判断系统是否配置了邮箱相关的参数，配置了继续，没配置白白
        host = (String) systemConfigService.selectAllConfig().get("mail.host");
        fromMailAddress = (String) systemConfigService.selectAllConfig().get("mail.username");
        authCode = (String) systemConfigService.selectAllConfig().get("mail.auth.code");
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", host); // 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
        props.put("mail.smtp.host", host); // 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
        props.put("mail.smtp.auth", "true"); // 用刚刚设置好的props对象构建一个session
        session = Session.getDefaultInstance(props); // 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
        // 用（你可以在控制台（console)上看到发送邮件的过程）
        session.setDebug(true); // 用session为参数定义消息对象
        return this.session;
    }

    public boolean sendEmail(String toMmail, String title, String content) {
        // 先判断session是否初始化了，没配置直接失败
        if (this.instance() == null) {
            return false;
        }
        //2 加载发件人信息
        Message message = new MimeMessage(this.session);
        try {
            message.setFrom(new InternetAddress(fromMailAddress));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toMmail)); // 加载收件人地址
            message.setSubject(title); // 加载标题
            Multipart multipart = new MimeMultipart(); // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            BodyPart contentPart = new MimeBodyPart(); // 设置邮件的文本内容
            contentPart.setContent(content, "text/html;charset=utf-8");
            multipart.addBodyPart(contentPart);
            message.setContent(multipart);
            message.saveChanges(); // 保存变化
            Transport transport = session.getTransport("smtp"); // 连接服务器的邮箱
            transport.connect("smtp.163.com", fromMailAddress, authCode); // 把邮件发送出去
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            return true;
        } catch (MessagingException e) {
            log.error(e.getMessage());
            return false;
        }
    }
}
