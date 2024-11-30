package com.omatheusmesmo.Lista.de.Compras.service;

import com.omatheusmesmo.Lista.de.Compras.Entity.Item;
import com.omatheusmesmo.Lista.de.Compras.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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
    public void testAdicionarItem(){
        Item mockItem = new Item("Feijão",1,"Alimentos");

        when(itemRepository.save(any(Item.class))).thenReturn(mockItem);

        Item item = itemService.adicionarItem(mockItem);

        assertNotNull(item);
        assertEquals("Feijão",item.getNome());

        verify(itemRepository, times(1)).save(mockItem);
    }
}
