package com.weikaiyu.security.distributed.uaa.dao;

import com.weikaiyu.security.distributed.uaa.pojo.permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermissionRepository extends JpaRepository<permission,Integer> {

    @Query(value = "SELECT * FROM t_permission WHERE id in(SELECT permission_id FROM role_permission WHERE role_id in(SELECT role_id FROM user_role WHERE user_id=?1))", nativeQuery = true)
     List<permission> findPermission(int id);
}
