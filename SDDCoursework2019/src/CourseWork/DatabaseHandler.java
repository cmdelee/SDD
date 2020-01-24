package CourseWork;

import java.sql.*;


/**
 *
 * @author cmdel
 */

public class DatabaseHandler {

    /**
     * creates a connection to the database, both local and LAMP (currently
     * commented out) including holding the password and login details for the
     * database
     * @return con
     */
    public static Connection handleDbConnection() {
        Connection con = null;

        try {

//        String host = "jdbc:mysql://lamp.scim.brad.ac.uk:3306/cdelee?useSSL=false";
//        String uName = "cdelee";
//        String uPass = "";

            String host = "jdbc:mysql://localhost/cdelee?useSSL=false";
            String uName = "root";
            String uPass = "";

            Class.forName("com.mysql.cj.jdbc.Driver");
            //System.out.println("Trying to connect");
            con = DriverManager.getConnection(host, uName, uPass);

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return con;
    }

}
