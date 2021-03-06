package com.carryjey.social.directive;

import com.carryjey.social.model.Topic;
import com.carryjey.social.service.inf.TopicService;
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
public class OtherTopicDirective implements TemplateDirectiveModel {

    @Autowired
    private TopicService topicService;

    @Override
    public void execute(
        Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody)
        throws TemplateException, IOException {
        long userId = Long.valueOf(map.get("userId").toString());
        Integer topicId = Integer.parseInt(map.get("topicId").toString());
        Integer limit = Integer.parseInt(map.get("limit").toString());
        List<Topic> topics = topicService.selectAuthorOtherTopic(userId, topicId, limit);

        DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_28);
        environment.setVariable("topics", builder.build().wrap(topics));
        templateDirectiveBody.render(environment.getOut());
    }
}
