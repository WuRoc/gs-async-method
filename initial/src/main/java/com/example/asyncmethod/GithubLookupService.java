package com.example.asyncmethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

/**
 * @ClassName GithubLookupService
 * @Description 该类用@Service注解标记, 使其成为 Spring 组件扫描的候选者，以检测并添加到应用程序上下文中
 * @Author XiaoShuMu
 * @Version 1.0
 * @Create 2021-12-03 16:25
 * @Blog https://www.cnblogs.com/WLCYSYS/
 **/
@Service
public class GithubLookupService {

    public static final Logger logger = LoggerFactory.getLogger(GithubLookupService.class);
    /**
     * MessageConverter
     */
    private final RestTemplate restTemplate;

    public GithubLookupService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    /**
     * Async 表示它应该在单独的线程上运行
     * @param user
     * @return
     * @throws InterruptedException
     */
    @Async
    public CompletableFuture<User> findUser(String user) throws InterruptedException {

        logger.info("Looking up " + user);

        //https://api.github.com/users/CloudFoundry
        String url = String.format("https://api.github.com/users/%s", user);

        User results = restTemplate.getForObject(url, User.class);

        //延迟1s
        Thread.sleep(1000L);

        return CompletableFuture.completedFuture(results);
    }
}
