package com.yeahbutstill.jpa;

import com.yeahbutstill.jpa.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TransactionTest {

    @Test
    void transaction() {
        EntityManagerFactory emf = JpaUtil.getEMF();
        EntityManager em = emf.createEntityManager();
        EntityTransaction entityTransaction = em.getTransaction();

        Assertions.assertNotNull(entityTransaction);

        try {
            entityTransaction.begin();
            // manipulasi database
            entityTransaction.commit();
        } catch (Exception e) {
            // kalau ada masalah kita bisa rollback
            e.printStackTrace();
            entityTransaction.rollback();
        }

        em.close();
    }

}
