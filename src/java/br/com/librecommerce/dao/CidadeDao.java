/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.librecommerce.dao;

import br.com.librecommerce.modelo.Cidade;
import br.com.librecommerce.modelo.Estado;
import br.com.librecommerce.util.EntityManagerUtil;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Clovis
 */
public class CidadeDao {
    
    public List<Cidade> buscarTodasDoEstado(Estado estado) {
        EntityManager em = EntityManagerUtil.getInstance();
        List<Cidade> cidades = null;
        try {
            cidades = em.createQuery("SELECT c FROM Cidade c WHERE c.estado = :Estado")
                    .setParameter("Estado", estado).getResultList();
        }
        catch (Exception e) {
            e.printStackTrace();
            return cidades;
        }
        finally {
            em.close();
        }
        return cidades;
    }
    
    public Cidade buscarCidadePorId(int id) {
        EntityManager em = EntityManagerUtil.getInstance();
        Cidade c = null;
        try {
            em.getTransaction().begin();
            c = em.find(Cidade.class, id);
            em.getTransaction().commit();
        }
        catch (Exception e) {
            e.printStackTrace();
            return c;
        }
        finally {
            em.close();
        }
        return c;
    }
    
}
