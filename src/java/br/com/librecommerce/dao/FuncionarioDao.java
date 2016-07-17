/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.librecommerce.dao;

import br.com.librecommerce.modelo.Funcionario;
import br.com.librecommerce.util.EntityManagerUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 *
 * @author Clovis
 */
public class FuncionarioDao extends GenericDao<Funcionario> {

    public Funcionario login(Funcionario funcionario) throws Exception, NoResultException {
        EntityManager em = EntityManagerUtil.getInstance();

        funcionario = (Funcionario) em.createQuery("SELECT f FROM Funcionario f WHERE f.login = :login AND f.senha = :senha")
                .setParameter("login", funcionario.getLogin())
                .setParameter("senha", funcionario.getSenha())
                .getSingleResult();

        em.close();

        return funcionario;

    }

    public List<Funcionario> buscarFuncionariosPorNome(String nome) throws Exception {
        EntityManager em = EntityManagerUtil.getInstance();
        List<Funcionario> funcionarios = null;

        funcionarios = em.createQuery("SELECT f FROM Funcionario f WHERE f.nome LIKE :nome")
                .setParameter("nome", "%" + nome + "%")
                .getResultList();
        
        em.close();

        return funcionarios;
    }

    public List<Funcionario> listarTodos() throws Exception {
        EntityManager em = EntityManagerUtil.getInstance();
        List<Funcionario> funcionarios = null;

        funcionarios = em.createQuery("FROM Funcionario f").getResultList();

        em.close();

        return funcionarios;
    }

}
