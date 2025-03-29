package com.omatheusmesmo.shoppmate.list.repository;

import com.omatheusmesmo.shoppmate.list.entity.ShoppList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppListRepository extends JpaRepository<ShoppList, Long> {

}
