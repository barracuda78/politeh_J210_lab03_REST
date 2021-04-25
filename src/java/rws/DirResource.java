package rws;

import java.io.File;
import java.io.StringWriter;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
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
    
    public static final String HOME_DIR = "D:/barracuda/";
    
    public DirResource() {
        
    }
    
    @Override
    @GET
    @Path("findall/{directory}")
    //@Produces(MediaType.TEXT_HTML)
    @Produces("text/html")
    public String findAll(@PathParam("directory")String dirName) {
        System.out.println("вызван class DirResource findAll(). dirName = " + dirName);
        return getFolderContentDeep(dirName);
    }
    
    @Override
    @GET
    @Path("find/{directory}") //сами дописали аннотацию
    @Produces("text/html")//сами дописали аннотацию. И ПасПарам тоже
    public String findFile(@PathParam("directory") String dirName, @QueryParam("file")String fileName) {
        System.out.println(" вызван class DirResource findFile(). dirName = " + dirName + " file = " + fileName);
        return findFileByFragment(dirName, fileName);
    }

    @Override
    @GET
    @Path("{directory}") //сами дописали аннотацию
    @Produces("text/html")//сами дописали аннотацию. И ПасПарам тоже
    public String getContent(@PathParam("directory") String dirName) {
        System.out.println("вызван class DirResource getContent(). dirName = " + dirName);
        return getFolderContent(dirName);
    }


    
    @GET
    @Path("hello") //сами дописали аннотацию
    @Produces("text/plain")//сами дописали аннотацию. И ПасПарам тоже
    public String hello() {
        return "date: " + (new Date()).toString();
    }
    
    private String getFolderContentDeep(String dirName){
        Walker walker = new Walker();
        
        if("default".equals(dirName)){
            dirName = HOME_DIR;
        }else if(dirName != null && (dirName.startsWith("C:") || dirName.startsWith("D:") || dirName.startsWith("E:") || dirName.startsWith("F:"))){
           walker.setSourceFolder(dirName);
        }else{
            walker.appendSourceFolder(dirName);
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("<p1>getFolderContentDeep() : <ul>");

        Map<String, IDirectory.Type> map2 = walker.walkDontRun();
        
        for(Map.Entry<String, IDirectory.Type> pair : map2.entrySet())  {
            sb.append("<li>");
            sb.append(pair.getKey());
            sb.append(" : ");
            sb.append(pair.getValue());
            sb.append("</li>");
        }
        sb.append("</ul></p1>");
        return sb.toString();
    }

    private String getFolderContent(String dirName) {
        StringBuilder sb = new StringBuilder();
        //File folder = new File("HOME_DIR" + File.separator + dirName);
        File folder = new File(HOME_DIR + dirName + "\\");
        System.out.println("=== folder = " + folder.toString());
        
        if("default".equals(dirName)){
            folder = new File(HOME_DIR);
            System.out.println("==== class DirResource: method getFolderContent()\"default\".equals(dirName)");
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
        File folder = new File(HOME_DIR + File.separator + dirName);

        if("default".equals(dirName)){
            folder = new File(HOME_DIR);
        }
        StringBuilder sb = new StringBuilder();
        
        File[] files = folder.listFiles(new FileSelector(fragment));
        
        if(files == null){
            return "<h2>Folder " + dirName + " does not exist</h2>";
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
//    @GET
//    @Produces(MediaType.APPLICATION_XML)        //-------------------------------------------->Добавить везде!!!
//    public ReturnData getXml() {
//        return new ReturnData();
//    }
    
    @GET
    @Produces(MediaType.APPLICATION_XML)        //-------------------------------------------->Добавить везде!!!
    public Walkable getXml() {
        Walkable walker = new Walker();
        return walker;
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
