package br.edu.uerr.biblio.appbiblio.modelo;

import br.edu.uerr.biblio.appbiblio.modelo.UsuarioPerfil;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-17T22:47:11")
@StaticMetamodel(TipoUsuarioPerfil.class)
public class TipoUsuarioPerfil_ { 

    public static volatile SingularAttribute<TipoUsuarioPerfil, Date> dataInfo;
    public static volatile ListAttribute<TipoUsuarioPerfil, UsuarioPerfil> usuarioPerfilList;
    public static volatile SingularAttribute<TipoUsuarioPerfil, Integer> id;
    public static volatile SingularAttribute<TipoUsuarioPerfil, String> descricao;

}