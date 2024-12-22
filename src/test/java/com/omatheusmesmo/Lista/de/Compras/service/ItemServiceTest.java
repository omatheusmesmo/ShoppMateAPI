package com.omatheusmesmo.Lista.de.Compras.service;

import com.omatheusmesmo.Lista.de.Compras.entity.Item;
import com.omatheusmesmo.Lista.de.Compras.repository.ItemRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
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
    }

    @Test
    public void testCheckNameAndQuantityItemNameNull() {
        Item itemNameNull = new Item(null, 1, "Alimentos");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> itemService.checkNameAndQuantity(itemNameNull));
        assertEquals("O nome do item não pode ser nulo!", exception.getMessage());
    }

    @Test
    public void testCheckNameAndQuantityItemNameBlank() {
        Item itemNameBlank = new Item(" ", 1, "Alimentos");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> itemService.checkNameAndQuantity(itemNameBlank));
        assertEquals("Preencha o nome do item corretamente!", exception.getMessage());
    }

    @Test
    public void testCheckNameAndQuantityNullQuantity() {
        Item nullQuantityItem = new Item("Feijão", null, "Alimentos");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> itemService.checkNameAndQuantity(nullQuantityItem));
        assertEquals("Quantidade do item não pode ser nula!", exception.getMessage());
    }

    @Test
    public void testCheckNameAndQuantityLessThanOneQuantity() {
        Item lessThanOneQuantityItem = new Item("Feijão", 0, "Alimentos");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> itemService.checkNameAndQuantity(lessThanOneQuantityItem));
        assertEquals("Quantidade do item deve ser superior a zero!", exception.getMessage());
    }

    @Test
    public void testFindItemPresent() {
        Item presentItem = new Item("Feijão", 1, "Alimentos");
        presentItem.setId(1L);

        when(itemRepository.findById(presentItem.getId())).thenReturn(Optional.of(presentItem));

        assertDoesNotThrow(() -> itemService.findItem(presentItem));
    }

    @Test
    public void testFindItemNotPresent() {
        Item notPresentItem = new Item("Feijão não presente", 1, "Alimentos");
        notPresentItem.setId(99L);

        when(itemRepository.findById(notPresentItem.getId())).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> itemService.findItem(notPresentItem));
        assertEquals("Item não encontrado", exception.getMessage());
    }

    @Test
    public void testFindAll() {
        List<Item> allItems = new ArrayList<>();

        when(itemRepository.findAll()).thenReturn(allItems);

        assertDoesNotThrow(() -> itemService.findAll());
    }

    @Test
    public void testEditItem() {
        Item editedItem = new Item("Feijão", 2, "Alimentos");
        editedItem.setId(1L);

        when(itemRepository.findById(editedItem.getId())).thenReturn(Optional.of(editedItem));

        when(itemRepository.save(editedItem)).thenReturn(editedItem);

        assertDoesNotThrow(() -> itemService.editItem(editedItem));

        verify(itemRepository, times(1)).save(editedItem);

        Item result = itemService.editItem(editedItem);
        assertEquals("Feijão", result.getName());
        assertEquals(2, result.getQuantity());
        assertEquals("Alimentos", result.getCategory());
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
