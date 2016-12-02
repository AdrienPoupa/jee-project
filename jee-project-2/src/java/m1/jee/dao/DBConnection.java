package m1.jee.dao;

import java.sql.Connection;

public class DBConnection {
  private Connection db;

  private DBConnection(){
     
  }

  private static class DBConnectionHolder{		
    private final static DBConnection INSTANCE = new DBConnection();
  }

  public static DBConnection getInstance(){
    return DBConnectionHolder.INSTANCE;
  }
}
