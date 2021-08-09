package com.crl.sleuthtrace1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;

@Slf4j
@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class SleuthTrace1Application {

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        /*SimpleClientHttpRequestFactory reqfac = new SimpleClientHttpRequestFactory();
        // fiddler代理，用于查看http报文数据
        reqfac.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888)));
        restTemplate.setRequestFactory(reqfac);*/
        return restTemplate;
    }

    @RequestMapping(value = "/trace-1", method = RequestMethod.GET)
    public String trace() {
        log.info("===call trace-1===");
        return restTemplate().getForEntity("http://trace-2/trace-2", String.class).getBody();
    }

    public static void main(String[] args) {
        SpringApplication.run(SleuthTrace1Application.class, args);
    }

}
