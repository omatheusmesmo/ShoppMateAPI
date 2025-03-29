package com.omatheusmesmo.shoppmate.list.repository;

import com.omatheusmesmo.shoppmate.list.entity.ShoppList;
import com.omatheusmesmo.shoppmate.list.entity.ShoppListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppListItemRepository extends JpaRepository<ShoppListItem, Long> {

}
