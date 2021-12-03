package com.example.asyncmethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * @InterfaceName AppRunner
 * @Description TODO
 * @Author XiaoShuMu
 * @Version 1.0
 * @Create 2021-12-03 19:10
 * @Blog https://www.cnblogs.com/WLCYSYS/
 **/
@Component
public class AppRunner implements CommandLineRunner {

    public static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

    private final GithubLookupService githubLookupService;

    public AppRunner(GithubLookupService githubLookupService) {
        this.githubLookupService = githubLookupService;
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        //开始计时
        long start = System.currentTimeMillis();

        //启动多个异步查找
        CompletableFuture<User> page1 = githubLookupService.findUser("PivotalSoftware");
        CompletableFuture<User> page2 = githubLookupService.findUser("CloudFoundry");
        CompletableFuture<User> page3 = githubLookupService.findUser("Spring-Projects");
        CompletableFuture<User> page4 = githubLookupService.findUser("WuRoc");

        //等待他们全被执行
        CompletableFuture.allOf(page2, page2, page3, page4).join();

        //打印结果，包括过去的时间
        logger.info("Elapsed time: " + (System.currentTimeMillis() - start));

        logger.info("--> " + page1.get());
        logger.info("--> " + page2.get());
        logger.info("--> " + page3.get());
        logger.info("--> " + page4.get());

    }
}
