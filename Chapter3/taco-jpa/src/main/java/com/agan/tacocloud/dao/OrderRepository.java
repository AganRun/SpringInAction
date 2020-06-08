package com.agan.tacocloud.dao;


import com.agan.tacocloud.po.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {

}
