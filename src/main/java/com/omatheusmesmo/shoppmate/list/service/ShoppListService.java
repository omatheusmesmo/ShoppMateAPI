package com.omatheusmesmo.shoppmate.list.service;

import com.omatheusmesmo.shoppmate.entity.User;
import com.omatheusmesmo.shoppmate.list.entity.ShoppList;

import com.omatheusmesmo.shoppmate.list.repository.ShoppListRepository;
import com.omatheusmesmo.shoppmate.service.UserService;
import com.omatheusmesmo.shoppmate.shared.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ShoppListService {

    @Autowired
    private ShoppListRepository shoppListRepository;
    @Autowired
    private AuditService auditService;
    @Autowired
    private UserService userService;

    public ShoppList saveList(ShoppList shoppList) {
        isListValid(shoppList);
        auditService.setAuditData(shoppList, true);
        shoppListRepository.save(shoppList);
        return shoppList;
    }

    public void isListValid(ShoppList shoppList) {
        shoppList.checkName();
        getOwnerId(shoppList);
    }
    
    private User getOwnerId(ShoppList shoppList){
        return userService.getUser(shoppList.getOwner().getId());
    }

    public Optional<ShoppList> findList(ShoppList shoppList) {
        Optional<ShoppList> foundList = shoppListRepository.findById(shoppList.getId());
        if (foundList.isPresent()) {
            return foundList;
        } else {
            throw new NoSuchElementException("ShoppingList not found");
        }
    }

    public Optional<ShoppList> findListById(Long id) {
        Optional<ShoppList> foundList = shoppListRepository.findById(id);
        if (foundList.isPresent()) {
            return foundList;
        } else {
            throw new NoSuchElementException("ShoppingList not found");
        }
    }

    public void removeList(Long id) {
        findListById(id);
        shoppListRepository.deleteById(id);
    }

    public ShoppList editList(ShoppList shoppList) {
        findListById(shoppList.getId());
        isListValid(shoppList);
        auditService.setAuditData(shoppList, false);
        shoppListRepository.save(shoppList);
        return shoppList;
    }

    public List<ShoppList> findAll() {
        return shoppListRepository.findAll();
    }
}
