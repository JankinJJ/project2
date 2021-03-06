package com.cskaoyan.service;

import com.cskaoyan.bean.generalize.Storage;
import com.cskaoyan.bean.systemBean.*;

import java.util.HashMap;
import java.util.List;

public interface SystemService {
    HashMap<String,Object> adminList(Integer page, Integer limit, String username, String sort, String order);

    List<Admin> adminList();

    List<Role> roleOptions();

    Admin adminCreate(Admin admin);

    Admin adminUpdate(Admin admin);

    void adminDelete(Admin admin);

    HashMap<String, Object> logList(Integer page,Integer limit,String name,String sort,String order);

    HashMap<String, Object> roleList(Integer page, Integer limit, String name, String sort, String order);

    Role roleCreate(Role role);

    Role roleUpdate(Role role);

    void roleDelete(Role role);

    HashMap<String, Object> storageList(Integer page,Integer limit,String sort,String order,String key,String name);

    Storage storageUpdate(Storage storage);

    void storageDelete(Storage storage);

    List<String> permissionList(Integer roleId);

    void insertLog(Log log);

    List<SystemPermission> systemPermissionsList();

    void changePermissions(Permissions permissions);
}
