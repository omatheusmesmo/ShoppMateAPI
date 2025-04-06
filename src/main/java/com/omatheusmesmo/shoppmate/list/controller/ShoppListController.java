package com.omatheusmesmo.shoppmate.list.controller;

import com.omatheusmesmo.shoppmate.list.entity.ShoppList;
import com.omatheusmesmo.shoppmate.list.service.ShoppListService;
import com.omatheusmesmo.shoppmate.utils.HttpResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/shopplists")
public class ShoppListController {

    @Autowired
    private ShoppListService service;

    @Operation(description = "Return all ShoppLists")
    @GetMapping
    public ResponseEntity<List<ShoppList>> getAllShoppLists() {
        try {
            List<ShoppList> shoppLists = service.findAll();
            return HttpResponseUtil.ok(shoppLists);
        } catch (Exception e) {
            return HttpResponseUtil.internalServerError();
        }
    }

    @Operation(summary = "Add a new shoppList")
    @PostMapping
    public ResponseEntity<ShoppList> addShoppList(@RequestBody ShoppList shoppList) {
        try {
            ShoppList addedShoppList = service.saveList(shoppList);
            return HttpResponseUtil.created(addedShoppList);
        } catch (IllegalArgumentException e) {
            return HttpResponseUtil.badRequest(shoppList);
        }
    }

    @Operation(summary = "Delete a shoppList by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShoppList(@PathVariable Long id) {
        try {
            service.removeList(id);
            return HttpResponseUtil.noContent();
        } catch (NoSuchElementException exception) {
            return HttpResponseUtil.notFound();
        }
    }

    @Operation(summary = "Update a shoppList")
    @PutMapping
    public ResponseEntity<ShoppList> updateShoppList(@RequestBody ShoppList shoppList) {
        try {
            service.editList(shoppList);
            return HttpResponseUtil.ok(shoppList);
        } catch (NoSuchElementException noSuchElementException) {
            return HttpResponseUtil.notFound();
        } catch (IllegalArgumentException illegalArgumentException) {
            return HttpResponseUtil.badRequest(shoppList);
        }
    }
}
