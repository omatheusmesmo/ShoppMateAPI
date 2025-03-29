package com.omatheusmesmo.shoppmate.list.service;

import com.omatheusmesmo.shoppmate.list.entity.ShoppListItem;
import com.omatheusmesmo.shoppmate.list.repository.ShoppListItemRepository;
import com.omatheusmesmo.shoppmate.service.ItemService;
import com.omatheusmesmo.shoppmate.shared.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ShoppListItemService {

    @Autowired
    private ShoppListItemRepository shoppListItemRepository;

    @Autowired
    private ShoppListService shoppListService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private AuditService auditService;

    public ShoppListItem addShoppItemList(ShoppListItem shoppListItem) {
        isListValid(shoppListItem);
        auditService.setAuditData(shoppListItem, true);
        shoppListItemRepository.save(shoppListItem);
        return shoppListItem;
    }

    public void isListValid(ShoppListItem shoppListItem) {
        itemService.isItemValid(shoppListItem.getItem());
        shoppListService.isListValid(shoppListItem.getShoppList());
        checkQuantity(shoppListItem);
    }

    private void checkQuantity(ShoppListItem shoppListItem){
        if (shoppListItem.getQuantity() == null || shoppListItem.getQuantity() <= 0){
            throw new IllegalArgumentException("Quantity must be informed and greater than 0!");
        }
    }


    public Optional<ShoppListItem> findListItem(ShoppListItem shoppListItem) {
        Optional<ShoppListItem> foundList = shoppListItemRepository.findById(shoppListItem.getId());
        if (foundList.isPresent()) {
            return foundList;
        } else {
            throw new NoSuchElementException("ShoppingList not found");
        }
    }

    public Optional<ShoppListItem> findListItemById(Long id) {
        Optional<ShoppListItem> foundList = shoppListItemRepository.findById(id);
        if (foundList.isPresent()) {
            return foundList;
        } else {
            throw new NoSuchElementException("ShoppingListItem not found");
        }
    }

    public void removeList(Long id) {
        findListItemById(id);
        shoppListItemRepository.deleteById(id);
    }

    public ShoppListItem editList(ShoppListItem shoppListItem) {
        findListItemById(shoppListItem.getId());
        isListValid(shoppListItem);
        shoppListItemRepository.save(shoppListItem);
        return shoppListItem;
    }

    public List<ShoppListItem> findAll() {
        return shoppListItemRepository.findAll();
    }
}
