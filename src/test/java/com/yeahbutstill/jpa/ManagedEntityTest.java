package com.yeahbutstill.jpa;

import com.yeahbutstill.jpa.entity.Brand;
import com.yeahbutstill.jpa.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;

class ManagedEntityTest {

    @Test
    void testManagedEntity() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        // unmanaged entity
        Brand brand = new Brand();
        brand.setId("apple-id");
        brand.setName("Apple");
        entityManager.persist(brand); // managed entity

        brand.setName("Apple Indonesia");

        entityTransaction.commit();
        entityManager.close();

    }

    @Test
    void testFindManagedEntity() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        // managed entity
        Brand brand = entityManager.find(Brand.class, "apple");
        brand.setName("Apple Indonesia");

        entityTransaction.commit();
        entityManager.close();

    }

    @Test
    void testDetachEntity() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        // managed entity
        Brand brand = entityManager.find(Brand.class, "apple");
        entityManager.detach(brand); // unmanaged entity
        brand.setName("Apple Indonesia");

        entityTransaction.commit();
        entityManager.close();

    }

    @Test
    void testFindManagedEntityAfterTransaction() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        // managed entity
        Brand brand = entityManager.find(Brand.class, "apple");

        entityTransaction.commit();
        entityManager.close();

        brand.setName("Apple Indonesia");

    }

}
