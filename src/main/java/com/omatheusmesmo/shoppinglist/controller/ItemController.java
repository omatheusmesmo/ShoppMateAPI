package com.omatheusmesmo.shoppinglist.controller;

import com.omatheusmesmo.shoppinglist.entity.Item;
import com.omatheusmesmo.shoppinglist.service.ItemService;
import com.omatheusmesmo.shoppinglist.utils.HttpResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Operation(summary = "Return all items")
    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        try {
            List<Item> items = itemService.findAll();
            return HttpResponseUtil.ok(items);
        } catch (Exception e) {
            return HttpResponseUtil.internalServerError();
        }
    }

    @Operation(summary = "Add a new item")
    @PostMapping
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        try {
            Item addedItem = itemService.addItem(item);
            return HttpResponseUtil.created(addedItem);
        } catch (IllegalArgumentException e) {
            return HttpResponseUtil.badRequest(item);
        }
    }

    @Operation(summary = "Delete a item by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        try {
            itemService.removeItem(id);
            return HttpResponseUtil.noContent();
        } catch (NoSuchElementException exception) {
            return HttpResponseUtil.notFound();
        }
    }

    @Operation(summary = "Update a item")
    @PutMapping
    public ResponseEntity<Item> updateItem(@RequestBody Item item) {
        try {
            itemService.editItem(item);
            return HttpResponseUtil.ok(item);
        } catch (NoSuchElementException noSuchElementException) {
            return HttpResponseUtil.notFound();
        } catch (IllegalArgumentException illegalArgumentException) {
            return HttpResponseUtil.badRequest(item);
        }
    }
}
