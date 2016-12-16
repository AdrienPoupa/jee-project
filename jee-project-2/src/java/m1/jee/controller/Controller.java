package m1.jee.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import m1.jee.dao.DBConnection;
import m1.jee.dao.DBDisconnect;
import m1.jee.model.BeanMember;

@WebServlet(name = "Controller", urlPatterns = {"/"})
public class Controller extends HttpServlet {  
  private static final Map<String, String> ERRORLIST;
  private static final Logger logger;
  
  static {
    ERRORLIST = new HashMap<>();
    ERRORLIST.put("MSG_ERROR_LOGIN_PWD", "Login failed! Please check your login and/or password and try again");
    ERRORLIST.put("MSG_ERROR_LOGIN", "Login failed! Please try later");
    ERRORLIST.put("MSG_SUCCESS_LOGIN", "You have been successfully logged in!");
    ERRORLIST.put("MSG_ERROR_WRONG", "Something went wrong");
    ERRORLIST.put("MSG_SUCCESS_NEW_MEMBER", "New members have been added successfully to the database");
    ERRORLIST.put("MSG_SUCCESS_DELETE_MEMBER", "Selected members have been deleted successfully from the database");
    ERRORLIST.put("MSG_ERROR_SELECT_MEMBER", "You must select at least one member");
    ERRORLIST.put("MSG_ERROR_NO_MEMBER", "No members found");
    ERRORLIST.put("MSG_INFO_NO_MEMBER", "The Club has no member!");
    
    logger = Logger.getLogger(Controller.class.getName());
  }
  
