package com.omatheusmesmo.shoppmate.list.service;

import com.omatheusmesmo.shoppmate.item.entity.Item;
import com.omatheusmesmo.shoppmate.item.service.ItemService;
import com.omatheusmesmo.shoppmate.list.entity.ShoppListItem;
import com.omatheusmesmo.shoppmate.list.entity.ShoppList;
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

    private ShoppListItem shoppListItem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        shoppListItem = createSampleItem();
    }

    @Test
    void addShoppItemList() {
        when(ListItemRepository.save(shoppListItem)).thenReturn(shoppListItem);
        
        ShoppListItem savedItem = service.addShoppItemList(shoppListItem);
        
        assertNotNull(savedItem);
        assertEquals(savedItem, shoppListItem);
        verify(itemService, times(1)).isItemValid(shoppListItem.getItem());
        verify(shoppingListService, times(1)).isListValid(shoppListItem.getShoppList());
        verify(auditService, times(1)).setAuditData(shoppListItem, true);
        verify(ListItemRepository, times(1)).save(shoppListItem);
    }

    @Test
    void isListItemValid_NoException() {

        assertDoesNotThrow( () -> service.isListItemValid(shoppListItem));

        verify(itemService, times(1)).isItemValid(shoppListItem.getItem());
        verify(itemService, times(1)).findById(1L);
        verify(shoppingListService, times(1)).isListValid(shoppListItem.getShoppList());
    }

    @Test
    void isListItemValid_InvalidItemQuantity() {
        shoppListItem.setQuantity(null);

        assertThrows(IllegalArgumentException.class, () -> service.isListItemValid(shoppListItem));

        verify(itemService, times(1)).isItemValid(shoppListItem.getItem());
        verify(itemService, times(1)).findById(1L);
        verify(shoppingListService, times(1)).isListValid(shoppListItem.getShoppList());
    }

    @Test
    void findListItem() {
        when(ListItemRepository.findById(shoppListItem.getId())).thenReturn(Optional.of(shoppListItem));

        ShoppListItem result =  service.findListItem(shoppListItem);

        assertNotNull(result);

        verify(ListItemRepository,times(1)).findById(shoppListItem.getId());
    }

    @Test
    void findListItemById() {
        when(ListItemRepository.findById(shoppListItem.getId())).thenReturn(Optional.of(shoppListItem));

        ShoppListItem result =  service.findListItemById(shoppListItem.getId());

        assertNotNull(result);

        verify(ListItemRepository,times(1)).findById(shoppListItem.getId());
    }

    @Test
    void findListItemById_WhenItemNotFound(){
        when(ListItemRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, ()->service.findListItemById(999L));

        verify(ListItemRepository, never()).save(any());
    }

    @Test
    void removeList_Ok() {
        when(ListItemRepository.findById(shoppListItem.getId())).thenReturn(Optional.of(shoppListItem));

        assertDoesNotThrow(()->service.removeList(shoppListItem.getId()));

        verify(ListItemRepository, times(1)).save(shoppListItem);
        verify(auditService, times(1)).softDelete(shoppListItem);
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
        when(ListItemRepository.findById(shoppListItem.getId())).thenReturn(Optional.of(shoppListItem));

        assertDoesNotThrow(() -> service.editList(shoppListItem));

        verify(auditService, times(1)).setAuditData(shoppListItem,false);
        verify(ListItemRepository, times(1)).save(shoppListItem);
    }

    @Test
    void editList_WhenListItemNotFound() {
        when(ListItemRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.editList(shoppListItem));

        verify(ListItemRepository, times(1)).findById(shoppListItem.getId());
    }

    @Test
    void findAll() {

        assertDoesNotThrow(() -> service.findAll());

        verify(ListItemRepository, times(1)).findAll();
    }
    
    private ShoppListItem createSampleItem(){
        
        ShoppList shoppList = new ShoppList();
        shoppList.setId(1L);
        
        Item item = new Item();
        item.setId(1L);

        return new ShoppListItem(shoppList,item,2,false);
    }
}