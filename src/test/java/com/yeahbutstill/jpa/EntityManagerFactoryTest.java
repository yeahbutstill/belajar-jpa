package com.yeahbutstill.jpa;

import com.yeahbutstill.jpa.utils.JpaUtil;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EntityManagerFactoryTest {

    @Test
    void createEntityManagerFactory() {
        EntityManagerFactory emf = JpaUtil.getEMF();
        Assertions.assertNotNull(emf);
    }

}
