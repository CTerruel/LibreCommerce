/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.librecommerce.bean;

import br.com.librecommerce.dao.CidadeDao;
import br.com.librecommerce.dao.ClienteDao;
import br.com.librecommerce.dao.EstadoDao;
import br.com.librecommerce.modelo.Cidade;
import br.com.librecommerce.modelo.Cliente;
import br.com.librecommerce.modelo.Estado;
import br.com.librecommerce.util.FacesUtil;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Clovis
 */
@ManagedBean
@SessionScoped
public class ClienteBean {
    
    private Cliente cliente;
    private Estado estado;
    private Cidade cidade;
    private List<Estado> estados;
    private List<Cidade> cidades;

    /**
     * Creates a new instance of ClienteBean
     */
    public ClienteBean() {
        cliente = new Cliente();
        estado = new Estado();
        estados = new EstadoDao().buscarTodos();
    }
    
    public void buscaCidadesDoEstado() {
        cidades = new CidadeDao().buscarTodasDoEstado(estado);
    }
    
    public void salvar() {
        new ClienteDao().salvar(cliente);
        cliente = new Cliente();
        FacesUtil.showInfoMessage("Cliente salvo com sucesso!", null);
    }
    
    public void atualizar() {
        new ClienteDao().atualizar(cliente);
        cliente = new Cliente();
        FacesUtil.showInfoMessage("Cliente atualizado!", null);
    }
    
    public String editar(Cliente cliente) {
        this.cliente = cliente;
        return "CadastroCliente";
    }
    
    public String novo() {
        if (cliente != null) cliente = new Cliente();
        return "CadastroCliente?faces-redirect=true";
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public List<Estado> getEstados() {
        return estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public List<Cidade> getCidades() {
        return cidades;
    }

    public void setCidades(List<Cidade> cidades) {
        this.cidades = cidades;
    }
    
    
}
