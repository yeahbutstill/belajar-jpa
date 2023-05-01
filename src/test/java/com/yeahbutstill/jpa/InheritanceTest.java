package com.yeahbutstill.jpa;

import com.yeahbutstill.jpa.entity.Employee;
import com.yeahbutstill.jpa.entity.Manager;
import com.yeahbutstill.jpa.entity.VicePresident;
import com.yeahbutstill.jpa.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InheritanceTest {

    @Test
    void testSingleTableInsert() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Employee employee = new Employee();
        employee.setId("maya");
        employee.setName("Maya");
        entityManager.persist(employee);
        Assertions.assertNotNull(employee);

        Manager manager = new Manager();
        manager.setId("naimi");
        manager.setName("Naimi");
        manager.setTotalEmployee(10);
        entityManager.persist(manager);
        Assertions.assertNotNull(manager);

        VicePresident vicePresident = new VicePresident();
        vicePresident.setId("dani");
        vicePresident.setName("Dani");
        vicePresident.setTotalManager(5);
        entityManager.persist(vicePresident);
        Assertions.assertNotNull(vicePresident);

        transaction.commit();
        entityManager.close();

    }

    @Test
    void testSingleTableFind() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // query ke child
        Manager manager = entityManager.find(Manager.class, "naimi");
        Assertions.assertNotNull(manager);
        Assertions.assertEquals("Naimi", manager.getName());

        // query ke parent
        Employee employee = entityManager.find(Employee.class, "dani");
        VicePresident vicePresident = (VicePresident) employee;
        Assertions.assertNotNull(vicePresident);
        Assertions.assertEquals("Dani", vicePresident.getName());

        transaction.commit();
        entityManager.close();
    }

}
