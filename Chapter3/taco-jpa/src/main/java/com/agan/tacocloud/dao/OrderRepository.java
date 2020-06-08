package com.agan.tacocloud.dao;


import com.agan.tacocloud.po.Order;

public interface OrderRepository {

  Order save(Order order);
  
}
