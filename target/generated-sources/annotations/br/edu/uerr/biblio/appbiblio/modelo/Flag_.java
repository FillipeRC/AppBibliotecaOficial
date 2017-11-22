package br.edu.uerr.biblio.appbiblio.modelo;

import br.edu.uerr.biblio.appbiblio.modelo.Locacao;
import br.edu.uerr.biblio.appbiblio.modelo.StatusLocacao;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-17T22:47:11")
@StaticMetamodel(Flag.class)
public class Flag_ { 

    public static volatile ListAttribute<Flag, StatusLocacao> statusLocacaoList;
    public static volatile SingularAttribute<Flag, Locacao> locacaoId;
    public static volatile SingularAttribute<Flag, Integer> id;
    public static volatile SingularAttribute<Flag, String> registro;

}