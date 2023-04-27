package com.yeahbutstill.jpa;

import com.yeahbutstill.jpa.entity.Department;
import com.yeahbutstill.jpa.entity.DepartmentId;
import com.yeahbutstill.jpa.entity.Member;
import com.yeahbutstill.jpa.entity.Name;
import com.yeahbutstill.jpa.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
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

    @Test
    void testEmbeddedId() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        DepartmentId departmentId = new DepartmentId();
        departmentId.setCompanyId("yeahbutstill");
        departmentId.setDepartmentId("tech");

        Department department = new Department();
        department.setId(departmentId);
        department.setName("Technologies");

        entityManager.persist(department);

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void testEmbeddedIdFind() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        DepartmentId departmentId = new DepartmentId();
        departmentId.setCompanyId("yeahbutstill");
        departmentId.setDepartmentId("tech");

        Department department = entityManager.find(Department.class, departmentId);
        Assertions.assertEquals("Technologies", department.getName());

        entityTransaction.commit();
        entityManager.close();
    }
}
