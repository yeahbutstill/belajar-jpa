package com.yeahbutstill.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "hobbies", joinColumns = @JoinColumn(
            name = "member_id", referencedColumnName = "id"
    ))
    @Column(name = "name") // kenapa name? karena di colum pada table hobbies akan menggabil column name yang akan diambil list of string
    private List<String> hobbies;

    @ElementCollection
    @CollectionTable(name = "skills", joinColumns = @JoinColumn(
            name = "member_id", referencedColumnName = "id"
    ))
    @MapKeyColumn(name = "name") // untuk map keynya di ambil dari colum name dari table skills
    @Column(name = "value") // columnya di ambil dari value, yang akan diambil dari map value pada table skills
    private Map<String, Integer> skills;

    @Transient
    private String fullName;

    @Embedded
    private Name name;

    private String email;

    @PostLoad
    public void postLoad() {
        // ini setelah setiap kali query ke database untuk data member
        fullName = name.getTitle()
                + ". " + name.getFirstName()
                + " " + name.getMiddleName()
                + " " + name.getLastName();
    }

}
