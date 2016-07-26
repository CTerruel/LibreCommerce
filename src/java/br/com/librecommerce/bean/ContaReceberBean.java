/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.librecommerce.bean;

import br.com.librecommerce.dao.CaixaDao;
import br.com.librecommerce.dao.ContaReceberDao;
import br.com.librecommerce.modelo.Caixa;
import br.com.librecommerce.modelo.ContaReceber;
import br.com.librecommerce.modelo.StatusConta;
import br.com.librecommerce.util.FacesUtil;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.NoResultException;

/**
 *
 * @author Clovis
 */
@ManagedBean
@SessionScoped
public class ContaReceberBean {

    private String nomeCliente;
    private Double totalReceber;
    private List<ContaReceber> contasReceber;

    /**
     * Creates a new instance of ContaReceberBean
     */
    public ContaReceberBean() {
        consultarTodas();
    }

    public String consultarPorCliente() {
        try {
            contasReceber = new ContaReceberDao().consultarPorCliente(nomeCliente);
            totalReceber = 0.0;
            geraTotalReceber(contasReceber);
        } 
        catch (NoResultException ne) {
            FacesUtil.showInfoMessage("Nenhum conta a receber deste Cliente!", null);
        } 
        catch (Exception ex) {
            FacesUtil.showErrorMessage(ex.getMessage(), null);
        }
        return "ContaReceber";
    }

    public String consultarTodas() {
        try {
            contasReceber = new ContaReceberDao().consultarTodas();
            totalReceber = 0.0;
            geraTotalReceber(contasReceber);
            return "ContaReceber";
        }
        catch (NoResultException ne) {
            FacesUtil.showInfoMessage("Nenhuma conta a receber!", null);
            return "ContaReceber";
        }
        catch (Exception e) {
            FacesUtil.showErrorMessage(e.getMessage(), null);
            return "ContaReceber";
        }
        
    }

    private void geraTotalReceber(List<ContaReceber> contasReceber) {
        for (int i = 0; i < contasReceber.size(); i++) {
            totalReceber += contasReceber.get(i).getVenda().getTotalVenda();
        }
    }

    public String receber(ContaReceber contaReceber) {
        try {
            contaReceber.setStatusConta(StatusConta.FECHADA);
            new ContaReceberDao().receber(contaReceber);
            Caixa caixa = getCaixaAberto();
            caixa.setContasRecebidas(caixa.getContasRecebidas() + contaReceber.getVenda().getTotalVenda());
            new CaixaDao().atualizarCaixa(caixa);
            FacesUtil.showInfoMessage("Conta Recebida!", null);
            return consultarTodas();
        } 
        catch (Exception e) {
            FacesUtil.showErrorMessage(e.getMessage(), null);
            return consultarTodas();
        }
    }
    
    public String receberTodas() {
        try {
            new ContaReceberDao().receberTodas(contasReceber);
            Caixa caixa = getCaixaAberto();
            caixa.setContasRecebidas(caixa.getContasRecebidas() + totalReceber);
            new CaixaDao().abrirCaixa(caixa);
            FacesUtil.showInfoMessage("Contas Recebidas!", null);
            return consultarTodas();
        } 
        catch (Exception e) {
            FacesUtil.showErrorMessage("OPS! Um erro ocorreu ao receber as contas!\n" + e.getMessage(), null);
            return consultarTodas();
        }
    }

    public String getNomeCliente() {
        return nomeCliente;
    }
    
    private Caixa getCaixaAberto() {
        try {
            return new CaixaDao().getCaixaAberto();
        } catch (Exception ex) {
            FacesUtil.showErrorMessage(ex.getMessage(), null);
            return null;
        }
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public Double getTotalReceber() {
        return totalReceber;
    }

    public void setTotalReceber(Double totalReceber) {
        this.totalReceber = totalReceber;
    }

    public List<ContaReceber> getContasReceber() {
        return contasReceber;
    }

    public void setContasReceber(List<ContaReceber> contasReceber) {
        this.contasReceber = contasReceber;
    }

}
