package com.agan.tacocloud.controller.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CarByConverter implements Converter<String, Car> {
    @Override
    public Car convert(String s) {
        return new Car().setId(s).setName("Converter");
    }
}
