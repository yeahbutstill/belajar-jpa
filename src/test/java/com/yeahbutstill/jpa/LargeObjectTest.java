package com.yeahbutstill.jpa;

import com.yeahbutstill.jpa.entity.Image;
import com.yeahbutstill.jpa.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class LargeObjectTest {

    @Test
    void testInsertLargeObject() throws IOException {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEMF();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Image image = new Image();
        image.setName("Image 1");
        image.setDescription("Description 1");
        byte[] imageBytes = Files.readAllBytes(Path.of(getClass().getResource("/images/NF.jpg").getPath()));
        image.setImage(imageBytes);

        entityManager.persist(image);
        Assertions.assertNotNull(image.getId());
        Assertions.assertNotNull(image.getName());
        Assertions.assertNotNull(image.getDescription());
        Assertions.assertNotNull(image.getImage());

        entityTransaction.commit();
        entityManager.close();
    }

}
