package com.cwks;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.cwks.bizcore.config.CwksProperies;

import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement //启用事务
@MapperScan({"com.cwks.**.dao","com.cwks.**.mapper"}) //扫描mybites dao扫描
@EnableConfigurationProperties({CwksProperies.class}) //启动配置文件加载器
@EnableCaching //redies 启动
@EnableSwagger2 //让swagger生成接口文档
@EnableAsync //开启异步调用
public class CssnjWorksApplication {
    public static void main(String[] args) {
    	 SpringApplication.run(CssnjWorksApplication.class, args);
         StringBuffer sbf = new StringBuffer("CSSNJ 通用开发平台-v2 is runing success! \n");
         sbf.append("．．．南京中软通用开发平台 ．．．．\n");
         sbf.append("．．．．．★ CSSNJ DEV ★．．．．．．\n");
         sbf.append("．．．．★ Version 2.0.1 ★．．．．．\n");
         System.out.println(sbf.toString());
    }
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
