/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.librecommerce.bean;

import br.com.librecommerce.dao.FuncionarioDao;
import br.com.librecommerce.modelo.Funcionario;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Clovis
 */
@ManagedBean
@SessionScoped
public class AutenticacaoBean {
    
    private Funcionario funcionario;

    /**
     * Creates a new instance of AutenticacaoBean
     */
    public AutenticacaoBean() {
        funcionario = new Funcionario();
    }

    public String login() {
        
        if (funcionario.getLogin().isEmpty() || funcionario.getSenha().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage("Os campos estão vazios!"));
            return "/seguranca/login";
        }
        
        funcionario = new FuncionarioDao().login(funcionario);
        if (funcionario != null) {
            HttpSession session = 
                    (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.setAttribute("login", funcionario);
            return "/app/GerenciarCaixa?faces-redirect=true";
        }
        else {
            funcionario = new Funcionario();
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage("Usuário não encontrado!"));
            return "/seguranca/login";
        }
        
    }
    
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/seguranca/login?faces-redirect=true";
    }
    
    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
    
}
