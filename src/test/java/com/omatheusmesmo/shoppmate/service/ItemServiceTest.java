package com.omatheusmesmo.shoppmate.service;

import com.omatheusmesmo.shoppmate.entity.Item;
import com.omatheusmesmo.shoppmate.repository.ItemRepository;
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
    public void testSaveItem() {
       Item mockItem = new Item();

        when(itemRepository.save(any(Item.class))).thenReturn(mockItem);

        Item item = itemService.saveItem(mockItem);

        assertNotNull(item);
        assertEquals("Feijão", item.getName());

        verify(itemRepository, times(1)).save(mockItem);
    }

    @Test
    public void testIsItemValidValidItem() {
        Item validItem = new Item();
        assertDoesNotThrow(() -> itemService.checkName(validItem));
        verifyNoInteractions(itemRepository);
    }

    @Test
    public void testCheckNameAndQuantityItemNameNull() {
        Item itemNameNull = new Item();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> itemService.checkNameAndQuantity(itemNameNull));
        assertEquals("The item name cannot be null!", exception.getMessage());
    }

    @Test
    public void testCheckNameAndQuantityItemNameBlank() {
        Item itemNameBlank = new Item();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> itemService.checkNameAndQuantity(itemNameBlank));
        assertEquals("Enter a valid item name!", exception.getMessage());
    }

    @Test
    public void testIsItemValid() {
        Item nullQuantityItem = new Item();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> itemService.checkNameAndQuantity(nullQuantityItem));
        assertEquals("The item quantity cannot be null!", exception.getMessage());
    }

    @Test
    public void testIsItemValid() {
        Item lessThanOneQuantityItem = new Item();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> itemService.checkNameAndQuantity(lessThanOneQuantityItem));
        assertEquals("Quantity must be greater than 0!", exception.getMessage());
    }

    @Test
    public void testFindItemPresent() {
        Item presentItem = new Item();
        presentItem.setId(1L);

        when(itemRepository.findById(presentItem.getId())).thenReturn(Optional.of(presentItem));

        Optional<Item> result = itemService.findItem(presentItem);

        assertNotNull(result);
        verify(itemRepository, times(1)).findById(presentItem.getId());
    }

    @Test
    public void testFindItemNotPresent() {
        Item notPresentItem = new Item();
        notPresentItem.setId(99L);

        when(itemRepository.findById(notPresentItem.getId())).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> itemService.findItem(notPresentItem));
        assertEquals("Item not found", exception.getMessage());
    }

    @Test
    public void testFindAll() {
        List<Item> allItems = List.of(
                new Item(),
                new Item()
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
        Item editedItem = new Item();
        editedItem.setId(1L);

        when(itemRepository.findById(editedItem.getId())).thenReturn(Optional.of(editedItem));
        when(itemRepository.save(editedItem)).thenReturn(editedItem);

        Item result = itemService.editItem(editedItem);

        assertNotNull(result);
        assertEquals("Feijão", result.getName());
        assertEquals(2, result.);
        assertEquals("Alimentos", result.getCategory());

        verify(itemRepository, times(1)).save(editedItem);
    }

    @Test
    public void testRemoveItem() {
        Long id = 1L;

        Item item = new Item();
        item.setId(id);

        when(itemRepository.findById(id)).thenReturn(Optional.of(item));

        assertDoesNotThrow(() -> itemService.removeItem(id));

        verify(itemRepository, times(1)).deleteById(id);
    }
}
