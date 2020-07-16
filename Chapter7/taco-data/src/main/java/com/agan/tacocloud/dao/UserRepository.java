package com.agan.tacocloud.dao;

import com.agan.tacocloud.po.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

}
