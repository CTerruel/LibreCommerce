/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.librecommerce.dao;

import br.com.librecommerce.modelo.Estado;
import br.com.librecommerce.util.EntityManagerUtil;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Clovis
 */
public class EstadoDao extends GenericDao<Estado> {

    public List<Estado> buscarTodos() throws Exception {
        EntityManager em = EntityManagerUtil.getInstance();
        List<Estado> estados = null;

        estados = em.createQuery("FROM Estado e").getResultList();
        
        em.close();

        return estados;
    }

}
