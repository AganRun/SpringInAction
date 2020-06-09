package com.agan.tacocloud.dao;

import com.agan.tacocloud.po.Taco;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TacoRepository extends CrudRepository<Taco, Long> {

}
