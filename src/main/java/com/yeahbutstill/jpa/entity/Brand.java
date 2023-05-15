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
@NamedQueries({
    @NamedQuery(name = "Brand.findAll", query = "SELECT B FROM Brand B"),
        @NamedQuery(name = "Brand.findAllByName", query = "SELECT B FROM Brand B WHERE B.name = :name"),
})
public class Brand extends AuditableEntity<String> {

    private String name;
    private String description;

    @OneToMany(mappedBy = "brand")
    private List<Product> products;

    @Version // di gunakan untuk locking
    private Long version;

}
