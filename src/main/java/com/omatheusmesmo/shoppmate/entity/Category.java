package com.omatheusmesmo.shoppmate.entity;

import com.omatheusmesmo.shoppmate.shared.domain.DomainEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="categories")
@Getter
@Setter
public class Category extends DomainEntity {
}
