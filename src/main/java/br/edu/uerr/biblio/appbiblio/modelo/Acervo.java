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
@Table(name = "acervo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Acervo.findAll", query = "SELECT a FROM Acervo a")
    , @NamedQuery(name = "Acervo.findById", query = "SELECT a FROM Acervo a WHERE a.id = :id")
    , @NamedQuery(name = "Acervo.findByTombamento", query = "SELECT a FROM Acervo a WHERE a.tombamento = :tombamento")
    , @NamedQuery(name = "Acervo.findByNome", query = "SELECT a FROM Acervo a WHERE a.nome = :nome")})
public class Acervo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "tombamento")
    private String tombamento;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @JoinColumn(name = "acervo_perfil_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AcervoPerfil acervoPerfilId;
    @JoinColumn(name = "genero_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Genero generoId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "acervoId")
    private List<Locacoes> locacoesList;

    public Acervo() {
    }

    public Acervo(Integer id) {
        this.id = id;
    }

    public Acervo(Integer id, String tombamento, String nome) {
        this.id = id;
        this.tombamento = tombamento;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTombamento() {
        return tombamento;
    }

    public void setTombamento(String tombamento) {
        this.tombamento = tombamento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public AcervoPerfil getAcervoPerfilId() {
        return acervoPerfilId;
    }

    public void setAcervoPerfilId(AcervoPerfil acervoPerfilId) {
        this.acervoPerfilId = acervoPerfilId;
    }

    public Genero getGeneroId() {
        return generoId;
    }

    public void setGeneroId(Genero generoId) {
        this.generoId = generoId;
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
        if (!(object instanceof Acervo)) {
            return false;
        }
        Acervo other = (Acervo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.edu.uerr.biblio.appbiblio.modelo.Acervo[ id=" + id + " ]";
    }
    
}
