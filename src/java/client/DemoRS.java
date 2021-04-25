package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import rws.IDirectory;
import rws.MyClass;
import rws.ReturnData;
import rws.Walker;

@WebServlet(name = "DemoRS", urlPatterns = {"/DemoRS"})
public class DemoRS extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        Client client = ClientBuilder.newClient();
        
        String button = request.getParameter("showDir");
        String directory = request.getParameter("directory");
        String deep = request.getParameter("deep");
        String result = null;
        String fragment = null;
        String regexp = null;   
        
        if(directory.trim().isEmpty()){
            directory = "default";
        }

        if(button != null && deep == null){
            result = client.target("http://localhost:8080/j210lab03RS/webresources/dir/")
                    .path("{directory}")
                    .resolveTemplate("directory", directory)
                    .request()
                    .get(String.class);
        }
        else if(button != null && deep != null){
            result = client.target("http://localhost:8080/j210lab03RS/webresources/dir/findall/")
                    .path("{directory}")
                    .resolveTemplate("directory", directory)
                    .request()
                    .get(String.class);
            System.out.println("!!!result!!!" + result);
        }
        else{
            fragment = request.getParameter("fragment");
            regexp = request.getParameter("regexp");
            System.out.println("Найти файл = по фрагменту: " + fragment + " в каталоге:" + directory);
            result = client.target("http://localhost:8080/j210lab03RS/webresources/dir/find/")
                    .path("{directory}")
                    .resolveTemplate("directory", directory)
                    .queryParam("file", fragment)     ///---------------добавлять сюда параметры по цепочке
                    .request()
                    .get(String.class);            
        }
        
        //теперь найдем объект нашего класса RetrunData
//        ReturnData dr = client.target("http://localhost:8080/j210lab03RS/webresources/dir/")
//                              .request()
//                              .get(ReturnData.class);
        
//        Walker walker = client.target("http://localhost:8080/j210lab03RS/webresources/dir/")
//                                .request()
//                                .get(Walker.class);
        
        String myDate = client.target("http://localhost:8080/j210lab03RS/webresources/dir/hello/")
                                .request()
                                .get(String.class);

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DemoRS</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DemoRS</h1>");
            out.println("<h2>Call fragment = " + fragment + ", flag = " + regexp + "</h2>");
            out.println("<h2>Call result = " + result + "</h2>");
            //out.println("<h3>ReturnData object check = " + dr.toString() + "</h3>");
            //out.println("<h3>Walker object check = " + walker.walkerToHtmlString() + "</h3>");
            out.println("<h3>Date check = " + myDate + "</h3>");
            out.println("</body>");
            out.println("</html>");
        }
    }


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
