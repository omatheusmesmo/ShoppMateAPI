package com.omatheusmesmo.Lista.de.Compras.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer quantity;
    private String category;
    private Boolean bought = false;

    public Item(String name, Integer quantity, String category) {
        this.name = name;
        this.quantity = quantity;
        this.category = category;
    }

    @Override
    public String toString() {
        return "\n" +
                "Item{" + "id=" + id + "," +
                " name='" + name + '\'' + "," +
                " quantity=" + quantity + "," +
                " category='" + category + '\'' + "," +
                " bought=" + bought + '}'+
                "\n"; }
}
