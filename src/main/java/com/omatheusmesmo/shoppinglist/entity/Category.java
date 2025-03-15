package com.omatheusmesmo.shoppinglist.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="categories")
public class Category {

    @Id
    @Column(name = "id_category", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    private Boolean deleted;
}
