package com.yeahbutstill.jpa;

import com.yeahbutstill.jpa.entity.Customer;
import com.yeahbutstill.jpa.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DataTypeTest {

    @Test
    void testDataType() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setPrimaryEmail("johndoe@me.com");
        customer.setAge((byte) 30);
        customer.setMarried(true);

        entityManager.persist(customer);
        Assertions.assertNotNull(customer.getId());
        Assertions.assertNotNull(customer.getName());
        Assertions.assertNotNull(customer.getPrimaryEmail());
        Assertions.assertNotNull(customer.getAge());
        Assertions.assertNotNull(customer.getMarried());

        entityTransaction.commit();
        entityManager.close();
    }

}
