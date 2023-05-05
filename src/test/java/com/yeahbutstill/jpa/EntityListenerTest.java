package com.yeahbutstill.jpa;

import com.yeahbutstill.jpa.entity.Category;
import com.yeahbutstill.jpa.entity.Member;
import com.yeahbutstill.jpa.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class EntityListenerTest {

    @Test
    void testListener() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Category category = new Category();
        category.setName("Contoh 9");
        category.setDescription("Contoh 9");

        entityManager.persist(category);
        Assertions.assertNotNull(category.getId());
        Assertions.assertNotNull(category.getName());
        Assertions.assertNotNull(category.getDescription());
        Assertions.assertNotNull(category.getCreatedAt());
        Assertions.assertNull(category.getUpdatedAt());

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void testListenerUpdated() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Category category = entityManager.find(Category.class, UUID.fromString("3a7c449b-0c92-4353-ae9a-0746f93b09a0"));
        category.setName("Contoh 9aa");
        category.setDescription("Contoh 9aa");

        entityManager.merge(category);
        Assertions.assertNotNull(category.getId());
        Assertions.assertNotNull(category.getName());
        Assertions.assertNotNull(category.getDescription());
        Assertions.assertNotNull(category.getCreatedAt());
        Assertions.assertNull(category.getUpdatedAt());

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void testListenerEntity() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Member member = entityManager.find(Member.class, 1L);
        Assertions.assertNotNull(member);
        Assertions.assertEquals("Mr. Bob Test Smith", member.getFullName());

        entityTransaction.commit();
        entityManager.close();
    }

}
