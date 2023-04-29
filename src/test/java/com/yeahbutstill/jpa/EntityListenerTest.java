package com.yeahbutstill.jpa;

import com.yeahbutstill.jpa.entity.Category;
import com.yeahbutstill.jpa.entity.Member;
import com.yeahbutstill.jpa.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EntityListenerTest {

    @Test
    void testListener() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Category category = new Category();
        category.setName("Contoh");

        entityManager.persist(category);
        Assertions.assertNotNull(category.getId());
        Assertions.assertNotNull(category.getName());

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
