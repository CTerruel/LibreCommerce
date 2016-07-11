/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.librecommerce.bean;

import br.com.librecommerce.dao.CidadeDao;
import br.com.librecommerce.dao.EstadoDao;
import br.com.librecommerce.dao.FornecedorDao;
import br.com.librecommerce.modelo.Cidade;
import br.com.librecommerce.modelo.Estado;
import br.com.librecommerce.modelo.Fornecedor;
import br.com.librecommerce.util.FacesUtil;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author Clovis
 */
@ManagedBean
@SessionScoped
public class FornecedorBean {

    private Fornecedor fornecedor;
    private Estado estado;
    private List<Estado> estados;
    private List<Cidade> cidades;

    /**
     * Creates a new instance of CadastroFornecedorBean
     */
    public FornecedorBean() {
        fornecedor = new Fornecedor();
        estados = new EstadoDao().buscarTodos();
    }
    
    public String novo() {
        fornecedor = new Fornecedor();
        return "CadastroFornecedor?faces-redirect=true";
    }
    
    public void getCidadesDoEstado(AjaxBehaviorEvent event) {
        cidades = new CidadeDao().buscarTodasDoEstado(estado);
    }
    
    public void salvar() {
        new FornecedorDao().salvar(fornecedor);
        FacesUtil.showInfoMessage("Fornecedor salvo com sucesso!", null);
    }
    
    public void atualizar() {
        new FornecedorDao().atualizar(fornecedor);
        FacesUtil.showInfoMessage("Fornecedor atualizado", null);
    }
    
    public String editar(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
        return "CadastroFornecedor?faces-redirect=true";
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public List<Estado> getEstados() {
        return estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public List<Cidade> getCidades() {
        return cidades;
    }

    public void setCidades(List<Cidade> cidades) {
        this.cidades = cidades;
    }

    
}
