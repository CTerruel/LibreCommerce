/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.librecommerce.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Clovis
 */
public class EntityManagerUtil {

    private static EntityManager em;

    private EntityManagerUtil() {}
    
    public static EntityManager getInstance() {
        EntityManagerFactory factory = null;

        if (em == null || !em.isOpen()) {
            factory = Persistence.createEntityManagerFactory("librecommercePU");
            em = factory.createEntityManager();
        }

        return em;
    }
    
    public static void close() {
        if (em != null) em.close();
    }

}
