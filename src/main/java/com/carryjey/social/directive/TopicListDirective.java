package com.carryjey.social.directive;

import com.baomidou.mybatisplus.core.metadata.IPage;
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
import java.util.Map;

/**
 * @author CarryJey
 * @since 2018/12/18
 */
@Component
public class TopicListDirective implements TemplateDirectiveModel {

    @Autowired
    private TopicService topicService;

    /**
     * 分页拉取最新话题列表
     */
    @Override
    public void execute(
        Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody)
        throws TemplateException, IOException {
        Integer pageNo = Integer.parseInt(map.get("pageNo").toString());
        String tab = map.get("tab").toString();
        IPage<Map<String, Object>> page = topicService.selectAll(pageNo, tab);

        DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_28);
        environment.setVariable("page", builder.build().wrap(page));
        templateDirectiveBody.render(environment.getOut());
    }
}
