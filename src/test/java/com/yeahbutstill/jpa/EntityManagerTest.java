package com.yeahbutstill.jpa;

import com.yeahbutstill.jpa.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EntityManagerTest {

    @Test
    void createEntityManager() {
        EntityManagerFactory emf = JpaUtil.getEMF();
        EntityManager em = emf.createEntityManager();

        // operasi databasenya
        Assertions.assertNotNull(em);

        em.close();
    }

}
