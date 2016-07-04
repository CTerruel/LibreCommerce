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
public class FuncionarioDao {

    public Funcionario login(Funcionario f) {

        try {
            EntityManager em = EntityManagerUtil.getInstance();
            f = (Funcionario) em.createQuery("SELECT f FROM Funcionario f WHERE f.login = :login AND f.senha = :senha")
                    .setParameter("login", f.getLogin())
                    .setParameter("senha", f.getSenha())
                    .getSingleResult();
            System.out.println("nome: " + f.getNome() + " " + f.isAdmin());
            return f;
        } 
        catch (NoResultException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }

    }

    public void salvar(Funcionario f) {
        EntityManager em = EntityManagerUtil.getInstance();
        try {
            em.getTransaction().begin();
            em.persist(f);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }

    }

    public void atualizar(Funcionario funcionario) {
        EntityManager em = EntityManagerUtil.getInstance();
        try {
            em.getTransaction().begin();
            em.merge(funcionario);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }
    }

    public List<Funcionario> buscarFuncionarioPorNome(String nome) {
        EntityManager em = EntityManagerUtil.getInstance();
        List<Funcionario> funcionarios = null;
        try {
            funcionarios = em.createQuery("SELECT f FROM Funcionario f WHERE f.nome LIKE :nome")
                    .setParameter("nome", "%" + nome + "%")
                    .getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return funcionarios;
        } finally {
            em.close();
        }
        return funcionarios;
    }

    public List<Funcionario> listarTodos() {
        EntityManager em = EntityManagerUtil.getInstance();
        List<Funcionario> funcionarios = null;
        try {
            funcionarios = em.createQuery("FROM Funcionario f").getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return funcionarios;
        }

        return funcionarios;
    }

}
