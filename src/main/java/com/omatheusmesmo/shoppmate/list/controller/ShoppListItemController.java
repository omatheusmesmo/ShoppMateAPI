package com.omatheusmesmo.shoppmate.list.controller;

import com.omatheusmesmo.shoppmate.list.entity.ShoppListItem;
import com.omatheusmesmo.shoppmate.list.service.ShoppListItemService;
import com.omatheusmesmo.shoppmate.utils.HttpResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/shopplist/{shopplistId}/items")
public class ShoppListItemController {

    @Autowired
    private ShoppListItemService service;

    @Operation(description = "Return all ShoppListItems")
    @GetMapping
    public ResponseEntity<List<ShoppListItem>> getAllShoppListItems() {
        try {
            List<ShoppListItem> shoppListItems = service.findAll();
            return HttpResponseUtil.ok(shoppListItems);
        } catch (Exception e) {
            return HttpResponseUtil.internalServerError();
        }
    }

    @Operation(summary = "Add a new shoppListItem")
    @PostMapping
    public ResponseEntity<ShoppListItem> addShoppListItem(@RequestBody ShoppListItem shoppListItem) {
        try {
            ShoppListItem addedShoppListItem = service.addShoppItemList(shoppListItem);
            return HttpResponseUtil.created(addedShoppListItem);
        } catch (IllegalArgumentException e) {
            return HttpResponseUtil.badRequest(shoppListItem);
        }
    }

    @Operation(summary = "Delete a shoppListItem by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShoppListItem(@PathVariable Long id) {
        try {
            service.removeList(id);
            return HttpResponseUtil.noContent();
        } catch (NoSuchElementException exception) {
            return HttpResponseUtil.notFound();
        }
    }

    @Operation(summary = "Update a shoppListItem")
    @PutMapping
    public ResponseEntity<ShoppListItem> updateShoppListItem(@RequestBody ShoppListItem shoppListItem) {
        try {
            service.editList(shoppListItem);
            return HttpResponseUtil.ok(shoppListItem);
        } catch (NoSuchElementException noSuchElementException) {
            return HttpResponseUtil.notFound();
        } catch (IllegalArgumentException illegalArgumentException) {
            return HttpResponseUtil.badRequest(shoppListItem);
        }
    }
}
