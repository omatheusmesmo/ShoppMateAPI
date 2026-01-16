package com.omatheusmesmo.shoppmate.list.controller;

import com.omatheusmesmo.shoppmate.list.dtos.ListPermissionRequestDTO;
import com.omatheusmesmo.shoppmate.list.dtos.ListPermissionResponseDTO;
import com.omatheusmesmo.shoppmate.list.dtos.ListPermissionUpdateRequestDTO;
import com.omatheusmesmo.shoppmate.list.entity.ListPermission;
import com.omatheusmesmo.shoppmate.list.mapper.ListPermissionMapper;
import com.omatheusmesmo.shoppmate.list.service.ListPermissionService;
import com.omatheusmesmo.shoppmate.utils.HttpResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/lists/{shopplistId}/permissions")
public class ListPermissionController {

    @Autowired
    private ListPermissionService service;

    @Autowired
    private ListPermissionMapper listPermissionMapper;

    @Operation(description = "Return all ListPermissions")
    @GetMapping
    public ResponseEntity<List<ListPermissionResponseDTO>> getAllListPermissions() {
            List<ListPermission> listPermissions = service.findAll();
            List<ListPermissionResponseDTO> responseDTOs = listPermissions.stream()
                    .map(listPermissionMapper::toResponseDTO)
                    .toList();
            return HttpResponseUtil.ok(responseDTOs);
    }

    @Operation(summary = "Add a new ListPermission")
    @PostMapping
    public ResponseEntity<ListPermissionResponseDTO> addListPermission(
            @Valid @RequestBody ListPermissionRequestDTO requestDTO) {
        ListPermission addedListPermission = service.addListPermission(requestDTO);
        ListPermissionResponseDTO responseDTO = listPermissionMapper.toResponseDTO(addedListPermission);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(addedListPermission.getId())
                .toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }

    @Operation(summary = "Delete a ListPermission by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListPermission(@PathVariable Long id) {
            service.removeList(id);
            return HttpResponseUtil.noContent();
    }

    @Operation(summary = "Update a ListPermission")
    @PutMapping("/{id}")
    public ResponseEntity<ListPermissionResponseDTO> updateListPermission(
            @PathVariable Long id,
            @Valid @RequestBody ListPermissionUpdateRequestDTO requestDTO) {

        ListPermission updatedListPermission = service.editList(id, requestDTO);
        ListPermissionResponseDTO responseDTO = listPermissionMapper.toResponseDTO(updatedListPermission);

        return ResponseEntity.ok(responseDTO);
    }
}
