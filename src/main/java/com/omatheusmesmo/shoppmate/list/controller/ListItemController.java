package com.omatheusmesmo.shoppmate.list.controller;

import com.omatheusmesmo.shoppmate.list.entity.ListItem;
import com.omatheusmesmo.shoppmate.list.service.ListItemService;
import com.omatheusmesmo.shoppmate.utils.HttpResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/lists/{shopplistId}/items")
public class ListItemController {

    @Autowired
    private ListItemService service;

    @Operation(description = "Return all ListItems")
    @GetMapping
    public ResponseEntity<List<ListItem>> getAllListItems() {
        try {
            List<ListItem> ListItems = service.findAll();
            return HttpResponseUtil.ok(ListItems);
        } catch (Exception e) {
            return HttpResponseUtil.internalServerError();
        }
    }

    @Operation(summary = "Add a new ListItem")
    @PostMapping
    public ResponseEntity<ListItem> addListItem(@RequestBody ListItem ListItem) {
        try {
            ListItem addedListItem = service.addShoppItemList(ListItem);
            return HttpResponseUtil.created(addedListItem);
        } catch (IllegalArgumentException e) {
            return HttpResponseUtil.badRequest(ListItem);
        }
    }

    @Operation(summary = "Delete a ListItem by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListItem(@PathVariable Long id) {
        try {
            service.removeList(id);
            return HttpResponseUtil.noContent();
        } catch (NoSuchElementException exception) {
            return HttpResponseUtil.notFound();
        }
    }

    @Operation(summary = "Update a ListItem")
    @PutMapping
    public ResponseEntity<ListItem> updateListItem(@RequestBody ListItem ListItem) {
        try {
            service.editList(ListItem);
            return HttpResponseUtil.ok(ListItem);
        } catch (NoSuchElementException noSuchElementException) {
            return HttpResponseUtil.notFound();
        } catch (IllegalArgumentException illegalArgumentException) {
            return HttpResponseUtil.badRequest(ListItem);
        }
    }
}
