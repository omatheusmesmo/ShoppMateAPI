package com.omatheusmesmo.shoppmate.item.controller;

import com.omatheusmesmo.shoppmate.item.dto.ItemDTO;
import com.omatheusmesmo.shoppmate.item.entity.Item;
import com.omatheusmesmo.shoppmate.item.mapper.ItemMapper;
import com.omatheusmesmo.shoppmate.item.service.ItemService;
import com.omatheusmesmo.shoppmate.utils.HttpResponseUtil;
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
    @Autowired
    private ItemMapper itemMapper;

    @Operation(summary = "Return all items")
    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        try {
            List<Item> items = itemService.findAll();
            return HttpResponseUtil.ok(items);
        } catch (Exception e) {                 //TODO: return correct error when is DB off or there is no item
            return HttpResponseUtil.internalServerError();
        }
    }

    @Operation(summary = "Add a new item")
    @PostMapping
    public ResponseEntity<Item> addItem(@RequestBody ItemDTO itemDTO) {
        Item item = null;
        try {
            item = itemMapper.toEntity(itemDTO);
            return HttpResponseUtil.created(itemService.addItem(item));
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
