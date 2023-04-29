package com.yeahbutstill.jpa.listener;

import com.yeahbutstill.jpa.entity.UpdatedAtWare;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

public class UpdatedAtListener {

    @PrePersist
    @PreUpdate
    public void setLastUpdatedAt(UpdatedAtWare object) {
        object.setUpdatedAt(LocalDateTime.now());
    }

}
