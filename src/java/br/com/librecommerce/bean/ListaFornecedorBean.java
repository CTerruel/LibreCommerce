/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.librecommerce.bean;

import br.com.librecommerce.dao.FornecedorDao;
import br.com.librecommerce.modelo.Fornecedor;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Clovis
 */
@ManagedBean
@SessionScoped
public class ListaFornecedorBean {
    
    private String buscaFornecedor;
    private List<Fornecedor> fornecedores;

    /**
     * Creates a new instance of ListaFornecedorBean
     */
    public ListaFornecedorBean() {
        fornecedores = new FornecedorDao().listarTodos();
    }
    
    public String pesquisar() {
        fornecedores = new FornecedorDao().buscarFornecedoresPorNome(buscaFornecedor);
        buscaFornecedor = "";
        return "ListaFornecedores";
    }

    public String getBuscaFornecedor() {
        return buscaFornecedor;
    }

    public void setBuscaFornecedor(String buscaFornecedor) {
        this.buscaFornecedor = buscaFornecedor;
    }

    public List<Fornecedor> getFornecedores() {
        return fornecedores;
    }

    public void setFornecedores(List<Fornecedor> fornecedores) {
        this.fornecedores = fornecedores;
    }
    
}
