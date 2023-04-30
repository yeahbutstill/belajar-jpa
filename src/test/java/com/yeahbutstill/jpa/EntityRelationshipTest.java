package com.yeahbutstill.jpa;

import com.yeahbutstill.jpa.entity.*;
import com.yeahbutstill.jpa.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

class EntityRelationshipTest {

    @Test
    void testOneToOnePersist() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Credential credential = new Credential();
        credential.setId("yeahbutstill");
        credential.setEmail("dani@yeahbutstill");
        credential.setPassword("rahasia");
        entityManager.persist(credential);
        Assertions.assertNotNull(credential.getId());

        User user = new User();
        user.setId("yeahbutstill");
        user.setName("Dani Setiawan");
        entityManager.persist(user);
        Assertions.assertNotNull(user.getId());

        transaction.commit();
        entityManager.close();
    }

    @Test
    void testOneToOneFind() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        User user = entityManager.find(User.class, "yeahbutstill");
        Assertions.assertNotNull(user.getCredential());
        Assertions.assertNotNull(user.getWallet());
        Assertions.assertEquals("yeahbutstill", user.getCredential().getId());
        Assertions.assertEquals("Dani Setiawan", user.getName());
        Assertions.assertEquals("dani@yeahbutstill", user.getCredential().getEmail());
        Assertions.assertEquals("rahasia", user.getCredential().getPassword());
        Assertions.assertEquals(1_000_000L, user.getWallet().getBalance());

        transaction.commit();
        entityManager.close();
    }

    @Test
    void testOneToOneJoinColumn() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        User user = entityManager.find(User.class, "yeahbutstill");
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setBalance(1_000_000L);
        entityManager.persist(wallet);
        Assertions.assertNotNull(wallet.getId());

        transaction.commit();
        entityManager.close();

    }

    @Test
    void testOneToManyInsert() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Brand brand = new Brand();
        brand.setId("samsung");
        brand.setName("Samsung");
        brand.setDescription("Samsung brand");
        entityManager.persist(brand);
        Assertions.assertNotNull(brand.getId());

        Product product1 = new Product();
        product1.setId("p1");
        product1.setName("Samsung Galaxy S10");
        product1.setBrand(brand);
        product1.setPrice(1_000_000L);
        product1.setDescription("Samsung Galaxy S10");
        entityManager.persist(product1);
        Assertions.assertNotNull(product1.getId());

        Product product2 = new Product();
        product2.setId("p2");
        product2.setName("Samsung Galaxy S15");
        product2.setBrand(brand);
        product2.setPrice(2_000_000L);
        product2.setDescription("Samsung Galaxy S15");
        entityManager.persist(product2);
        Assertions.assertNotNull(product2.getId());

        transaction.commit();
        entityManager.close();
    }

    @Test
    void testOneToManyFind() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Brand brand = entityManager.find(Brand.class, "samsung");
        Assertions.assertNotNull(brand.getProducts());
        Assertions.assertEquals(2, brand.getProducts().size());

        brand.getProducts().forEach(product -> {
            System.out.println(product.getName());
        });

        transaction.commit();
        entityManager.close();
    }

    @Test
    void testManyToManyInsert() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        User user = entityManager.find(User.class, "yeahbutstill");
        user.setLikes(new HashSet<>());

        Product product1 = entityManager.find(Product.class, "p1");
        Product product2 = entityManager.find(Product.class, "p2");

        user.getLikes().add(product1);
        user.getLikes().add(product2);

        entityManager.merge(user);
        Assertions.assertNotNull(user.getId());
        Assertions.assertNotNull(user.getLikes());

        transaction.commit();
        entityManager.close();
    }

    @Test
    void testManyToManyRemove() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        User user = entityManager.find(User.class, "yeahbutstill");
        Product product = null;
        for (Product item : user.getLikes()) {
            product = item;
            break;
        }

        user.getLikes().remove(product);
        entityManager.merge(user);

        transaction.commit();
        entityManager.close();

    }

}
