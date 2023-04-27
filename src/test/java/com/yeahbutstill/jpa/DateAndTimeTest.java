package com.yeahbutstill.jpa;

import com.yeahbutstill.jpa.entity.Category;
import com.yeahbutstill.jpa.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Calendar;

class DateAndTimeTest {

    @Test
    void testInsertDateAndTime() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Category category = new Category();
        category.setName("Food");
        category.setDescription("Macam-macam Makanan");
        category.setCreatedAt(Calendar.getInstance());
        category.setUpdatedAt(LocalDateTime.now());

        entityManager.persist(category);
        Assertions.assertNotNull(category.getId());
        Assertions.assertNotNull(category.getName());
        Assertions.assertNotNull(category.getDescription());
        Assertions.assertNotNull(category.getCreatedAt());
        Assertions.assertNotNull(category.getUpdatedAt());

        entityTransaction.commit();
        entityManager.close();
    }

}
