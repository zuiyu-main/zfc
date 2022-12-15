package com.zuiyu.boot.factory;

import com.zuiyu.rest.BaseRestHandler;
import com.zuiyu.rest.RestHandler;
import com.zuiyu.rest.action.FileHandlerEnum;
import com.zuiyu.rest.action.office.Text2Pdf;
import com.zuiyu.usage.UsageService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author zuiyu
 * @date 2022/11/1
 * @description 转换类工厂
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
@Component
public class FileConvertFactory {
    private final UsageService initUsageService;
    private static UsageService usageService;
    public FileConvertFactory() {
        // 初始化
        initUsageService = new UsageService();
        // 加载所有的handler
        initUsageService.addRestHandler(new Text2Pdf());
    }
    @PostConstruct
    public void postInit(){
        usageService = initUsageService;
    }

    /**
     * 根据处理类型返回具体的handler
     * @param fileHandlerEnum
     * @return 具体的响应类 {@link RestHandler}
     */
    public static BaseRestHandler buildRestHandler(FileHandlerEnum fileHandlerEnum){
        FileHandlerEnum[] values = FileHandlerEnum.values();
        for (FileHandlerEnum value : values) {
            if (value.getValue().equals(fileHandlerEnum.getValue())){
                return usageService.getRestHandler(fileHandlerEnum);
            }
        }

        return usageService.getRestHandler(FileHandlerEnum.TEXT2PDF);
    }
}
