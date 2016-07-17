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
    private List<Fornecedor> fornecedores;
    private List<Estado> estados;
    private List<Cidade> cidades;
    private boolean habilitaEdicao = false;

    /**
     * Creates a new instance of CadastroFornecedorBean
     */
    public FornecedorBean() {
        fornecedor = new Fornecedor();
        estados = getTodosEstados();
        fornecedores = getTodosFornecedores();
    }
    
    public void getCidadesDoEstado(AjaxBehaviorEvent event) {
        try {
            cidades = new CidadeDao().buscarTodasDoEstado(estado);
        } catch (Exception ex) {
            FacesUtil.showErrorMessage(ex.getMessage(), null);
        }
    }
    
    public void buscaFornecedorPorNome() {
        try {
            fornecedores = new FornecedorDao().buscarFornecedoresPorNome(fornecedor.getNome());
        } catch (Exception ex) {
            FacesUtil.showErrorMessage(ex.getMessage(), null);
        }
    }
    
    public void salvar() {
        try {
            new FornecedorDao().salvar(fornecedor);
            fornecedor = new Fornecedor();
            FacesUtil.showInfoMessage("Fornecedor salvo com sucesso!", null);
        } 
        catch (Exception ex) {
            FacesUtil.showErrorMessage(ex.getMessage(), null);
        }
        
    }
    
    public void atualizar() {
        try {
            new FornecedorDao().atualizar(fornecedor);
            fornecedor = new Fornecedor();
            FacesUtil.showInfoMessage("Fornecedor atualizado", null);
        } 
        catch (Exception ex) {
            FacesUtil.showErrorMessage(ex.getMessage(), null);
        }
        
    }
    
    public String editar(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
        this.habilitaEdicao = true;
        return "CadastroFornecedor?faces-redirect=true";
    }
    
    private List<Estado> getTodosEstados() {
        try {
            return new EstadoDao().buscarTodos();
        } catch (Exception ex) {
            FacesUtil.showErrorMessage(ex.getMessage(), null);
            return null;
        }
    }
    
    private List<Fornecedor> getTodosFornecedores() {
        try {
            return new FornecedorDao().listarTodos();
        } catch (Exception ex) {
            FacesUtil.showErrorMessage(ex.getMessage(), null);
            return null;
        }
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

    public List<Fornecedor> getFornecedores() {
        return fornecedores;
    }

    public void setFornecedores(List<Fornecedor> fornecedores) {
        this.fornecedores = fornecedores;
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

    public boolean isHabilitaEdicao() {
        return habilitaEdicao;
    }

    public void setHabilitaEdicao(boolean habilitaEdicao) {
        this.habilitaEdicao = habilitaEdicao;
    }
    
}
