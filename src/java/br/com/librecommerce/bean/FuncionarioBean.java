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
    private List<Funcionario> funcionarios;
    private List<Estado> estados;
    private List<Cidade> cidades;
    private boolean habilitaEdicao = false;

    /**
     * Creates a new instance of FuncionarioBean
     */
    public FuncionarioBean() {
        funcionario = new Funcionario();
        estado = new Estado();
        funcionarios = getTodosFuncionarios();
        estados = getTodosEstados();
    }

    public void getCidadesDoEstado(AjaxBehaviorEvent event) {
        try {
            cidades = new CidadeDao().buscarTodasDoEstado(estado);
        } catch (Exception ex) {
            FacesUtil.showErrorMessage(ex.getMessage(), null);
        }
    }

    public void buscaFuncionariosPorNome() {
        try {
            funcionarios = new FuncionarioDao().buscarFuncionariosPorNome(funcionario.getNome());
        } catch (Exception ex) {
            FacesUtil.showErrorMessage(ex.getMessage(), null);
        }
    }
    
    public String novo() {
        funcionario = new Funcionario();
        return "CadastroFuncionario";
    }

    public void salvar() {
        try {
            new FuncionarioDao().salvar(funcionario);
            funcionario = new Funcionario();
            FacesUtil.showInfoMessage("Funcionário salvo com sucesso!", null);
        } catch (Exception ex) {
            FacesUtil.showErrorMessage(ex.getMessage(), null);
        }

    }

    public void atualizar() {
        try {
            new FuncionarioDao().atualizar(funcionario);
            funcionario = new Funcionario();
            FacesUtil.showInfoMessage("Funcionário atualizado!", null);
        } 
        catch (Exception ex) {
            FacesUtil.showErrorMessage(ex.getMessage(), null);
        }

    }

    public String editar(Funcionario funcionario) {
        this.funcionario = funcionario;
        System.out.println(funcionario.getSenha());
        this.habilitaEdicao = true;
        return "CadastroFuncionario?faces-redirect=true";
    }

    private List<Funcionario> getTodosFuncionarios() {
        try {
            return new FuncionarioDao().listarTodos();
        } catch (Exception ex) {
            FacesUtil.showErrorMessage(ex.getMessage(), null);
            return null;
        }
    }
    
    private List<Estado> getTodosEstados() {
        try {
            return new EstadoDao().buscarTodos();
        } catch (Exception ex) {
            FacesUtil.showErrorMessage(ex.getMessage(), null);
            return null;
        }
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

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
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
