/**
 * Created by IntelliJ IDEA.
 * User: BJ
 * Date: 2019/11/15
 * Time: 16:12
 */
package com.cskaoyan.controller;

import com.cskaoyan.bean.Admin;
import com.cskaoyan.bean.BaseReqVo;
import com.cskaoyan.shiro.AuthToken;
import com.cskaoyan.service.AuthService;
import com.cskaoyan.utils.AuthUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

@RestController
@RequestMapping("admin/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @RequestMapping("login")
    public BaseReqVo login(@RequestBody Admin admin, HttpServletRequest request) throws Exception {
        String username = admin.getUsername();
        if ("".equals(username)) { return BaseReqVo.fail(401,"账号不可为空,请输入"); }
        AuthToken authenticationToken = new AuthToken(admin.getUsername(), admin.getPassword(),"admin");
//        AuthToken authenticationToken = new AuthToken(admin.getUsername(), AuthUtils.encrypt(admin.getPassword()),"admin");
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(authenticationToken);
        } catch (AuthenticationException e) {
            return BaseReqVo.fail(507,"账号或密码输入有误,请确认后重新输入");
            //e.printStackTrace();
        }
        Serializable sessionId = subject.getSession().getId();
        return BaseReqVo.ok(sessionId);
       /* BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        String username = admin.getUsername();
        if ("".equals(username)) {
            baseReqVo.setErrno(401);
            baseReqVo.setErrmsg("账号不可为空,请输入");
            return baseReqVo;
        }
        Admin admin1 = authService.getUsernameByUsername(username);
        if (admin1 == null) {
            baseReqVo.setErrno(401);
            baseReqVo.setErrmsg("用户不存在,请确认后重新输入");
            return baseReqVo;
        }
        else if (admin.getPassword().equals(admin1.getPassword())) {
            baseReqVo.setErrno(0);
            baseReqVo.setData(username);
            baseReqVo.setErrmsg("成功");
            request.getSession().setAttribute("admin",username);
            return baseReqVo;
        }else {
            baseReqVo.setErrno(605);
            baseReqVo.setErrmsg("账号或密码输入有误,请确认后重新输入");
            return baseReqVo;
        }*/
    }

    /**
     * @request
     * token:mall123
     *
     * @Response
     * {
     * 	"errno": 0,
     * 	"data": {
     * 		"roles": ["商场管理员"],
     * 		"name": "mall123",
     * 		"perms": ["GET /admin/order/list", "POST /admin/ad/create", "POST /admin/topic/create", "GET /admin/topic/read", "GET /admin/index/read", "GET /admin/history/list", "POST /admin/issue/update", "GET /admin/coupon/list", "POST /admin/order/reply", "GET /admin/goods/list", "GET /admin/ad/list", "POST /admin/brand/create", "POST /admin/config/express", "GET /admin/feedback/list", "POST /admin/groupon/create", "GET /admin/stat/user", "POST /admin/coupon/update", "POST /admin/issue/delete", "POST /admin/category/delete", "GET /admin/category/read", "POST /admin/keyword/update", "POST /admin/keyword/delete", "POST /admin/config/mall", "GET /admin/ad/read", "GET /admin/category/list", "GET /admin/config/express", "GET /admin/issue/list", "GET /admin/brand/list", "POST /admin/topic/delete", "GET /admin/address/list", "POST /admin/groupon/update", "POST /admin/coupon/create", "POST /admin/admin/create", "GET /admin/brand/read", "POST /admin/brand/update", "GET /admin/collect/list", "GET /admin/coupon/listuser", "POST /admin/admin/delete", "POST /admin/goods/update", "GET /admin/config/wx", "GET /admin/footprint/list", "POST /admin/brand/delete", "GET /admin/comment/list", "POST /admin/admin/update", "POST /admin/goods/delete", "POST /admin/groupon/delete", "GET /admin/goods/detail", "GET /admin/config/order", "GET /admin/user/list", "GET /admin/admin/list", "POST /admin/ad/delete", "POST /admin/config/order", "POST /admin/index/write", "GET /admin/config/mall", "POST /admin/ad/update", "POST /admin/order/refund", "GET /admin/stat/order", "GET /admin/keyword/list", "POST /admin/coupon/delete", "POST /admin/config/wx", "GET /admin/admin/read", "POST /admin/category/update", "GET /admin/order/detail", "GET /admin/groupon/list", "POST /admin/issue/create", "POST /admin/keyword/create", "GET /admin/stat/goods", "GET /admin/keyword/read", "POST /admin/category/create", "GET /admin/groupon/listRecord", "GET /admin/topic/list", "GET /admin/coupon/read", "POST /admin/order/ship", "POST /admin/comment/delete", "POST /admin/goods/create", "POST /admin/topic/update"],
     * 		"avatar": "'"       //头像
     *        },
     * 	"errmsg": "成功"
     * }
     * */
    @RequestMapping("info")
    public BaseReqVo info() {
        //SystemBean.out.println(token);
        HashMap<String,Object> map = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        Admin admin = (Admin) subject.getPrincipal();
        //Admin admin = authService.getUsernameByUsername(token);
        String[] roleIds = admin.getRole_ids();
        String roleIdArray = Arrays.toString(roleIds);
        List<String> roleNames = new ArrayList<>();
        List<String> permsList = new ArrayList<>();
        boolean permission = roleIdArray.contains("1");    //如果是超级管理员就拥有全部权限
        if (permission) {
            for (String roleId: roleIds) {
                String roleName = authService.getRoleNameById(roleId);
                roleNames.add(roleName);
            }
            permsList.add("*");       //许可路径
        }else {
            for (String roleId: roleIds) {
                String roleName = authService.getRoleNameById(roleId);
                List<String>  perms  = authService.getPermsMethodNameByRoleId(roleId);
                roleNames.add(roleName);
                permsList.addAll(perms);
            }
        }
        if (permsList.size() == 0){
            permsList.add("");
        }
        map.put("roles", roleNames);    //所有权限
        map.put("perms", permsList);    //许可路径
        map.put("name", admin.getUsername());         //名称
        map.put("avatar",admin.getAvatar());    //头像
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        baseReqVo.setErrno(0);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @RequestMapping("logout")
    public BaseReqVo logout() {
        SecurityUtils.getSubject().logout();
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

}
