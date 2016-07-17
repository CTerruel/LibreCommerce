/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.librecommerce.dao;

import br.com.librecommerce.modelo.Cliente;
import br.com.librecommerce.util.EntityManagerUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 *
 * @author Clovis
 */
public class ClienteDao extends GenericDao<Cliente> {

    public List<Cliente> buscarClientesPorNome(String nome) throws Exception, NoResultException {
        EntityManager em = EntityManagerUtil.getInstance();
        List<Cliente> clientes = null;

        clientes = em.createQuery("SELECT c FROM Cliente c WHERE c.nome LIKE :nomeCliente")
                .setParameter("nomeCliente", "%" + nome + "%")
                .getResultList();

        em.close();

        return clientes;
    }

    public List<Cliente> listarTodos() throws Exception {
        EntityManager em = EntityManagerUtil.getInstance();
        List<Cliente> clientes = null;

        clientes = em.createQuery("FROM Cliente c").getResultList();
        
        em.close();

        return clientes;
    }

}
