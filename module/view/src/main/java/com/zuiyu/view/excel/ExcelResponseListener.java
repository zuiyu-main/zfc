package com.zuiyu.view.excel;

import com.alibaba.fastjson.JSONObject;
import com.zuiyu.view.ResponseListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author zuiyu
 * @date 2023/1/6
 * @description 默认自定义响应内容监听
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public class ExcelResponseListener implements ResponseListener {
    private static final Logger log = LoggerFactory.getLogger(ExcelResponseListener.class);



    public void result(JSONObject object) {
        log.info("当前行内容:[{}]",object);
    }
}
