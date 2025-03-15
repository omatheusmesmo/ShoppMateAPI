package com.omatheusmesmo.shoppinglist.repository;

import com.omatheusmesmo.shoppinglist.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
