package com.omatheusmesmo.shoppmate.list.service;

import com.omatheusmesmo.shoppmate.list.entity.ListPermission;
import com.omatheusmesmo.shoppmate.list.repository.ShoppListUserPermissionRepository;
import com.omatheusmesmo.shoppmate.user.service.UserService;
import com.omatheusmesmo.shoppmate.shared.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ListPermissionService {

    @Autowired
    private ShoppListUserPermissionRepository shoppListUserPermissionRepository;

    @Autowired
    private ShoppingListService shoppingListService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuditService auditService;

    public ListPermission addShoppListUserPermission(ListPermission listPermission) {
        isListValid(listPermission);
        auditService.setAuditData(listPermission, true);
        shoppListUserPermissionRepository.save(listPermission);
        return listPermission;
    }

    public void isListValid(ListPermission listPermission) {
        userService.isUserValid(listPermission.getUser());
        shoppingListService.isListValid(listPermission.getShoppingList());
    }
    
    public Optional<ListPermission> findListItem(ListPermission listPermission) {
        Optional<ListPermission> foundList = shoppListUserPermissionRepository.findById(listPermission.getId());
        if (foundList.isPresent()) {
            return foundList;
        } else {
            throw new NoSuchElementException("ShoppingList not found");
        }
    }

    public Optional<ListPermission> findListUserPermissionById(Long id) {
        Optional<ListPermission> foundList = shoppListUserPermissionRepository.findById(id);
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

    public ListPermission editList(ListPermission listPermission) {
        findListUserPermissionById(listPermission.getId());
        isListValid(listPermission);
        shoppListUserPermissionRepository.save(listPermission);
        return listPermission;
    }

    public List<ListPermission> findAll() {
        return shoppListUserPermissionRepository.findAll();
    }
}
