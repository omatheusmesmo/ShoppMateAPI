package com.omatheusmesmo.shoppmate.list.service;

import com.omatheusmesmo.shoppmate.item.entity.Item;
import com.omatheusmesmo.shoppmate.item.service.ItemService;
import com.omatheusmesmo.shoppmate.list.entity.ShoppList;
import com.omatheusmesmo.shoppmate.list.entity.ShoppListItem;
import com.omatheusmesmo.shoppmate.list.repository.ShoppListItemRepository;
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

class ShoppListItemServiceTest {

    @Mock
    private ShoppListItemRepository shoppListItemRepository;
    @Mock
    private ShoppListService shoppListService;
    @Mock
    private ItemService itemService;
    @Mock
    private AuditService auditService;

    @InjectMocks
    private ShoppListItemService service;

    private ShoppListItem listItem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        listItem = createSampleItem();
    }

    @Test
    void addShoppItemList() {
        when(shoppListItemRepository.save(listItem)).thenReturn(listItem);
        
        ShoppListItem savedItem = service.addShoppItemList(listItem);
        
        assertNotNull(savedItem);
        assertEquals(savedItem, listItem);
        verify(itemService, times(1)).isItemValid(listItem.getItem());
        verify(shoppListService, times(1)).isListValid(listItem.getShoppList());
        verify(auditService, times(1)).setAuditData(listItem, true);
        verify(shoppListItemRepository, times(1)).save(listItem);
    }

    @Test
    void isListItemValid_NoException() {

        assertDoesNotThrow( () -> service.isListItemValid(listItem));

        verify(itemService, times(1)).isItemValid(listItem.getItem());
        verify(itemService, times(1)).findItemById(1L);
        verify(shoppListService, times(1)).isListValid(listItem.getShoppList());
    }

    @Test
    void isListItemValid_InvalidItemQuantity() {
        listItem.setQuantity(null);

        assertThrows(IllegalArgumentException.class, () -> service.isListItemValid(listItem));

        verify(itemService, times(1)).isItemValid(listItem.getItem());
        verify(itemService, times(1)).findItemById(1L);
        verify(shoppListService, times(1)).isListValid(listItem.getShoppList());
    }

    @Test
    void findListItem() {
        when(shoppListItemRepository.findById(listItem.getId())).thenReturn(Optional.of(listItem));

        ShoppListItem result =  service.findListItem(listItem);

        assertNotNull(result);

        verify(shoppListItemRepository,times(1)).findById(listItem.getId());
    }

    @Test
    void findListItemById() {
        when(shoppListItemRepository.findById(listItem.getId())).thenReturn(Optional.of(listItem));

        ShoppListItem result =  service.findListItemById(listItem.getId());

        assertNotNull(result);

        verify(shoppListItemRepository,times(1)).findById(listItem.getId());
    }

    @Test
    void findListItemById_WhenItemNotFound(){
        when(shoppListItemRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, ()->service.findListItemById(999L));

        verify(shoppListItemRepository, never()).save(any());
    }

    @Test
    void removeList_Ok() {
        when(shoppListItemRepository.findById(listItem.getId())).thenReturn(Optional.of(listItem));

        assertDoesNotThrow(()->service.removeList(listItem.getId()));

        verify(shoppListItemRepository, times(1)).save(listItem);
        verify(auditService, times(1)).softDelete(listItem);
    }

    @Test
    void removeList_ItemNotFound() {
        when(shoppListItemRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, ()->service.removeList(999L));

        verify(shoppListItemRepository, never()).save(any());
        verify(auditService, never()).softDelete(any());
    }

    @Test
    void editList_Ok() {
        when(shoppListItemRepository.findById(listItem.getId())).thenReturn(Optional.of(listItem));

        assertDoesNotThrow(() -> service.editList(listItem));

        verify(auditService, times(1)).setAuditData(listItem,false);
        verify(shoppListItemRepository, times(1)).save(listItem);
    }

    @Test
    void editList_WhenListItemNotFound() {
        when(shoppListItemRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.editList(listItem));

        verify(shoppListItemRepository, times(1)).findById(listItem.getId());
    }

    @Test
    void findAll() {

        assertDoesNotThrow(() -> service.findAll());

        verify(shoppListItemRepository, times(1)).findAll();
    }
    
    private ShoppListItem createSampleItem(){
        
        ShoppList shoppList = new ShoppList();
        shoppList.setId(1L);
        
        Item item = new Item();
        item.setId(1L);

        return new ShoppListItem(shoppList,item,2,false);
    }
}