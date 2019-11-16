/**
 * Created by IntelliJ IDEA.
 * User: BJ
 * Date: 2019/11/15
 * Time: 22:27
 */
package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseReqVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/stat/")
public class StatController {


    /**
     * @Respones
     * {
     * 	"errno": 0,
     * 	"data": {
     * 		"columns": ["day", "users"],
     * 		"rows": [{
     * 			"day": "2019-04-19",
     * 			"users": 22
     *                }]    * 	},
     * 	"errmsg": "成功"
     * }
     * */
    @RequestMapping("user")
    public BaseReqVo user() {
        return null;
    }


    /**
     * @Respones
     * {
     * 	"errno": 0,
     * 	"data": {
     * 		"columns": ["day", "orders", "customers", "amount", "pcr"],
     * 		"rows": [{
     * 			"amount": 9622.00,
     * 			"orders": 8,
     * 			"customers": 1,
     * 			"day": "2019-08-20",
     * 			"pcr": 9622.00
     *                }, {
     * 			"amount": 5218.00,
     * 			"orders": 13,
     * 			"customers": 1,
     * 			"day": "2019-08-21",
     * 			"pcr": 5218.00
     *        }, {
     * 			"amount": 46212.00,
     * 			"orders": 71,
     * 			"customers": 1,
     * 			"day": "2019-08-22",
     * 			"pcr": 46212.00
     *        }, {
     * 			"amount": 54494.60,
     * 			"orders": 52,
     * 			"customers": 1,
     * 			"day": "2019-08-23",
     * 			"pcr": 54494.60
     *        }, {
     * 			"amount": -182644.20,
     * 			"orders": 63,
     * 			"customers": 1,
     * 			"day": "2019-10-04",
     * 			"pcr": -182644.20
     *        }, {
     * 			"amount": 5406.60,
     * 			"orders": 58,
     * 			"customers": 2,
     * 			"day": "2019-10-05",
     * 			"pcr": 2703.30
     *        }, {
     * 			"amount": -87137.40,
     * 			"orders": 5,
     * 			"customers": 1,
     * 			"day": "2019-10-06",
     * 			"pcr": -87137.40
     *        }, {
     * 			"amount": 20486.00,
     * 			"orders": 68,
     * 			"customers": 3,
     * 			"day": "2019-10-07",
     * 			"pcr": 6828.67
     *        }, {
     * 			"amount": 54655.10,
     * 			"orders": 44,
     * 			"customers": 3,
     * 			"day": "2019-10-08",
     * 			"pcr": 18218.37
     *        }]* 	},
     * 	"errmsg": "成功"
     * }*/
    @RequestMapping("order")
    public BaseReqVo order() {
        return null;
    }


    /**
     * @Respones
     * {
     * 	"errno": 0,
     * 	"data": {
     * 		"columns": ["day", "orders", "products", "amount"],
     * 		"rows": [{
     * 			"amount": 65017.00,
     * 			"orders": 26,
     * 			"day": "2019-08-20",
     * 			"products": 152
     *                }, {
     * 			"amount": 36110.00,
     * 			"orders": 42,
     * 			"day": "2019-08-21",
     * 			"products": 56
     *        }, {
     * 			"amount": 56686.00,
     * 			"orders": 84,
     * 			"day": "2019-08-22",
     * 			"products": 212
     *        }, {
     * 			"amount": 69070.50,
     * 			"orders": 63,
     * 			"day": "2019-08-23",
     * 			"products": 220
     *        }, {
     * 			"amount": 28473.50,
     * 			"orders": 77,
     * 			"day": "2019-10-04",
     * 			"products": 138
     *        }, {
     * 			"amount": 36875.80,
     * 			"orders": 68,
     * 			"day": "2019-10-05",
     * 			"products": 301
     *        }, {
     * 			"amount": 6136.60,
     * 			"orders": 7,
     * 			"day": "2019-10-06",
     * 			"products": 20
     *        }, {
     * 			"amount": 51191.90,
     * 			"orders": 79,
     * 			"day": "2019-10-07",
     * 			"products": 151
     *        }, {
     * 			"amount": 67088.10,
     * 			"orders": 52,
     * 			"day": "2019-10-08",
     * 			"products": 131
     *        }]* 	},
     * 	"errmsg": "成功"
     * }*/
    @RequestMapping("goods")
    public BaseReqVo goods() {
        return null;
    }
}