package br.edu.uerr.biblio.appbiblio.modelo;

import br.edu.uerr.biblio.appbiblio.modelo.AcervoPerfil;
import br.edu.uerr.biblio.appbiblio.modelo.Genero;
import br.edu.uerr.biblio.appbiblio.modelo.Locacoes;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-17T22:47:11")
@StaticMetamodel(Acervo.class)
public class Acervo_ { 

    public static volatile ListAttribute<Acervo, Locacoes> locacoesList;
    public static volatile SingularAttribute<Acervo, Genero> generoId;
    public static volatile SingularAttribute<Acervo, String> nome;
    public static volatile SingularAttribute<Acervo, Integer> id;
    public static volatile SingularAttribute<Acervo, String> tombamento;
    public static volatile SingularAttribute<Acervo, AcervoPerfil> acervoPerfilId;

}