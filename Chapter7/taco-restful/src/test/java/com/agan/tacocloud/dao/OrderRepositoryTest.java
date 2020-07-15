package com.agan.tacocloud.dao;

import com.agan.tacocloud.po.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testSave() {
        Order order = new Order();
        order.setDeliveryName("Ima Hungry");
        order.setDeliveryStreet("1234 Culinary Blvd.");
        order.setDeliveryCity("Foodsville");
        order.setDeliveryState("CO");
        order.setDeliveryZip("81019");
        order.setCcNumber("4111111111111111");
        order.setCcExpiration("10/19");
        order.setCcCVV("123");
        orderRepository.save(order);
        assert(order.getId() == 1L);
    }
}
