package com.cskaoyan.wx_controller;

import com.cskaoyan.bean.BaseReqVo;

import com.cskaoyan.bean.generalize.Coupon;

import com.cskaoyan.bean.mall.BaseRespVo;

import com.cskaoyan.bean.mall.region.MallRegion;
import com.cskaoyan.bean.user.CouponRequest;
import com.cskaoyan.bean.user.User;
import com.cskaoyan.bean.user.UserRequest;

import com.cskaoyan.service.MallService;
import com.cskaoyan.service.OrderService;
import com.cskaoyan.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController_wx {

    @Autowired
    UserService userService;

    @Autowired
    MallService mallService;

    @Autowired
    OrderService orderService;

    @RequestMapping("wx/search/index")
    public BaseReqVo searchIndex(){
        User principal = (User) SecurityUtils.getSubject().getPrincipal();
        BaseReqVo<Object> objectBaseReqVo = new BaseReqVo<>();
        objectBaseReqVo.setData(userService.selectSearchIndex());
        objectBaseReqVo.setErrno(0);
        objectBaseReqVo.setErrmsg("成功");
        return objectBaseReqVo;
    }

    @RequestMapping("wx/search/helper")
    public BaseReqVo searchHelper(String keyword){
        BaseReqVo<Object> objectBaseReqVo = new BaseReqVo<>();
        objectBaseReqVo.setData(userService.searchHelper(keyword));
        objectBaseReqVo.setErrno(0);
        objectBaseReqVo.setErrmsg("成功");
        return objectBaseReqVo;
    }

    @RequestMapping("wx/search/clearhistory")
    public BaseReqVo searchClearHistory(ServletRequest servletRequest){
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        BaseReqVo<Object> objectBaseReqVo = new BaseReqVo<>();
        userService.searchClearHistory(request);
        objectBaseReqVo.setErrno(0);
        objectBaseReqVo.setErrmsg("成功");
        return objectBaseReqVo;
    }

    //groupon
    @RequestMapping("wx/groupon/list")
    public BaseReqVo grouponlist(UserRequest userRequest){
        BaseReqVo<Object> objectBaseReqVo = new BaseReqVo<>();
        objectBaseReqVo.setData(userService.selectGroupon(userRequest));
        objectBaseReqVo.setErrno(0);
        objectBaseReqVo.setErrmsg("成功");
        return objectBaseReqVo;
    }

    @RequestMapping("wx/groupon/my")
    public BaseReqVo grouponMy(int showType,ServletRequest servletRequest){
        BaseReqVo<Object> objectBaseReqVo = new BaseReqVo<>();
        objectBaseReqVo.setData(userService.grouponMy(showType));
        objectBaseReqVo.setErrno(0);
        objectBaseReqVo.setErrmsg("成功");
        return objectBaseReqVo;
    }

    @RequestMapping("wx/groupon/detail")
    public BaseReqVo grouponDetail(int grouponId){
        BaseReqVo<Object> objectBaseReqVo = new BaseReqVo<>();
        objectBaseReqVo.setData(userService.grouponDetail(grouponId));
        objectBaseReqVo.setErrno(0);
        objectBaseReqVo.setErrmsg("成功");
        return objectBaseReqVo;
    }

    //coupon
    @RequestMapping("wx/coupon/list")
    public BaseReqVo couponList(UserRequest userRequest,ServletRequest servletRequest){
        BaseReqVo<Object> objectBaseReqVo = new BaseReqVo<>();
        //如果用户没有登录，直接返回501，去登录
       /* HttpServletRequest request = (HttpServletRequest) servletRequest;
        String header = request.getHeader("X-Litemall-Token");
        String header1 = request.getHeader("X-cskaoyanmall-Admin-Token");*/
        Subject subject = SecurityUtils.getSubject();
        User principal = (User) subject.getPrincipal();
        if(principal==null){
            objectBaseReqVo.setErrno(501);
            return objectBaseReqVo;
        }
        List<Coupon> coupons = userService.selectCoupon(userRequest);
        int size = coupons.size();
        Map<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("data",coupons);
        objectObjectHashMap.put("count",size);
        objectBaseReqVo.setData(objectObjectHashMap);
        objectBaseReqVo.setErrno(0);
        objectBaseReqVo.setErrmsg("成功");
        return objectBaseReqVo;
    }

    @RequestMapping("wx/coupon/mylist")
    public BaseReqVo couponMyList(CouponRequest couponRequest){
        BaseReqVo<Object> objectBaseReqVo = new BaseReqVo<>();
        objectBaseReqVo.setData(userService.couponMyList(couponRequest));
        objectBaseReqVo.setErrno(0);
        objectBaseReqVo.setErrmsg("成功");
        return objectBaseReqVo;
    }

    @RequestMapping("wx/coupon/receive")
    public BaseReqVo couponReceive(@RequestBody CouponRequest couponRequest){
        BaseReqVo<Object> objectBaseReqVo = new BaseReqVo<>();
        Subject subject = SecurityUtils.getSubject();
        User principal = (User) subject.getPrincipal();
        if(principal==null){
            objectBaseReqVo.setErrno(501);
            //objectBaseReqVo.setErrmsg("成功");
            return objectBaseReqVo;
        }
        if(userService.couponReceive(couponRequest)==1){
            objectBaseReqVo.setErrno(0);
            objectBaseReqVo.setErrmsg("成功");
        }else if(userService.couponReceive(couponRequest)==0) {
            objectBaseReqVo.setErrno(507);
            objectBaseReqVo.setErrmsg("您已经领取过了");
        }else if(userService.couponReceive(couponRequest)==2) {
            objectBaseReqVo.setErrno(507);
            objectBaseReqVo.setErrmsg("优惠券已领完");
        }else if(userService.couponReceive(couponRequest)==3) {
            objectBaseReqVo.setErrno(507);
            objectBaseReqVo.setErrmsg("优惠券已过期");
        }

        return objectBaseReqVo;
    }

    @RequestMapping("wx/coupon/selectlist")
    public BaseReqVo couponSelectList(CouponRequest couponRequest){
        BaseReqVo<Object> objectBaseReqVo = new BaseReqVo<>();
        if(couponRequest.getGroupResultId()!=0){
            objectBaseReqVo.setErrno(0);
            objectBaseReqVo.setErrmsg("成功");
            return objectBaseReqVo;
        }
        objectBaseReqVo.setData(userService.couponSelectList(couponRequest));
        objectBaseReqVo.setErrno(0);
        objectBaseReqVo.setErrmsg("成功");
        return objectBaseReqVo;
    }

    @RequestMapping("wx/coupon/exchange")
    public BaseReqVo couponExchange(@RequestBody CouponRequest couponRequest){
        BaseReqVo<Object> objectBaseReqVo = new BaseReqVo<>();
        int i = userService.couponExchange(couponRequest);
        if(i==1){
            objectBaseReqVo.setErrno(0);
            objectBaseReqVo.setErrmsg("成功");
        }else if(i==0) {
            objectBaseReqVo.setErrno(507);
            objectBaseReqVo.setErrmsg("您已经领取过了");
        }else if(i==2) {
            objectBaseReqVo.setErrno(507);
            objectBaseReqVo.setErrmsg("优惠券已领完");
        }else if(i==3) {
            objectBaseReqVo.setErrno(507);
            objectBaseReqVo.setErrmsg("优惠券已过期");
        }

        return objectBaseReqVo;
    }




    @RequestMapping("wx/region/list")
    public BaseReqVo regionList(Integer pid){
        List<MallRegion> mallRegionList =  mallService.regionListByPid(pid);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(mallRegionList);
        return baseReqVo;
    }

    @GetMapping("wx/user/index")
    public Object list(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Integer userId = user.getId();
        HashMap<String,Object> orderStatusByUserId = orderService.countOrderStatusByUserId(userId);
        HashMap<String,Object> data = new HashMap<>();
        //***********************************
        //根据userId查询订单信息
        data.put("order", orderStatusByUserId);
        //***********************************
        return BaseRespVo.ok(data);
    }

    private Integer getUserID(){
        User principal =(User) SecurityUtils.getSubject().getPrincipal();
        return principal.getId();
    }
}

