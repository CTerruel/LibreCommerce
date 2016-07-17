/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.librecommerce.bean;

import br.com.librecommerce.dao.FuncionarioDao;
import br.com.librecommerce.modelo.Funcionario;
import br.com.librecommerce.util.FacesUtil;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;
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
            FacesUtil.showAlertMessage("Os campos estão vazios!", null);
            return "/seguranca/login";
        }
        
        try {
            funcionario = new FuncionarioDao().login(funcionario);
            
            //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("login", funcionario);
            HttpSession session = 
                    (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.setAttribute("login", funcionario);
            
            return "/app/vendas/GerenciarCaixa?faces-redirect=true";
        } 
        catch (NoResultException nex) {
            FacesUtil.showAlertMessage("Usuário não encontrado!", null);
            funcionario = new Funcionario();
            return "/seguranca/login";
        }
        catch (Exception ex) {
            FacesUtil.showErrorMessage(ex.getMessage(), null);
            funcionario = new Funcionario();
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
