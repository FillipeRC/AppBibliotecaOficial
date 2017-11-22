package br.edu.uerr.biblio.appbiblio.modelo;

import br.edu.uerr.biblio.appbiblio.modelo.Flag;
import br.edu.uerr.biblio.appbiblio.modelo.Locacoes;
import br.edu.uerr.biblio.appbiblio.modelo.UsuarioPerfil;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-17T22:47:11")
@StaticMetamodel(Locacao.class)
public class Locacao_ { 

    public static volatile ListAttribute<Locacao, Locacoes> locacoesList;
    public static volatile ListAttribute<Locacao, Flag> flagList;
    public static volatile SingularAttribute<Locacao, String> cod;
    public static volatile SingularAttribute<Locacao, UsuarioPerfil> funcionarioPerfilId;
    public static volatile SingularAttribute<Locacao, Integer> id;
    public static volatile SingularAttribute<Locacao, UsuarioPerfil> usuarioPerfilId;

}