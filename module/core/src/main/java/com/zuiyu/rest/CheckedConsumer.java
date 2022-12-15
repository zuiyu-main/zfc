package com.zuiyu.rest;

/**
 * @author zuiyu
 * @date 2022/10/25
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
@FunctionalInterface
public interface CheckedConsumer<T, E extends Exception> {
    void accept(T t) throws E;

}
