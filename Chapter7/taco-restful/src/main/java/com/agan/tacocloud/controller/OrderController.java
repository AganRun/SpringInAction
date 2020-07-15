package com.agan.tacocloud.controller;

import com.agan.tacocloud.common.ResponseMessage;
import com.agan.tacocloud.dao.OrderRepository;
import com.agan.tacocloud.po.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

    private OrderRepository orderRepo;

    @Autowired
    public OrderController(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @PutMapping("/{orderId}")
    public ResponseMessage<Order> putOrder(@RequestBody Order order) {
        return ResponseMessage.success(orderRepo.save(order));
    }

    @PatchMapping(path = "/{orderId}", consumes = "application/json")
    public ResponseMessage patchOrder(@RequestBody Order patch, @PathVariable("orderId") Long orderId) {
        Order order = orderRepo.findById(orderId).get();
        if (patch.getDeliveryName() != null) {
            order.setDeliveryName(patch.getDeliveryName());
        }
        //此处省略其他属性替换
        if (patch.getCcCVV() != null) {
            order.setCcCVV(patch.getCcCVV());
        }
        return ResponseMessage.success(orderRepo.save(order));
    }

   @DeleteMapping("/{orderId}")
   @ResponseStatus(code = HttpStatus.NO_CONTENT)
   public void deleteOrder(@PathVariable("orderId") Long orderId) {
        try{
            orderRepo.deleteById(orderId);
        } catch (EmptyResultDataAccessException e) {

        }
   }

}
