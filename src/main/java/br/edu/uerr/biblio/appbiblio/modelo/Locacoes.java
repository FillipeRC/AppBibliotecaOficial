/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uerr.biblio.appbiblio.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jklac
 */
@Entity
@Table(name = "locacoes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Locacoes.findAll", query = "SELECT l FROM Locacoes l")
    , @NamedQuery(name = "Locacoes.findById", query = "SELECT l FROM Locacoes l WHERE l.id = :id")})
public class Locacoes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "acervo_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Acervo acervoId;
    @JoinColumn(name = "locacao_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Locacao locacaoId;

    public Locacoes() {
    }

    public Locacoes(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Acervo getAcervoId() {
        return acervoId;
    }

    public void setAcervoId(Acervo acervoId) {
        this.acervoId = acervoId;
    }

    public Locacao getLocacaoId() {
        return locacaoId;
    }

    public void setLocacaoId(Locacao locacaoId) {
        this.locacaoId = locacaoId;
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
        if (!(object instanceof Locacoes)) {
            return false;
        }
        Locacoes other = (Locacoes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.edu.uerr.biblio.appbiblio.modelo.Locacoes[ id=" + id + " ]";
    }
    
}
