package com.omatheusmesmo.shoppmate.list.mapper;

import com.omatheusmesmo.shoppmate.list.dtos.ShoppingListRequestDTO;
import com.omatheusmesmo.shoppmate.list.dtos.ShoppingListResponseDTO;
import com.omatheusmesmo.shoppmate.list.entity.ShoppingList;
import com.omatheusmesmo.shoppmate.user.dtos.UserResponseDTO;
import com.omatheusmesmo.shoppmate.user.entity.User;
import com.omatheusmesmo.shoppmate.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class ListMapper {

    @Autowired
    private UserRepository userRepository;

    public ShoppingList toEntity(ShoppingListRequestDTO dto){
        User owner = userRepository.findById(dto.idUser())
                .orElseThrow(()-> new NoSuchElementException("User not found with ID:" + dto.idUser()));

        ShoppingList list = new ShoppingList(owner);
        list.setName(dto.name());
        return list;
    }

    public ShoppingListResponseDTO toResponseDTO(ShoppingList shoppingList){
        UserResponseDTO ownerDTO = new UserResponseDTO(
                shoppingList.getOwner().getId(),
                shoppingList.getOwner().getFullName(),
                shoppingList.getOwner().getEmail());

        return new ShoppingListResponseDTO(
                shoppingList.getId(),
                shoppingList.getName(),
                ownerDTO
                );
    }

}
