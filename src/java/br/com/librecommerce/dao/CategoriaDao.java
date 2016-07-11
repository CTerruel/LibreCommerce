/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.librecommerce.dao;

import br.com.librecommerce.modelo.Categoria;
import br.com.librecommerce.util.EntityManagerUtil;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Clovis
 */
public class CategoriaDao extends GenericDao<Categoria> {
    
    public List<Categoria> listarTodos() {
        EntityManager em = EntityManagerUtil.getInstance();
        List<Categoria> categorias = null;
        try {
            categorias = em.createQuery("FROM Categoria c").getResultList();
        } 
        catch (Exception e) {
            e.printStackTrace();
            return categorias;
        }
        finally {
            em.close();
        }
        return categorias;
    }

}
