package com.domain.store.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "authorities")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String authority;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users = new HashSet<>();
}
