package com.oa.platform.config;

import com.oa.platform.web.interceptor.PlatformWebHandlerInterceptor;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 平台MVC配置
 * @author Feng
 * @date 2018/10/15
 */
@Configuration
@AutoConfigureBefore(SecurityConfig.class)
public class PlatformWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PlatformWebHandlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/test","/api/auth/login","/api/auth/logout","/test/**","/index","/login",
                        "/test/**", "/logout","/","/4**","/5**","/css/**","/img/**","/images/**","/js/**","/fonts/**",
                        "/error", "/tmp/**", "/api/file/**", "/api/socket/**", "/api/verify/**", "/api/news/**",
                        "/ueditor/**","/api/auth/needSms","/api/auth/getSMS","/api/auth/validateSMS");
    }

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        //LOGGER.info("跨域已设置");
        corsRegistry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
