/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uerr.biblio.appbiblio.modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jklac
 */
@Entity
@Table(name = "flag")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Flag.findAll", query = "SELECT f FROM Flag f")
    , @NamedQuery(name = "Flag.findById", query = "SELECT f FROM Flag f WHERE f.id = :id")
    , @NamedQuery(name = "Flag.findByRegistro", query = "SELECT f FROM Flag f WHERE f.registro = :registro")})
public class Flag implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "registro")
    private String registro;
    @JoinColumn(name = "locacao_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Locacao locacaoId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "flagId")
    private List<StatusLocacao> statusLocacaoList;

    public Flag() {
    }

    public Flag(Integer id) {
        this.id = id;
    }

    public Flag(Integer id, String registro) {
        this.id = id;
        this.registro = registro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public Locacao getLocacaoId() {
        return locacaoId;
    }

    public void setLocacaoId(Locacao locacaoId) {
        this.locacaoId = locacaoId;
    }

    @XmlTransient
    public List<StatusLocacao> getStatusLocacaoList() {
        return statusLocacaoList;
    }

    public void setStatusLocacaoList(List<StatusLocacao> statusLocacaoList) {
        this.statusLocacaoList = statusLocacaoList;
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
        if (!(object instanceof Flag)) {
            return false;
        }
        Flag other = (Flag) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.edu.uerr.biblio.appbiblio.modelo.Flag[ id=" + id + " ]";
    }
    
}
