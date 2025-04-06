package com.omatheusmesmo.shoppmate.list.controller;

import com.omatheusmesmo.shoppmate.list.entity.ShoppListUserPermission;
import com.omatheusmesmo.shoppmate.list.service.ShoppListUserPermissionService;
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
    private ShoppListUserPermissionService service;

    @Operation(description = "Return all ShoppListUserPermissions")
    @GetMapping
    public ResponseEntity<List<ShoppListUserPermission>> getAllShoppListUserPermissions() {
        try {
            List<ShoppListUserPermission> shoppListUserPermissions = service.findAll();
            return HttpResponseUtil.ok(shoppListUserPermissions);
        } catch (Exception e) {
            return HttpResponseUtil.internalServerError();
        }
    }

    @Operation(summary = "Add a new shoppListUserPermission")
    @PostMapping
    public ResponseEntity<ShoppListUserPermission> addShoppListUserPermission(@RequestBody ShoppListUserPermission shoppListUserPermission) {
        try {
            ShoppListUserPermission addedShoppListUserPermission = service.addShoppListUserPermission(shoppListUserPermission);
            return HttpResponseUtil.created(addedShoppListUserPermission);
        } catch (IllegalArgumentException e) {
            return HttpResponseUtil.badRequest(shoppListUserPermission);
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
    public ResponseEntity<ShoppListUserPermission> updateShoppListUserPermission(@RequestBody ShoppListUserPermission shoppListUserPermission) {
        try {
            service.editList(shoppListUserPermission);
            return HttpResponseUtil.ok(shoppListUserPermission);
        } catch (NoSuchElementException noSuchElementException) {
            return HttpResponseUtil.notFound();
        } catch (IllegalArgumentException illegalArgumentException) {
            return HttpResponseUtil.badRequest(shoppListUserPermission);
        }
    }
}
