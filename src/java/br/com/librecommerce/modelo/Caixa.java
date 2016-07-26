/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.librecommerce.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Clovis
 */

@Entity
public class Caixa implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int id;
    private Double contasRecebidas;
    private Double contasPagas;
    private Double sangria;
    private Date dataAbertura;
    private Date dataFechamento;
    private Double valorAbertura;
    private Double totalVendas;
    private Double totalDinheiro;
    private Double totalCartaoDebito;
    private Double totalCartaoCredito;
    private Double totalPrazo30Dias;
    private Double valorFechamento;
    private StatusCaixa statusCaixa;
    private List<Venda> vendas;
    private Funcionario funcionario;

    public Caixa() {
        vendas = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "CONTAS_RECEBIDAS", scale = 2)
    public Double getContasRecebidas() {
        return contasRecebidas;
    }

    public void setContasRecebidas(Double contasRecebidas) {
        this.contasRecebidas = contasRecebidas;
    }

    @Column(name = "CONTAS_PAGAS", scale = 2)
    public Double getContasPagas() {
        return contasPagas;
    }

    public void setContasPagas(Double contasPagas) {
        this.contasPagas = contasPagas;
    }

    @Column(scale = 2)
    public Double getSangria() {
        return sangria;
    }

    public void setSangria(Double sangria) {
        this.sangria = sangria;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_ABERTURA")
    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_FECHAMENTO")
    public Date getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(Date dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    @Column(scale = 2, name = "VALOR_ABERTURA")
    public Double getValorAbertura() {
        return valorAbertura;
    }

    public void setValorAbertura(Double valorAbertura) {
        this.valorAbertura = valorAbertura;
    }
    
    @Column(scale = 2, name = "TOTAL_VENDAS")
    public Double getTotalVendas() {
        if (totalVendas == null) totalVendas = 0.0;
        return totalVendas;
    }

    public void setTotalVendas(Double totalVendas) {
        this.totalVendas = totalVendas;
    }

    @Column(scale = 2, name = "TOTAL_DINHEIRO")
    public Double getTotalDinheiro() {
        if (totalDinheiro == null) totalDinheiro = 0.0;
        return totalDinheiro;
    }

    public void setTotalDinheiro(Double totalDinheiro) {
        this.totalDinheiro = totalDinheiro;
    }

    @Column(scale = 2, name = "TOTAL_CARTAO_DEBITO")
    public Double getTotalCartaoDebito() {
        if (totalCartaoDebito == null) totalCartaoDebito = 0.0;
        return totalCartaoDebito;
    }

    public void setTotalCartaoDebito(Double totalCartaoDebito) {
        this.totalCartaoDebito = totalCartaoDebito;
    }

    @Column(scale = 2, name = "TOTAL_CARTAO_CREDITO")
    public Double getTotalCartaoCredito() {
        if (totalCartaoCredito == null) totalCartaoCredito = 0.0;
        return totalCartaoCredito;
    }

    public void setTotalCartaoCredito(Double totalCartaoCredito) {
        this.totalCartaoCredito = totalCartaoCredito;
    }

    @Column(scale = 2, name = "TOTAL_PRAZO_30_DIAS")
    public Double getTotalPrazo30Dias() {
        if (totalPrazo30Dias == null) totalPrazo30Dias = 0.0;
        return totalPrazo30Dias;
    }

    public void setTotalPrazo30Dias(Double totalPrazo30Dias) {
        this.totalPrazo30Dias = totalPrazo30Dias;
    }

    @Column(scale = 2, name = "VALOR_FECHAMENTO")
    public Double getValorFechamento() {
        return valorFechamento;
    }

    public void setValorFechamento(Double valorFechamento) {
        this.valorFechamento = valorFechamento;
    }

    @Enumerated(EnumType.STRING)
    @Column(length = 15, name = "STATUS_CAIXA")
    public StatusCaixa getStatusCaixa() {
        return statusCaixa;
    }

    public void setStatusCaixa(StatusCaixa statusCaixa) {
        this.statusCaixa = statusCaixa;
    }
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "caixa", fetch = FetchType.LAZY)
    public List<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(List<Venda> vendas) {
        this.vendas = vendas;
    }

    @ManyToOne
    @JoinColumn(name = "FUNCIONARIO_ID", referencedColumnName = "ID")
    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Caixa other = (Caixa) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
}
