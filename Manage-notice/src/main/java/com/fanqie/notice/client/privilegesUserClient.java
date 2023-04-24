package com.fanqie.notice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

//todo： 优化空间，换一种实现方式，这个是简单的，还有一种是api统一导包实现的。

/**
 * 包路径可能与生产者不一样，必须要通过
 * @SpringBootApplication(scanBasePackages = {"com.javadaily.feign"})
 * 扫描Feign的路径，当消费端需要引入很多生产者Feign时那就需要扫描很多个接口路径。
 *
 */
@FeignClient("manage-service")
public interface privilegesUserClient {

    @RequestMapping("/manage/privileges-user/getPrivilegesById")
    public String getPrivilegesById(String id);
}
