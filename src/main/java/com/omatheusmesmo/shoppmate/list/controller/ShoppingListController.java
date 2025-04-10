package com.omatheusmesmo.shoppmate.list.controller;

import com.omatheusmesmo.shoppmate.list.dtos.ShoppingListRequestDTO;
import com.omatheusmesmo.shoppmate.list.dtos.ShoppingListResponseDTO;
import com.omatheusmesmo.shoppmate.list.entity.ShoppingList;
import com.omatheusmesmo.shoppmate.list.mapper.ListMapper;
import com.omatheusmesmo.shoppmate.list.service.ShoppingListService;
import com.omatheusmesmo.shoppmate.utils.HttpResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/lists")
public class ShoppingListController {

    @Autowired
    private ShoppingListService service;

    @Autowired
    private ListMapper listMapper;

    @Operation(description = "Return all Shopping Lists")
    @GetMapping
    public ResponseEntity<List<ShoppingListResponseDTO>> getAllShoppingLists() {
        List<ShoppingList> shoppingLists = service.findAll();

        List<ShoppingListResponseDTO> responseDTOs = shoppingLists.stream()
                .map(listMapper::toResponseDTO)
                .toList();
        return HttpResponseUtil.ok(responseDTOs);
    }

    @Operation(summary = "Add a new Shopping List")
    @PostMapping
    public ResponseEntity<ShoppingListResponseDTO> addShoppingList(@Valid @RequestBody ShoppingListRequestDTO dto) {
        ShoppingList shoppingList = listMapper.toEntity(dto);
        ShoppingList savedList = service.saveList(shoppingList);
        ShoppingListResponseDTO responseDTO = listMapper.toResponseDTO(savedList);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedList.getId())
                .toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }

    @Operation(summary = "Delete a Shopping List by id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteShoppingList(@PathVariable Long id) {
            service.removeList(id);
    }

    @Operation(summary = "Update a Shopping List")
    @PutMapping
    public ResponseEntity<ShoppingListResponseDTO> updateShoppingList(@Valid @RequestBody ShoppingListRequestDTO requestDTO) {
        ShoppingList shoppingList = listMapper.toEntity(requestDTO);

        ShoppingList updatedList = service.editList(shoppingList);

        ShoppingListResponseDTO responseDTO = listMapper.toResponseDTO(updatedList);

        return ResponseEntity.ok(responseDTO);
    }
}
