package com.omatheusmesmo.shoppmate.list.repository;

import com.omatheusmesmo.shoppmate.list.entity.ListPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListPermissionRepository extends JpaRepository<ListPermission, Long> {

}
