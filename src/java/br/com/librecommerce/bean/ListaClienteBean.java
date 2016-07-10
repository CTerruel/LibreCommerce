/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.librecommerce.bean;

import br.com.librecommerce.dao.ClienteDao;
import br.com.librecommerce.modelo.Cliente;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Clovis
 */
@ManagedBean
@RequestScoped
public class ListaClienteBean {
    
    private String buscaCliente;
    private List<Cliente> clientes;

    /**
     * Creates a new instance of ListaClienteBean
     */
    public ListaClienteBean() {
        this.buscaCliente = "";
        this.clientes = new ClienteDao().listarTodos();
    }

    public void pesquisar() {
        this.clientes = new ClienteDao().buscarClientesPorNome(buscaCliente);
    }
    
    public String getBuscaCliente() {
        return buscaCliente;
    }

    public void setBuscaCliente(String buscaCliente) {
        this.buscaCliente = buscaCliente;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }
    
    
    
}
