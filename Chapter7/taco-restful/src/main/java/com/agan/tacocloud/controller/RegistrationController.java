package com.agan.tacocloud.controller;

import com.agan.tacocloud.Vo.RegistrationForm;
import com.agan.tacocloud.common.ResponseMessage;
import com.agan.tacocloud.dao.UserRepository;
import com.agan.tacocloud.po.Order;
import com.agan.tacocloud.po.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Constructor;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public ResponseMessage<String> processRegistration(RegistrationForm form) {
        userRepo.save(form.toUser(passwordEncoder));
        return ResponseMessage.success("success");
    }

    @GetMapping("test")
    public ResponseMessage<Object> test() {
        Order order = new Order();
        order.setId(1L);
        return ResponseMessage.success(order);
    }
}
