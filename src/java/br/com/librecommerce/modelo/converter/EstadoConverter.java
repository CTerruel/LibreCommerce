/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.librecommerce.modelo.converter;

import br.com.librecommerce.dao.EstadoDao;
import br.com.librecommerce.modelo.Estado;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Clovis
 */

@FacesConverter(forClass = Estado.class)
public class EstadoConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string != null && !string.isEmpty()) {
            return new EstadoDao().buscaEstadoPorId(Integer.valueOf(string));
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null && o instanceof Estado) {
            Estado e = (Estado)o;
            return String.valueOf(e.getId());
        }
        
        return "";
    }
    
}
