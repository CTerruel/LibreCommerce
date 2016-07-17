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
import javax.persistence.NoResultException;

/**
 *
 * @author Clovis
 */
@ManagedBean
@SessionScoped
public class ClienteBean {

    private Cliente cliente;
    private Estado estado;
    private List<Cliente> clientes;
    private List<Estado> estados;
    private List<Cidade> cidades;
    private boolean habilitaEdicao = false;

    /**
     * Creates a new instance of ClienteBean
     */
    public ClienteBean() {
        cliente = new Cliente();
        estado = new Estado();
        clientes = getTodosClientes();
        estados = getTodosEstados();
    }

    public void buscaCidadesDoEstado() {
        try {
            cidades = new CidadeDao().buscarTodasDoEstado(estado);
        } catch (Exception ex) {
            FacesUtil.showErrorMessage(ex.getMessage(), null);
        }
    }

    public void buscaClientesPorNome() {
        try {
            clientes = new ClienteDao().buscarClientesPorNome(cliente.getNome());
        } 
        catch (NoResultException nex) {
            FacesUtil.showAlertMessage("Cliente não encontrado!", null);
        }
        catch (Exception ex) {
            FacesUtil.showErrorMessage(ex.getMessage(), null);
        }
    }

    public void salvar() {
        try {
            new ClienteDao().salvar(cliente);
            cliente = new Cliente();
            FacesUtil.showInfoMessage("Cliente salvo com sucesso!", null);
        } catch (Exception ex) {
            FacesUtil.showErrorMessage(ex.getMessage(), null);
        }

    }

    public void atualizar() {
        try {
            new ClienteDao().atualizar(cliente);
            cliente = new Cliente();
            FacesUtil.showInfoMessage("Cliente atualizado!", null);
        } 
        catch (Exception ex) {
            FacesUtil.showErrorMessage(ex.getMessage(), null);
        }

    }

    public String editar(Cliente cliente) {
        this.cliente = cliente;
        this.habilitaEdicao = true;
        return "CadastroCliente?faces-redirect=true";
    }
    
    private List<Cliente> getTodosClientes() {
        try {
            return new ClienteDao().listarTodos();
        } 
        catch (Exception ex) {
            FacesUtil.showErrorMessage(ex.getMessage(), null);
            return null;
        }
    }
    
    private List<Estado> getTodosEstados() {
        try {
            return new EstadoDao().buscarTodos();
        } catch (Exception ex) {
            FacesUtil.showErrorMessage(ex.getMessage(), null);
            return null;
        }
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

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
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

    public boolean isHabilitaEdicao() {
        return habilitaEdicao;
    }

    public void setHabilitaEdicao(boolean habilitaEdicao) {
        this.habilitaEdicao = habilitaEdicao;
    }

}
