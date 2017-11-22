/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uerr.biblio.appbiblio.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author jklac
 */
public abstract class AppInfoUtils {
    public static final String PU = "AppBiblioPU";
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String INPUT_CPF_FORMAT = "###.###.###-##";
    public static final String INPUT_DATE_FORMAT = "##/##/####";
    public static final String INPUT_CELULAR_FORMAT = "(##)#####-####";
    public static final String INPUT_TELEPHONE_FORMAT = "####-####";
    public static final String INPUT_CEP_FORMAT = "##.###-###";
    
    public static Date getDataAtual(){
        Calendar c = new GregorianCalendar();
        return c.getTime();
    }
    
    public static Calendar getCalendarioAtual(){
        Calendar c = new GregorianCalendar();
        return c;
    }
    
    public static String getDateFormat(Date data){
        DateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return format.format(data);
    }
    
    public static int getHoraDoDia(){
        int hora = getCalendarioAtual().get(Calendar.HOUR_OF_DAY);
        return hora;
    }
    
}
