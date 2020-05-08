package com.oa.platform.res;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * OA：资源访问服务器
 * @author jianbo.feng
 */
@SpringBootApplication
public class ResApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ResApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ResApplication.class);
    }

}
