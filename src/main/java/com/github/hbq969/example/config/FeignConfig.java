package com.github.hbq969.example.config;

import com.github.hbq969.code.common.spring.cloud.feign.FeignFactoryBean;
import com.github.hbq969.example.feign.DemoService;
import com.github.hbq969.example.feign.interceptors.MyBasicAuthRequestInterceptor;
import feign.RequestInterceptor;

import java.util.LinkedList;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : hbq969@gmail.com
 * @description : Feign自动转配类
 * @createTime : 2023/8/26 20:15
 */
@Configuration
@Slf4j
public class FeignConfig {

  @Value("${feign.service.example.url:localhost:8080}")
  private String exampleServiceUrl;

  @Bean("feign-demoService")
  public DemoService demoService() throws Exception {
    FeignFactoryBean factory = new FeignFactoryBean() {
      @Override
      protected LinkedList<RequestInterceptor> interceptors() {
        LinkedList<RequestInterceptor> list = super.interceptors();
        list.addFirst(new MyBasicAuthRequestInterceptor());
        return list;
      }
    };
    factory.setInter(DemoService.class);
    factory.setUrl(exampleServiceUrl);
    return (DemoService) factory.getObject();
  }
}
