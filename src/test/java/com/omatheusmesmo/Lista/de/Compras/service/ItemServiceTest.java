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
    public void testAdicionarItem(){
        Item mockItem = new Item("Feijão",1,"Alimentos");

        when(itemRepository.save(any(Item.class))).thenReturn(mockItem);

        Item item = itemService.adicionarItem(mockItem);

        assertNotNull(item);
        assertEquals("Feijão",item.getName());

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

    @Test
    public void testBuscarItemPresente(){
        Item itemPresente = new Item("Feijão",1,"Alimentos");
        itemPresente.setId(1L);

        when(itemRepository.findById(itemPresente.getId())).thenReturn(Optional.of(itemPresente));

        assertDoesNotThrow(()-> itemService.buscarItem(itemPresente));
    }

    @Test
    public void testBuscarItemNaoPresente(){
        Item itemNaoPresente = new Item("Feijão não presente",1,"Alimentos");
        itemNaoPresente.setId(99L);

        when(itemRepository.findById(itemNaoPresente.getId())).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                ()-> itemService.buscarItem(itemNaoPresente));
        assertEquals("Item não encontrado",exception.getMessage());
    }

    @Test
    public void testBuscarTodos(){
        List<Item> todosItens = new ArrayList<>();

        when(itemRepository.findAll()).thenReturn(todosItens);

        assertDoesNotThrow(()->itemService.buscarTodos());
    }

    @Test
    public void testEditarItem(){
        Item itemEditado = new Item("Feijão",2,"Alimentos");
        itemEditado.setId(1L);

        when(itemRepository.findById(itemEditado.getId())).thenReturn(Optional.of(itemEditado));

        when(itemRepository.save(itemEditado)).thenReturn(itemEditado);

        assertDoesNotThrow(()->itemService.editarItem(itemEditado));

        verify(itemRepository, times(1)).save(itemEditado);

        Item resultado = itemService.editarItem(itemEditado);
        assertEquals("Feijão", resultado.getName());
        assertEquals(2, resultado.getQuantity());
        assertEquals("Alimentos",resultado.getCategory());
    }

    @Test
    public void testRemoverItem(){
        Long id = 1L;

        Item item = new Item("Feijão", 1, "Alimentos");
        item.setId(id);

        when(itemRepository.findById(id)).thenReturn(Optional.of(item));

        assertDoesNotThrow(()->itemService.removerItem(id));

        verify(itemRepository, times(1)).deleteById(id);
    }
}
