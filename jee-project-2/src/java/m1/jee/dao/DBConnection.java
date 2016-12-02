package m1.jee.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
  private Connection db;

  private DBConnection(){
    try{
      Properties prop = new Properties();
      InputStream input = new FileInputStream("/WEB-INF/db.properties");

      String url = prop.getProperty("url");
      String user = prop.getProperty("login");
      String password = prop.getProperty("password");
      
      db = DriverManager.getConnection(url, user, password);
    }
    catch(FileNotFoundException | SQLException e){
      System.out.println(e.getMessage());
    }
  }

  private static class DBConnectionHolder{		
    private final static DBConnection INSTANCE = new DBConnection();
  }

  public static DBConnection getInstance(){
    return DBConnectionHolder.INSTANCE;
  }
}
