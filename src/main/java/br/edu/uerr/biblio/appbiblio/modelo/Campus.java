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
@Table(name = "campus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Campus.findAll", query = "SELECT c FROM Campus c")
    , @NamedQuery(name = "Campus.findById", query = "SELECT c FROM Campus c WHERE c.id = :id")
    , @NamedQuery(name = "Campus.findByNome", query = "SELECT c FROM Campus c WHERE c.nome = :nome")
    , @NamedQuery(name = "Campus.findByTelefone", query = "SELECT c FROM Campus c WHERE c.telefone = :telefone")
    , @NamedQuery(name = "Campus.findByResponsavel", query = "SELECT c FROM Campus c WHERE c.responsavel = :responsavel")
    , @NamedQuery(name = "Campus.findByCnpj", query = "SELECT c FROM Campus c WHERE c.cnpj = :cnpj")})
public class Campus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "telefone")
    private String telefone;
    @Column(name = "responsavel")
    private String responsavel;
    @Column(name = "cnpj")
    private String cnpj;
    @JoinColumn(name = "enderecos_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Enderecos enderecosId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "campusId")
    private List<AcervoPerfil> acervoPerfilList;

    public Campus() {
    }

    public Campus(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Enderecos getEnderecosId() {
        return enderecosId;
    }

    public void setEnderecosId(Enderecos enderecosId) {
        this.enderecosId = enderecosId;
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
        if (!(object instanceof Campus)) {
            return false;
        }
        Campus other = (Campus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.edu.uerr.biblio.appbiblio.modelo.Campus[ id=" + id + " ]";
    }
    
}
