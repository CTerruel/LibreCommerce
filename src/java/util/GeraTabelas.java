/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.persistence.EntityManager;

/**
 *
 * @author Clovis
 */
public class GeraTabelas {
    
    public static void main(String[] args) {
        EntityManager em = br.com.librecommerce.util.EntityManagerUtil.getInstance();
        em.close();
    }
    
}
