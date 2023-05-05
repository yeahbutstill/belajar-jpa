package com.yeahbutstill.jpa.listener;

import com.yeahbutstill.jpa.entity.CreatedUpdatedAtWare;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;
import java.util.Calendar;

public class CreatedAtAndUpdatedAtListener {

    // event annotation
    @PreUpdate // sebelum melakukan update data
    public void setLastUpdatedAt(CreatedUpdatedAtWare object) {
        object.setUpdatedAt(LocalDateTime.now());
    }

    @PrePersist // sebelum melakukan insert data
    public void setLastCreatedAt(CreatedUpdatedAtWare object) {
        object.setCreatedAt(Calendar.getInstance());
    }

}
