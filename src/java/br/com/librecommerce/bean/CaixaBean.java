/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.librecommerce.bean;

import br.com.librecommerce.dao.CaixaDao;
import br.com.librecommerce.modelo.Caixa;
import br.com.librecommerce.modelo.Funcionario;
import br.com.librecommerce.modelo.StatusCaixa;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Clovis
 */
@ManagedBean
@SessionScoped
public class CaixaBean {

    private Caixa caixa;

    /**
     * Creates a new instance of CaixaBean
     */
    public CaixaBean() {
        this.caixa = new CaixaDao().getCaixaAberto();
        if (this.caixa != null) {
            adicionaCaixaNaSessao(caixa);
        } else {
            this.caixa = new Caixa();
        }

    }

    public String prepararAbrirCaixa() {
        if (this.caixa.getStatusCaixa() == null || this.caixa.getStatusCaixa() == StatusCaixa.FECHADO) {
            RequestContext.getCurrentInstance().execute("PF('modalAbrirCaixa').show();");
        } else {
            showMessage("O caixa ja esta aberto!");
        }
        return "GerenciarCaixa";

    }

    public String abrirCaixa() {
        caixa.setDataAbertura(new Date());
        caixa.setFuncionario(
                (Funcionario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login"));
        caixa.setStatusCaixa(StatusCaixa.ABERTO);
        caixa = new CaixaDao().abrirCaixa(caixa);
        adicionaCaixaNaSessao(caixa);
        showMessage("Caixa aberto e pronto para operar!");
        return "GerenciarCaixa";
    }

    public void adicionaCaixaNaSessao(Caixa caixa) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("caixa", caixa);
    }

    public String prepararFecharCaixa() {
        if (this.caixa.getStatusCaixa() == null || this.caixa.getStatusCaixa() == StatusCaixa.FECHADO) {
            //RequestContext.getCurrentInstance().execute("PF('modalValidacaoCaixaFechado').show();");
            showMessage("O caixa ja esta fechado!");
        } else {
            RequestContext.getCurrentInstance().execute("PF('modalFecharCaixa').show();");
        }
        return "GerenciarCaixa";
    }

    public String fecharCaixa() {
        caixa.setDataFechamento(new Date());
        caixa.setStatusCaixa(StatusCaixa.FECHADO);
        new CaixaDao().fecharCaixa(caixa);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("caixa");
        //RequestContext.getCurrentInstance().execute("PF('modalCaixaEncerradoOK').show();");
        showMessage("Caixa fechado com sucesso!");
        return "GerenciarCaixa";

    }

    public String novaVenda() {
        if (caixa.getStatusCaixa() == null || caixa.getStatusCaixa() == StatusCaixa.FECHADO) {
            showMessage("É preciso abrir o caixa antes de efetuar vendas!");
            return "GerenciarCaixa";
        }
        else {
            return "NovaVenda?faces-redirect=true";
        }
    }

    public void showMessage(String mensagem) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(mensagem));
    }

    public Caixa getCaixa() {
        return caixa;
    }

    public void setCaixa(Caixa caixa) {
        this.caixa = caixa;
    }

}
