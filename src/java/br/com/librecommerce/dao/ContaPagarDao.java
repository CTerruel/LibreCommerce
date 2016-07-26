/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.librecommerce.dao;

import br.com.librecommerce.modelo.ContaPagar;
import br.com.librecommerce.util.EntityManagerUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import org.primefaces.model.filter.ContainsFilterConstraint;

/**
 *
 * @author Clovis
 */
public class ContaPagarDao {

    public void salvarConta(ContaPagar contaPagar) {
        EntityManager em = EntityManagerUtil.getInstance();

        em.getTransaction().begin();
        em.persist(contaPagar);
        em.getTransaction().commit();

        em.close();
    }

    public void pagarConta(ContaPagar contaPagar) throws Exception {
        EntityManager em = EntityManagerUtil.getInstance();

        em.getTransaction().begin();
        em.merge(contaPagar);
        em.getTransaction().commit();

        em.close();
    }

    public void pagarTodasContas(List<ContaPagar> contasPagar) throws Exception {
        EntityManager em = EntityManagerUtil.getInstance();

        em.getTransaction().begin();

        for (ContaPagar contaPagar : contasPagar) {
            em.merge(contaPagar);
        }

        em.getTransaction().commit();

        em.close();
    }

    public List<ContaPagar> consultarTodas() throws NoResultException, Exception {
        EntityManager em = EntityManagerUtil.getInstance();
        List<ContaPagar> contasPagar = null;

        contasPagar = em.createQuery("FROM ContaPagar cp")
                .getResultList();

        em.close();

        return contasPagar;
    }

    public List<ContaPagar> consultarPorDescricao(String descricao) throws NoResultException, Exception {
        EntityManager em = EntityManagerUtil.getInstance();
        List<ContaPagar> contasPagar = null;

        contasPagar = em.createQuery("SELECT cp FROM ContaPagar cp WHERE cp.descricao LIKE :descricao")
                .setParameter("descricao", "%" + descricao + "%")
                .getResultList();

        em.close();

        return contasPagar;
    }

}
