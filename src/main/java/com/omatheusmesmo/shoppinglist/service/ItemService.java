package com.omatheusmesmo.shoppinglist.service;

import com.omatheusmesmo.shoppinglist.entity.Item;
import com.omatheusmesmo.shoppinglist.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item addItem(Item item) {
        checkNameAndQuantity(item);
        itemRepository.save(item);
        return item;
    }

    public void checkNameAndQuantity(Item item) {
        if (item.getName() == null) {
            throw new IllegalArgumentException("The item name cannot be null!");
        } else if (item.getName().isBlank()) {
            throw new IllegalArgumentException("Enter a valid item name!");
        }

        if (item.getQuantity() == null) {
            throw new IllegalArgumentException("The item quantity cannot be null!");
        } else if (item.getQuantity() < 1) {
            throw new IllegalArgumentException("Quantity must be greater than 0!");
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
        checkNameAndQuantity(item);
        itemRepository.save(item);
        return item;
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }
}
