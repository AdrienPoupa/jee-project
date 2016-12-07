package m1.jee.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
  
  static {
    ERRORLIST = new HashMap<>();
    ERRORLIST.put("MSG_ERROR_LOGIN_PWD", "Login failed! Please check your login and/or password and try again");
    ERRORLIST.put("MSG_ERROR_LOGIN", "Login failed! Please try later");
    ERRORLIST.put("MSG_SUCCESS_LOGIN", "You have been successfully logged in!");
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
        if(session.getAttribute("logged") == null){
          seeAction(request, response, session);
        }
        else{
          response.sendRedirect("/");
        }
        break;
        
      case "/delete":
        if(session.getAttribute("logged") == null){
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
    Connection db = DBConnection.getConnection(getServletContext());
    List<BeanMember> memberList = new ArrayList<>();
    
    try{
      
    }
    catch(Exception e){
      
    }
    
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
    request.getRequestDispatcher("/WEB-INF/views/add.jsp").forward(request, response);
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
    request.getRequestDispatcher("/WEB-INF/views/delete.jsp").forward(request, response);
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
    request.getRequestDispatcher("/WEB-INF/views/see.jsp").forward(request, response);
  }

  /**
   * Returns a short description of the servlet.
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Short description";
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
      response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
  }
}
