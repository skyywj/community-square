package com.carryjey.social.service.inf;

import com.carryjey.social.model.RolePermission;

import java.util.List;

/**
 * Created by CarryJey on 2019-04-11.
 */
public interface RolePermissionService {
    // 根据角色id查询所有的角色权限关联记录
    List<RolePermission> selectByRoleId(Integer roleId);

    // 根据角色id删除关联关系
    void deleteByRoleId(Integer roleId);

    // 根据权限id删除关联关系
    void deleteByPermissionId(Integer permissionId);

    void insert(RolePermission rolePermission);
}
