package com.agan.tacocloud.dao;

import com.agan.tacocloud.po.Taco;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends CrudRepository<Taco, Long> {

}
