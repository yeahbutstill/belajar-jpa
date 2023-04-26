package com.yeahbutstill.jpa;

import com.yeahbutstill.jpa.entity.Category;
import com.yeahbutstill.jpa.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GeneratedValueTest {

    @Test
    void testGeneratedValue() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Category category = new Category();
        category.setName("PC");
        category.setDescription("PC Termurah");
        entityManager.persist(category);

        Assertions.assertNotNull(category.getId());
        Assertions.assertNotNull(category.getName());
        Assertions.assertNotNull(category.getDescription());

        entityTransaction.commit();
        entityManager.close();
    }

}
