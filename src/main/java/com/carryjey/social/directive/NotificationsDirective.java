package com.carryjey.social.directive;

import com.carryjey.social.service.inf.NotificationService;
import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author CarryJey
 * @since 2018/12/18
 */
@Component
public class NotificationsDirective implements TemplateDirectiveModel {

    @Autowired
    private NotificationService notificationService;

    /**
     * 事实通知渲染
     */
    @Override
    public void execute(
        Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody)
        throws TemplateException, IOException {
        long userId = Long.valueOf(map.get("userId").toString());
        Boolean read = Integer.parseInt(map.get("read").toString()) == 1;
        // 如果想查询所有的消息，limit 传一个负数就可以了 比如 -1
        Integer limit = Integer.parseInt(map.get("limit").toString());
        List<Map<String, Object>> notifications = notificationService.selectByUserId(userId, read, limit);

        DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_28);
        environment.setVariable("notifications", builder.build().wrap(notifications));
        templateDirectiveBody.render(environment.getOut());
    }
}
