package com.yeahbutstill.jpa.utils;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {

    private static EntityManagerFactory EMF = null;

    public static EntityManagerFactory getEMF() {
        if (EMF == null) {
            EMF = Persistence.createEntityManagerFactory("BELAJAR");
        }
        return EMF;
    }

}
