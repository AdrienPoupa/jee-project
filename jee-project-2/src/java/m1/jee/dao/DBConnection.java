package m1.jee.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

public class DBConnection {
  public static Connection getConnection(ServletContext context){
    try{
      Properties prop = new Properties();
      
      InputStream input = context.getResourceAsStream("/WEB-INF/db.properties");
      prop.load(input);

      String url = prop.getProperty("url");
      String user = prop.getProperty("user");
      String password = prop.getProperty("password");

      return DriverManager.getConnection(url, user, password);
    }
    catch(FileNotFoundException | SQLException e){
      System.out.println(e.getMessage());
    }
    catch (IOException ex) {
      Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return null;
  }
}
