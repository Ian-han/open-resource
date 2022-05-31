package com.online.taxi.order;

import com.online.taxi.order.config.MyCuratorListener;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author yueyi2019
 */
@EnableEurekaClient
@SpringBootApplication
@EnableAsync
public class ServiceOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceOrderApplication.class, args);
	}


	/**
	 * zookeeper 客户端对象
	 * Curator Apache
	 * 供的个访问Zookeeper的工具包，封装了这些低级别操作同时也提供一些高级服务，比如分布式锁、领导选取
	 * @return
	 */
	@Bean
	public CuratorFramework curatorFramework(){
		// ExponentialBackoffRetry是种重连策略，每次重连的间隔会越来越长,1000毫秒是初始化的间隔时间,3代表尝试重连次数。
		ExponentialBackoffRetry retry = new ExponentialBackoffRetry(1000, 3);
		// 创建client
		CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("localhost:2181", retry);
		// 添加watched 监听器
		curatorFramework.getCuratorListenable().addListener(new MyCuratorListener());
		curatorFramework.start();
		return curatorFramework;
	}

}
