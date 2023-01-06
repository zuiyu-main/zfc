package com.zuiyu.view;

import com.alibaba.fastjson.JSONObject;

/**
 * @author zuiyu
 * @date 2023/1/6
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public interface ResponseListener {

    /**
     *
     * @param object
     */
    void result(JSONObject object);
}
