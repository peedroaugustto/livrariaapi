package com.peedroaugustto.libraryapi.model;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.UUID;

@Entity
@Table
@Data
public class Usuario {

    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private String login;

    @Column
    private String senha;

    @Column
    private String email;

    @Column(name = "roles", columnDefinition = "varchar[]")
    @Type(ListArrayType.class)
    List<String> roles;
}
