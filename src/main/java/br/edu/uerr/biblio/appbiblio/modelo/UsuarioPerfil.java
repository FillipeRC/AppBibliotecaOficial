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
@Table(name = "usuario_perfil")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioPerfil.findAll", query = "SELECT u FROM UsuarioPerfil u")
    , @NamedQuery(name = "UsuarioPerfil.findById", query = "SELECT u FROM UsuarioPerfil u WHERE u.id = :id")
    , @NamedQuery(name = "UsuarioPerfil.findByLogin", query = "SELECT u FROM UsuarioPerfil u WHERE u.login = :login")
    , @NamedQuery(name = "UsuarioPerfil.findBySenha", query = "SELECT u FROM UsuarioPerfil u WHERE u.senha = :senha")
    , @NamedQuery(name = "UsuarioPerfil.findByLoginAndSenha", query = "SElECT u FROM UsuarioPerfil u WHERE u.login = :login AND u.senha = :senha")})
public class UsuarioPerfil implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "login")
    private String login;
    @Basic(optional = false)
    @Column(name = "senha")
    private String senha;
    @JoinColumn(name = "tipo_usuario_perfil_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoUsuarioPerfil tipoUsuarioPerfilId;
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioPerfilId")
    private List<Locacao> locacaoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionarioPerfilId")
    private List<Locacao> locacaoList1;

    public UsuarioPerfil() {
    }

    public UsuarioPerfil(Integer id) {
        this.id = id;
    }

    public UsuarioPerfil(Integer id, String login, String senha) {
        this.id = id;
        this.login = login;
        this.senha = senha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoUsuarioPerfil getTipoUsuarioPerfilId() {
        return tipoUsuarioPerfilId;
    }

    public void setTipoUsuarioPerfilId(TipoUsuarioPerfil tipoUsuarioPerfilId) {
        this.tipoUsuarioPerfilId = tipoUsuarioPerfilId;
    }

    public Usuario getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuario usuarioId) {
        this.usuarioId = usuarioId;
    }

    @XmlTransient
    public List<Locacao> getLocacaoList() {
        return locacaoList;
    }

    public void setLocacaoList(List<Locacao> locacaoList) {
        this.locacaoList = locacaoList;
    }

    @XmlTransient
    public List<Locacao> getLocacaoList1() {
        return locacaoList1;
    }

    public void setLocacaoList1(List<Locacao> locacaoList1) {
        this.locacaoList1 = locacaoList1;
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
        if (!(object instanceof UsuarioPerfil)) {
            return false;
        }
        UsuarioPerfil other = (UsuarioPerfil) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.edu.uerr.biblio.appbiblio.modelo.UsuarioPerfil[ id=" + id + " ]";
    }
    
}
