package br.edu.uerr.biblio.appbiblio.modelo;

import br.edu.uerr.biblio.appbiblio.modelo.Flag;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-17T22:47:11")
@StaticMetamodel(StatusLocacao.class)
public class StatusLocacao_ { 

    public static volatile SingularAttribute<StatusLocacao, Date> dataRealizacao;
    public static volatile SingularAttribute<StatusLocacao, String> horaRealizacao;
    public static volatile SingularAttribute<StatusLocacao, Flag> flagId;
    public static volatile SingularAttribute<StatusLocacao, Integer> id;

}