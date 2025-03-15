package com.omatheusmesmo.shoppinglist.repository;

import com.omatheusmesmo.shoppinglist.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
}
