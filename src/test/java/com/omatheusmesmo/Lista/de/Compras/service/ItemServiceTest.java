package com.omatheusmesmo.Lista.de.Compras.service;

import com.omatheusmesmo.Lista.de.Compras.entity.Item;
import com.omatheusmesmo.Lista.de.Compras.repository.ItemRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @Test
    public void testAddItem() {
        Item mockItem = new Item("Feijão", 1, "Alimentos");

        when(itemRepository.save(any(Item.class))).thenReturn(mockItem);

        Item item = itemService.addItem(mockItem);

        assertNotNull(item);
        assertEquals("Feijão", item.getName());

        verify(itemRepository, times(1)).save(mockItem);
    }

    @Test
    public void testCheckNameAndQuantityValidItem() {
        Item validItem = new Item("Feijão", 1, "Alimentos");
        assertDoesNotThrow(() -> itemService.checkNameAndQuantity(validItem));
        verifyNoInteractions(itemRepository);
    }

    @Test
    public void testCheckNameAndQuantityItemNameNull() {
        Item itemNameNull = new Item(null, 1, "Alimentos");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> itemService.checkNameAndQuantity(itemNameNull));
        assertEquals("The item name cannot be null!", exception.getMessage());
    }

    @Test
    public void testCheckNameAndQuantityItemNameBlank() {
        Item itemNameBlank = new Item(" ", 1, "Alimentos");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> itemService.checkNameAndQuantity(itemNameBlank));
        assertEquals("Enter a valid item name!", exception.getMessage());
    }

    @Test
    public void testCheckNameAndQuantityNullQuantity() {
        Item nullQuantityItem = new Item("Feijão", null, "Alimentos");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> itemService.checkNameAndQuantity(nullQuantityItem));
        assertEquals("The item quantity cannot be null!", exception.getMessage());
    }

    @Test
    public void testCheckNameAndQuantityLessThanOneQuantity() {
        Item lessThanOneQuantityItem = new Item("Feijão", 0, "Alimentos");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> itemService.checkNameAndQuantity(lessThanOneQuantityItem));
        assertEquals("Quantity must be greater than 0!", exception.getMessage());
    }

    @Test
    public void testFindItemPresent() {
        Item presentItem = new Item("Feijão", 1, "Alimentos");
        presentItem.setId(1L);

        when(itemRepository.findById(presentItem.getId())).thenReturn(Optional.of(presentItem));

        Optional<Item> result = itemService.findItem(presentItem);

        assertNotNull(result);
        verify(itemRepository, times(1)).findById(presentItem.getId());
    }

    @Test
    public void testFindItemNotPresent() {
        Item notPresentItem = new Item("Feijão não presente", 1, "Alimentos");
        notPresentItem.setId(99L);

        when(itemRepository.findById(notPresentItem.getId())).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> itemService.findItem(notPresentItem));
        assertEquals("Item not found", exception.getMessage());
    }

    @Test
    public void testFindAll() {
        List<Item> allItems = List.of(
                new Item("Feijão", 1, "Alimentos"),
                new Item("Arroz", 2, "Alimentos")
        );

        when(itemRepository.findAll()).thenReturn(allItems);

        List<Item> result = itemService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Feijão", result.get(0).getName());
        assertEquals("Arroz", result.get(1).getName());

        verify(itemRepository, times(1)).findAll();
    }

    @Test
    public void testEditItem() {
        Item editedItem = new Item("Feijão", 2, "Alimentos");
        editedItem.setId(1L);

        when(itemRepository.findById(editedItem.getId())).thenReturn(Optional.of(editedItem));
        when(itemRepository.save(editedItem)).thenReturn(editedItem);

        Item result = itemService.editItem(editedItem);

        assertNotNull(result);
        assertEquals("Feijão", result.getName());
        assertEquals(2, result.getQuantity());
        assertEquals("Alimentos", result.getCategory());

        verify(itemRepository, times(1)).save(editedItem);
    }

    @Test
    public void testRemoveItem() {
        Long id = 1L;

        Item item = new Item("Feijão", 1, "Alimentos");
        item.setId(id);

        when(itemRepository.findById(id)).thenReturn(Optional.of(item));

        assertDoesNotThrow(() -> itemService.removeItem(id));

        verify(itemRepository, times(1)).deleteById(id);
    }
}
