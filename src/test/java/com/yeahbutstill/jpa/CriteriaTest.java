package com.yeahbutstill.jpa;

import com.yeahbutstill.jpa.entity.Brand;
import com.yeahbutstill.jpa.entity.SimpleBrand;
import com.yeahbutstill.jpa.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Test;

import java.util.List;
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

    @Test
    void testCriteriaNonEntity() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Object[]> criteria = criteriaBuilder.createQuery(Object[].class);
        Root<Brand> b = criteria.from(Brand.class);
        criteria.select(criteriaBuilder.array(b.get("id"), b.get("name")));
        // select b.id, b.name from Brand b

        List<Object[]> resultList = entityManager.createQuery(criteria).getResultList();
        for (Object[] o : resultList) {
            System.out.println("Id: " + o[0] + ", Name: " + o[1]);
        }

        entityTransaction.commit();
        entityManager.close();

    }

    @Test
    void testConstructorExpression() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<SimpleBrand> criteria = criteriaBuilder.createQuery(SimpleBrand.class);
        Root<Brand> b = criteria.from(Brand.class);
        criteria.select(criteriaBuilder.construct(SimpleBrand.class, b.get("id"), b.get("name")));
        // select b.id, b.name from Brand b

        TypedQuery<SimpleBrand> query = entityManager.createQuery(criteria);
        List<SimpleBrand> resultList = query.getResultList();
        for (SimpleBrand simpleBrand : resultList) {
            System.out.println(simpleBrand.getId() + " : " + simpleBrand.getName());
        }

        entityTransaction.commit();
        entityManager.close();

    }

}
