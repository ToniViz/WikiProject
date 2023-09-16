
package dominio.bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Contains the connection logic to the SQLITE database
 * @author Toni
 */
public class Conexion {
    
    //URL to create the embedded database
    private static final String JDBC_URL = "jdbc:sqlite::memory:";
    //The SQL Driver
    private static final String SQL_DRIVER = "org.sqlite.JDBC";
    private static Connection conn = null;

    /**
     * If the connection is null initiates a connection with the database and return it. If it is not null, returns the connection object. 
     * @return: Connection
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static Connection getConnexion() throws SQLException, ClassNotFoundException {
        
        if (conn == null) {
            try {
                Class.forName(SQL_DRIVER);
                conn = DriverManager.getConnection(JDBC_URL);

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return conn;
    }


}
