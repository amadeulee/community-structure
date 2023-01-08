package io.academy.backend.academy.controller;

import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, SortType> {
    @Override
    public SortType convert(String source) {
        return SortType.valueOf(source.toUpperCase());
    }
}
