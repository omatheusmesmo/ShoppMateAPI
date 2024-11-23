package com.omatheusmesmo.Lista.de.Compras.service;

import com.omatheusmesmo.Lista.de.Compras.Entity.Item;
import com.omatheusmesmo.Lista.de.Compras.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item adicionarItem(Item item){
        verificaNomeQuantidade(item);
        itemRepository.save(item);
        return item;
    }

    private void verificaNomeQuantidade(Item item){
        if (item.getNome() == null) {
            throw new IllegalArgumentException("O nome do item não pode ser nulo!");
        } else if (item.getNome().isBlank()) {
            throw new IllegalArgumentException("Preencha o nome do item corretamente!");
        }

        if(item.getQuantidade() ==null){
            throw new IllegalArgumentException("Quantidade do item não pode ser nula!");
        } else if (item.getQuantidade() < 1) {
            throw new IllegalArgumentException("Quantidade do item deve ser superior a 0");
        }
    }

    public Optional<Item> buscarItem(Item item){
        Optional<Item> itemBuscado = itemRepository.findById(item.getId());
        if(itemBuscado.isPresent()){
            return itemBuscado;
        }else {
            throw new NoSuchElementException("Item não encontrado");
        }
    }

    public Optional<Item> buscarItemPorId(Long id){
        Optional<Item> itemBuscado = itemRepository.findById(id);
        if(itemBuscado.isPresent()){
            return itemBuscado;
        }else {
            throw new NoSuchElementException("Item não encontrado");
        }
    }

    public void removerItem(Long id){
        buscarItemPorId(id);
        itemRepository.deleteById(id);
    }

    public Item editarItem(Item item){
        buscarItemPorId(item.getId());
        verificaNomeQuantidade(item);
        itemRepository.save(item);
        return item;
    }

    public List<Item> buscarTodos(){
        return itemRepository.findAll();
    }

}
