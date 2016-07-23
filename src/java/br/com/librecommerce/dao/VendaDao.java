/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.librecommerce.dao;

import br.com.librecommerce.modelo.ContaReceber;
import br.com.librecommerce.modelo.ItemVenda;
import br.com.librecommerce.modelo.StatusConta;
import br.com.librecommerce.modelo.Venda;
import br.com.librecommerce.util.EntityManagerUtil;
import java.util.Calendar;
import java.util.Date;
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
        
        // Se a venda possui um cliente gera uma conta a receber
        if (venda.getCliente() != null) {
            gerarContaReceber(venda, em);
        }
        
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

    private void gerarContaReceber(Venda venda, EntityManager em) {
        ContaReceber contaReceber = new ContaReceber();
        contaReceber.setVenda(venda);
        // Calcular data de vencimento pex: mes seguinte
        contaReceber.setDataVencimento(gerarDataVencimento(new Date()));
        contaReceber.setStatusConta(StatusConta.ABERTA);
        new ContaReceberDao().gerarContaReceber(contaReceber, em);
    }

    private Date gerarDataVencimento(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.MONTH, c.get(Calendar.MONTH)+1);
        return c.getTime();
    }

}
