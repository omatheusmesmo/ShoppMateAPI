package com.omatheusmesmo.shoppmate.entity;

import com.omatheusmesmo.shoppmate.shared.domain.BaseAuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseAuditableEntity {

    private String email;

    @Column(name = "full_name")
    private String fullName;

    private String password;
    private String role = "USER";

}
