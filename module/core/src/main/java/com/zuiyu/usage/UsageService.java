package com.zuiyu.usage;

import com.zuiyu.rest.BaseRestHandler;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author zuiyu
 * @date 2022/10/21
 * @description
 * @link https://github.com/zuiyu-main
 */
public class UsageService {
    private final Map<String, BaseRestHandler> handlers;

    public UsageService() {
        this.handlers = new HashMap<>();
    }

    public void addRestHandler(BaseRestHandler handler){
        Objects.requireNonNull(handler);
        if (handler.getName() == null) {
            throw new IllegalArgumentException("handler of type [" + handler.getClass().getName() + "] does not have a name");
        }
        final BaseRestHandler maybeHandler = handlers.put(handler.getName(), handler);
        if (maybeHandler != null && maybeHandler != handler) {
            final String message = String.format(
                    Locale.ROOT,
                    "handler of type [%s] conflicts with handler of type [%s] as they both have the same name [%s]",
                    handler.getClass().getName(),
                    maybeHandler.getClass().getName(),
                    handler.getName()
            );
            throw new IllegalArgumentException(message);
        }

    }
}
