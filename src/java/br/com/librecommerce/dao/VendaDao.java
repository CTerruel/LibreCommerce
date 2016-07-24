/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.librecommerce.dao;

import br.com.librecommerce.modelo.ItemVenda;
import br.com.librecommerce.modelo.Venda;
import br.com.librecommerce.util.EntityManagerUtil;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Clovis
 */
public class VendaDao {

    public void salvar(Venda venda) throws Exception {
        EntityManager em = EntityManagerUtil.getInstance();

        em.getTransaction().begin();
        em.persist(venda);
        
        atualizarEstoque(venda.getItensVenda(), em);
        
        em.getTransaction().commit();
        
        em.close();

    }

    private void atualizarEstoque(List<ItemVenda> itensVenda, EntityManager em) {
        ProdutoDao pDao = new ProdutoDao();
        for (int i = 0; i < itensVenda.size(); i++) {
            System.out.println(itensVenda.get(i).getProduto());
            pDao.atualizarEstoque(itensVenda.get(i).getProduto(), em);
        }
    }

}
