package m1.jee.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import m1.jee.model.EMF;
import m1.jee.model.Members;
import java.util.Collection;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

@WebServlet(name = "Controller", urlPatterns = {"/"})
public class Controller extends HttpServlet {  
  private static final Map<String, String> ERRORLIST;
  private static final Logger logger;
  private EntityManager em;
  
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
    
    try{
      if (em == null) {
        em = EMF.createEntityManager();
      }

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
    catch(IllegalStateException e){
      logger.log(Level.SEVERE, e.getMessage());
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
    
    try{
      if (em == null) {
        em = EMF.createEntityManager();
      }

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
    catch(IllegalStateException e){
      logger.log(Level.SEVERE, e.getMessage());
      response.sendError(HttpServletResponse.SC_NOT_FOUND);
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
    try{  
      Collection<Members> memberList = em.createNamedQuery("Members.findAll", Members.class).getResultList();
      request.setAttribute("memberList", memberList);
      request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
    }
    catch(PersistenceException e){
      logger.log(Level.SEVERE, e.getMessage());
    }
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
      createFiveMembers();
      session.setAttribute("success", ERRORLIST.get("MSG_SUCCESS_NEW_MEMBERS"));
      response.sendRedirect("/");
    }
    catch(PersistenceException e){
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
    String select[] = request.getParameterValues("member");

    if (select != null && select.length != 0) {
      for (String selected : select) {
        Members member = em.find(Members.class, Integer.parseInt(selected.trim()));

        if(member != null){
          em.getTransaction().begin();
          em.remove(member);
          em.getTransaction().commit();
        }
      }

      session.setAttribute("success", ERRORLIST.get("MSG_SUCCESS_DELETE_MEMBER"));
    }
    else{
      session.setAttribute("danger", ERRORLIST.get("MSG_ERROR_SELECT_MEMBER"));
    }
    
    response.sendRedirect("/");
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
      String select[] = request.getParameterValues("member");
      String inQuery = "";  
      
      if(select != null && select.length > 0){
        TypedQuery<Members> query = em.createNamedQuery("Members.findInList", Members.class).setParameter("list", Arrays.asList(select));
        Collection<Members> memberList = query.getResultList();
        
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
    }
    catch(PersistenceException e){
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
      response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
  }
  
  /**
   * Add five new users
   */
  private void createFiveMembers(){
    em.getTransaction().begin();
    createMember("Simpson","Homer","0123456789","0612345678","0698765432","2 avenue Duff","92700","Colombes","hsimpson@gmail.com");
    createMember("Simpson","Bart","0145362787","0645362718","0611563477","10 rue des Rebelles","92270","Bois-colombes","bsimpson@gmail.com");
    createMember("Lagaffe","Gaston","0187665987","0623334256","0654778654","65 rue de la Paresse","92700","Colombes","glagaffe@yahoo.fr");
    createMember("Mafalda","Querida","0187611987","0783334256","0658878654","6 rue de Buenos Aires","75016","Paris","qmafalda@hotmail.ar");
    createMember("Woodpecker","Woody","0187384987","0622494256","0674178654","5 bvd des Picoreurs","21000","Dijon","woody@mail.co.uk");
    createMember("Brown","Charlie","0122456678","0699854673","0623445166","140 avenue Foche","90000","Nanterre","cbrown@live.com");
    em.getTransaction().commit();
  }
  
  /**
   * Create a new member
   * @param  name       String
   * @param  firstName  String
   * @param  telHome    String
   * @param  telMob     String
   * @param  telPro     String
   * @param  address    String
   * @param  postalCode String
   * @param  city       String
   * @param  email      String
   * @return            Members
   */
  private Members createMember(String name, String firstName, String telHome, String telMob, String telPro, String address, String postalCode, String city, String email){
    Members member = new Members(name, firstName, telHome, telMob, telPro, address, postalCode, city, email);
    em.persist(member);
    return member;
  }
}
