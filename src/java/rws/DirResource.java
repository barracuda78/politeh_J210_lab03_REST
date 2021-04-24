/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rws;

import java.io.File;
import java.util.Date;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author ENVY
 */
@Path("dir")
public class DirResource implements IDirectory {

    @Context
    private UriInfo context;
    
    public static final String HOME_DIR = "C:\\home\\barracuda\\testdir";
    
    public DirResource() {
    }

    @Override
    @GET
    @Path("{directory}") //сами дописали аннотацию
    @Produces("text/html")//сами дописали аннотацию. И ПасПарам тоже
    public String getContent(@PathParam("directory") String dirName) {
        System.out.println(" вызван class DirResource getContent() dirName = " + dirName);
        return getFolderContent(dirName);
    }

    @Override
    @GET
    @Path("find/{directory}") //сами дописали аннотацию
    @Produces("text/html")//сами дописали аннотацию. И ПасПарам тоже
    public String findFile(@PathParam("directory") String dirName, @QueryParam("file")String fileName) {
        System.out.println(" вызван class DirResource findFile() dirName = " + dirName + " file = " + fileName);
        return findFileByFragment(dirName, fileName);
    }
    

    @GET
    @Path("hello") //сами дописали аннотацию
    @Produces("text/plain")//сами дописали аннотацию. И ПасПарам тоже
    public String hello() {
        return "date: " + (new Date()).toString();
    }

    private String getFolderContent(String dirName) {
        StringBuilder sb = new StringBuilder();
        File folder = new File("HOME_DIR" + File.separator + dirName);

        if("?default?".equals(dirName)){
            folder = new File(HOME_DIR);
        }
        
        
        if(!folder.exists()){
            sb.append("Folder " + dirName + " does not exist");
        }else{
            File[] files = folder.listFiles();
            if(files == null){
                return "<h2>Folder " + dirName + " does not exist</h2>";
            } 
            
            if(files.length == 0){
                return "<h2>Folder " + dirName + " is empty</h2>";
            }
            
            sb.append("<h2>Directory " + dirName + " contains : </h2><ol><big>");
            for(File f : files){
                sb.append("<li>" + f.getName() + " - " + (f.isDirectory() ? "folder" : "file"));
            }
            sb.append("</big></ol>");
        }
        return sb.toString();
    }

    private String findFileByFragment(String dirName, String fragment) {
        File folder = new File("HOME_DIR" + File.separator + dirName);

        if("?default?".equals(dirName)){
            folder = new File(HOME_DIR);
        }
        StringBuilder sb = new StringBuilder();
//        File folder = null;
//        if(dirName.equals("?default?")){
//            folder = new File("HOME_DIR");
//        }else{
//            folder = new File(HOME_DIR + File.separator + dirName);
//        }
        
        File[] files = folder.listFiles(new FileSelector(fragment));
        
        if(files == null){
            return "Folder " + dirName + " does not exist";
        }
        if(files.length == 0){
            return "<h2>Nothing corresponding your fragment " + dirName + "</h2>";
        }
        
            sb.append("<h2>In directory " + dirName + " we found : </h2><ol><big>");
            for(File f : files){
                sb.append("<li>" + f.getName() + " - " + (f.isDirectory() ? "folder" : "file"));
            }
            sb.append("</big></ol>");
               
        return sb.toString();     
    }

    /**
     * Retrieves representation of an instance of rws.DirResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)        //-------------------------------------------->Добавить везде!!!
    public ReturnData getXml() {
        return new ReturnData();
    }
//
//    /**
//     * PUT method for updating or creating an instance of DirResource
//     * @param content representation for the resource
//     */
//    @PUT
//    @Consumes(MediaType.APPLICATION_XML)
//    public void putXml(String content) {
//    }
    
    
}
