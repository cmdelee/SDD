/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CourseWork;

import java.sql.SQLException;
import static junit.framework.Assert.assertEquals;

/**
 *
 * @author cmdel
 */
public class DatabaseHandlerNGTest {

    @org.junit.Test
    public void testhandleDbConnection() throws Exception {
        try {
            DatabaseHandler test = new DatabaseHandler();

            String uName = DatabaseHandler.handleDbConnection().getCatalog();

            assertEquals("cdelee", uName);

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
