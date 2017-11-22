package br.edu.uerr.biblio.appbiblio.modelo;

import br.edu.uerr.biblio.appbiblio.modelo.Enderecos;
import br.edu.uerr.biblio.appbiblio.modelo.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-17T22:47:11")
@StaticMetamodel(Endereco.class)
public class Endereco_ { 

    public static volatile SingularAttribute<Endereco, String> numero;
    public static volatile SingularAttribute<Endereco, Enderecos> enderecosId;
    public static volatile SingularAttribute<Endereco, Integer> id;
    public static volatile SingularAttribute<Endereco, Usuario> usuarioId;

}