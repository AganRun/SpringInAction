package com.agan.tacocloud.controller.convert;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Car {
    String id;
    String name;
}
