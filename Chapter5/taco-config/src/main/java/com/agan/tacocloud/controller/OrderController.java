package com.agan.tacocloud.controller;


import com.agan.tacocloud.config.OrderProperties;
import com.agan.tacocloud.dao.OrderRepository;
import com.agan.tacocloud.po.Order;
import com.agan.tacocloud.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
//@ConfigurationProperties(prefix = "taco.orders")
public class OrderController {

    private OrderRepository orderRepo;

//    private int pageSize = 20;
//
//    public void setPageSize(int pageSize) {
//        this.pageSize = pageSize;
//    }

    private OrderProperties properties;

    @Autowired
    public OrderController(OrderRepository orderRepo, OrderProperties properties) {
        this.orderRepo = orderRepo;
        this.properties = properties;
    }

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(Order order, Errors errors, SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        orderRepo.save(order);
        sessionStatus.setComplete();
        return "redirect:/";
    }

    @GetMapping
    public String ordersForUser(@AuthenticationPrincipal User user, Model model) {
//        Pageable pageable = PageRequest.of(0, 20);
//        Pageable pageable = PageRequest.of(0, pageSize);
        Pageable pageable = PageRequest.of(0, properties.getPageSize());
        model.addAttribute("orders", orderRepo.findByUserOrderByPlacedAtDesc(user, pageable));
        return "orderList";
    }
}
