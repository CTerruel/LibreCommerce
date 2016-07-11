/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.librecommerce.bean;

import br.com.librecommerce.dao.ProdutoDao;
import br.com.librecommerce.modelo.Produto;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Clovis
 */
@ManagedBean
@RequestScoped
public class ListaProdutoBean {

    private String buscaProduto;
    private List<Produto> produtos;

    /**
     * Creates a new instance of ListaProdutoBean
     */
    public ListaProdutoBean() {
        this.produtos = new ProdutoDao().listarTodos();
    }

    public String pesquisar() {
        this.produtos = new ProdutoDao().buscaProdutoPorNome(buscaProduto);
        buscaProduto = "";
        return "ListaProdutos";
    }

    public String getBuscaProduto() {
        return buscaProduto;
    }

    public void setBuscaProduto(String buscaProduto) {
        this.buscaProduto = buscaProduto;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

}
