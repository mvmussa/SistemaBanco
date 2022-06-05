package Config;

import exception.BancoException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConection {
/*
 * Retorna el objeto de la conección a la BD
 */

    public static Connection getConnection() throws Exception {

        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url       = "jdbc:mysql://localhost:3306/sistemabanco";
            String user      = "root";
            String password  = "";
            conn = DriverManager.getConnection(url,user, password);

        } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                throw new BancoException("Error al conectar a la BD"+ex.getMessage());
        } catch (ClassNotFoundException ex) {
                System.out.println(ex.getMessage());
                throw new BancoException("Error de driver mysql "+ex.getMessage());
        }
        return conn;

    }


}
