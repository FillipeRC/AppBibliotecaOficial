package br.edu.uerr.biblio.appbiblio.controle.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author jklac
 */
public abstract class Conexao {
    private static final String USUARIO = "root";
    private static final String SENHA = "Polo1964";
    private static final String URL = "jdbc:mysql//127.0.0.1:3306/db_biblioteca";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    
    public static Connection abrirConn() throws SQLException, ClassNotFoundException{
        Class.forName(DRIVER);
        Connection c = DriverManager.getConnection(URL, USUARIO, SENHA);
        return c;
    }
}
