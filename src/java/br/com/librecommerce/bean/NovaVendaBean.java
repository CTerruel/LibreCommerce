/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.librecommerce.bean;

import br.com.librecommerce.dao.CaixaDao;
import br.com.librecommerce.dao.ClienteDao;
import br.com.librecommerce.dao.ContaReceberDao;
import br.com.librecommerce.dao.ProdutoDao;
import br.com.librecommerce.dao.VendaDao;
import br.com.librecommerce.modelo.Caixa;
import br.com.librecommerce.modelo.Cliente;
import br.com.librecommerce.modelo.ContaReceber;
import br.com.librecommerce.modelo.FormaPagamento;
import br.com.librecommerce.modelo.ItemVenda;
import br.com.librecommerce.modelo.Produto;
import br.com.librecommerce.modelo.StatusConta;
import br.com.librecommerce.modelo.Venda;
import br.com.librecommerce.util.FacesUtil;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
public class NovaVendaBean {

    private Cliente cliente;
    private Venda venda;
    private Produto produto;
    private ItemVenda itemVenda;
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
            venda.setCaixa(getCaixaAberto());
            venda.setDataVenda(new Date());

            VendaDao vendaDao = new VendaDao();

            if (venda.getCliente() == null) {
                vendaDao.salvar(venda);
                atualizarCaixa(venda.getFormaPagamento(), venda.getTotalVenda());
            } else {
                venda.setValorPago(0.0);
                vendaDao.salvar(venda);
                atualizarCaixa(venda.getFormaPagamento(), venda.getTotalVenda());
                gerarContaReceber(venda);
            }

            numeroItem = 1;
            nomeCliente = "";
            venda = new Venda();
            cliente = new Cliente();

            FacesUtil.showInfoMessage("Venda salva com sucesso!", null);
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesUtil.showErrorMessage(ex.getMessage(), null);
        }

        return "NovaVenda";
    }

    private void gerarContaReceber(Venda venda) throws Exception {
        ContaReceber contaReceber = new ContaReceber();
        contaReceber.setVenda(venda);
        // Calcular data de vencimento pex: mes seguinte
        contaReceber.setDataVencimento(gerarDataVencimento(new Date()));
        contaReceber.setStatusConta(StatusConta.ABERTA);
        new ContaReceberDao().gerarContaReceber(contaReceber);
    }

    private Date gerarDataVencimento(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
        return c.getTime();
    }

    private void atualizarCaixa(FormaPagamento formaPagamento, Double totalVenda) throws Exception {
        Caixa caixa = getCaixaAberto();
        if (caixa != null) {
            switch (formaPagamento) {
                case DINHEIRO:
                    caixa.setTotalDinheiro(caixa.getTotalDinheiro() + totalVenda);
                    caixa.setTotalVendas(caixa.getTotalVendas() + totalVenda);
                    break;
                case CARTAO_DEBITO:
                    caixa.setTotalCartaoDebito(caixa.getTotalCartaoDebito() + totalVenda);
                    caixa.setTotalVendas(caixa.getTotalVendas() + totalVenda);
                    break;
                case CARTAO_CREDITO:
                    caixa.setTotalCartaoCredito(caixa.getTotalCartaoCredito() + totalVenda);
                    caixa.setTotalVendas(caixa.getTotalVendas() + totalVenda);
                    break;
                case PRAZO_30_DIAS:
                    caixa.setTotalPrazo30Dias(caixa.getTotalPrazo30Dias() + totalVenda);
                    //caixa.setTotalVendas(caixa.getTotalVendas() + totalVenda);
                    break;
            }

            new CaixaDao().atualizarCaixa(caixa);

        }
    }

    private Caixa getCaixaAberto() throws Exception {
        return new CaixaDao().getCaixaAberto();
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
