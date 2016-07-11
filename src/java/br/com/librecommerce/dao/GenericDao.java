/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.librecommerce.dao;

import br.com.librecommerce.modelo.EntidadeBase;
import br.com.librecommerce.util.EntityManagerUtil;
import javax.persistence.EntityManager;

/**
 *
 * @author Clovis
 */
public class GenericDao<T extends EntidadeBase> {
    
    public void salvar(T t) {
        EntityManager em = EntityManagerUtil.getInstance();
        try {
            em.getTransaction().begin();
            em.persist(t);
            em.getTransaction().commit();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            em.close();
        }
    } 
    
    public void atualizar(T t) {
        EntityManager em = EntityManagerUtil.getInstance();
        try {
            em.getTransaction().begin();
            em.merge(t);
            em.getTransaction().commit();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            em.close();
        }
    }
    
    public T buscarPorId(Class<T> clazz, int id) {
        EntityManager em = EntityManagerUtil.getInstance();
        T t = null;
        try {
            t = em.find(clazz, id);
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            em.close();
        }
        return t;
    }
    
}