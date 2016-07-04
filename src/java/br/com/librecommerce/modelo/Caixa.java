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
    private Date dataAbertura;
    private Date dataFechamento;
    private Double valorAbertura;
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

    @Temporal(TemporalType.TIMESTAMP)
    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(Date dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    @Column(scale = 2)
    public Double getValorAbertura() {
        return valorAbertura;
    }

    public void setValorAbertura(Double valorAbertura) {
        this.valorAbertura = valorAbertura;
    }

    @Column(scale = 2)
    public Double getValorFechamento() {
        return valorFechamento;
    }

    public void setValorFechamento(Double valorFechamento) {
        this.valorFechamento = valorFechamento;
    }

    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    public StatusCaixa getStatusCaixa() {
        return statusCaixa;
    }

    public void setStatusCaixa(StatusCaixa statusCaixa) {
        this.statusCaixa = statusCaixa;
    }
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "caixa")
    public List<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(List<Venda> vendas) {
        this.vendas = vendas;
    }

    @ManyToOne
    @JoinColumn(name = "idFuncionario", referencedColumnName = "id")
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
