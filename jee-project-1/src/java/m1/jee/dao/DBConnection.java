package m1.jee.dao;

public class DBConnection {
  

  private DBConnection(){

  }

  private static class DBConnectionHolder{		
    private final static DBConnection INSTANCE = new DBConnection();
  }

  public static DBConnection getInstance(){
    return DBConnectionHolder.INSTANCE;
  }
}
