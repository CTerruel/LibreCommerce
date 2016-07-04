/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.librecommerce.dao;

import br.com.librecommerce.modelo.Produto;
import br.com.librecommerce.util.EntityManagerUtil;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 *
 * @author Clovis
 */
public class ProdutoDao {

    public Produto buscarProdutoPorCodigoB(int codigoBarra) {
        EntityManager em = EntityManagerUtil.getInstance();
        Produto p = null;
        try {
            p = (Produto) em.createQuery("SELECT p FROM Produto p WHERE P.codigoBarra = :codigoBarra")
                    .setParameter("codigoBarra", codigoBarra)
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
            return p;
        } finally {
            em.close();

        }
        return p;
    }

    protected void atualizarEstoque(Produto p, EntityManager em) {
        try {
            em.merge(p);//em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
