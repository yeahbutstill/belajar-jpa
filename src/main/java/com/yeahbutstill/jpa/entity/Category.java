package com.yeahbutstill.jpa.entity;

import com.yeahbutstill.jpa.listener.UpdatedAtListener;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.annotation.Annotation;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners({UpdatedAtListener.class})
@Table(name = "categories")
public class Category implements UpdatedAtWare {

    @Id
    // jangan pake yang AUTO
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String description;

    // terlalu lawas menggunakan java util Calendar atau java util Date
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Calendar createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
