package com.yeahbutstill.jpa;

import com.yeahbutstill.jpa.entity.*;
import com.yeahbutstill.jpa.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

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

    @Test
    void testJoinedTableInsert() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        PaymentGopay gopay = new PaymentGopay();
        gopay.setId("gopay1");
        gopay.setAmount(100_000L);
        gopay.setGopayId("087777777");
        Assertions.assertNotNull(gopay);
        entityManager.persist(gopay);

        PaymentCreditCard creditCard = new PaymentCreditCard();
        creditCard.setId("cc1");
        creditCard.setAmount(1_000_000L);
        creditCard.setMaskedCard("4555-5455-5555");
        creditCard.setBank("BCA");
        Assertions.assertNotNull(creditCard);
        entityManager.persist(creditCard);

        transaction.commit();
        entityManager.close();
    }

    @Test
    void testJoinTableFindChild() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        PaymentGopay gopay = entityManager.find(PaymentGopay.class, "gopay1");
        Assertions.assertNotNull(gopay);
        Assertions.assertEquals("gopay1", gopay.getId());

        PaymentCreditCard creditCard = entityManager.find(PaymentCreditCard.class, "cc1");
        Assertions.assertNotNull(creditCard);
        Assertions.assertEquals("cc1", creditCard.getId());

        transaction.commit();
        entityManager.close();
    }

    @Test
    void testJoinTableFindParent() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Payment gopay = entityManager.find(Payment.class, "gopay1");
        Assertions.assertNotNull(gopay);
        Assertions.assertEquals("gopay1", gopay.getId());

        Payment creditCard = entityManager.find(Payment.class, "cc1");
        Assertions.assertNotNull(creditCard);
        Assertions.assertEquals("cc1", creditCard.getId());

        transaction.commit();
        entityManager.close();
    }

}
