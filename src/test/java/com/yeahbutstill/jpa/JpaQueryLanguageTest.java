package com.yeahbutstill.jpa;

import com.yeahbutstill.jpa.entity.Brand;
import com.yeahbutstill.jpa.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;

import java.util.List;

class JpaQueryLanguageTest {

    @Test
    void testSelectJPAQL() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        TypedQuery<Brand> query = entityManager.createQuery("SELECT B FROM Brand B", Brand.class);
        // using map
        query.getResultStream().forEach(brand -> System.out.println(brand.getId() + " : " + brand.getName()));

        System.out.println("---------------------------------------------------");

        // using list
        List<Brand> brands = query.getResultList();

        for (Brand brand : brands) {
            System.out.println(brand.getId() + " : " + brand.getName());
        }

        entityTransaction.commit();
        entityManager.close();

    }

}
