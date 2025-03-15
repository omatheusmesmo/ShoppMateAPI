package com.omatheusmesmo.shoppinglist.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="units")
public class Unit {

    @Id
    @Column(name = "id_unit")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String symbol;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private Boolean deleted;

}
