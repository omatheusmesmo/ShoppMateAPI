package com.omatheusmesmo.Lista.de.Compras.repository;

import com.omatheusmesmo.Lista.de.Compras.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
