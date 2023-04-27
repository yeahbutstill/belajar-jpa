package com.yeahbutstill.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Lob @Basic(fetch = FetchType.LAZY)
    @Column(name = "description")
    private String description;

    @Lob @Basic(fetch = FetchType.LAZY)
    @Column(name = "image", columnDefinition = "bytea")
    private byte[] image;

}
