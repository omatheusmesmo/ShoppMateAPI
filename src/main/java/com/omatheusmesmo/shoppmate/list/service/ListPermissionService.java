package com.omatheusmesmo.shoppmate.list.service;

import com.omatheusmesmo.shoppmate.list.entity.ListPermission;
import com.omatheusmesmo.shoppmate.list.repository.ListPermissionRepository;
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
    private ListPermissionRepository listPermissionRepository;

    @Autowired
    private ShoppingListService shoppingListService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuditService auditService;

    public ListPermission addListPermission(ListPermission listPermission) {
        isListValid(listPermission);
        auditService.setAuditData(listPermission, true);
        listPermissionRepository.save(listPermission);
        return listPermission;
    }

    public void isListValid(ListPermission listPermission) {
        userService.isUserValid(listPermission.getUser());
        shoppingListService.isListValid(listPermission.getShoppingList());
    }
    
    public Optional<ListPermission> findListItem(ListPermission listPermission) {
        Optional<ListPermission> foundList = listPermissionRepository.findById(listPermission.getId());
        if (foundList.isPresent()) {
            return foundList;
        } else {
            throw new NoSuchElementException("ShoppingList not found");
        }
    }

    public Optional<ListPermission> findListUserPermissionById(Long id) {
        Optional<ListPermission> foundList = listPermissionRepository.findById(id);
        if (foundList.isPresent()) {
            return foundList;
        } else {
            throw new NoSuchElementException("ShoppingListItem not found");
        }
    }

    public void removeList(Long id) {
        findListUserPermissionById(id);
        listPermissionRepository.deleteById(id);
    }

    public ListPermission editList(ListPermission listPermission) {
        findListUserPermissionById(listPermission.getId());
        isListValid(listPermission);
        listPermissionRepository.save(listPermission);
        return listPermission;
    }

    public List<ListPermission> findAll() {
        return listPermissionRepository.findAll();
    }
}
