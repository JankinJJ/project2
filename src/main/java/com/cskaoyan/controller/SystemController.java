package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseReqVo;
import com.cskaoyan.bean.generalize.Storage;
import com.cskaoyan.bean.systemBean.*;
import com.cskaoyan.service.SystemService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("admin")
public class SystemController {

    @Autowired
    SystemService systemService;

    @RequestMapping("role/options")
    @RequiresPermissions(value = {"admin:admin:list","admin:admin:update"
            ,"admin:admin:delete","admin:admin:read","admin:admin:create"},logical = Logical.OR)
    public BaseReqVo roleOptions(){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        List<Role> roleList = systemService.roleOptions();
        List<HashMap> hashMapList = new ArrayList<>();
        for (Role role : roleList) {
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("value",role.getId());
            hashMap.put("label",role.getName());
            hashMapList.add(hashMap);
        }
        baseReqVo.setData(hashMapList);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("admin/list")
    @RequiresPermissions(value = {"admin:admin:list","admin:admin:update"
    ,"admin:admin:delete","admin:admin:read","admin:admin:create"},logical = Logical.OR)
    public BaseReqVo adminList(Integer page,Integer limit,String username,String sort,String order){
        HashMap<String,Object> hashMap = systemService.adminList(page, limit, username, sort, order);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(hashMap);
        return baseReqVo;
    }

    @RequestMapping("admin/create")
    @RequiresPermissions(value = {"admin:admin:create"})
    public BaseReqVo adminCreate(@RequestBody Admin admin){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        if (admin.getUsername().length() < 6 ){
            baseReqVo.setErrno(500);
            baseReqVo.setErrmsg("管理员名称长度不能小于6");
            return baseReqVo;
        }
        if (admin.getPassword().length() < 6 ){
            baseReqVo.setErrno(500);
            baseReqVo.setErrmsg("管理员密码长度不能小于6");
            return baseReqVo;
        }
        Admin a = systemService.adminCreate(admin);
        if (a == null){
            baseReqVo.setErrno(500);
            baseReqVo.setErrmsg("管理员名称已存在");
            return baseReqVo;
        }
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(a);
        return baseReqVo;
    }

    @RequestMapping("admin/update")
    @RequiresPermissions(value = {"admin:admin:update"})
    public BaseReqVo adminUpdate(@RequestBody Admin admin){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        if (admin.getUsername().length() < 6 ){
            baseReqVo.setErrno(500);
            baseReqVo.setErrmsg("管理员名称长度不能小于6");
            return baseReqVo;
        }
        if (admin.getPassword().length() < 6 ){
            baseReqVo.setErrno(500);
            baseReqVo.setErrmsg("管理员密码长度不能小于6");
            return baseReqVo;
        }
        Admin a = systemService.adminUpdate(admin);
        if (a == null){
            baseReqVo.setErrno(500);
            baseReqVo.setErrmsg("管理员名称已存在");
            return baseReqVo;
        }
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(a);
        return baseReqVo;
    }

    @RequestMapping("admin/delete")
    @RequiresPermissions(value = {"admin:admin:delete"})
    public BaseReqVo adminDelete(@RequestBody Admin admin){
        Subject subject = SecurityUtils.getSubject();
        com.cskaoyan.bean.Admin admin1 = (com.cskaoyan.bean.Admin) subject.getPrincipal();
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        if (admin.getId().equals(admin1.getId())){
            baseReqVo.setErrno(500);
            baseReqVo.setErrmsg("你不能删除自己");
            return baseReqVo;
        }
        systemService.adminDelete(admin);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @RequestMapping("log/list")
    @RequiresPermissions(value = {"admin:log:list"},logical = Logical.OR)
    public BaseReqVo logList(Integer page,Integer limit,String name,String sort,String order){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        HashMap<String,Object> hashMap = systemService.logList(page, limit, name, sort, order);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(hashMap);
        return baseReqVo;
    }

    @RequestMapping("role/list")
    @RequiresPermissions(value = {"admin:role:list","admin:role:read","admin:role:create"
                        ,"admin:role:update","admin:role:delete","admin:role:permission:update"
                        ,"admin:role:permission"},logical = Logical.OR)
    public BaseReqVo roleList(Integer page,Integer limit,String name,String sort,String order){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        HashMap<String,Object> hashMap = systemService.roleList(page, limit, name, sort, order);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(hashMap);
        return baseReqVo;
    }

    @RequestMapping("role/create")
    @RequiresPermissions(value = {"admin:role:create"})
    public BaseReqVo roleCreate(@RequestBody Role role){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        Role r = systemService.roleCreate(role);
        if (r == null){
            baseReqVo.setErrno(500);
            baseReqVo.setErrmsg("角色已存在");
            return baseReqVo;
        }
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(r);
        return baseReqVo;
    }

    @RequestMapping("role/update")
    @RequiresPermissions(value = {"admin:role:update"})
    public BaseReqVo roleUpdate(@RequestBody Role role){
        BaseReqVo baseReqVo = new BaseReqVo();
        if(role.getId().equals(1)){
            baseReqVo.setErrmsg("你不能修改超级管理员信息");
            baseReqVo.setErrno(500);
        } else {
            Role r = systemService.roleUpdate(role);
            if (r == null) {
                baseReqVo.setErrno(500);
                baseReqVo.setErrmsg("角色已存在");
                return baseReqVo;
            }
            baseReqVo.setErrmsg("成功");
            baseReqVo.setErrno(0);
            baseReqVo.setData(r);
        }
        return baseReqVo;
    }

    @RequestMapping("role/delete")
    @RequiresPermissions(value = {"admin:role:delete"})
    public BaseReqVo roleDelete(@RequestBody Role role){
        List<Admin> adminList = systemService.adminList();
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        if (role.getId().equals(1)){
            baseReqVo.setErrno(500);
            baseReqVo.setErrmsg("你不能删除超级管理员");
            return baseReqVo;
        }
        for (Admin admin : adminList) {
            Integer[] roleIds = admin.getRoleIds();
            for (Integer roleId : roleIds) {
                if(role.getId().equals(roleId)){
                    baseReqVo.setErrno(642);
                    baseReqVo.setErrmsg("当前角色存在管理员，不能删除");
                    return baseReqVo;
                }
            }
        }
        systemService.roleDelete(role);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @RequestMapping("storage/list")
    @RequiresPermissions(value = {"admin:storage:list","admin:storage:read","admin:storage:update"
                        ,"admin:storage:delete","admin:storage:create"},logical = Logical.OR)
    public BaseReqVo storageList(Integer page,Integer limit,String sort,String order,String key,String name){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        HashMap<String,Object> hashMap = systemService.storageList(page, limit, sort, order,key,name);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(hashMap);
        return baseReqVo;
    }

    @RequestMapping("storage/update")
    @RequiresPermissions(value = {"admin:storage:update"})
    public BaseReqVo storageUpdate(@RequestBody Storage storage){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        Storage s = systemService.storageUpdate(storage);
        baseReqVo.setData(s);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @RequestMapping("storage/delete")
    @RequiresPermissions(value = {"admin:storage:delete"})
    public BaseReqVo storageDelete(@RequestBody Storage storage){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        systemService.storageDelete(storage);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @GetMapping("role/permissions")
    @RequiresPermissions(value = {"admin:role:permission:get","admin:role:permission:update"},logical = Logical.OR)
    public BaseReqVo rolePermissionsGet(Integer roleId){
        BaseReqVo baseReqVo = new BaseReqVo();
        List<SystemPermission> systemPermissionsList = systemService.systemPermissionsList();
        List<String> permissionList = systemService.permissionList(roleId);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("systemPermissions",systemPermissionsList);
        hashMap.put("assignedPermissions",permissionList);
        baseReqVo.setData(hashMap);
        return baseReqVo;
    }

    @PostMapping("role/permissions")
    @RequiresPermissions(value = {"admin:role:permission:update"})
    public BaseReqVo rolePermissionsPost(@RequestBody Permissions permissions, HttpServletResponse response){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        if (permissions.getRoleId().equals(1)){
            baseReqVo.setErrno(500);
            baseReqVo.setErrmsg("你不能修改超级管理员的权限");
        } else {
            systemService.changePermissions(permissions);
            baseReqVo.setErrno(0);
            baseReqVo.setErrmsg("成功，即将刷新");
        }
        return baseReqVo;
    }


}
