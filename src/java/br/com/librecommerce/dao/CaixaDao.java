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

    public Caixa abrirCaixa(Caixa caixa) {
        // persiste o caixa recém aberto
        EntityManager em = EntityManagerUtil.getInstance();
        try {
            em.getTransaction().begin();
            em.persist(caixa);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();;
        } finally {
            em.close();
        }

        //busca o caixa recém aberto
        return getCaixaAberto();

    }

    public Caixa getCaixaAberto() {
        EntityManager em = EntityManagerUtil.getInstance();
        Caixa caixa = null;
        try {
            caixa = (Caixa) em.createQuery("SELECT c FROM Caixa c WHERE c.statusCaixa = :statusCaixa")
                    .setParameter("statusCaixa", StatusCaixa.ABERTO)
                    .getSingleResult();
        }
        catch (NoResultException ne) {
            System.out.println(ne.getMessage());
        }
        finally {
            em.close();
        }

        return caixa;
    }
    
    public void fecharCaixa(Caixa c) {
        EntityManager em = EntityManagerUtil.getInstance();
        try {
            em.getTransaction().begin();
            em.merge(c);
            em.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            em.close();
        }
    }

}
