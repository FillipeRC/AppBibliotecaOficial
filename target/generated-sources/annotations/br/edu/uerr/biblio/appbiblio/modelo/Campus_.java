package br.edu.uerr.biblio.appbiblio.modelo;

import br.edu.uerr.biblio.appbiblio.modelo.AcervoPerfil;
import br.edu.uerr.biblio.appbiblio.modelo.Enderecos;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-17T22:47:11")
@StaticMetamodel(Campus.class)
public class Campus_ { 

    public static volatile SingularAttribute<Campus, String> telefone;
    public static volatile SingularAttribute<Campus, Enderecos> enderecosId;
    public static volatile ListAttribute<Campus, AcervoPerfil> acervoPerfilList;
    public static volatile SingularAttribute<Campus, String> nome;
    public static volatile SingularAttribute<Campus, Integer> id;
    public static volatile SingularAttribute<Campus, String> cnpj;
    public static volatile SingularAttribute<Campus, String> responsavel;

}