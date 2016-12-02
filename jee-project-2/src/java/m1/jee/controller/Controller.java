package m1.jee.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Controller", urlPatterns = {"/"})
public class Controller extends HttpServlet {

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
    
    System.out.println(url);
    
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
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
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
    
    System.out.println(url);
    
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
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
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
    
  }

  /**
   * Returns a short description of the servlet.
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Short description";
  }
}
