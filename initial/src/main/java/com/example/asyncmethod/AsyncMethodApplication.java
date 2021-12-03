package com.example.asyncmethod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
/**
 * @Description: Creating Asynchronous Methods
 * @Author: XiaoShuMu
 * @Date: 2021/12/3
 * @Version 1.0
 * @Blog https://www.cnblogs.com/WLCYSYS/
 */
@SpringBootApplication
@EnableAsync
public class AsyncMethodApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsyncMethodApplication.class, args).close();
	}

	/**
	 * 主要思想是，当提交任务时，如果当前活动线程的数量小于核心大小，则执行器首先尝试使用空闲线程。
	 * 如果已达到核心大小，则只要尚未达到其容量，就将任务添加到队列中。
	 * 只有这样，如果队列的容量已达到，执行程序才会创建超出核心大小的新线程。如果也达到了最大大小，
	 * 则执行程序将拒绝该任务
	 * @return
	 */
	@Bean
	public Executor taskExecutor() {

		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		//核心线程
		executor.setCorePoolSize(2);
		//最大核心线程
		executor.setMaxPoolSize(3);
		//队列的容量
		executor.setQueueCapacity(2);
		//线程前缀名称
		executor.setThreadNamePrefix("GithubLookup-");

		executor.initialize();

		return executor;
	}
}
