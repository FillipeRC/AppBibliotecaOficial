package br.edu.uerr.biblio.appbiblio.modelo;

import br.edu.uerr.biblio.appbiblio.modelo.Campus;
import br.edu.uerr.biblio.appbiblio.modelo.Endereco;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-17T22:47:11")
@StaticMetamodel(Enderecos.class)
public class Enderecos_ { 

    public static volatile ListAttribute<Enderecos, Endereco> enderecoList;
    public static volatile SingularAttribute<Enderecos, String> cidade;
    public static volatile SingularAttribute<Enderecos, String> estado;
    public static volatile ListAttribute<Enderecos, Campus> campusList;
    public static volatile SingularAttribute<Enderecos, String> logradouro;
    public static volatile SingularAttribute<Enderecos, String> bairro;
    public static volatile SingularAttribute<Enderecos, Integer> id;
    public static volatile SingularAttribute<Enderecos, String> cep;

}