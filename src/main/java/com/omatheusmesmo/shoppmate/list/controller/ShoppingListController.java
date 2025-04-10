package com.omatheusmesmo.shoppmate.list.controller;

import com.omatheusmesmo.shoppmate.list.dtos.ShoppingListRequestDTO;
import com.omatheusmesmo.shoppmate.list.dtos.ShoppingListResponseDTO;
import com.omatheusmesmo.shoppmate.list.entity.ShoppingList;
import com.omatheusmesmo.shoppmate.list.mapper.ListMapper;
import com.omatheusmesmo.shoppmate.list.service.ShoppingListService;
import com.omatheusmesmo.shoppmate.utils.HttpResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/lists")
public class ShoppingListController {

    @Autowired
    private ShoppingListService service;

    @Autowired
    private ListMapper listMapper;

    @Operation(description = "Return all ShoppingLists")
    @GetMapping
    public ResponseEntity<List<ShoppingList>> getAllShoppingLists() {

            List<ShoppingList> ShoppingLists = service.findAll();
            return HttpResponseUtil.ok(ShoppingLists);
    }

    @Operation(summary = "Add a new ShoppingList")
    @PostMapping
    public ResponseEntity<ShoppingListResponseDTO> addShoppingList(@RequestBody ShoppingListRequestDTO dto) {
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

    @Operation(summary = "Delete a ShoppingList by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShoppingList(@PathVariable Long id) {
        try {
            service.removeList(id);
            return HttpResponseUtil.noContent();
        } catch (NoSuchElementException exception) {
            return HttpResponseUtil.notFound();
        }
    }

    @Operation(summary = "Update a ShoppingList")
    @PutMapping
    public ResponseEntity<ShoppingList> updateShoppingList(@RequestBody ShoppingList ShoppingList) {
        try {
            service.editList(ShoppingList);
            return HttpResponseUtil.ok(ShoppingList);
        } catch (NoSuchElementException noSuchElementException) {
            return HttpResponseUtil.notFound();
        } catch (IllegalArgumentException illegalArgumentException) {
            return HttpResponseUtil.badRequest(ShoppingList);
        }
    }
}
