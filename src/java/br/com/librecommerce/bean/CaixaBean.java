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
import br.com.librecommerce.util.FacesUtil;
import java.util.Date;
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
            FacesUtil.showAlertMessage("O caixa ja esta aberto!", null);
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
        FacesUtil.showInfoMessage("Caixa aberto e pronto para vendas!", null);
        return "GerenciarCaixa";
    }

    public void adicionaCaixaNaSessao(Caixa caixa) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("caixa", caixa);
    }

    public String prepararFecharCaixa() {
        if (this.caixa.getStatusCaixa() == null || this.caixa.getStatusCaixa() == StatusCaixa.FECHADO) {
            FacesUtil.showAlertMessage("O caixa ja esta fechado!", null);
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
        FacesUtil.showInfoMessage("Caixa fechado com sucesso!", null);
        return "GerenciarCaixa";

    }

    public String novaVenda() {
        if (caixa.getStatusCaixa() == null || caixa.getStatusCaixa() == StatusCaixa.FECHADO) {
            FacesUtil.showAlertMessage("É preciso abrir o caixa antes de efetuar vendas!", null);
            return "GerenciarCaixa";
        }
        else {
            return "NovaVenda?faces-redirect=true";
        }
    }

    public Caixa getCaixa() {
        return caixa;
    }

    public void setCaixa(Caixa caixa) {
        this.caixa = caixa;
    }

}
