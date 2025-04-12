package com.omatheusmesmo.shoppmate.list.mapper;

import com.omatheusmesmo.shoppmate.item.entity.Item;
import com.omatheusmesmo.shoppmate.item.mapper.ItemMapper; // Supondo que você tenha/crie um
import com.omatheusmesmo.shoppmate.item.repository.ItemRepository;
import com.omatheusmesmo.shoppmate.list.dtos.ListItemRequestDTO;
import com.omatheusmesmo.shoppmate.list.dtos.ListItemResponseDTO;
import com.omatheusmesmo.shoppmate.list.entity.ListItem;
import com.omatheusmesmo.shoppmate.list.entity.ShoppingList;
import com.omatheusmesmo.shoppmate.list.repository.ShoppingListRepository; // Precisa do repositório
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class ListItemMapper {

    @Autowired
    private ShoppingListRepository shoppingListRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ListMapper listMapper;

    @Autowired
    private ItemMapper itemMapper;

    public ListItem toEntity(ListItemRequestDTO dto) {
        ShoppingList shoppingList = shoppingListRepository.findById(dto.listId())
                .orElseThrow(() -> new NoSuchElementException("ShoppingList not found with ID: " + dto.listId()));

        Item item = itemRepository.findById(dto.itemId())
                .orElseThrow(() -> new NoSuchElementException("Item not found with ID: " + dto.itemId()));

        ListItem listItem = new ListItem();
        listItem.setShoppList(shoppingList);
        listItem.setItem(item);
        listItem.setQuantity(dto.quantity());
        return listItem;
    }

    public ListItemResponseDTO toResponseDTO(ListItem listItem) {
        return new ListItemResponseDTO(
                listMapper.toResponseDTO(listItem.getShoppList()),
                itemMapper.toResponseDTO(listItem.getItem()),
                listItem.getId(),
                listItem.getQuantity(),
                listItem.getPurchased()
        );
    }

    public void updateEntityFromDto(ListItemRequestDTO dto, ListItem entity) {
         ShoppingList shoppingList = shoppingListRepository.findById(dto.listId())
                 .orElseThrow(() -> new NoSuchElementException("ShoppingList not found with ID: " + dto.listId()));
         Item item = itemRepository.findById(dto.itemId())
                 .orElseThrow(() -> new NoSuchElementException("Item not found with ID: " + dto.itemId()));
         entity.setShoppList(shoppingList);
         entity.setItem(item);

        entity.setQuantity(dto.quantity());
    }

}