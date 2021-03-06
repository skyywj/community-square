package com.carryjey.social.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.carryjey.social.mapper.RolePermissionMapper;
import com.carryjey.social.model.RolePermission;
import com.carryjey.social.service.inf.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author CarryJey
 * @since 2018/12/17
 */
@Service
@Transactional
public class RolePermissionServiceImpl implements RolePermissionService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    // 根据角色id查询所有的角色权限关联记录
    @Override
    public List<RolePermission> selectByRoleId(Integer roleId) {
        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(RolePermission::getRoleId, roleId);
        return rolePermissionMapper.selectList(wrapper);
    }

    // 根据角色id删除关联关系
    @Override
    public void deleteByRoleId(Integer roleId) {
        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(RolePermission::getRoleId, roleId);
        rolePermissionMapper.delete(wrapper);
    }

    // 根据权限id删除关联关系
    @Override
    public void deleteByPermissionId(Integer permissionId) {
        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(RolePermission::getPermissionId, permissionId);
        rolePermissionMapper.delete(wrapper);
    }

    @Override
    public void insert(RolePermission rolePermission) {
        rolePermissionMapper.insert(rolePermission);
    }
}
