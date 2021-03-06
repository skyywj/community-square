package com.carryjey.social.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.carryjey.social.Dao.UserDao;
import com.carryjey.social.mapper.UserMapper;
import com.carryjey.social.model.User;
import com.carryjey.social.service.inf.NotificationService;
import com.carryjey.social.service.inf.SystemConfigService;
import com.carryjey.social.service.inf.TopicService;
import com.carryjey.social.service.inf.UserService;
import com.carryjey.social.util.bcrypt.BCryptPasswordEncoder;
import com.carryjey.social.util.identicon.Identicon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * @author CarryJey
 * @since 2018/12/17
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CollectServiceImpl collectService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private Identicon identicon;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private UserDao userDao;

    private SnowflakeIdWorker snowflakeIdWorker = new IdService().createUserId();

    // 根据用户名查询用户，用于获取用户的信息比对密码
    @Override
    public User selectByUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getUsername, username);
        return userMapper.selectOne(wrapper);
    }

    // 递归生成token，防止token重复
    // 理论上uuid生成的token是不可能重复的
    // 加个逻辑放心 : )
    private String generateToken() {
        String token = UUID.randomUUID().toString();
        User user = this.selectByToken(token);
        if (user != null) {
            return this.generateToken();
        }
        return token;
    }

    // 注册创建用户
    @Override
    public User addUser(String username, String password, String mail) {
        String token = this.generateToken();
        User user = new User();
        user.setUserId(snowflakeIdWorker.nextId());
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setToken(token);
        user.setAvatar(identicon.generator(username));
        user.setEmail(mail);
        user.setCreatedTime(System.currentTimeMillis());
        user.setUpdatedTime(System.currentTimeMillis());
        userMapper.insert(user);
        // 再查一下，有些数据库里默认值保存后，类里还是null
        return this.selectByUserId(user.getUserId());
    }

    // 根据用户token查询用户
    @Override
    public User selectByToken(String token) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getToken, token);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public User selectByUserId(long userId) {
        return userDao.getByUserId(userId);
    }

    // 查询用户积分榜
    @Override
    public List<User> selectTop(Integer limit) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("score").last("limit " + limit);
        return userMapper.selectList(wrapper);
    }

    @Override
    public void update(User user) {
        userMapper.updateById(user);
    }

    @Override
    public void updateAvatar(User user) {
        userDao.updateAvater(user);
    }

    // ------------------------------- admin ------------------------------------------
    @Override
    public IPage<User> selectAll(Integer pageNo) {
        Page<User> page =
                new Page<>(pageNo, Integer.parseInt((String) systemConfigService.selectAllConfig().get("pageSize")));
        page.setDesc("created_time");
        return userMapper.selectPage(page, null);
    }

    // 删除用户
    @Override
    public void deleteUser(Integer id) {
        // 删除用户的通知
        notificationService.deleteByUserId(id);
        // 删除用户的收藏
        collectService.deleteByUserId(id);
        // 删除用户发的帖子
        topicService.deleteByUserId(id);
        // 删除用户发的评论
        commentService.deleteByUserId(id);
        // 删除用户本身
        userMapper.deleteById(id);
    }
}
