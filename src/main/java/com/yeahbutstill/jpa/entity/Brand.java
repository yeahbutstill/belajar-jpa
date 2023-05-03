package com.yeahbutstill.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
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
public class Brand extends AuditableEntity<String> {

    private String name;
    private String description;

    @OneToMany(mappedBy = "brand")
    private List<Product> products;

    @Version // di gunakan untuk locking
    private Long version;

}
