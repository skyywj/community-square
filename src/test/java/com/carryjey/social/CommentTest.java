package com.carryjey.social;

import com.carryjey.social.controller.front.TopicController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;

import java.util.Collection;
import java.util.Map;

/**
 * @author CarryJey
 * @since 2018/12/19
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Component
public class CommentTest {
    @Autowired
    private TopicController topicController;

    @Test
    public void testGetCommont() {
        Model model =
            new Model() {
                @Override
                public Model addAttribute(String s, Object o) {
                    return null;
                }

                @Override
                public Model addAttribute(Object o) {
                    return null;
                }

                @Override
                public Model addAllAttributes(Collection<?> collection) {
                    return null;
                }

                @Override
                public Model addAllAttributes(Map<String, ?> map) {
                    return null;
                }

                @Override
                public Model mergeAttributes(Map<String, ?> map) {
                    return null;
                }

                @Override
                public boolean containsAttribute(String s) {
                    return false;
                }

                @Override
                public Map<String, Object> asMap() {
                    return null;
                }
            };
        topicController.detail(1, model);
    }
}
