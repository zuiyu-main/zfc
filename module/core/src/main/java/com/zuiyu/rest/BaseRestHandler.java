package com.zuiyu.rest;

import com.zuiyu.rest.action.ConvertTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * @author zuiyu
 * @date 2022/10/21
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public abstract class BaseRestHandler implements RestHandler{

    public final Logger log = LoggerFactory.getLogger(getClass());
    /**
     * 响应名称路径
     * @return
     */
    public abstract String getName();

    /**
     * 源文件类型
     * @return
     */
    public abstract String getSourceFileType();

    /**
     * 目标文件类型
     * @return
     */
    public abstract String getTargetFileType();

    /**
     * 源文件
     * @return
     */
    public abstract File getSourceFile();

    /**
     * 目标文件
     * @return
     */
    public abstract File getTargetFile();

    /**
     * 获取支持的类型
     * @return
     */
    public abstract List<String> getIncludeType();


    /**
     * 具体的执行方法
     * @param request
     * @throws Exception
     */
    public abstract RestChannelConsumer preRequest(RestRequest request,ConvertTypeEnum convertTypeEnum) throws Exception;





    @Override
    public final void handleRequest(RestRequest request, RestChannel channel) throws Exception {
        log.debug("handleRequest ======> info");
        // 验证参数、校验类型是否支持
        List<String> includeType = getIncludeType();
        String name = request.getFile().getName();
        String fileType = name.substring(name.lastIndexOf("." )+1);
        boolean contains = includeType.contains(fileType.toUpperCase(Locale.ROOT));
        log.debug("文件类型校验 [{}] ",contains);
        if (!contains){
            throw new IllegalArgumentException(String.format("文件类型不支持:%s",fileType));
        }
        ConvertTypeEnum baseFileConvertService;
        if (null == request.getConvertType()||request.getConvertType().length()==0){
            baseFileConvertService = getBaseFileConvertService(ConvertTypeEnum.ITEXT.name());
            log.debug("未指定转换方式，即将使用[{}]执行转换",ConvertTypeEnum.ITEXT.name());
        }else{
            baseFileConvertService = getBaseFileConvertService(request.getConvertType());
        }
        RestChannelConsumer restChannelConsumer = preRequest(request,baseFileConvertService);
        restChannelConsumer.accept(channel);
    }

    @FunctionalInterface
    protected interface RestChannelConsumer extends CheckedConsumer<RestChannel,Exception>{}

    /**
     * 根据传入参数获取转换方式
     * @param name
     * @return
     */
    private ConvertTypeEnum getBaseFileConvertService(String name) {
        ConvertTypeEnum[] values = ConvertTypeEnum.values();
        for (ConvertTypeEnum value : values) {
            if (name.equalsIgnoreCase(value.name())){
                return value;
            }
        }
        return ConvertTypeEnum.ITEXT;
    }

}
