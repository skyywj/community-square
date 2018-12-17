package co.yiiu.pybbs.controller.admin;

import co.yiiu.pybbs.model.Tag;
import co.yiiu.pybbs.model.Topic;
import co.yiiu.pybbs.service.TagService;
import co.yiiu.pybbs.service.TopicService;
import co.yiiu.pybbs.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
@RequestMapping("/admin/topic")
public class TopicAdminController extends BaseAdminController {

  @Autowired
  private TopicService topicService;
  @Autowired
  private TagService tagService;

  @RequiresPermissions("topic:list")
  @GetMapping("/list")
  public String list(@RequestParam(defaultValue = "1") Integer pageNo, String startDate, String endDate,
                     String username, Model model) {
    if (StringUtils.isEmpty(startDate)) startDate = null;
    if (StringUtils.isEmpty(endDate)) endDate = null;
    if (StringUtils.isEmpty(username)) username = null;
    IPage<Map<String, Object>> page = topicService.selectAllForAdmin(pageNo, startDate, endDate, username);
    model.addAttribute("page", page);
    model.addAttribute("startDate", startDate);
    model.addAttribute("endDate", endDate);
    model.addAttribute("username", username);
    return "admin/topic/list";
  }

  @RequiresPermissions("topic:edit")
  @GetMapping("/edit")
  public String edit(Integer id, Model model) {
    model.addAttribute("topic", topicService.selectById(id));
    // 将标签集合转成逗号隔开的字符串
    List<Tag> tagList = tagService.selectByTopicId(id);
    String tags = StringUtils.collectionToCommaDelimitedString(tagList.stream().map(Tag::getName).collect(Collectors.toList()));
    model.addAttribute("tags", tags);
    return "admin/topic/edit";
  }

  @RequiresPermissions("topic:edit")
  @PostMapping("edit")
  @ResponseBody
  public Result update(Integer id, String title, String content, String tags) {
    Topic topic = topicService.selectById(id);
    topicService.updateTopic(topic, title, content, tags);
    return success();
  }

  @RequiresPermissions("topic:good")
  @GetMapping("/good")
  @ResponseBody
  public Result good(Integer id) {
    Topic topic = topicService.selectById(id);
    topic.setGood(!topic.getGood());
    topicService.update(topic);
    return success();
  }

  @RequiresPermissions("topic:top")
  @GetMapping("/top")
  @ResponseBody
  public Result top(Integer id) {
    Topic topic = topicService.selectById(id);
    topic.setTop(!topic.getTop());
    topicService.update(topic);
    return success();
  }

  @RequiresPermissions("topic:delete")
  @GetMapping("/delete")
  @ResponseBody
  public Result delete(Integer id) {
    Topic topic = topicService.selectById(id);
    topicService.delete(topic, null);
    return success();
  }
}
