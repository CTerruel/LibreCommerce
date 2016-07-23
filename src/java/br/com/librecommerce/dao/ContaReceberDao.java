/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.librecommerce.dao;

import br.com.librecommerce.modelo.ContaReceber;
import br.com.librecommerce.modelo.StatusConta;
import br.com.librecommerce.util.EntityManagerUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 *
 * @author Clovis
 */
public class ContaReceberDao {

    protected void gerarContaReceber(ContaReceber contaReceber, EntityManager em) {
        try {
            em.persist(contaReceber);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ContaReceber> consultarTodas() throws NoResultException, Exception {
        EntityManager em = EntityManagerUtil.getInstance();
        List<ContaReceber> contasReceber = null;
        
        contasReceber = em.createQuery("SELECT  cc FROM ContaReceber cc WHERE cc.statusConta = :statusConta")
                .setParameter("statusConta", StatusConta.ABERTA)
                .getResultList();
        
        em.close();
        
        return contasReceber;
    }

    public List<ContaReceber> consultarPorCliente(String nomeCliente) throws NoResultException, Exception {
        EntityManager em = EntityManagerUtil.getInstance();
        List<ContaReceber> contasReceber = null;

        contasReceber
                = em.createQuery("SELECT cc FROM ContaReceber cc "
                        + "WHERE cc.venda.cliente.nome LIKE :nomeCliente "
                        + "AND cc.statusConta = :statusConta")
                .setParameter("nomeCliente", "%" + nomeCliente + "%")
                .setParameter("statusConta", StatusConta.ABERTA)
                .getResultList();

        em.close();

        return contasReceber;
    }

    public void receber(ContaReceber contaReceber) throws Exception {
        EntityManager em = EntityManagerUtil.getInstance();

        em.getTransaction().begin();
        em.merge(contaReceber);
        em.getTransaction().commit();

        em.close();
    }
    
    public void receberTodas(List<ContaReceber> contasReceber) throws Exception {
        EntityManager em = EntityManagerUtil.getInstance();
        
        em.getTransaction().begin();
        
        for (int i = 0; i < contasReceber.size(); i++) {
            em.merge(contasReceber.get(i));
        }
        
        em.getTransaction().commit();
        
        em.close();
    }
    
}
