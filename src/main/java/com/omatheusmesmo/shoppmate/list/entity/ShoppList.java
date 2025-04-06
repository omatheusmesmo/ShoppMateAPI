package com.omatheusmesmo.shoppmate.list.entity;

import com.omatheusmesmo.shoppmate.user.entity.User;
import com.omatheusmesmo.shoppmate.shared.domain.DomainEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="lists")
@Getter
@Setter
public class ShoppList extends DomainEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id_user", nullable = false)
    private User owner;
}
