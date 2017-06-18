package com.iss.yzsxy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by gaowenfeng on 2017/5/19.
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter{
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/admin").setViewName("admin");
        registry.addViewController("/classes").setViewName("classes");
        registry.addViewController("/teacher").setViewName("teacher");
        registry.addViewController("/student").setViewName("student");
        registry.addViewController("/titleclass").setViewName("titleclass");
        registry.addViewController("/account").setViewName("account");
        registry.addViewController("/change_title").setViewName("change_title");
        registry.addViewController("/title").setViewName("title");
        registry.addViewController("/myTitle").setViewName("myTitle");
        registry.addViewController("/teacheraccount").setViewName("teacheraccount");
        registry.addViewController("/studentaccount").setViewName("studentaccount");
        registry.addViewController("/openReport").setViewName("openReport");
        registry.addViewController("/assigen").setViewName("assigen");
        registry.addViewController("/adminManager").setViewName("adminManger");
    }
}
