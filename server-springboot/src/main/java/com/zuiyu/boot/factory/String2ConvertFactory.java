package com.zuiyu.boot.factory;

import com.zuiyu.boot.module.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * @author zuiyu
 * @date 2022/11/1
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public class String2ConvertFactory implements ConverterFactory<String, BaseEnum> {
    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> aClass) {
        return new String2Enum<>(aClass);
    }
    /**
     * 转换器
     */
    private class String2Enum<T extends BaseEnum> implements Converter<String, T> {

        private final Class<T> targetType;

        private String2Enum(Class<T> targetType) {
            this.targetType = targetType;
        }

        @Override
        public T convert(String source) {
            for (T enumConstant : targetType.getEnumConstants()) {
                if (enumConstant.getValue().toString().equals(source)) {
                    return enumConstant;
                }
            }
            return null;
        }
    }
}
