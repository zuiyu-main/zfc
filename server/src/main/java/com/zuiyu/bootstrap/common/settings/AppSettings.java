package com.zuiyu.bootstrap.common.settings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * @author zuiyu
 * @date 2022/10/16
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public class AppSettings {

    private String appName;
    private String host;
    private Integer port;
    public final Logger log = LoggerFactory.getLogger(getClass());
    public AppSettings(){
        try {
            Resource resource = new ClassPathResource("application.properties");

            Properties props = PropertiesLoaderUtils.loadProperties(resource);
            appName = props.getProperty("application.name");
            host = props.getProperty("netty.server.host");
            port = Integer.valueOf(props.getProperty("netty.server.port"));
            log.debug("{} 注册地址 {}:{}",appName,host,port);
        }catch (IOException e) {
            log.error("application.properties 读取失败：",e);
            throw new RuntimeException(e);
        }
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
