package com.omatheusmesmo.shoppmate.list.dtos;

import jakarta.validation.constraints.NotBlank;

public record UpdateShoppingListRequestDTO(
        @NotBlank(message = "List name cannot be blank")
        String name
) {
}
