package com.omatheusmesmo.Lista.de.Compras.controller;

import com.omatheusmesmo.Lista.de.Compras.Entity.Item;
import com.omatheusmesmo.Lista.de.Compras.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    @Autowired
    ItemService itemService;

    @GetMapping("/item")
    public List<Item> retornarTodosItens(){
        return itemService.buscarTodos();
    }

    @PostMapping("/item")
    public Item adicionarItem(@RequestBody Item item){
        itemService.adicionarItem(item);
        return item;
    }

    @DeleteMapping("/item")
    public ResponseEntity<Void> removerItem(Long id){
        itemService.removerItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/item")
    public Item editarItem(@RequestBody Item item){
        itemService.editarItem(item);
        return item;
    }


}
