package com.carryjey.social.service.inf;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.carryjey.social.model.User;

import java.util.List;

/**
 * Created by CarryJey on 2019-04-11.
 */
public interface UserService {

    // 根据用户名查询用户，用于获取用户的信息比对密码
    User selectByUsername(String username);

    // 注册创建用户
    User addUser(String username, String password, String mail);

    // 根据用户token查询用户
    User selectByToken(String token);

    User selectByUserId(long userId);

    // 查询用户积分榜
    List<User> selectTop(Integer limit);

    void update(User user);

    void updateAvatar(User user);

    IPage<User> selectAll(Integer pageNo);

    // 删除用户
    void deleteUser(Integer id);
}
