package com.agan.tacocloud.controller.convert;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.agan.tacocloud.controller.convert.Order;

@RequestMapping("/order")
@RestController
public class CarOrderController {

    @GetMapping
    public Order getOrder(Order order) {
        System.out.println(order);
        return order;
    }
}
