/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.librecommerce.dao;

import br.com.librecommerce.modelo.Caixa;
import br.com.librecommerce.modelo.StatusCaixa;
import br.com.librecommerce.util.EntityManagerUtil;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 *
 * @author Clovis
 */
public class CaixaDao {

    public Caixa abrirCaixa(Caixa caixa) throws Exception {
        // persiste o caixa recém aberto
        EntityManager em = EntityManagerUtil.getInstance();

        em.getTransaction().begin();
        em.persist(caixa);
        em.getTransaction().commit();

        em.close();

        return getCaixaAberto();

    }

    public Caixa getCaixaAberto() throws Exception {
        EntityManager em = EntityManagerUtil.getInstance();
        Caixa caixa = null;

        caixa = (Caixa) em.createQuery("SELECT c FROM Caixa c WHERE c.statusCaixa = :statusCaixa")
                .setParameter("statusCaixa", StatusCaixa.ABERTO)
                .getSingleResult();

        em.close();

        return caixa;
    }

    public void atualizarCaixa(Caixa caixa) throws Exception {
        EntityManager em = EntityManagerUtil.getInstance();

        em.getTransaction().begin();
        em.merge(caixa);
        em.getTransaction().commit();

        em.close();

    }

    public void fecharCaixa(Caixa c) throws Exception {
        EntityManager em = EntityManagerUtil.getInstance();
        
        em.getTransaction().begin();
        em.merge(c);
        em.getTransaction().commit();
        
        em.close();
        
    }

}
