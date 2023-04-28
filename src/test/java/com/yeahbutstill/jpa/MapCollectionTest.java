package com.yeahbutstill.jpa;

import com.yeahbutstill.jpa.entity.Member;
import com.yeahbutstill.jpa.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

class MapCollectionTest {

    // behafiornya sama dengan Collection, jadi kalau nanti find abis itu update lagi hibernate akan menghapus data yang ada di collection
    // lalu akan insert ulang semuanya, perlu diperhatikan jangan ada relasi ke table lain di table skill ini
    @Test
    void testUpdateSkillUsingMapCollection() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Member member = entityManager.find(Member.class, 2L);
        member.setSkills(new HashMap<>());
        member.getSkills().put("Java", 90);
        member.getSkills().put("PostgreSQL", 80);
        member.getSkills().put("Spring Boot", 60);
        member.getSkills().put("Docker", 80);

        entityManager.merge(member);
        Assertions.assertNotNull(member.getId());
        Assertions.assertNotNull(member.getSkills());

        entityTransaction.commit();
        entityManager.close();
    }

}
