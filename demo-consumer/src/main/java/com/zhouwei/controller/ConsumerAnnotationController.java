package com.zhouwei.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zhouwei.api.DemoAnnotationService;
import com.zhouwei.api.DemoService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

@Controller
@RequestMapping(value = "/demo/annotation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ConsumerAnnotationController {
    @Reference
    private DemoAnnotationService demoAnnotationService;

    @ResponseBody
    @RequestMapping("/demoAnnotation")
    public String sayHello(String name){
        try {
            return demoAnnotationService.sayHello(name);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
