package com.crl.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

@RefreshScope
@RestController
public class TestController {

    @Value("${config-from}")
//    @Value("${info.from}")
    private String from;


    @Resource
    private RequestMappingHandlerMapping handlerMapping;

    @RequestMapping("/getAllApi")
    public void getAllApi(){
        Map map = this.handlerMapping.getHandlerMethods();
        Set set = map.keySet();
        for (Object object : set) {
            RequestMappingInfo info =(RequestMappingInfo) object;
            String reqURIs = info.getPatternsCondition().toString();
            System.out.println(reqURIs.substring(1, reqURIs.length()-1));
        }
    }

    @RequestMapping("/from")
    public String from() {
        return this.from;
    }

}
