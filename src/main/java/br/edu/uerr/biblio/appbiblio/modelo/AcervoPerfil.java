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
@Table(name = "acervo_perfil")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AcervoPerfil.findAll", query = "SELECT a FROM AcervoPerfil a")
    , @NamedQuery(name = "AcervoPerfil.findById", query = "SELECT a FROM AcervoPerfil a WHERE a.id = :id")
    , @NamedQuery(name = "AcervoPerfil.findByDescricao", query = "SELECT a FROM AcervoPerfil a WHERE a.descricao = :descricao")})
public class AcervoPerfil implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "descricao")
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "acervoPerfilId")
    private List<Acervo> acervoList;
    @JoinColumn(name = "campus_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Campus campusId;
    @JoinColumn(name = "tipo_acervo_perfil_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoAcervoPerfil tipoAcervoPerfilId;

    public AcervoPerfil() {
    }

    public AcervoPerfil(Integer id) {
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
    public List<Acervo> getAcervoList() {
        return acervoList;
    }

    public void setAcervoList(List<Acervo> acervoList) {
        this.acervoList = acervoList;
    }

    public Campus getCampusId() {
        return campusId;
    }

    public void setCampusId(Campus campusId) {
        this.campusId = campusId;
    }

    public TipoAcervoPerfil getTipoAcervoPerfilId() {
        return tipoAcervoPerfilId;
    }

    public void setTipoAcervoPerfilId(TipoAcervoPerfil tipoAcervoPerfilId) {
        this.tipoAcervoPerfilId = tipoAcervoPerfilId;
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
        if (!(object instanceof AcervoPerfil)) {
            return false;
        }
        AcervoPerfil other = (AcervoPerfil) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.edu.uerr.biblio.appbiblio.modelo.AcervoPerfil[ id=" + id + " ]";
    }
    
}
