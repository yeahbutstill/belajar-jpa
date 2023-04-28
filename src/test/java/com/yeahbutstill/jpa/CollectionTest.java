package com.yeahbutstill.jpa;

import com.yeahbutstill.jpa.entity.Member;
import com.yeahbutstill.jpa.entity.Name;
import com.yeahbutstill.jpa.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class CollectionTest {

    @Test
    void testCreateCollection() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Name name = new Name();
        name.setTitle("Mr");
        name.setFirstName("John");
        name.setMiddleName("Doe");
        name.setLastName("Smith");

        Member member = new Member();
        member.setEmail("kXjJG@example.com");
        member.setName(name);

        member.setHobbies(new ArrayList<>());
        member.getHobbies().add("Coding");
        member.getHobbies().add("Reading");
        member.getHobbies().add("Swimming");
        member.getHobbies().add("Sleeping");

        entityManager.persist(member);
        Assertions.assertNotNull(member.getId());
        Assertions.assertNotNull(member.getName());
        Assertions.assertNotNull(member.getEmail());
        Assertions.assertNotNull(member.getHobbies());

        entityTransaction.commit();
        entityManager.close();

    }

    @Test
    void testUpdateCollection() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Member member = entityManager.find(Member.class, 2L);
        member.getHobbies().add("Gamming");

        entityManager.merge(member);
        Assertions.assertNotNull(member.getId());
        Assertions.assertNotNull(member.getName());
        Assertions.assertNotNull(member.getEmail());
        Assertions.assertNotNull(member.getHobbies());

        entityTransaction.commit();
        entityManager.close();

    }

}
