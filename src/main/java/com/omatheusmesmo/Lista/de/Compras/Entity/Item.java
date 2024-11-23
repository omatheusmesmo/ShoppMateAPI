package com.omatheusmesmo.Lista.de.Compras.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
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
    private String nome;
    private Integer quantidade;
    private String categoria;
    private Boolean comprado = false;

    public Item(String nome, Integer quantidade, String categoria) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "\n" +
                "Item{" + "id=" + id + "," +
                " nome='" + nome + '\'' + "," +
                " quantidade=" + quantidade + "," +
                " categoria='" + categoria + '\'' + "," +
                " comprado=" + comprado + '}'+
                "\n"; }
}
