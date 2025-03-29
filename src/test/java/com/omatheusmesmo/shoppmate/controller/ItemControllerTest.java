package com.omatheusmesmo.shoppmate.controller;

import com.omatheusmesmo.shoppmate.entity.Item;
import com.omatheusmesmo.shoppmate.service.ItemService;
import com.omatheusmesmo.shoppmate.service.JwtService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemController.class)
@AutoConfigureMockMvc(addFilters = false)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    @WithMockUser
    void testGetAllItems() throws Exception {
        Item item1 = new Item("Feijão", 1, "Alimentos");
        Item item2 = new Item("Arroz", 2, "Alimentos");
        List<Item> allItems = Arrays.asList(item1, item2);

        when(itemService.findAll()).thenReturn(allItems);

        mockMvc.perform(get("/item"))
                .andExpect(status().isOk())
                .andExpect(content().json("[" +
                        "{'name':'Feijão','quantity':1,'category':'Alimentos'}," +
                        "{'name':'Arroz','quantity':2,'category':'Alimentos'}" +
                        "]"));
    }

    @Test
    @WithMockUser
    void testPostAddItem() throws Exception {
        Item newItem = new Item(1, "Feijão", "Alimentos");

        when(itemService.saveItem(any(Item.class))).thenReturn(newItem);

        mockMvc.perform(post("/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Feijão\",\"quantity\":1,\"category\":\"Alimentos\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().json(
                        "{'name':'Feijão','quantity':1,'category':'Alimentos'}"
                ));
    }

    @Test
    @WithMockUser
    void testDeleteRemoveItem() throws Exception {
        Long id = 1L;

        Mockito.doNothing().when(itemService).removeItem(id);

        mockMvc.perform(delete("/item/" + id))
                .andExpect(status().isNoContent());

        verify(itemService, times(1)).removeItem(id);
    }

    @Test
    @WithMockUser
    void testPutEditItem() throws Exception {
        Item editedItem = new Item("Feijão", 1, "Alimentos");

        when(itemService.editItem(any(Item.class))).thenReturn(editedItem);

        mockMvc.perform(put("/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Feijão\",\"quantity\":1,\"category\":\"Alimentos\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{'name':'Feijão','quantity':1,'category':'Alimentos'}"
                ));
    }

    @Test
    @WithMockUser
    void testPostAddItem_BadRequest() throws Exception {
        when(itemService.saveItem(any(Item.class))).thenThrow(new IllegalArgumentException());

        mockMvc.perform(post("/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"quantity\":0,\"category\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void testPutEditItem_NotFound() throws Exception {
        doThrow(new NoSuchElementException()).when(itemService).editItem(any(Item.class));

        mockMvc.perform(put("/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Feijão\",\"quantity\":1,\"category\":\"Alimentos\"}"))
                .andExpect(status().isNotFound());
    }
}