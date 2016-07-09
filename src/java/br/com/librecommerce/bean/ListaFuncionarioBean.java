/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.librecommerce.bean;

import br.com.librecommerce.dao.FuncionarioDao;
import br.com.librecommerce.modelo.Funcionario;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Clovis
 */
@ManagedBean
@SessionScoped
public class ListaFuncionarioBean {

    private String buscaNome;
    private List<Funcionario> funcionarios;
    /**
     * Creates a new instance of ListaFuncionarioBean
     */
    public ListaFuncionarioBean() {
        this.funcionarios = new FuncionarioDao().listarTodos();
        this.buscaNome = "";
    }

    public void pesquisar() {
        this.funcionarios = new FuncionarioDao().buscarFuncionarioPorNome(buscaNome);
    }
    
    public String getBuscaNome() {
        return buscaNome;
    }

    public void setBuscaNome(String buscaNome) {
        this.buscaNome = buscaNome;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }
    
}
