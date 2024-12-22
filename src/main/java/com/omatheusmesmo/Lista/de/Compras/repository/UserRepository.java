package com.omatheusmesmo.Lista.de.Compras.repository;

import com.omatheusmesmo.Lista.de.Compras.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
