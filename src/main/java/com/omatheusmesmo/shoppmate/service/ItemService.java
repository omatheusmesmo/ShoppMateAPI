package com.omatheusmesmo.shoppmate.service;

import com.omatheusmesmo.shoppmate.entity.Item;
import com.omatheusmesmo.shoppmate.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    private UnitService unitService;
    private CategoryService categoryService;

    public Item saveItem(Item item) {
        isItemValid(item);
        itemRepository.save(item);
        return item;
    }

    public void isItemValid(Item item) {
        categoryService.isCategoryValid(item.getCategory());
        unitService.isUnitValid(item.getUnit());
        checkName(item.getName());
    }

    private void checkName(String name){
        if (name == null) {
            throw new IllegalArgumentException("The item name cannot be null!");
        } else if (name.isBlank()) {
            throw new IllegalArgumentException("Enter a valid item name!");
        }
    }

    public Optional<Item> findItem(Item item) {
        Optional<Item> foundItem = itemRepository.findById(item.getId());
        if (foundItem.isPresent()) {
            return foundItem;
        } else {
            throw new NoSuchElementException("Item not found");
        }
    }

    public Optional<Item> findItemById(Long id) {
        Optional<Item> foundItem = itemRepository.findById(id);
        if (foundItem.isPresent()) {
            return foundItem;
        } else {
            throw new NoSuchElementException("Item not found");
        }
    }

    public void removeItem(Long id) {
        findItemById(id);
        itemRepository.deleteById(id);
    }

    public Item editItem(Item item) {
        findItemById(item.getId());
        isItemValid(item);
        itemRepository.save(item);
        return item;
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }
}
