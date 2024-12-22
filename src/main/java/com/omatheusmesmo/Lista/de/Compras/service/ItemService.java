package com.omatheusmesmo.Lista.de.Compras.service;

import com.omatheusmesmo.Lista.de.Compras.entity.Item;
import com.omatheusmesmo.Lista.de.Compras.repository.ItemRepository;
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
            throw new IllegalArgumentException("O nome do item não pode ser nulo!");
        } else if (item.getName().isBlank()) {
            throw new IllegalArgumentException("Preencha o nome do item corretamente!");
        }

        if (item.getQuantity() == null) {
            throw new IllegalArgumentException("Quantidade do item não pode ser nula!");
        } else if (item.getQuantity() < 1) {
            throw new IllegalArgumentException("Quantidade do item deve ser superior a zero!");
        }
    }

    public Optional<Item> findItem(Item item) {
        Optional<Item> foundItem = itemRepository.findById(item.getId());
        if (foundItem.isPresent()) {
            return foundItem;
        } else {
            throw new NoSuchElementException("Item não encontrado");
        }
    }

    public Optional<Item> findItemById(Long id) {
        Optional<Item> foundItem = itemRepository.findById(id);
        if (foundItem.isPresent()) {
            return foundItem;
        } else {
            throw new NoSuchElementException("Item não encontrado");
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
