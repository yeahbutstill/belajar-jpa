package com.yeahbutstill.jpa;

import com.yeahbutstill.jpa.entity.Customer;
import com.yeahbutstill.jpa.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ColumnTest {

    @Test
    void testColumn() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Customer customer = new Customer();
        customer.setId("2");
        customer.setName("Dani Setiawan");
        customer.setPrimaryEmail("dani@gmail");

        entityManager.persist(customer);
        Assertions.assertNotNull(customer.getId());
        Assertions.assertNotNull(customer.getName());
        Assertions.assertNotNull(customer.getPrimaryEmail());

        entityTransaction.commit();
        entityManager.close();
    }

}
