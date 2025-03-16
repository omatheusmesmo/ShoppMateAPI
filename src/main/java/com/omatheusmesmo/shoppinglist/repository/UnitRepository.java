package com.omatheusmesmo.shoppinglist.repository;

import com.omatheusmesmo.shoppinglist.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
    Optional<Unit> findBySymbol(String symbol);

    Optional<Unit> findByName(String name);
}
