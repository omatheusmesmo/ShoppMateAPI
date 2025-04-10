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
public class ShoppListUserPermissionController {

    @Autowired
    private ListPermissionService service;

    @Operation(description = "Return all ShoppListUserPermissions")
    @GetMapping
    public ResponseEntity<List<ListPermission>> getAllShoppListUserPermissions() {
        try {
            List<ListPermission> listPermissions = service.findAll();
            return HttpResponseUtil.ok(listPermissions);
        } catch (Exception e) {
            return HttpResponseUtil.internalServerError();
        }
    }

    @Operation(summary = "Add a new shoppListUserPermission")
    @PostMapping
    public ResponseEntity<ListPermission> addShoppListUserPermission(@RequestBody ListPermission listPermission) {
        try {
            ListPermission addedListPermission = service.addShoppListUserPermission(listPermission);
            return HttpResponseUtil.created(addedListPermission);
        } catch (IllegalArgumentException e) {
            return HttpResponseUtil.badRequest(listPermission);
        }
    }

    @Operation(summary = "Delete a shoppListUserPermission by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShoppListUserPermission(@PathVariable Long id) {
        try {
            service.removeList(id);
            return HttpResponseUtil.noContent();
        } catch (NoSuchElementException exception) {
            return HttpResponseUtil.notFound();
        }
    }

    @Operation(summary = "Update a shoppListUserPermission")
    @PutMapping
    public ResponseEntity<ListPermission> updateShoppListUserPermission(@RequestBody ListPermission listPermission) {
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
