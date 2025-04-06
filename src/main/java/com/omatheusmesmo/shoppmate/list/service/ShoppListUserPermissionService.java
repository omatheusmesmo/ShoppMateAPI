package com.omatheusmesmo.shoppmate.list.service;

import com.omatheusmesmo.shoppmate.list.entity.ShoppListUserPermission;
import com.omatheusmesmo.shoppmate.list.repository.ShoppListUserPermissionRepository;
import com.omatheusmesmo.shoppmate.user.service.UserService;
import com.omatheusmesmo.shoppmate.shared.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ShoppListUserPermissionService {

    @Autowired
    private ShoppListUserPermissionRepository shoppListUserPermissionRepository;

    @Autowired
    private ShoppListService shoppListService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuditService auditService;

    public ShoppListUserPermission addShoppListUserPermission(ShoppListUserPermission shoppListUserPermission) {
        isListValid(shoppListUserPermission);
        auditService.setAuditData(shoppListUserPermission, true);
        shoppListUserPermissionRepository.save(shoppListUserPermission);
        return shoppListUserPermission;
    }

    public void isListValid(ShoppListUserPermission shoppListUserPermission) {
        userService.isUserValid(shoppListUserPermission.getUser());
        shoppListService.isListValid(shoppListUserPermission.getShoppList());
    }
    
    public Optional<ShoppListUserPermission> findListItem(ShoppListUserPermission shoppListUserPermission) {
        Optional<ShoppListUserPermission> foundList = shoppListUserPermissionRepository.findById(shoppListUserPermission.getId());
        if (foundList.isPresent()) {
            return foundList;
        } else {
            throw new NoSuchElementException("ShoppingList not found");
        }
    }

    public Optional<ShoppListUserPermission> findListUserPermissionById(Long id) {
        Optional<ShoppListUserPermission> foundList = shoppListUserPermissionRepository.findById(id);
        if (foundList.isPresent()) {
            return foundList;
        } else {
            throw new NoSuchElementException("ShoppingListItem not found");
        }
    }

    public void removeList(Long id) {
        findListUserPermissionById(id);
        shoppListUserPermissionRepository.deleteById(id);
    }

    public ShoppListUserPermission editList(ShoppListUserPermission shoppListUserPermission) {
        findListUserPermissionById(shoppListUserPermission.getId());
        isListValid(shoppListUserPermission);
        shoppListUserPermissionRepository.save(shoppListUserPermission);
        return shoppListUserPermission;
    }

    public List<ShoppListUserPermission> findAll() {
        return shoppListUserPermissionRepository.findAll();
    }
}
