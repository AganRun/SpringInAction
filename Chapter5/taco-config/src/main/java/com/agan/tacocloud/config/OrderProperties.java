package com.agan.tacocloud.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "taco.orders")
public class OrderProperties {

    private int pageSize = 20;

}
