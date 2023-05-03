package com.yeahbutstill.jpa;

import com.yeahbutstill.jpa.entity.Brand;
import com.yeahbutstill.jpa.entity.Product;
import com.yeahbutstill.jpa.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class LockingTest {

    @Test
    void testOptimisticLocking() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Brand brand = new Brand();
        brand.setId("apple");
        brand.setName("Apple");
        brand.setDescription("Apple brand");
        brand.setCreatedAt(LocalDateTime.now());
        brand.setUpdatedAt(LocalDateTime.now());
        entityManager.persist(brand);
        Assertions.assertNotNull(brand);

        Product product = new Product();
        product.setId("p4");
        product.setBrand(brand);
        product.setName("Iphone 14 Pro");
        product.setDescription("Iphone 14 Pro");
        product.setPrice(21_249_000L);
        entityManager.persist(product);
        Assertions.assertNotNull(product);

        transaction.commit();
        entityManager.close();
    }

    @Test
    void testOptimisticLockingDemo1() throws InterruptedException {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Brand iphone = entityManager.find(Brand.class, "apple");
        iphone.setName("Iphone 14");
        iphone.setUpdatedAt(LocalDateTime.now());

        // Transaksi ini berjalan 10 detik baru selesai
        Thread.sleep(10 * 1000L);
        entityManager.persist(iphone);
        Assertions.assertNotNull(iphone);

        transaction.commit();
        entityManager.close();
    }

    @Test
    void testOptimisticLockingDemo2() throws InterruptedException {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Brand iphone = entityManager.find(Brand.class, "apple");
        iphone.setName("Iphone 14 Siapa cepat dia dapat");
        iphone.setUpdatedAt(LocalDateTime.now());

        entityManager.persist(iphone);
        Assertions.assertNotNull(iphone);

        transaction.commit();
        entityManager.close();
    }

    @Test
    void testPessimisticLocking1() throws InterruptedException {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Brand brand = entityManager.find(Brand.class, "apple", LockModeType.PESSIMISTIC_WRITE);
        brand.setName("Apple Updated");
        brand.setUpdatedAt(LocalDateTime.now());
        brand.setCreatedAt(LocalDateTime.now());
        Assertions.assertNotNull(brand);

        Thread.sleep(10 * 1000L);
        entityManager.persist(brand);

        transaction.commit();
        entityManager.close();
    }

    @Test
    void testPessimisticLocking2() throws InterruptedException {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Brand brand = entityManager.find(Brand.class, "apple", LockModeType.PESSIMISTIC_WRITE);
        brand.setName("Apple Updated");
        brand.setUpdatedAt(LocalDateTime.now());
        brand.setCreatedAt(LocalDateTime.now());
        Assertions.assertNotNull(brand);

        entityManager.persist(brand);

        transaction.commit();
        entityManager.close();
    }

}
