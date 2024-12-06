package com.omatheusmesmo.Lista.de.Compras.controller;

import com.omatheusmesmo.Lista.de.Compras.Entity.Item;
import com.omatheusmesmo.Lista.de.Compras.service.ItemService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Test
    public void testGetRetornarTodosItens() throws Exception{
        Item item1 = new Item("Feijão",1,"Alimentos");
        Item item2 = new Item("Arroz",2,"Alimentos");
        List<Item> todosItens = Arrays.asList(item1,item2);

        when(itemService.buscarTodos()).thenReturn(todosItens);

        mockMvc.perform(get("/item"))
                .andExpect(status().isOk())
                .andExpect(content().json("[" +
                        "{'nome':'Feijão','quantidade':1,'categoria':'Alimentos'}," +
                        "{'nome':'Arroz','quantidade':2,'categoria':'Alimentos'}" +
                        "]"));
    }

    @Test
    public void testPostAdicionarItem() throws Exception{
        Item novoItem = new Item("Feijão",1,"Alimentos");

        when(itemService.adicionarItem(any(Item.class))).thenReturn(novoItem);

        mockMvc.perform(post("/item")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"Feijão\",\"quantidade\":1,\"categoria\":\"Alimentos\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{'nome':'Feijão','quantidade':1,'categoria':'Alimentos'}"
                        ));
    }

    @Test
    public void testDeleteRemoverItem() throws Exception{
        Long id = 1L;

        Mockito.doNothing().when(itemService).removerItem(id);

        mockMvc.perform(delete("/item/"+id))
                .andExpect(status().isNoContent());

        verify(itemService, times(1)).removerItem(id);
    }

    @Test
    public void testPostEditarItem() throws Exception{
        Item itemEditado = new Item("Feijão",1,"Alimentos");

        when(itemService.editarItem(any(Item.class))).thenReturn(itemEditado);

        mockMvc.perform(put("/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Feijão\",\"quantidade\":1,\"categoria\":\"Alimentos\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{'nome':'Feijão','quantidade':1,'categoria':'Alimentos'}"
                ));
    }

}
