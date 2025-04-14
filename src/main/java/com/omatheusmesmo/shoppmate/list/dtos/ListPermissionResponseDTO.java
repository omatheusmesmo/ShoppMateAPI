package com.omatheusmesmo.shoppmate.list.dtos;

import com.omatheusmesmo.shoppmate.list.entity.Permission;
import com.omatheusmesmo.shoppmate.user.dtos.UserResponseDTO;

public record ListPermissionResponseDTO(
        Long id,
        ShoppingListResponseDTO shoppingListResponseDTO,
        UserResponseDTO userResponseDTO,
        Permission permission
){}