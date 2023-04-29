package com.yeahbutstill.jpa;

import com.yeahbutstill.jpa.entity.Credential;
import com.yeahbutstill.jpa.entity.User;
import com.yeahbutstill.jpa.entity.Wallet;
import com.yeahbutstill.jpa.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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

}
