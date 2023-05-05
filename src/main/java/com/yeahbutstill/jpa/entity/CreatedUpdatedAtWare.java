package com.yeahbutstill.jpa.entity;

import java.time.LocalDateTime;
import java.util.Calendar;

public interface CreatedUpdatedAtWare {

    void setUpdatedAt(LocalDateTime updatedAt);
    void setCreatedAt(Calendar createdAt);

}
