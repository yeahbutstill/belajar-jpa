package com.yeahbutstill.jpa;

import com.yeahbutstill.jpa.entity.Member;
import com.yeahbutstill.jpa.entity.Name;
import com.yeahbutstill.jpa.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;

class EmbeddedTest {

    @Test
    void testInsertEmbeddedMember() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Member member = new Member();
        member.setEmail("b@b.com");

        Name name = new Name();
        name.setTitle("Mr");
        name.setFirstName("Bob");
        name.setMiddleName("Test");
        name.setLastName("Smith");
        member.setName(name);

        entityManager.persist(member);

        entityTransaction.commit();
        entityManager.close();
    }

}
