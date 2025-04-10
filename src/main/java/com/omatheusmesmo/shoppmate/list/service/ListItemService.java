package com.omatheusmesmo.shoppmate.list.service;

import com.omatheusmesmo.shoppmate.list.entity.ListItem;
import com.omatheusmesmo.shoppmate.list.repository.ListItemRepository;
import com.omatheusmesmo.shoppmate.item.service.ItemService;
import com.omatheusmesmo.shoppmate.shared.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ListItemService {

    @Autowired
    private ListItemRepository ListItemRepository;

    @Autowired
    private ShoppingListService shoppingListService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private AuditService auditService;

    public ListItem addShoppItemList(ListItem ListItem) {
        isListItemValid(ListItem);
        auditService.setAuditData(ListItem, true);
        ListItemRepository.save(ListItem);
        return ListItem;
    }

    public void isListItemValid(ListItem ListItem) throws NoSuchElementException {
        itemService.findById(ListItem.getItem().getId());
        shoppingListService.findListById(ListItem.getShoppList().getId());

        itemService.isItemValid(ListItem.getItem());
        shoppingListService.isListValid(ListItem.getShoppList());

        checkQuantity(ListItem);
    }

    private void checkQuantity(ListItem ListItem){
        if (ListItem.getQuantity() == null || ListItem.getQuantity() <= 0){
            throw new IllegalArgumentException("Quantity must be informed and greater than 0!");
        }
    }


    public ListItem findListItem(ListItem ListItem) {
        return ListItemRepository.findById(ListItem.getId())
                .orElseThrow(() -> new NoSuchElementException("ListItem not found"));

    }

    public ListItem findListItemById(Long id) {
        return ListItemRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("ShoppingListItem not found"));
    }

    public void removeList(Long id) {
        ListItem deletedItem = findListItemById(id);
        auditService.softDelete(deletedItem);
        ListItemRepository.save(deletedItem);
    }

    public ListItem editList(ListItem ListItem) {
        findListItemById(ListItem.getId());
        isListItemValid(ListItem);
        auditService.setAuditData(ListItem, false);
        ListItemRepository.save(ListItem);
        return ListItem;
    }

    public List<ListItem> findAll() {
        return ListItemRepository.findAll();
    }
}
