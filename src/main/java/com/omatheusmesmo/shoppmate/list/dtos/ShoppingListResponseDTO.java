package com.omatheusmesmo.shoppmate.list.dtos;

import com.omatheusmesmo.shoppmate.user.dtos.UserResponseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ShoppingListResponseDTO(
        Long idList,
        String listName,
        UserResponseDTO owner
) {
}
