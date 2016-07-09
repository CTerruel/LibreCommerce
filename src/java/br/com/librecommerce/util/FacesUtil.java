/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.librecommerce.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Clovis
 */
public class FacesUtil {
    
    public static void showInfoMessage(String mensagem, String mensagemDetalhe) {
        FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, mensagemDetalhe));
    }
    
    public static void showErrorMessage(String mensagem, String mensagemDetalhe) {
        FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, mensagemDetalhe));
    }
    
    public static void showAlertMessage(String mensagem, String mensagemDetalhe) {
        FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_WARN, mensagem, mensagemDetalhe));
    }
    
}
