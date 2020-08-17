package com.lr.framework.service.impl;

import com.alibaba.fastjson.JSON;
import com.lr.framework.annotion.LrService;
import com.lr.framework.service.TestService;

import java.util.HashMap;
import java.util.Map;

/**
 * @version v1.0
 * @ProjectName: gupao
 * @ClassName: TestServiceImpl
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 刘然
 * @Date: 2020/6/30 19:05
 */
@LrService("testService")
public class TestServiceImpl implements TestService {
    public String testService(String name, String addr) {
        Map<String,Object> row = new HashMap<String, Object>();
        row.put("name",name);
        row.put("address",addr);
        Map<String,Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("rows",row);
        String result = JSON.toJSONString(rtnMap);
        System.out.println(result);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}
