package com.agan.tacocloud.controller.convert;

import lombok.Data;

import java.util.List;

@Data
public class Order {
    String id;
    List<Car> cars;
}
