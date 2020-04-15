package com.oa.platform;

import net.sf.ehcache.CacheManager;
import org.mybatis.spring.annotation.MapperScan;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ResourceUtils;

import java.io.IOException;

/**
 * 项目启动类
 * @author
 */
@EnableTransactionManagement
@SpringBootApplication
@MapperScan("com.oa.platform.repository")
@EnableCaching
public class PlatformApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(PlatformApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(PlatformApplication.class);
	}

	/**
	 * 重新注入缓存管理器，避免报重命名'ehcacheManager'的bug
	 * @return
	 */
	@Bean(name = "ehcacheManager")
	public CacheManager ehCacheManagerFactoryBean() {
		CacheManager cacheManager = CacheManager.getCacheManager("es");
		if(cacheManager == null){
			try {
				cacheManager = CacheManager.create(ResourceUtils.getURL("classpath:ehcache.xml"));
			} catch (IOException e) {
				throw new RuntimeException("initialize cacheManager failed");
			}
		}
		return cacheManager;
	}
}
