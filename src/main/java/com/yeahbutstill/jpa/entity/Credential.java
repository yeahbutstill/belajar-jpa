package com.yeahbutstill.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "credentials")
public class Credential {

    @Id
    private String id;

    private String email;
    private String password;

    @OneToOne(mappedBy = "credential") // di mapping berdasarkan property credential
    private User user;

}
