/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uerr.biblio.appbiblio.modelo;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jklac
 */
@Entity
@Table(name = "tipo_usuario_perfil")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoUsuarioPerfil.findAll", query = "SELECT t FROM TipoUsuarioPerfil t")
    , @NamedQuery(name = "TipoUsuarioPerfil.findById", query = "SELECT t FROM TipoUsuarioPerfil t WHERE t.id = :id")
    , @NamedQuery(name = "TipoUsuarioPerfil.findByDescricao", query = "SELECT t FROM TipoUsuarioPerfil t WHERE t.descricao = :descricao")
    , @NamedQuery(name = "TipoUsuarioPerfil.findByDataInfo", query = "SELECT t FROM TipoUsuarioPerfil t WHERE t.dataInfo = :dataInfo")})
public class TipoUsuarioPerfil implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "data_info")
    @Temporal(TemporalType.DATE)
    private Date dataInfo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoUsuarioPerfilId")
    private List<UsuarioPerfil> usuarioPerfilList;

    public TipoUsuarioPerfil() {
    }

    public TipoUsuarioPerfil(Integer id) {
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

    public Date getDataInfo() {
        return dataInfo;
    }

    public void setDataInfo(Date dataInfo) {
        this.dataInfo = dataInfo;
    }

    @XmlTransient
    public List<UsuarioPerfil> getUsuarioPerfilList() {
        return usuarioPerfilList;
    }

    public void setUsuarioPerfilList(List<UsuarioPerfil> usuarioPerfilList) {
        this.usuarioPerfilList = usuarioPerfilList;
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
        if (!(object instanceof TipoUsuarioPerfil)) {
            return false;
        }
        TipoUsuarioPerfil other = (TipoUsuarioPerfil) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getDescricao().substring(0,5)+"...";
    }
    
}
