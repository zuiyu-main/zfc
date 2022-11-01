package com.zuiyu.boot.filter;

import com.zuiyu.boot.factory.String2ConvertFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zuiyu
 * @date 2022/11/1
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${file.pre}")
    private String filePre;
    @Override
    public void addFormatters(FormatterRegistry registry) {
        // 添加请求参数转换enum类型
        registry.addConverterFactory(new String2ConvertFactory());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 添加请求转换 url 预览
        registry.addResourceHandler("/"+filePre+"/**")
                .addResourceLocations("file:/");

    }
}
