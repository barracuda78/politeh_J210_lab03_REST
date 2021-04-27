package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
    
    private String toValidPath(String s){
        return s.replaceAll("\\\\", "/");
    }
    
    private String toRepresentativePath(String s){
        return s.replaceAll("/", "\\\\" );
    }
    
    private String mapToString(Map<String, IDirectory.Type> map, String dirName){
        if("?default?".equals(dirName)){
            dirName = this.toRepresentativePath(client.Walker.HOME_DIR);
        }
        if(dirName != null && dirName.endsWith("\\")){
            dirName = this.toRepresentativePath(dirName.substring(0, dirName.length()-1));
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<h3>Directory \"" + dirName + "\" contains: </h3><ul>");

        if(map == null || map.isEmpty()){
            return "<p1>No content found for resource: " + dirName + "</p1><br><p1>Probably \"" + dirName + "\" doesn't exist.</p1>";
        }
        
        for(Map.Entry<String, IDirectory.Type> pair : map.entrySet())  {
            sb.append("<li>");
            sb.append(pair.getKey());
            sb.append(" : ");
            sb.append(pair.getValue());
            sb.append("</li>");
        }
        sb.append("</ul></p1>");
        return sb.toString();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        Client client = ClientBuilder.newClient();
        
        String buttonShowDir = request.getParameter("showDir");
        System.out.println("---=== class DemoRS, buttonShowDir = " + buttonShowDir);
        String directory = request.getParameter("directory");
        directory = this.toValidPath(directory);
        String deep = request.getParameter("deep");
        System.out.println("---=== class DemoRS, deep = " + deep);
        
        String buttonFindFile = request.getParameter("findFile");
        String fragment = request.getParameter("fragment");
        String regexp = request.getParameter("regexp");
        
        String result = null;
        //LinkedHashMap<String, client.IDirectory.Type> map = null;
        Map map = null;
        client.Walker walker = null;           //-----------------------------------> потом оттестировать на client.Walker 
        
        if(directory.trim().isEmpty()){
            directory = "?default?";
        }

        if(buttonShowDir != null && "Show folder".equals(buttonShowDir) && deep == null){
            System.out.println("---=== class DemoRS, button != null && deep == null, directory = " + directory);
            result = client.target("http://localhost:8080/j210lab03RS/webresources/dir/")
                    .path("{directory}")
                    .resolveTemplate("directory", directory)
                    .request()
                    .get(String.class);
        }else if(buttonShowDir != null && "Show folder".equals(buttonShowDir) && deep != null){
            System.out.println("---=== class DemoRS, button != null && deep != null, directory = " + directory);
            walker = client.target("http://localhost:8080/j210lab03RS/webresources/dir/findMap")
                    .path("{directory}")
                    .resolveTemplate("directory", directory)
                    .request()
                    .get(client.Walker.class);
        }
//Это для получения String из map:       
//        else if(button != null && deep != null){
//            result = client.target("http://localhost:8080/j210lab03RS/webresources/dir/findall/")
//                    .path("{directory}")
//                    .resolveTemplate("directory", directory)
//                    .request()
//                    .get(String.class);
//            System.out.println("!!!result!!!" + result);
//        }
        else if(buttonFindFile != null && regexp == null){
            System.out.println("Найти файл = по фрагменту: " + fragment + " в каталоге:" + directory);
            walker = client.target("http://localhost:8080/j210lab03RS/webresources/dir/find/")
                    .path("{directory}")
                    .resolveTemplate("directory", directory)
                    .queryParam("file", fragment)     
                    .request()
                    .get(client.Walker.class);            
        }else if(buttonFindFile != null && regexp != null){
            System.out.println("Найти файл = по фрагменту: " + fragment + " в каталоге:" + directory + ", regexp = " + regexp);
            walker = client.target("http://localhost:8080/j210lab03RS/webresources/dir/findRegexp/")
                    .path("{directory}")
                    .resolveTemplate("directory", directory)
                    .queryParam("file", fragment)
                    .queryParam("regexp", regexp)       //---------------добавлять сюда параметры по цепочке
                    .request()
                    .get(client.Walker.class);               
        }else{
            result = "Странная штука...";
        }
        
        String myDate = client.target("http://localhost:8080/j210lab03RS/webresources/dir/hello/")
                                .request()
                                .get(String.class);
        if(walker != null){
            map = walker.getMap();
            System.out.println("---===class DemoRS, walker != null");
            if(map!= null){
                if(map.isEmpty()){
                    System.out.println("---===class DemoRS, map.isEmpty()");
                }
                result = mapToString(map, directory);
                System.out.println("---===class DemoRS, map!= null");
            }else{
                System.out.println("---===class DemoRS, map== null");
            }
        }
        else{
            System.out.println("---===class DemoRS, walker == null");
        }
        
        if(deep != null){
            request.setAttribute("lookDeep", "checked");
        }
        request.setAttribute("result", result);
        request.getRequestDispatcher("index.jsp").forward(request, response);
        
        //теперь найдем объект нашего класса RetrunData
//        ReturnData dr = client.target("http://localhost:8080/j210lab03RS/webresources/dir/")
//                              .request()
//                              .get(ReturnData.class);
        
//        Walker walker = client.target("http://localhost:8080/j210lab03RS/webresources/dir/")
//                                .request()
//                                .get(Walker.class);


//        try (PrintWriter out = response.getWriter()) {
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/style01.css\"/>");
//            out.println("<title>Servlet DemoRS</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet DemoRS</h1>");
//            out.println("<h2>Call fragment = " + fragment + ", flag = " + regexp + "</h2>");
//            out.println("<h2>Call result = " + result + "</h2>");
//            //out.println("<h3>ReturnData object check = " + dr.toString() + "</h3>");
//            //out.println("<h3>Walker object check = " + walker.walkerToHtmlString() + "</h3>");
//            out.println("<h3>Date check = " + myDate + "</h3>");
//            out.println("</body>");
//            out.println("</html>");
//        }
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
