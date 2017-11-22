package br.edu.uerr.biblio.appbiblio.modelo;

import br.edu.uerr.biblio.appbiblio.modelo.Endereco;
import br.edu.uerr.biblio.appbiblio.modelo.UsuarioPerfil;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-17T22:47:11")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile ListAttribute<Usuario, Endereco> enderecoList;
    public static volatile SingularAttribute<Usuario, String> telefone;
    public static volatile SingularAttribute<Usuario, Date> dataNasc;
    public static volatile SingularAttribute<Usuario, String> rg;
    public static volatile ListAttribute<Usuario, UsuarioPerfil> usuarioPerfilList;
    public static volatile SingularAttribute<Usuario, Date> dataCad;
    public static volatile SingularAttribute<Usuario, String> cpf;
    public static volatile SingularAttribute<Usuario, String> matricula;
    public static volatile SingularAttribute<Usuario, String> celular;
    public static volatile SingularAttribute<Usuario, String> nome;
    public static volatile SingularAttribute<Usuario, Integer> id;
    public static volatile SingularAttribute<Usuario, String> cadastro;

}