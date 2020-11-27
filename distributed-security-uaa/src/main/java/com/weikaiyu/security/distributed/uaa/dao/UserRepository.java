package com.weikaiyu.security.distributed.uaa.dao;

import com.weikaiyu.security.distributed.uaa.pojo.user;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<user,Integer> {

    user findByName(String s);


}
