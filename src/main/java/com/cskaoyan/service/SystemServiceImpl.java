package com.cskaoyan.service;

import com.cskaoyan.bean.generalize.Storage;
import com.cskaoyan.bean.generalize.StorageExample;
import com.cskaoyan.bean.systemBean.*;
import com.cskaoyan.mapper.*;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    LogMapper logMapper;

    @Autowired
    StorageMapper storageMapper;


    @Autowired
    PermissionMapper permissionMapper;

    @Autowired
    SystemPermissionMapper systemPermissionMapper;

    @Override
    public HashMap<String,Object> adminList(Integer page, Integer limit, String username, String sort, String order) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andDeletedNotEqualTo(true);
        if (username != null){
            criteria.andUsernameLike("%" + username + "%");
        }
        long l = adminMapper.countByExample(adminExample);
        adminExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(page,limit);
        List<Admin> adminList = adminMapper.selectByExample(adminExample);
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("total",Math.toIntExact(l));
        hashMap.put("items",adminList);
        return hashMap;
    }

    @Override
    public List<Admin> adminList() {
        AdminExample adminExample = new AdminExample();
        List<Admin> adminList = adminMapper.selectByExample(adminExample);
        return adminList;
    }

    @Override
    public List<Role> roleOptions() {
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        List<Role> roleList = roleMapper.selectByExample(roleExample);
        return roleList;
    }

    @Override
    public Admin adminCreate(Admin admin) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andUsernameEqualTo(admin.getUsername());
        List<Admin> adminList = adminMapper.selectByExample(adminExample);
        if (adminList.size() > 0){
            return null;
        }
        admin.setDeleted(false);
        admin.setUpdateTime(new Date());
        admin.setAddTime(new Date());
        adminMapper.insert(admin);
        return admin;
    }

    @Override
    public Admin adminUpdate(Admin admin) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andUsernameEqualTo(admin.getUsername());
        List<Admin> adminList = adminMapper.selectByExample(adminExample);
        if (adminList.size() > 0) {
            if (!adminList.get(0).getId().equals(admin.getId())) {
                return null;
            }
        }
        admin.setUpdateTime(new Date());
        adminMapper.updateByPrimaryKey(admin);
        return admin;
    }

    @Override
    public void adminDelete(Admin admin) {
        admin.setDeleted(true);
        admin.setUpdateTime(new Date());
        adminMapper.updateByPrimaryKey(admin);
    }

    @Override
    public HashMap<String, Object> logList(Integer page, Integer limit, String name, String sort, String order) {
        LogExample logExample = new LogExample();
        LogExample.Criteria criteria = logExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (name != null){
            criteria.andAdminLike("%" + name + "%");
        }
        long l = logMapper.countByExample(logExample);
        logExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(page,limit);
        List<Log> logs = logMapper.selectByExample(logExample);
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("total",Math.toIntExact(l));
        hashMap.put("items",logs);
        return hashMap;
    }

    @Override
    public HashMap<String, Object> roleList(Integer page, Integer limit, String name, String sort, String order) {
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (name != null){
            criteria.andNameLike("%" + name + "%");
        }
        long l = roleMapper.countByExample(roleExample);
        roleExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(page,limit);
        List<Role> roleList = roleMapper.selectByExample(roleExample);
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("total",Math.toIntExact(l));
        hashMap.put("items",roleList);
        return hashMap;
    }

    @Override
    public Role roleCreate(Role role) {
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andNameEqualTo(role.getName());
        List<Role> roleList = roleMapper.selectByExample(roleExample);
        if (roleList.size() > 0){
            return null;
        }
        role.setDeleted(false);
        role.setAddTime(new Date());
        role.setUpdateTime(new Date());
        role.setEnabled(true);
        roleMapper.insert(role);
        return role;
    }

    @Override
    public Role roleUpdate(Role role) {
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andNameEqualTo(role.getName());
        List<Role> roleList = roleMapper.selectByExample(roleExample);
        if (roleList.size() > 0) {
            if (!roleList.get(0).getId().equals(role.getId())) {
                return null;
            }
        }
        role.setUpdateTime(new Date());
        roleMapper.updateByPrimaryKey(role);
        return role;
    }

    @Override
    public void roleDelete(Role role) {
        role.setDeleted(true);
        role.setUpdateTime(new Date());
        roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public HashMap<String, Object> storageList(Integer page,Integer limit,String sort,String order,String key,String name) {
        StorageExample storageExample = new StorageExample();
        StorageExample.Criteria criteria = storageExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (name != null){
            criteria.andNameLike("%" + name + "%");
        }
        if (key != null){
            criteria.andKeyEqualTo(key);
        }
        long l = storageMapper.countByExample(storageExample);
        storageExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(page,limit);
        List<Storage> storages = storageMapper.selectByExample(storageExample);
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("total",Math.toIntExact(l));
        hashMap.put("items",storages);
        return hashMap;
    }

    @Override
    public Storage storageUpdate(Storage storage) {
        storage.setUpdateTime(new Date());
        storageMapper.updateByPrimaryKey(storage);
        return storage;
    }

    @Override
    public void storageDelete(Storage storage) {
        storage.setDeleted(true);
        storage.setUpdateTime(new Date());
        storageMapper.updateByPrimaryKey(storage);
    }

    @Override
    public List<String> permissionList(Integer roleId) {
        PermissionExample permissionExample = new PermissionExample();
        PermissionExample.Criteria criteria = permissionExample.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        criteria.andDeletedEqualTo(false);
        List<Permission> permissionList = permissionMapper.selectByExample(permissionExample);
        List<String> strings = new ArrayList<>();
        for (Permission permission : permissionList) {
            if (!permission.getPermission().equals("*")) {
                strings.add(permission.getPermission());
            } else {
                SystemPermissionExample systemPermissionExample = new SystemPermissionExample();
                SystemPermissionExample.Criteria criteria1 = systemPermissionExample.createCriteria();
                criteria1.andLevelEqualTo("3");
                List<SystemPermission> systemPermissions = systemPermissionMapper.selectByExample(systemPermissionExample);
                for (SystemPermission systemPermission : systemPermissions) {
                    strings.add(systemPermission.getId());
                }
            }
        }
        return strings;
    }

    @Override
    public void insertLog(Log log) {
        logMapper.insert(log);
    }

    @Override
    public List<SystemPermission> systemPermissionsList() {
        SystemPermissionExample systemPermissionExample3 = new SystemPermissionExample();
        SystemPermissionExample.Criteria criteria3 = systemPermissionExample3.createCriteria();
        criteria3.andLevelEqualTo("3");
        List<SystemPermission> systemPermissions3 = systemPermissionMapper.selectByExample(systemPermissionExample3);
        SystemPermissionExample systemPermissionExample2 = new SystemPermissionExample();
        SystemPermissionExample.Criteria criteria2 = systemPermissionExample2.createCriteria();
        criteria2.andLevelEqualTo("2");
        List<SystemPermission> systemPermissions2 = systemPermissionMapper.selectByExample(systemPermissionExample2);
        SystemPermissionExample systemPermissionExample1 = new SystemPermissionExample();
        SystemPermissionExample.Criteria criteria1 = systemPermissionExample1.createCriteria();
        criteria1.andLevelEqualTo("1");
        List<SystemPermission> systemPermissions1 = systemPermissionMapper.selectByExample(systemPermissionExample1);
        for (SystemPermission systemPermission : systemPermissions3) {
            for (SystemPermission permission : systemPermissions2) {
                if (systemPermission.getpId().equals(permission.getsId())){
                    permission.getChildren().add(systemPermission);
                }
            }
        }
        for (SystemPermission systemPermission : systemPermissions2) {
            for (SystemPermission permission : systemPermissions1) {
                if (systemPermission.getpId().equals(permission.getsId())) {
                    permission.getChildren().add(systemPermission);
                }
            }
        }
        return systemPermissions1;
    }

    @Override
    public void changePermissions(Permissions permissions) {
        PermissionExample permissionExample = new PermissionExample();
        PermissionExample.Criteria criteria = permissionExample.createCriteria();
        criteria.andRoleIdEqualTo(permissions.getRoleId());
        permissionMapper.deleteByExample(permissionExample);
        List<String> ps = permissions.getPermissions();
        for (String p : ps) {
            Permission permission = new Permission();
            permission.setRoleId(permissions.getRoleId());
            permission.setPermission(p);
            permission.setAddTime(new Date());
            permission.setUpdateTime(new Date());
            permission.setDeleted(false);
            permissionMapper.insert(permission);
        }
    }
}
