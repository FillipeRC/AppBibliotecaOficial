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
@Table(name = "locacao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Locacao.findAll", query = "SELECT l FROM Locacao l")
    , @NamedQuery(name = "Locacao.findById", query = "SELECT l FROM Locacao l WHERE l.id = :id")
    , @NamedQuery(name = "Locacao.findByCod", query = "SELECT l FROM Locacao l WHERE l.cod = :cod")})
public class Locacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "cod")
    private String cod;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "locacaoId")
    private List<Flag> flagList;
    @JoinColumn(name = "usuario_perfil_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UsuarioPerfil usuarioPerfilId;
    @JoinColumn(name = "funcionario_perfil_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UsuarioPerfil funcionarioPerfilId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "locacaoId")
    private List<Locacoes> locacoesList;

    public Locacao() {
    }

    public Locacao(Integer id) {
        this.id = id;
    }

    public Locacao(Integer id, String cod) {
        this.id = id;
        this.cod = cod;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    @XmlTransient
    public List<Flag> getFlagList() {
        return flagList;
    }

    public void setFlagList(List<Flag> flagList) {
        this.flagList = flagList;
    }

    public UsuarioPerfil getUsuarioPerfilId() {
        return usuarioPerfilId;
    }

    public void setUsuarioPerfilId(UsuarioPerfil usuarioPerfilId) {
        this.usuarioPerfilId = usuarioPerfilId;
    }

    public UsuarioPerfil getFuncionarioPerfilId() {
        return funcionarioPerfilId;
    }

    public void setFuncionarioPerfilId(UsuarioPerfil funcionarioPerfilId) {
        this.funcionarioPerfilId = funcionarioPerfilId;
    }

    @XmlTransient
    public List<Locacoes> getLocacoesList() {
        return locacoesList;
    }

    public void setLocacoesList(List<Locacoes> locacoesList) {
        this.locacoesList = locacoesList;
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
        if (!(object instanceof Locacao)) {
            return false;
        }
        Locacao other = (Locacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.edu.uerr.biblio.appbiblio.modelo.Locacao[ id=" + id + " ]";
    }
    
}
