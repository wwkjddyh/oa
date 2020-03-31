package com.oa.platform.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class ExecutorConfig {
	private static final Logger logger = LoggerFactory.getLogger(ExecutorConfig.class);
	
	@Bean
	public Executor asyncServiceExecutor() {
		logger.info("init asyncServiceExecutor");
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		//核心线程数
		executor.setCorePoolSize(10);
		//最大线程数
		executor.setMaxPoolSize(50);
		//队列大小
		executor.setQueueCapacity(999);
		//线程前缀
		executor.setThreadNamePrefix("async-service-");
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.initialize();
		return executor;
	}
}
