/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.librecommerce.bean;

import br.com.librecommerce.dao.CaixaDao;
import br.com.librecommerce.dao.ContaPagarDao;
import br.com.librecommerce.modelo.Caixa;
import br.com.librecommerce.modelo.ContaPagar;
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
public class ContaPagarBean {
    
    private ContaPagar contaPagar;
    private List<ContaPagar> contasPagar;

    /**
     * Creates a new instance of ContaPagarBean
     */
    public ContaPagarBean() {
        contaPagar = new ContaPagar();
        consultarTodas();
    }

    public String salvarConta() {
        try {
            contaPagar.setStatusConta(StatusConta.ABERTA);
            System.out.println("descricao" + contaPagar.getDescricao());
            new ContaPagarDao().salvarConta(contaPagar);
            contaPagar = new ContaPagar();
            return "ContaPagar";
        } 
        catch (Exception e) {
            FacesUtil.showErrorMessage(e.getMessage(), null);
            return "ContaPagar";
        }
    }
    
    public String consultarTodas() {
        try {
            contasPagar = new ContaPagarDao().consultarTodas();
            return "ContaPagar";
        }
        catch (NoResultException ne) {
            FacesUtil.showErrorMessage("Nenhuma conta encontrada!", null);
            return "ContaPagar";
        }
        catch (Exception e) {
            FacesUtil.showErrorMessage(e.getMessage(), null);
            return "ContaPagar";
        }
    }
    
    public void consultarPorDescricao() {
        try {
            contasPagar = new ContaPagarDao().consultarPorDescricao(contaPagar.getDescricao());
        }
        catch (NoResultException ne) {
            FacesUtil.showErrorMessage("Nenhuma conta encontrada!", null);
        }
        catch (Exception e) {
            FacesUtil.showErrorMessage(e.getMessage(), null);
        }
    }
    
    public String pagarConta(ContaPagar contaPagar) {
        try {
            contaPagar.setStatusConta(StatusConta.FECHADA);
            new ContaPagarDao().pagarConta(contaPagar);
            
            CaixaDao caixaDao = new CaixaDao();
            Caixa caixa = caixaDao.getCaixaAberto();
            caixa.setContasPagas(caixa.getContasPagas() + contaPagar.getValor());
            caixaDao.atualizarCaixa(caixa);
            
            return consultarTodas();
        } 
        catch (Exception e) {
            FacesUtil.showErrorMessage(e.getMessage(), null);
            return consultarTodas();
        }
    } 
    
    public ContaPagar getContaPagar() {
        return contaPagar;
    }

    public void setContaPagar(ContaPagar contaPagar) {
        this.contaPagar = contaPagar;
    }

    public List<ContaPagar> getContasPagar() {
        return contasPagar;
    }

    public void setContasPagar(List<ContaPagar> contasPagar) {
        this.contasPagar = contasPagar;
    }
    
}
