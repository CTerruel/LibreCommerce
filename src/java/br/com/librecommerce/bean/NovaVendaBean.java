/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.librecommerce.bean;

import br.com.librecommerce.dao.ClienteDao;
import br.com.librecommerce.dao.ProdutoDao;
import br.com.librecommerce.dao.VendaDao;
import br.com.librecommerce.modelo.Caixa;
import br.com.librecommerce.modelo.Cliente;
import br.com.librecommerce.modelo.FormaPagamento;
import br.com.librecommerce.modelo.ItemVenda;
import br.com.librecommerce.modelo.Produto;
import br.com.librecommerce.modelo.Venda;
import br.com.librecommerce.util.FacesUtil;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;

/**
 *
 * @author Clovis
 */
@ManagedBean
@SessionScoped
public class NovaVendaBean {

    private Cliente cliente;
    private Venda venda;
    private Produto produto;
    private ItemVenda itemVenda;
    private Caixa caixa;
    private String buscaNomeCliente = "";
    private String nomeCliente = "";
    private int numeroItem = 1;
    private List<FormaPagamento> formasPagamentos = Arrays.asList(FormaPagamento.values());
    private List<Cliente> clientes;

    /**
     * Creates a new instance of NovaVendaBean
     */
    public NovaVendaBean() {
        venda = new Venda();
        itemVenda = new ItemVenda();
        cliente = new Cliente();
        produto = new Produto();
        caixa = (Caixa) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("caixa");
    }

    public String adicionar() {
        try {
            produto = new ProdutoDao().buscarProdutoPorCodigoBarra(produto.getCodigoBarra());

            produto.setEstoque(produto.getEstoque() - itemVenda.getQuantidadeProduto());
            itemVenda.setProduto(produto);
            itemVenda.setNumeroItem(numeroItem);
            numeroItem++;
            itemVenda.setValorTotal(itemVenda.getQuantidadeProduto()
                    * produto.getValorUnitario());
            venda.getItensVenda().add(itemVenda);
            venda.setTotalVenda(venda.getTotalVenda() + itemVenda.getValorTotal());
            itemVenda.setVenda(venda);
            itemVenda = new ItemVenda();
            produto = new Produto();

        } catch (NoResultException nex) {
            FacesUtil.showAlertMessage("Produto não encontrado!", null);
        } catch (Exception e) {
            FacesUtil.showErrorMessage(e.getMessage(), null);
        }

        return "NovaVenda";
    }

    public String prepararSalvarVenda() {
        if (venda.getItensVenda().isEmpty()) {
            FacesUtil.showAlertMessage("Nenhum item vendido!", null);
            return "NovaVenda";
        }
        return "ConfirmarVenda";
    }

    public String salvar() {
        try {
            venda.setCaixa(caixa);
            venda.setDataVenda(new Date());
            
            new VendaDao().salvar(venda);
            
            numeroItem = 1;
            venda = new Venda();
            cliente = new Cliente();
            
            FacesUtil.showInfoMessage("Venda salva com sucesso!", null);
        } catch (Exception ex) {
            FacesUtil.showErrorMessage(ex.getMessage(), null);
        }

        return "NovaVenda";
    }
   
    public String cancelarVendaPasso1() {
        clearVenda();
        return "NovaVenda";
    }
    
    public String cancelarVendaPasso2() {
        clearVenda();
        this.nomeCliente = "";
        return "NovaVenda";
    }
    
    private void clearVenda() {
        venda.getItensVenda().clear();
        venda.setTotalVenda(0.0);
    }

    public String removerItem(ItemVenda itemVenda) {
        venda.setTotalVenda(venda.getTotalVenda() - itemVenda.getValorTotal());
        venda.getItensVenda().remove(itemVenda);
        return "NovaVenda";
    }

    public void buscatClientesPorNome() {
        try {
            clientes = new ClienteDao().buscarClientesPorNome(buscaNomeCliente);
        } catch (Exception ex) {
            FacesUtil.showErrorMessage(ex.getMessage(), null);
        }
    }

    public void atualizaTroco() {
        venda.setTroco(venda.getValorPago() - venda.getTotalVenda());
    }

    public void escolheCliente(Cliente cliente) {
        this.nomeCliente = cliente.getNome();
        venda.setCliente(cliente);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public ItemVenda getItemVenda() {
        return itemVenda;
    }

    public void setItemVenda(ItemVenda itemVenda) {
        this.itemVenda = itemVenda;
    }

    public List<FormaPagamento> getFormasPagamentos() {
        return formasPagamentos;
    }

    public void setFormasPagamentos(List<FormaPagamento> formasPagamentos) {
        this.formasPagamentos = formasPagamentos;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public String getBuscaNomeCliente() {
        return buscaNomeCliente;
    }

    public void setBuscaNomeCliente(String nomeCliente) {
        this.buscaNomeCliente = nomeCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

}
