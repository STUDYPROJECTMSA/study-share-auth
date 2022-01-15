package com.auth.auth.user.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Builder
@Table(name = "users")
public class User {
    @Id
    private  Long id;

    private String name;

    private  String email;

    private String imageUrl;

    private  Role role;

    public User update(String name,  String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;

        return this;
    }
}
