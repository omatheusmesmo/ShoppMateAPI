package com.omatheusmesmo.shoppmate.list.service;

import com.omatheusmesmo.shoppmate.item.entity.Item;
import com.omatheusmesmo.shoppmate.item.service.ItemService;
import com.omatheusmesmo.shoppmate.list.entity.ListItem;
import com.omatheusmesmo.shoppmate.list.entity.ShoppingList;
import com.omatheusmesmo.shoppmate.list.repository.ListItemRepository;
import com.omatheusmesmo.shoppmate.shared.service.AuditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShoppingListItemServiceTest {

    @Mock
    private ListItemRepository ListItemRepository;
    @Mock
    private ShoppingListService shoppingListService;
    @Mock
    private ItemService itemService;
    @Mock
    private AuditService auditService;

    @InjectMocks
    private ListItemService service;

    private ListItem ListItem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ListItem = createSampleItem();
    }

    @Test
    void addShoppItemList() {
        when(ListItemRepository.save(ListItem)).thenReturn(ListItem);
        
        ListItem savedItem = service.addShoppItemList(ListItem);
        
        assertNotNull(savedItem);
        assertEquals(savedItem, ListItem);
        verify(itemService, times(1)).isItemValid(ListItem.getItem());
        verify(shoppingListService, times(1)).isListValid(ListItem.getShoppList());
        verify(auditService, times(1)).setAuditData(ListItem, true);
        verify(ListItemRepository, times(1)).save(ListItem);
    }

    @Test
    void isListItemValid_NoException() {

        assertDoesNotThrow( () -> service.isListItemValid(ListItem));

        verify(itemService, times(1)).isItemValid(ListItem.getItem());
        verify(itemService, times(1)).findById(1L);
        verify(shoppingListService, times(1)).isListValid(ListItem.getShoppList());
    }

    @Test
    void isListItemValid_InvalidItemQuantity() {
        ListItem.setQuantity(null);

        assertThrows(IllegalArgumentException.class, () -> service.isListItemValid(ListItem));

        verify(itemService, times(1)).isItemValid(ListItem.getItem());
        verify(itemService, times(1)).findById(1L);
        verify(shoppingListService, times(1)).isListValid(ListItem.getShoppList());
    }

    @Test
    void findListItem() {
        when(ListItemRepository.findById(ListItem.getId())).thenReturn(Optional.of(ListItem));

        ListItem result =  service.findListItem(ListItem);

        assertNotNull(result);

        verify(ListItemRepository,times(1)).findById(ListItem.getId());
    }

    @Test
    void findListItemById() {
        when(ListItemRepository.findById(ListItem.getId())).thenReturn(Optional.of(ListItem));

        ListItem result =  service.findListItemById(ListItem.getId());

        assertNotNull(result);

        verify(ListItemRepository,times(1)).findById(ListItem.getId());
    }

    @Test
    void findListItemById_WhenItemNotFound(){
        when(ListItemRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, ()->service.findListItemById(999L));

        verify(ListItemRepository, never()).save(any());
    }

    @Test
    void removeList_Ok() {
        when(ListItemRepository.findById(ListItem.getId())).thenReturn(Optional.of(ListItem));

        assertDoesNotThrow(()->service.removeList(ListItem.getId()));

        verify(ListItemRepository, times(1)).save(ListItem);
        verify(auditService, times(1)).softDelete(ListItem);
    }

    @Test
    void removeList_ItemNotFound() {
        when(ListItemRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, ()->service.removeList(999L));

        verify(ListItemRepository, never()).save(any());
        verify(auditService, never()).softDelete(any());
    }

    @Test
    void editList_Ok() {
        when(ListItemRepository.findById(ListItem.getId())).thenReturn(Optional.of(ListItem));

        assertDoesNotThrow(() -> service.editList(ListItem));

        verify(auditService, times(1)).setAuditData(ListItem,false);
        verify(ListItemRepository, times(1)).save(ListItem);
    }

    @Test
    void editList_WhenListItemNotFound() {
        when(ListItemRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.editList(ListItem));

        verify(ListItemRepository, times(1)).findById(ListItem.getId());
    }

    @Test
    void findAll() {

        assertDoesNotThrow(() -> service.findAll());

        verify(ListItemRepository, times(1)).findAll();
    }
    
    private ListItem createSampleItem(){
        
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setId(1L);
        
        Item item = new Item();
        item.setId(1L);

        return new ListItem(shoppingList,item,2,false);
    }
}