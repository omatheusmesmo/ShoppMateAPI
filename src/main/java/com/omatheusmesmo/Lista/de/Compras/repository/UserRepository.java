package com.omatheusmesmo.Lista.de.Compras.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<com.omatheusmesmo.Lista.de.Compras.entity.User, Long> {

    Optional<com.omatheusmesmo.Lista.de.Compras.entity.User> findByEmail(String email);
}
