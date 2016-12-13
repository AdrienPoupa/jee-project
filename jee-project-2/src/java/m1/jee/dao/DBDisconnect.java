package m1.jee.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBDisconnect {
  /**
   * Close a database connection given in parameter
   * @param db Connection
   */
  public static void disconnect(Connection db){
    try {
      if(db != null){
        db.close();
      }
    } 
    catch (SQLException ex) {
      Logger.getLogger(DBDisconnect.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