  /**
   * Handles the HTTP <code>GET</code> method.
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    String url = request.getServletPath();
    
    switch(url){
      case "/":
        if(session.getAttribute("logged") != null){
          indexAction(request, response, session);
        }
        else{ 
          loginAction(request, response, session);
        }
        break;

      case "/add":
        if(session.getAttribute("logged") != null){
          addAction(request, response, session);
        }
        else{
          response.sendRedirect("/");
        }
        break;
      
      default:
        includeFileStream(response, url);
        break;
    }
  }

  /**
   * Handles the HTTP <code>POST</code> method.
   * @param request servlet request
   * @param response servlet response
   * @throws IOException if an I/O error occurs
   * @throws javax.servlet.ServletException
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    HttpSession session = request.getSession();
    String url = request.getServletPath();
    
    switch(url){
      case "/login":
        if(session.getAttribute("logged") == null){
          loginAction(request, response, session);
        }
        else{
          response.sendRedirect("/");
        }
        break;
        
      case "/see":
        if(session.getAttribute("logged") != null){
          seeAction(request, response, session);
        }
        else{
          response.sendRedirect("/");
        }
        break;
        
      case "/delete":
        if(session.getAttribute("logged") != null){
          deleteAction(request, response, session);
        }
        else{
          response.sendRedirect("/");
        }
        break;
      
      default:
        includeFileStream(response, url);
        break;
    }
  }
  
  /**
   * Index action
   * @param request servlet request
   * @param response servlet response
   * @param session servlet session
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  private void indexAction(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException{
    ResultSet data;
    List<BeanMember> memberList = new ArrayList<>();
    
    try{
      Connection db = DBConnection.getConnection(getServletContext());
      Statement statement = db.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      data = statement.executeQuery("SELECT * FROM members");

      while(data.next()){
        BeanMember member = new BeanMember();
        member.setId(data.getString("ID"));
        member.setName(data.getString("NAME"));
        member.setFirstName(data.getString("FIRSTNAME"));
        member.setEmail(data.getString("EMAIL"));
        member.setTelHome(data.getString("TELHOME"));
        member.setTelMob(data.getString("TELMOB"));
        member.setTelPro(data.getString("TELPRO"));
        member.setAddress(data.getString("ADRESS"));
        member.setPostalCode(data.getString("POSTALCODE"));
        memberList.add(member);
      }
      
      DBDisconnect.disconnect(db);
    }
    catch(SQLException e){
      logger.log(Level.SEVERE, e.getMessage());
      session.setAttribute("danger", ERRORLIST.get("MSG_ERROR_WRONG"));
    }
    
    if(memberList.isEmpty()){
      session.setAttribute("info", ERRORLIST.get("MSG_INFO_NO_MEMBER"));
    }
    
    request.setAttribute("memberList", memberList);
    request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);  
  }
  
  /**
   * Login action
   * @param request servlet request
   * @param response servlet response
   * @param session servlet session
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  private void loginAction(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException{
    if(request.getMethod().equals("POST")){      
      if(getServletContext().getInitParameter("username").equals(request.getParameter("username")) 
              && getServletContext().getInitParameter("password").equals(request.getParameter("password"))){
        session.setAttribute("logged", true);
        session.setAttribute("success", ERRORLIST.get("MSG_SUCCESS_LOGIN"));
        
        response.sendRedirect("/");
      }
      else{
        session.setAttribute("danger", ERRORLIST.get("MSG_ERROR_LOGIN_PWD"));
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
      }
    }
    else{
      request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }
  }
  
  /**
   * Add members action
   * @param request servlet request
   * @param response servlet response
   * @param session servlet session
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  private void addAction(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException{
    try{
      Connection db = DBConnection.getConnection(getServletContext());
      
      String query = "INSERT INTO MEMBERS(NAME, FIRSTNAME, TELHOME, TELMOB, TELPRO, ADRESS, POSTALCODE, CITY,EMAIL) VALUES"
            + "('Simpson','Homer','0123456789','0612345678','0698765432','2 avenue Duff','92700','Colombes','hsimpson@gmail.com'),"
            + "('Simpson','Bart','0145362787','0645362718','0611563477','10 rue des Rebelles','92270','Bois-colombes','bsimpson@gmail.com'),"
            + "('Lagaffe','Gaston','0187665987','0623334256','0654778654','65 rue de la Paresse','92700','Colombes','glagaffe@yahoo.fr'),"
            + "('Mfalda','Querida','0187611987','0783334256','0658878654','6 rue de Buenos Aires','75016','Paris','qmafalda@hotmail.ar'),"
            + "('Woodpecker','Woody','0187384987','0622494256','0674178654','5 bvd des Picoreurs','21000','Dijon','woody@mail.co.uk'),"
            + "('Brown','Charlie','0122456678','0699854673','0623445166','140 avenue Foche','90000','Nanterre','cbrown@live.com')";
      
      Statement statement = db.createStatement();
      statement.execute(query);

      DBDisconnect.disconnect(db);
      
      session.setAttribute("success", ERRORLIST.get("MSG_SUCCESS_NEW_MEMBERS"));
    }
    catch(SQLException e){
      logger.log(Level.SEVERE, e.getMessage());
      session.setAttribute("danger", ERRORLIST.get("MSG_ERROR_WRONG"));
    }
    finally{
      response.sendRedirect("/");
    }
  }
  
  /**
   * Delete members action
   * @param request servlet request
   * @param response servlet response
   * @param session servlet session
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  private void deleteAction(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException{
    try{
      Connection db = DBConnection.getConnection(getServletContext());
      
      String select[] = request.getParameterValues("member");

      if (select != null && select.length != 0) {
        for (String select1 : select) {
          PreparedStatement statement = db.prepareStatement("DELETE FROM members WHERE ID = ?");
          statement.setInt(1, Integer.parseInt(select1.trim()));
          statement.execute();
        }

        session.setAttribute("success", ERRORLIST.get("MSG_SUCCESS_DELETE_MEMBER"));
      }
      else{
        session.setAttribute("danger", ERRORLIST.get("MSG_ERROR_SELECT_MEMBER"));
      }

      DBDisconnect.disconnect(db);
    }
    catch(SQLException e){
      logger.log(Level.SEVERE, e.getMessage());
      session.setAttribute("danger", ERRORLIST.get("MSG_ERROR_WRONG"));
    }
    finally{
      response.sendRedirect("/");
    }
  }
  
  /**
   * See members action
   * @param request servlet request
   * @param response servlet response
   * @param session servlet session
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  private void seeAction(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException{
    try{
      Connection db = DBConnection.getConnection(getServletContext());
      
      String select[] = request.getParameterValues("member");
      StringBuilder inQuery = new StringBuilder();
      
      if(select != null && select.length > 0){
        for (int i = 0; i < select.length; i++) {
          if(i < select.length -1){
            inQuery.append(select[i]).append(", ");
          }
          else{
            inQuery.append(select[i]);
          }
        }
        
        Statement statement = db.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet data = statement.executeQuery("SELECT * FROM members WHERE ID IN (" + inQuery + ")");
        
        List<BeanMember> memberList = new ArrayList<>();
        
        while(data.next()){
          BeanMember member = new BeanMember();
          member.setName(data.getString("NAME"));
          member.setFirstName(data.getString("FIRSTNAME"));
          member.setTelHome(data.getString("TELHOME"));
          member.setTelMob(data.getString("TELMOB"));
          member.setTelPro(data.getString("TELPRO"));
          member.setAddress(data.getString("ADRESS"));
          member.setPostalCode(data.getString("POSTALCODE"));
          member.setEmail(data.getString("EMAIL"));
          member.setPostalCode(data.getString("POSTALCODE"));
          member.setCity(data.getString("CITY"));
          memberList.add(member);
        }
        
        if(memberList.size() > 0){
          request.setAttribute("memberList", memberList);
          request.getRequestDispatcher("/WEB-INF/views/see.jsp").forward(request, response);
        }
        else{
          session.setAttribute("danger", ERRORLIST.get("MSG_ERROR_NO_MEMBER"));
          response.sendRedirect("/");
        }
      }
      else{
        session.setAttribute("danger", ERRORLIST.get("MSG_ERROR_SELECT_MEMBER"));
        response.sendRedirect("/");
      }

      DBDisconnect.disconnect(db);
    }
    catch(SQLException e){
      logger.log(Level.SEVERE, e.getMessage());
      session.setAttribute("danger", ERRORLIST.get("MSG_ERROR_WRONG"));
      response.sendRedirect("/");
    }
  }

  /**
   * Returns a short description of the servlet.
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Front controller";
  }
  
  /**
   * Display file stream or return 404
   * @param response servlet response
   * @throws IOException if an I/O error occurs
   */
  private void includeFileStream(HttpServletResponse response, String url) throws IOException{
    try{
      ServletContext context = getServletContext();
      InputStream is = context.getResourceAsStream("/WEB-INF" + url);
      OutputStream output = response.getOutputStream();
      byte[] buffer = new byte[10240];

      for (int length = 0; (length = is.read(buffer)) > 0;) {
          output.write(buffer, 0, length);
      }
    }
    catch(IOException | NullPointerException e){
      logger.log(Level.SEVERE, e.getMessage());
      response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
  }
}
