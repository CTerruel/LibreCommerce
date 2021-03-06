/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.librecommerce.modelo.converter;

import br.com.librecommerce.dao.CategoriaDao;
import br.com.librecommerce.modelo.Categoria;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Clovis
 */
@FacesConverter(forClass = Categoria.class)
public class CategoriaConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string != null || !string.isEmpty()) {
            return new CategoriaDao().buscarPorId(Categoria.class, Integer.valueOf(string));
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null && o instanceof Categoria) {
            Categoria c = (Categoria) o;
            return String.valueOf(c.getId());
        }
        return "";
    }
    
}
