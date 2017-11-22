package br.edu.uerr.biblio.appbiblio.modelo;

import br.edu.uerr.biblio.appbiblio.modelo.Locacao;
import br.edu.uerr.biblio.appbiblio.modelo.TipoUsuarioPerfil;
import br.edu.uerr.biblio.appbiblio.modelo.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-17T22:47:11")
@StaticMetamodel(UsuarioPerfil.class)
public class UsuarioPerfil_ { 

    public static volatile SingularAttribute<UsuarioPerfil, String> senha;
    public static volatile SingularAttribute<UsuarioPerfil, TipoUsuarioPerfil> tipoUsuarioPerfilId;
    public static volatile ListAttribute<UsuarioPerfil, Locacao> locacaoList;
    public static volatile ListAttribute<UsuarioPerfil, Locacao> locacaoList1;
    public static volatile SingularAttribute<UsuarioPerfil, Integer> id;
    public static volatile SingularAttribute<UsuarioPerfil, String> login;
    public static volatile SingularAttribute<UsuarioPerfil, Usuario> usuarioId;

}