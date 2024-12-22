package com.omatheusmesmo.Lista.de.Compras.service;

import com.omatheusmesmo.Lista.de.Compras.Entity.User;
import com.omatheusmesmo.Lista.de.Compras.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User addUser(User user) {
        validateIfDataIsNullOrEmpty(user);
        validateIfUserExists(user.getEmail());
        userRepository.save(user);
        return user;
    }

    public void validateIfUserExists(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            throw new IllegalArgumentException("e-mail já está sendo utilizado!");
        }
    }

    public void validateIfDataIsNullOrEmpty(User user) {
        String email = user.getEmail();
        if (email.isBlank()) {
            throw new IllegalArgumentException("Preencha o e-mail corretamente!");
        }
        String password = user.getPassword();
        if (password.isBlank()) {
            throw new IllegalArgumentException("É necessário preencher a senha!");
        }
    }

    public User editUser(User user) {
        findUserById(user.getId());
        validateIfDataIsNullOrEmpty(user);
        validateIfUserExists(user.getEmail());
        userRepository.save(user);
        return user;
    }

    public Optional<User> findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user;
        } else {
            throw new NoSuchElementException("Usuário inexistente!");
        }
    }

    public void removeUser(Long id) {
        findUserById(id);
        userRepository.deleteById(id);
    }
}
