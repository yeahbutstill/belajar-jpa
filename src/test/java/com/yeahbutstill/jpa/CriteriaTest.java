package com.yeahbutstill.jpa;

import com.yeahbutstill.jpa.entity.Brand;
import com.yeahbutstill.jpa.entity.Product;
import com.yeahbutstill.jpa.entity.SimpleBrand;
import com.yeahbutstill.jpa.utils.JpaUtil;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
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

    @Test
    void testCriteriaWhereClause() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Brand> criteriaQuery = criteriaBuilder.createQuery(Brand.class);
        Root<Brand> b = criteriaQuery.from(Brand.class);

        criteriaQuery.where(
                criteriaBuilder.equal(b.get("id"), "xiami"),
                criteriaBuilder.equal(b.get("name"), "Xiaomi"),
                criteriaBuilder.isNotNull(b.get("createdAt"))
        );

        TypedQuery<Brand> query = entityManager.createQuery(criteriaQuery);
        List<Brand> resultList = query.getResultList();
        for (Brand brand : resultList) {
            System.out.println(brand.getId() + " : " + brand.getName());
        }

        entityTransaction.commit();
        entityManager.close();

    }

    @Test
    void testCriteriaOrOperation() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Brand> criteriaQuery = criteriaBuilder.createQuery(Brand.class);
        Root<Brand> b = criteriaQuery.from(Brand.class);

        criteriaQuery.where(
                criteriaBuilder.or(
                    criteriaBuilder.equal(b.get("name"), "Apple Indonesia"),
                    criteriaBuilder.equal(b.get("name"), "Xiaomi")
                )
        );

        TypedQuery<Brand> query = entityManager.createQuery(criteriaQuery);
        List<Brand> resultList = query.getResultList();
        for (Brand brand : resultList) {
            System.out.println(brand.getId() + " : " + brand.getName());
        }

        entityTransaction.commit();
        entityManager.close();

    }

    @Test
    void testCriteriaJoinClause() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Brand> criteriaQuery = criteriaBuilder.createQuery(Brand.class);
        Root<Product> productRoot = criteriaQuery.from(Product.class);
        Join<Product, Brand> brandRoot = productRoot.join("brand");

        // select productRoot from Product productRoot join productRoot.brand brandRoot
        criteriaQuery.select(brandRoot);
        criteriaQuery.where(
                criteriaBuilder.equal(brandRoot.get("name"), "Samsung Update")
        );
        // select productRoot from Product productRoot join productRoot.brand brandRoot where brandRoot.name = 'Samsung Update'

        TypedQuery<Brand> query = entityManager.createQuery(criteriaQuery);
        List<Brand> resultList = query.getResultList();
        for (Brand brand : resultList) {
            System.out.println(brand.getId() + " : " + brand.getName());
        }

        entityTransaction.commit();
        entityManager.close();

    }

    @Test
    void testCriteriaParameter() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> p = criteriaQuery.from(Product.class);
        Join<Product, Brand> b= p.join("brand");

        ParameterExpression<String> brandParameterExpression = criteriaBuilder.parameter(String.class, "brand");

        // select p from Product p join p.brand b
        criteriaQuery.select(p);
        criteriaQuery.where(
                criteriaBuilder.equal(b.get("name"), brandParameterExpression)
        );
        // select p from Product p join p.brand b where b.name = 'Samsung Update'

        TypedQuery<Product> query = entityManager.createQuery(criteriaQuery);
        query.setParameter(brandParameterExpression, "Samsung Update");

        List<Product> resultList = query.getResultList();
        for (Product product : resultList) {
            System.out.println(product.getId() + " : " + product.getName());
        }

        entityTransaction.commit();
        entityManager.close();

    }

    @Test
    void testCriteriaAggregateQuery() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Product> p = criteriaQuery.from(Product.class);
        Join<Product, Brand> b = p.join("brand");

        criteriaQuery.select(criteriaBuilder.array(
                b.get("id"),
                criteriaBuilder.min(p.get("price")),
                criteriaBuilder.max(p.get("price")),
                criteriaBuilder.avg(p.get("price"))
        ));
        // select b.id, min(p.price), max(p.price), avg(p.price) from Product p join p.brand b

        criteriaQuery.groupBy(b.get("id"));
        // select b.id, min(p.price), max(p.price), avg(p.price) from Product p join p.brand b group by b.id

        criteriaQuery.having(criteriaBuilder.greaterThan(criteriaBuilder.min(p.get("price")), 500_000L));
        // select b.id, min(p.price), max(p.price), avg(p.price) from Product p join p.brand b group by b.id having min(p.price) > 500_000

        TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
        List<Object[]> resultList = query.getResultList();


        for (Object[] objects : resultList) {
            System.out.println("Brand : " + objects[0]);
            System.out.println("Min Price : " + objects[1]);
            System.out.println("Max Price : " + objects[2]);
            System.out.println("Avg Price : " + objects[3]);
        }

        entityTransaction.commit();
        entityManager.close();

    }

    @Test
    void testCriteriaNonQuerySelectCriteriaUpdate() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Brand> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Brand.class);
        Root<Brand> b = criteriaUpdate.from(Brand.class);

        criteriaUpdate.set(b.get("name"), "Apple Updated");
        criteriaUpdate.where(criteriaBuilder.equal(b.get("id"), "apple-in"));

        Query query = entityManager.createQuery(criteriaUpdate);
        int impactedRecords = query.executeUpdate();
        System.out.println("Success Update : " + impactedRecords + " records");

        entityTransaction.commit();
        entityManager.close();

    }

}
