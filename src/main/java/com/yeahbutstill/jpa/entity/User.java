package com.yeahbutstill.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    private String id;

    private String name;

    @OneToOne
    @PrimaryKeyJoinColumn(
            name = "id", // column primary key
            referencedColumnName = "id" // column reference dari table credential
    )
    private Credential credential;

    @OneToOne(mappedBy = "user")
    private Wallet wallet;

}
