/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uerr.biblio.appbiblio.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jklac
 */
@Entity
@Table(name = "status_locacao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StatusLocacao.findAll", query = "SELECT s FROM StatusLocacao s")
    , @NamedQuery(name = "StatusLocacao.findById", query = "SELECT s FROM StatusLocacao s WHERE s.id = :id")
    , @NamedQuery(name = "StatusLocacao.findByDataRealizacao", query = "SELECT s FROM StatusLocacao s WHERE s.dataRealizacao = :dataRealizacao")
    , @NamedQuery(name = "StatusLocacao.findByHoraRealizacao", query = "SELECT s FROM StatusLocacao s WHERE s.horaRealizacao = :horaRealizacao")})
public class StatusLocacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "data_realizacao")
    @Temporal(TemporalType.DATE)
    private Date dataRealizacao;
    @Column(name = "hora_realizacao")
    private String horaRealizacao;
    @JoinColumn(name = "flag_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Flag flagId;

    public StatusLocacao() {
    }

    public StatusLocacao(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataRealizacao() {
        return dataRealizacao;
    }

    public void setDataRealizacao(Date dataRealizacao) {
        this.dataRealizacao = dataRealizacao;
    }

    public String getHoraRealizacao() {
        return horaRealizacao;
    }

    public void setHoraRealizacao(String horaRealizacao) {
        this.horaRealizacao = horaRealizacao;
    }

    public Flag getFlagId() {
        return flagId;
    }

    public void setFlagId(Flag flagId) {
        this.flagId = flagId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StatusLocacao)) {
            return false;
        }
        StatusLocacao other = (StatusLocacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.edu.uerr.biblio.appbiblio.modelo.StatusLocacao[ id=" + id + " ]";
    }
    
}
