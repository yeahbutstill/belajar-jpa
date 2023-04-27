package com.yeahbutstill.jpa.entity;

import com.yeahbutstill.jpa.enums.CustomerType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "customers")
public class Customer {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    private String name;

    // properties ini maka secara automatis akan di ignore ketika proses manipulasi data ke database
    @Transient
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CustomerType type;

    @Column(name = "primary_email")
    private String primaryEmail;

    // smallint kalau di postgre
    // pastikan juga menggunakan tipe data object di entitiy, harus menggunakan tipe data object,
    // jangan yang non object "byte, int, boolean" karena di database itu tipe datanya object jadi bisa null.
    // kalau kita menggunakan tipe data primitive(yang bukan object) itu tidak bisa null
    private Byte age;

    private Boolean married;

}
