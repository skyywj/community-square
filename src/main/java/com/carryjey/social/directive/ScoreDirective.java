package com.carryjey.social.directive;

import com.carryjey.social.model.User;
import com.carryjey.social.service.inf.UserService;
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
public class ScoreDirective implements TemplateDirectiveModel {

    @Autowired
    private UserService userService;

    /**
     * 通过实现FreeMarker的TemplateDirectiveModel就在后端实现了一个自定义的宏，
     * 这个宏的功能很简单，只是根据给定的参数将排行数据“users”放到model中去，
     * 然后模板页中就可以使用这个变量了。
     *
     * 无需在各个controller的各个接口中去重复的向model中添加所需的排行数据，
     * 而是当FreeMarker渲染模板页时遇到相应的宏它可以回到后端去调用相应的方法取到所需的数据
     */
    @Override
    public void execute(
        Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody)
        throws TemplateException, IOException {
        Integer limit = Integer.parseInt(map.get("limit").toString());
        if (limit > 100) {
            limit = 100;
        }
        List<User> users = userService.selectTop(limit);

        DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_28);
        environment.setVariable("users", builder.build().wrap(users));
        templateDirectiveBody.render(environment.getOut());
    }
}
