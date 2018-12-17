package com.carryjey.social;

import com.carryjey.social.util.StringUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
/**
 * @author yanwenjie
 * @since 2018/12/17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommunitySquareApplicationTests {

    @Test
    public void contextLoads() {
        String content =
            "@tomoya 在配置中加上两个bean就可以了\n"
                + "\n"
                + "```java\n"
                + "@Bean\n"
                + "public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {\n"
                + "  AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();\n"
                + "  authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);\n"
                + "  return authorizationAttributeSourceAdvisor;\n"
                + "}\n"
                + "\n"
                + "@Bean\n"
                + "@ConditionalOnMissingBean\n"
                + "public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {\n"
                + "  DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();\n"
                + "  defaultAAP.setProxyTargetClass(true);\n"
                + "  return defaultAAP;\n"
                + "}\n"
                + "```\n"
                + " \n"
                + "加上上面@test两个bean就可以在controller上愉快的使用 `@RequiresPermissions({\"topic:list\"})` 了";
        List<String> strings = StringUtil.fetchAtUser(content);
        System.out.println(strings);
    }
}
