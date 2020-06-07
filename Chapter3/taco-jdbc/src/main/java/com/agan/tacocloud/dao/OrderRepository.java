package com.agan.tacocloud.dao;


import com.agan.tacocloud.pojo.Order;

public interface OrderRepository {

  Order save(Order order);
  
}
