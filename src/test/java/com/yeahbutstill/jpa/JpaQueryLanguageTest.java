package com.yeahbutstill.jpa;

import com.yeahbutstill.jpa.entity.Brand;
import com.yeahbutstill.jpa.entity.Member;
import com.yeahbutstill.jpa.entity.Product;
import com.yeahbutstill.jpa.entity.User;
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

    @Test
    void testWhereClauseJPAQL() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        TypedQuery<Member> query = entityManager
                .createQuery("SELECT M FROM Member M WHERE M.name.firstName = :firstName " +
                        "AND M.name.lastName = :lastName", Member.class);
        query.setParameter("firstName", "Dani");
        query.setParameter("lastName", "Setiawan");

        List<Member> members = query.getResultList();
        for (Member member : members) {
            System.out.println(member.getId() + " : " + member.getFullName() + " : " + member.getEmail());
        }


        entityTransaction.commit();
        entityManager.close();

    }

    @Test
    void testJoinClauseJPAQL() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        TypedQuery<Product> query = entityManager
                .createQuery("SELECT P FROM Product P JOIN P.brand B WHERE B.name = :brand ", Product.class);
        query.setParameter("brand", "Samsung");

        List<Product> resultList = query.getResultList();
        for (Product product : resultList) {
            System.out.println(product.getId() + " : " + product.getName() + " : " + product.getPrice());
        }

        entityTransaction.commit();
        entityManager.close();

    }

    @Test
    void testJoinFetchClauseJPAQL() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        TypedQuery<User> query = entityManager
                .createQuery("SELECT U FROM User U JOIN FETCH U.likes P WHERE P.name = :product", User.class);
        query.setParameter("product", "Samsung Galaxy S10");

        List<User> resultList = query.getResultList();
        for (User user : resultList) {
            System.out.println("User : " + user.getName());
            for (Product product : user.getLikes()) {
                System.out.println("Product : " + product.getName());
            }
        }

        entityTransaction.commit();
        entityManager.close();

    }

    @Test
    void testOrderByClauseJPAQL() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        TypedQuery<Brand> query = entityManager
                .createQuery("SELECT B FROM Brand B ORDER BY B.name DESC", Brand.class);

        List<Brand> resultList = query.getResultList();
        for (Brand brand : resultList) {
            System.out.println(brand.getId() + " : " + brand.getName());
        }

        entityTransaction.commit();
        entityManager.close();

    }

    @Test
    void testInsertRandomBrands() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        for (int i = 0; i < 100; i++) {
            Brand brand = new Brand();
            brand.setId(String.valueOf(i));
            brand.setName("Brand " + i);
            entityManager.persist(brand);
        }

        entityTransaction.commit();
        entityManager.close();

    }

    @Test
    void testLimitOffsetJPAQL() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        TypedQuery<Brand> query = entityManager
                .createQuery("SELECT B FROM Brand B ORDER BY B.name DESC", Brand.class);
        query.setFirstResult(10); // ini 10 di awal yang akan di skip
        query.setMaxResults(10); // ini 10 di akhir yang akan di tampilkan

        List<Brand> resultList = query.getResultList();
        for (Brand brand : resultList) {
            System.out.println(brand.getId() + " : " + brand.getName());
        }

        entityTransaction.commit();
        entityManager.close();

    }

    @Test
    void testNamedQuery() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        TypedQuery<Brand> namedQuery = entityManager.createNamedQuery("Brand.findAllByName", Brand.class);
        namedQuery.setParameter("name", "Samsung");

        List<Brand> resultList = namedQuery.getResultList();
        for (Brand brand : resultList) {
            System.out.println(brand.getId() + " : " + brand.getName());
        }

        entityTransaction.commit();
        entityManager.close();

    }

}
