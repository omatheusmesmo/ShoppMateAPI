package com.omatheusmesmo.Lista.de.Compras.service;

import com.omatheusmesmo.Lista.de.Compras.Entity.Item;
import com.omatheusmesmo.Lista.de.Compras.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Null;
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

    @Test
    public void testVerificaNomeQuantidadeItemValido(){
        Item itemValido = new Item("Feijão",1,"Alimentos");
        assertDoesNotThrow(()->itemService.verificaNomeQuantidade(itemValido));
    }

    @Test
    public void testVerificaNomeQuantidadeItemNomeNulo(){
        Item itemNomeNulo = new Item(null,1,"Alimentos");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                ()->itemService.verificaNomeQuantidade(itemNomeNulo));
        assertEquals("O nome do item não pode ser nulo!", exception.getMessage());
    }

    @Test
    public void testVerificaNomeQuantidadeItemNomeEmBranco(){
        Item itemNomeEmBranco = new Item(" ",1,"Alimentos");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                ()->itemService.verificaNomeQuantidade(itemNomeEmBranco));
        assertEquals("Preencha o nome do item corretamente!", exception.getMessage());
    }

    @Test
    public void testVerificaNomeQuantidadeNula(){
        Item itemQuantidadeNula = new Item("Feijão", null,"Alimentos");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                ()->itemService.verificaNomeQuantidade(itemQuantidadeNula));
        assertEquals("Quantidade do item não pode ser nula!", exception.getMessage());
    }

    @Test
    public void testVerificaNomeQuantidadeMenorQueUm(){
        Item itemQuantidadeMenorQueUm = new Item("Feijão", 0,"Alimentos");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                ()->itemService.verificaNomeQuantidade(itemQuantidadeMenorQueUm));
        assertEquals("Quantidade do item deve ser superior a zero!", exception.getMessage());
    }
}
