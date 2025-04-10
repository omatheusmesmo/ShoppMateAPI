package com.omatheusmesmo.shoppmate.list.controller;

import com.omatheusmesmo.shoppmate.list.entity.ListPermission;
import com.omatheusmesmo.shoppmate.list.service.ListPermissionService;
import com.omatheusmesmo.shoppmate.utils.HttpResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/shopplist/{shopplistId}/permissions")
public class ListPermissionController {

    @Autowired
    private ListPermissionService service;

    @Operation(description = "Return all ListPermissions")
    @GetMapping
    public ResponseEntity<List<ListPermission>> getAllListPermissions() {
        try {
            List<ListPermission> listPermissions = service.findAll();
            return HttpResponseUtil.ok(listPermissions);
        } catch (Exception e) {
            return HttpResponseUtil.internalServerError();
        }
    }

    @Operation(summary = "Add a new ListPermission")
    @PostMapping
    public ResponseEntity<ListPermission> addListPermission(@RequestBody ListPermission listPermission) {
        try {
            ListPermission addedListPermission = service.addListPermission(listPermission);
            return HttpResponseUtil.created(addedListPermission);
        } catch (IllegalArgumentException e) {
            return HttpResponseUtil.badRequest(listPermission);
        }
    }

    @Operation(summary = "Delete a ListPermission by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListPermission(@PathVariable Long id) {
        try {
            service.removeList(id);
            return HttpResponseUtil.noContent();
        } catch (NoSuchElementException exception) {
            return HttpResponseUtil.notFound();
        }
    }

    @Operation(summary = "Update a ListPermission")
    @PutMapping
    public ResponseEntity<ListPermission> updateListPermission(@RequestBody ListPermission listPermission) {
        try {
            service.editList(listPermission);
            return HttpResponseUtil.ok(listPermission);
        } catch (NoSuchElementException noSuchElementException) {
            return HttpResponseUtil.notFound();
        } catch (IllegalArgumentException illegalArgumentException) {
            return HttpResponseUtil.badRequest(listPermission);
        }
    }
}
