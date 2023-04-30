package com.yeahbutstill.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {

    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "brand_id",
            referencedColumnName = "id"
    )
    private Brand brand;

    private String name;
    private Long price;
    private String description;

    @ManyToMany(mappedBy = "likes")
    private Set<User> likedBy;

}
