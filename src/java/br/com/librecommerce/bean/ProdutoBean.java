/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.librecommerce.bean;

import br.com.librecommerce.dao.CategoriaDao;
import br.com.librecommerce.dao.ProdutoDao;
import br.com.librecommerce.modelo.Categoria;
import br.com.librecommerce.modelo.Produto;
import br.com.librecommerce.util.FacesUtil;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Clovis
 */
@ManagedBean
@SessionScoped
public class ProdutoBean {
    
    private Produto produto;
    private List<Categoria> categorias;
    private List<Produto> produtos;
    
    /**
     * Creates a new instance of ProdutoBean
     */
    public ProdutoBean() {
        produto = new Produto();
        produtos = new ProdutoDao().listarTodos();
        categorias = new CategoriaDao().listarTodos();
    }
    
    public void buscaProdutoPorNome() {
        produtos = new ProdutoDao().buscaProdutoPorNome(produto.getNome());
        System.out.println("nome " + produto.getNome());
    }
    
    public void salvar() {
        new ProdutoDao().salvar(produto);
        produto = new Produto();
        FacesUtil.showInfoMessage("Produto salvo com sucesso!", null);
    }
    
    public void atualizar() {
        new ProdutoDao().atualizar(produto);
        produto = new Produto();
        FacesUtil.showInfoMessage("Produto atualizado!", null);
    }
    
    public String editar(Produto produto) {
        this.produto = produto;
        return "CadastroProduto?faces-redirect=true";
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
    
    
}
