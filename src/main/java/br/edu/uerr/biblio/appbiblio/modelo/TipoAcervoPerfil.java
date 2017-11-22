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
@Table(name = "tipo_acervo_perfil")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoAcervoPerfil.findAll", query = "SELECT t FROM TipoAcervoPerfil t")
    , @NamedQuery(name = "TipoAcervoPerfil.findById", query = "SELECT t FROM TipoAcervoPerfil t WHERE t.id = :id")
    , @NamedQuery(name = "TipoAcervoPerfil.findByDescricao", query = "SELECT t FROM TipoAcervoPerfil t WHERE t.descricao = :descricao")})
public class TipoAcervoPerfil implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "descricao")
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoAcervoPerfilId")
    private List<AcervoPerfil> acervoPerfilList;

    public TipoAcervoPerfil() {
    }

    public TipoAcervoPerfil(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<AcervoPerfil> getAcervoPerfilList() {
        return acervoPerfilList;
    }

    public void setAcervoPerfilList(List<AcervoPerfil> acervoPerfilList) {
        this.acervoPerfilList = acervoPerfilList;
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
        if (!(object instanceof TipoAcervoPerfil)) {
            return false;
        }
        TipoAcervoPerfil other = (TipoAcervoPerfil) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.edu.uerr.biblio.appbiblio.modelo.TipoAcervoPerfil[ id=" + id + " ]";
    }
    
}
