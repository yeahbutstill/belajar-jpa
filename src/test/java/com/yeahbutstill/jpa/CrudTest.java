package com.yeahbutstill.jpa;

import com.yeahbutstill.jpa.entity.Customer;
import com.yeahbutstill.jpa.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CrudTest {

    private EntityManagerFactory emf;

    @BeforeEach
    void setUp() {
        emf = JpaUtil.getEMF();
    }

    @Test
    void testInsert() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction entityTransaction = em.getTransaction();
        entityTransaction.begin();

        Customer customer = new Customer();
        customer.setName("dani");

        // persist ini digunakan untuk memasukan data ke database
        em.persist(customer);
        Assertions.assertNotNull(customer.getId());
        Assertions.assertNotNull(customer.getName());

        entityTransaction.commit();
        em.close();
    }

    @Test
    void testFind() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Customer customer = entityManager.find(Customer.class, "1");
        Assertions.assertNotNull(customer);
        Assertions.assertEquals("dani", customer.getName());
        Assertions.assertEquals("1", customer.getId());

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void testUpdate() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Customer customer = entityManager.find(Customer.class, "1");
        customer.setName("Dani Setiawan");

        // merge ini digunakan untuk mengupdate data
        entityManager.merge(customer);
        Assertions.assertNotNull(customer.getId());
        Assertions.assertNotNull(customer.getName());

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void testDelete() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Customer customer = entityManager.find(Customer.class, "1");
        entityManager.remove(customer);
        Assertions.assertNotNull(customer.getId());

        entityTransaction.commit();
        entityManager.close();
    }

}
