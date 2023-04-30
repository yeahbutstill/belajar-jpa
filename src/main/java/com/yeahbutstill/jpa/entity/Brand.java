package com.yeahbutstill.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "brands")
public class Brand {

    @Id
    private String id;

    private String name;
    private String description;

    @OneToMany(mappedBy = "brand")
    private List<Product> products;

}
