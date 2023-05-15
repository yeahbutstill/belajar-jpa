package com.yeahbutstill.jpa;

import com.yeahbutstill.jpa.entity.*;
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

    @Test
    void testSelectSomeFields() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        TypedQuery<Object[]> query = entityManager
                .createQuery("select b.id, b.name from Brand b where b.name = :name", Object[].class);
        query.setParameter("name", "Samsung");

        List<Object[]> resultList = query.getResultList();
        for (Object[] object : resultList) {
            System.out.println(object[0] + " : " + object[1]);
        }

        entityTransaction.commit();
        entityManager.close();

    }

    @Test
    void testSelectNewConstructor() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        TypedQuery<SimpleBrand> query = entityManager
                .createQuery("select new com.yeahbutstill.jpa.entity.SimpleBrand(b.id, b.name) " +
                        "from Brand b where b.name = :name", SimpleBrand.class);
        query.setParameter("name", "Samsung");

        List<SimpleBrand> resultList = query.getResultList();
        for (SimpleBrand simpleBrand : resultList) {
            System.out.println(simpleBrand.getId() + " : " + simpleBrand.getName());
        }

        entityTransaction.commit();
        entityManager.close();

    }

    @Test
    void testAggrgateFunction() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        TypedQuery<Object[]> query = entityManager
                .createQuery("select min(p.price), max(p.price), avg(p.price) from Product p", Object[].class);

        Object[] singleResult = query.getSingleResult();
        System.out.println("Min " + singleResult[0]);
        System.out.println("Max " + singleResult[1]);
        System.out.println("Average " + singleResult[2]);

        entityTransaction.commit();
        entityManager.close();

    }

    @Test
    void testGroupByAndHaving() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        TypedQuery<Object[]> query = entityManager
                .createQuery("select b.id, min(p.price), max(p.price), avg(p.price) from Product p " +
                        "join p.brand b group by b.id having min(p.price) > :min", Object[].class);

        query.setParameter("min", 500_000L);

        List<Object[]> resultList = query.getResultList();
        for (Object[] object : resultList) {
            System.out.println("Brand : " + object[0]);
            System.out.println("Min : " + object[1]);
            System.out.println("Max : " + object[2]);
            System.out.println("Average : " + object[3]);
            System.out.println("-------------------------");
        }

        entityTransaction.commit();
        entityManager.close();

    }

}
