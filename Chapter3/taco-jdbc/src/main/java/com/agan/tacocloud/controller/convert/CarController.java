package com.agan.tacocloud.controller.convert;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/car")
public class CarController {

    @GetMapping
    public Car car(Car car) {
        System.out.println(car);
        return car;
    }

//    @GetMapping
//    public Car car(String carId) {
//        return new Car().setId(carId).setName("Default");
//    }
}
