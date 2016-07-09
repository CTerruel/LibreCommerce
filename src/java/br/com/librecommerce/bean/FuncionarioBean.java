/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.librecommerce.bean;

import br.com.librecommerce.dao.CidadeDao;
import br.com.librecommerce.dao.EstadoDao;
import br.com.librecommerce.dao.FuncionarioDao;
import br.com.librecommerce.modelo.Cidade;
import br.com.librecommerce.modelo.Estado;
import br.com.librecommerce.modelo.Funcionario;
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
public class FuncionarioBean {
    
    private Funcionario funcionario;
    private Estado estado;
    private Cidade cidade;
    private String buscaNome = "";
    private List<Estado> estados;
    private List<Cidade> cidades;
    private List<Funcionario> funcionarios;

    /**
     * Creates a new instance of FuncionarioBean
     */
    public FuncionarioBean() {
        funcionario = new Funcionario();
        estado = new Estado();
        cidade = new Cidade();
        estados = new EstadoDao().buscarTodos();
        funcionarios = new FuncionarioDao().listarTodos();
    }
    
    public void getCidadesDoEstado(AjaxBehaviorEvent event) {
        cidades = new CidadeDao().buscarTodasDoEstado(estado);
    }
    
    public String novo() {
        if (funcionario != null) {
            funcionario = new Funcionario();
        }
        return "CadastroFuncionario?faces-redirect=true";
    }
    
    public void salvar() {
        FuncionarioDao funcDao = new FuncionarioDao();
        funcDao.salvar(funcionario);
        funcionarios = funcDao.listarTodos();
        funcionario = new Funcionario();
        FacesUtil.showInfoMessage("Funcionário salvo com sucesso!", null);
    }
    
    public void atualizar() {
        new FuncionarioDao().atualizar(funcionario);
        funcionario = new Funcionario();
        FacesUtil.showInfoMessage("Funcionário atualizado!", null);
    }
    
    public String editar(Funcionario funcionario) {
        this.funcionario = funcionario;
        return "CadastroFuncionario?faces-redirect=true";
    }
    
    public void pesquisar() {
        funcionarios = new FuncionarioDao().buscarFuncionarioPorNome(buscaNome);
    }
    
    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getBuscaNome() {
        return buscaNome;
    }

    public void setBuscaNome(String buscaNome) {
        this.buscaNome = buscaNome;
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

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

}
