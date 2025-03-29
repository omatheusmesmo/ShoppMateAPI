package com.omatheusmesmo.shoppmate.repository;

import com.omatheusmesmo.shoppmate.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
