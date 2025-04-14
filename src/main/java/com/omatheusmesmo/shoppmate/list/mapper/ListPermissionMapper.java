package com.omatheusmesmo.shoppmate.list.mapper;

import com.omatheusmesmo.shoppmate.list.entity.Permission;
import com.omatheusmesmo.shoppmate.user.dtos.UserResponseDTO;
import com.omatheusmesmo.shoppmate.user.entity.User;
import com.omatheusmesmo.shoppmate.user.repository.UserRepository;
import com.omatheusmesmo.shoppmate.list.dtos.ListPermissionRequestDTO;
import com.omatheusmesmo.shoppmate.list.dtos.ListPermissionResponseDTO;
import com.omatheusmesmo.shoppmate.list.entity.ListPermission;
import com.omatheusmesmo.shoppmate.list.entity.ShoppingList;
import com.omatheusmesmo.shoppmate.list.repository.ShoppingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class ListPermissionMapper {

    @Autowired
    private ShoppingListRepository shoppingListRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ListMapper listMapper;
    
    public ListPermission toEntity(ListPermissionRequestDTO dto) {
        ShoppingList shoppingList = shoppingListRepository.findById(dto.idList())
                .orElseThrow(() -> new NoSuchElementException("ShoppingList not found with ID: " + dto.idList()));

        User user = userRepository.findById(dto.idUser())
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + dto.idUser()));

        ListPermission listPermission = new ListPermission();
        listPermission.setShoppingList(shoppingList);
        listPermission.setPermission(dto.permission());
        listPermission.setUser(user);
        return listPermission;
    }

    public ListPermissionResponseDTO toResponseDTO(ListPermission listPermission) {
        UserResponseDTO userResponseDTO = new UserResponseDTO(
                listPermission.getUser().getId(),
                listPermission.getUser().getFullName(),
                listPermission.getUser().getEmail());

        return new ListPermissionResponseDTO(
                listPermission.getId(),
                listMapper.toResponseDTO(listPermission.getShoppingList()),
                userResponseDTO,
                listPermission.getPermission()
        );
    }

//    public void updateEntityFromDto(ListPermissionRequestDTO dto, ListPermission entity) {
//         ShoppingList shoppingList = shoppingListRepository.findById(dto.listId())
//                 .orElseThrow(() -> new NoSuchElementException("ShoppingList not found with ID: " + dto.listId()));
//         Permission permission = userRepository.findById(dto.permissionId())
//                 .orElseThrow(() -> new NoSuchElementException("Permission not found with ID: " + dto.permissionId()));
//         entity.setShoppList(shoppingList);
//         entity.setPermission(permission);
//
//        entity.setQuantity(dto.quantity());
//    }

}