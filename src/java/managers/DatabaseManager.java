/**
 *
 * Author: Roberto Confalonieri
 *
 *
 */
package managers;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;
import java.sql.Statement;

import java.util.Properties;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



/**
 * @author RConfalonieri
 *
 */
public class DatabaseManager {

    private static String dbLocation = "cochemotosnew";
    private static String dbURL = "jdbc:mysql://";
    private static String dbHost = "localhost";
    private static String dbPort = "3306";
    private static String driverName = "com.mysql.jdbc.Driver";
    private static String option = "?autoReconnect=true";
    private static String userdb = "root";
    private static String pwddb = "";

    public static Connection conn = null;
    private static Statement stmt = null;

    /**
     * @return @throws Exception
     */
    public static void openConnection()  {
        try {
            //Connection conn = null;
            if (conn == null || conn.isClosed()) {
                conn = createConnection(userdb, pwddb);
            }
            //return conn;
        } catch (Exception ex) {
             LoggerManager.getLog().error(ex.getMessage());
           
        } 
    }

    /**
     *
     */
    public static void closeConnection() {

        try {

            if (conn != null) {
                conn.close();
                conn = null;
                LoggerManager.getLog().info("Connection closed!");
            }

        } catch (Exception e) {
            LoggerManager.getLog().error(e.toString());

        }
    }

    private static Connection createConnection(String userName, String password) throws SQLException, ClassNotFoundException, UnknownHostException {
        Connection conn = null;
        try {
            Class.forName(driverName);
            Properties dbProps = new Properties();
            dbProps.put("user", userName);
            dbProps.put("password", password);
            //dbHost = InetAddress.getLocalHost().getHostAddress();
            conn = DriverManager.getConnection(dbURL + dbHost + ":" + dbPort + "/" + dbLocation + option, dbProps);
            LoggerManager.getLog().info("Connection successful!");
        } catch (SQLException except) {
            LoggerManager.getLog().error(except.toString());
            LoggerManager.getLog().error("Could not connect to the database with username: " + userName);
            LoggerManager.getLog().error(" password " + password);
            LoggerManager.getLog().error("Check that the MySQL Server is running on: " + dbHost);
            throw new SQLException();
        } catch (ClassNotFoundException cnf) {
            LoggerManager.getLog().error(cnf.getMessage());
            LoggerManager.getLog().error(cnf.toString());
            throw new ClassNotFoundException();
        }
      
        return conn;
    }

   

    /**
     * @param conn
     * @param sql
     * @throws SQLException
     * @throws Exception
     */
    public static void executeDelete(Connection conn, String sql) throws SQLException, Exception {

        stmt = conn.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
    }

    public static int executeUpdate(String insertSql) throws SQLException  {
       
        int id = -1;
        String ultimoIdQuery = "SELECT last_insert_id() as last_id";
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        
        //imprimo la query en el logger
        if (LoggerManager.DEBUG)
            LoggerManager.getLog().info(insertSql);
        
        //creamos el preparedStatement para ejecutar el sql
        preparedStatement = conn.prepareStatement(insertSql);
        //lo ejecutamos
        preparedStatement.executeUpdate();
        
        //queremos recuperar el id del ultimo elemento añadido a la base de datos
        //creamos el preparedStament para ejecutar la SELECT
        preparedStatement = conn.prepareStatement(ultimoIdQuery);
        //ejecutamos la query ultimoIdQuery
        resultSet = preparedStatement.executeQuery();
        
        //procesamos el resultado
        while (resultSet.next()) {
            id = resultSet.getInt("last_id");
        }
        
        //cierro los objetos Java que gestionan una query y su resultado 
        preparedStatement.close();
        resultSet.close();
        
        return id;
    }

    
}
