/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.librecommerce.dao;

import br.com.librecommerce.modelo.Fornecedor;
import br.com.librecommerce.util.EntityManagerUtil;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Clovis
 */
public class FornecedorDao extends GenericDao<Fornecedor> {
    
    public List<Fornecedor> buscarFornecedoresPorNome(String nome) {
        EntityManager em = EntityManagerUtil.getInstance();
        List<Fornecedor> fornecedores = null;
        try {
            fornecedores = em.createQuery("SELECT f FROM Fornecedor f WHERE f.nome LIKE :nome")
                    .setParameter("nome", "%" + nome + "%")
                    .getResultList();
        } 
        catch (Exception e) {
            e.printStackTrace();
            return fornecedores;
        }
        finally {
            em.close();
        }
        return fornecedores;
    }
    
    public List<Fornecedor> listarTodos() {
        EntityManager em = EntityManagerUtil.getInstance();
        List<Fornecedor> fornecedores = null;
        try {
            fornecedores = em.createQuery("FROM Fornecedor f").getResultList();
        } 
        catch (Exception e) {
            e.printStackTrace();
            return fornecedores;
        }
        finally {
            em.close();
        }
        return fornecedores;
    }
    
}
