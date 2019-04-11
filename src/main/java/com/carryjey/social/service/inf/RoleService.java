package com.carryjey.social.service.inf;

import com.carryjey.social.model.Role;

import java.util.List;

/**
 * Created by CarryJey on 2019-04-11.
 */
public interface RoleService {
    Role selectById(Integer roleId);

    List<Role> selectAll();

    void insert(String name, Integer[] permissionIds);

    void update(Integer id, String name, Integer[] permissionIds);

    void delete(Integer id);
}
