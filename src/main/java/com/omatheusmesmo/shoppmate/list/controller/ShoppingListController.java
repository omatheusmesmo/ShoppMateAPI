package com.omatheusmesmo.shoppmate.list.controller;

import com.omatheusmesmo.shoppmate.list.entity.ShoppingList;
import com.omatheusmesmo.shoppmate.list.service.ShoppingListService;
import com.omatheusmesmo.shoppmate.utils.HttpResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/ShoppingLists")
public class ShoppingListController {

    @Autowired
    private ShoppingListService service;

    @Operation(description = "Return all ShoppingLists")
    @GetMapping
    public ResponseEntity<List<ShoppingList>> getAllShoppingLists() {
        try {
            List<ShoppingList> ShoppingLists = service.findAll();
            return HttpResponseUtil.ok(ShoppingLists);
        } catch (Exception e) {
            return HttpResponseUtil.internalServerError();
        }
    }

    @Operation(summary = "Add a new ShoppingList")
    @PostMapping
    public ResponseEntity<ShoppingList> addShoppingList(@RequestBody ShoppingList ShoppingList) {
        try {
            ShoppingList addedShoppingList = service.saveList(ShoppingList);
            return HttpResponseUtil.created(addedShoppingList);
        } catch (IllegalArgumentException e) {
            return HttpResponseUtil.badRequest(ShoppingList);
        }
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
