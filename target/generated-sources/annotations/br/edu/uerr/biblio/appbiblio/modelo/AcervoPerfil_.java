package br.edu.uerr.biblio.appbiblio.modelo;

import br.edu.uerr.biblio.appbiblio.modelo.Acervo;
import br.edu.uerr.biblio.appbiblio.modelo.Campus;
import br.edu.uerr.biblio.appbiblio.modelo.TipoAcervoPerfil;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-17T22:47:11")
@StaticMetamodel(AcervoPerfil.class)
public class AcervoPerfil_ { 

    public static volatile ListAttribute<AcervoPerfil, Acervo> acervoList;
    public static volatile SingularAttribute<AcervoPerfil, TipoAcervoPerfil> tipoAcervoPerfilId;
    public static volatile SingularAttribute<AcervoPerfil, Campus> campusId;
    public static volatile SingularAttribute<AcervoPerfil, Integer> id;
    public static volatile SingularAttribute<AcervoPerfil, String> descricao;

}