package com.omatheusmesmo.shoppmate.list.service;

import com.omatheusmesmo.shoppmate.list.entity.ShoppListItem;
import com.omatheusmesmo.shoppmate.list.repository.ShoppListItemRepository;
import com.omatheusmesmo.shoppmate.item.service.ItemService;
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
        isListItemValid(shoppListItem);
        auditService.setAuditData(shoppListItem, true);
        shoppListItemRepository.save(shoppListItem);
        return shoppListItem;
    }

    public void isListItemValid(ShoppListItem shoppListItem) throws NoSuchElementException {
        itemService.findItemById(shoppListItem.getItem().getId());
        shoppListService.findListById(shoppListItem.getShoppList().getId());

        itemService.isItemValid(shoppListItem.getItem());
        shoppListService.isListValid(shoppListItem.getShoppList());

        checkQuantity(shoppListItem);
    }

    private void checkQuantity(ShoppListItem shoppListItem){
        if (shoppListItem.getQuantity() == null || shoppListItem.getQuantity() <= 0){
            throw new IllegalArgumentException("Quantity must be informed and greater than 0!");
        }
    }


    public ShoppListItem findListItem(ShoppListItem shoppListItem) {
        return shoppListItemRepository.findById(shoppListItem.getId())
                .orElseThrow(() -> new NoSuchElementException("ShoppListItem not found"));

    }

    public ShoppListItem findListItemById(Long id) {
        return shoppListItemRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("ShoppingListItem not found"));
    }

    public void removeList(Long id) {
        ShoppListItem deletedItem = findListItemById(id);
        auditService.softDelete(deletedItem);
        shoppListItemRepository.save(deletedItem);
    }

    public ShoppListItem editList(ShoppListItem shoppListItem) {
        findListItemById(shoppListItem.getId());
        isListItemValid(shoppListItem);
        auditService.setAuditData(shoppListItem, false);
        shoppListItemRepository.save(shoppListItem);
        return shoppListItem;
    }

    public List<ShoppListItem> findAll() {
        return shoppListItemRepository.findAll();
    }
}
