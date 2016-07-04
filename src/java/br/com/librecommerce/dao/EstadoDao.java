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
public class EstadoDao {
    
    public List<Estado> buscarTodos() {
        EntityManager em = EntityManagerUtil.getInstance();
        List<Estado> estados = null;
        try {
            estados = em.createQuery("FROM Estado e").getResultList();
        }
        catch (Exception e) {
            e.printStackTrace();
            return estados;
        }
        finally {
            em.close();
        }
        return estados;
    }
    
    public Estado buscaEstadoPorId(int id) {
        EntityManager em = EntityManagerUtil.getInstance();
        Estado e = null;
        try {
            em.getTransaction().begin();
            e = em.find(Estado.class, id);
            em.getTransaction().commit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return e;
        }
        finally {
            em.close();
        }
        return e;
    }
    
}
