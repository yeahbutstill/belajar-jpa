package com.yeahbutstill.jpa;

import com.yeahbutstill.jpa.entity.Brand;
import com.yeahbutstill.jpa.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

class CriteriaTest {

    @Test
    void testCriteriaQuery() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Brand> criteriaQuery = criteriaBuilder.createQuery(Brand.class);
        Root<Brand> b = criteriaQuery.from(Brand.class);

        // ini mirip seperti
        // SELECT b FROM Brand b
        criteriaQuery.select(b);

        TypedQuery<Brand> query = entityManager.createQuery(criteriaQuery);
        Stream<Brand> resultStream = query.getResultStream();

        resultStream.forEach(brand -> System.out.println(brand.getId() + " : " + brand.getName()));

        entityTransaction.commit();
        entityManager.close();

    }

}
