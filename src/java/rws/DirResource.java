package rws;

import java.io.File;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    
    //public static final String HOME_DIR = "D:/barracuda/";
    public static final String HOME_DIR = rws.Walker.HOME_DIR;
    
    public DirResource() {
        
    }
    
    @GET
    @Produces(MediaType.APPLICATION_XML)        //-------------------------------------------->Добавить везде!!!
    public Walkable getXml() {
        Walkable walker = new Walker();
        return walker;
    }
    
    @Override
    @GET
    @Path("findall/{directory}")
    //@Produces(MediaType.TEXT_HTML)
    @Produces("text/html")
    public String findAll(@PathParam("directory")String dirName) {
        System.out.println("вызван class DirResource findAll(). dirName = " + dirName);
        return getFolderContentDeep(dirName);
        //return getFileEntriesContentDeep(dirName);
    }
    
    @Override
    @GET
    @Path("find/{directory}") 
    @Produces(MediaType.APPLICATION_XML)
    public Walker findFile(@PathParam("directory") String dirName, @QueryParam("file")String fileName) {
        System.out.println(" вызван class DirResource findFile(). dirName = " + dirName + " file = " + fileName);
        return findFileByFragment(dirName, fileName, null);
    }
    
    @Override
    @GET
    @Path("findRegexp/{directory}") 
    @Produces(MediaType.APPLICATION_XML)
    public Walker findFileRegexp(@PathParam("directory") String dirName, @QueryParam("file")String fileName, @QueryParam("regexp")String regexp) {
        System.out.println(" вызван class DirResource findFile(). dirName = " + dirName + " file = " + fileName);
        return findFileByFragment(dirName, fileName, regexp);
    }

    @Override
    @GET
    @Path("{directory}") 
    @Produces("text/html")
    public String getContent(@PathParam("directory") String dirName) {
        System.out.println("вызван class DirResource getContent(). dirName = " + dirName);
        return getFolderContent(dirName);
    }
    
    @Override
    @GET
    @Path("findMap/{directory}")
    @Produces(MediaType.APPLICATION_XML)
    public Walker getMappedContent(@PathParam("directory")String dirName) {
        System.out.println("---=== class DirResource, getMappedContent(), dirName = " + dirName);
        return getMappedFolderContent(dirName);
    }
    
    
    private Walker getMappedFolderContent(String dirName) {
        Walker walker = new Walker();

        if("?default?".equals(dirName)){
            dirName = HOME_DIR;
        }else if(dirName != null && (dirName.startsWith("C:") || dirName.startsWith("D:") || dirName.startsWith("E:") || dirName.startsWith("F:") || dirName.startsWith("c:") || dirName.startsWith("d:") || dirName.startsWith("e:") || dirName.startsWith("f:"))){
           walker.setSourceFolder(dirName);
        }else{
            walker.appendSourceFolder(dirName);
        }

        Map<String, IDirectory.Type> map = walker.walkDontRun();
        walker.setMap(map);
        System.out.println("---=== class DirResource, getMappedFolderContent(), dirName = " + dirName);
        return walker;
    }
    
    private String getFolderContentDeep(String dirName){
        Walker walker = new Walker();

        if("?default?".equals(dirName)){
            dirName = HOME_DIR;
        }else if(dirName != null && (dirName.startsWith("C:") || dirName.startsWith("D:") || dirName.startsWith("E:") || dirName.startsWith("F:") || dirName.startsWith("c:") || dirName.startsWith("d:") || dirName.startsWith("e:") || dirName.startsWith("f:"))){
           walker.setSourceFolder(dirName);
        }else{
            walker.appendSourceFolder(dirName);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<h3>Directory \"" + walker.getSourceFolder() + "\" contains: </h3><ul>");

        Map<String, IDirectory.Type> map2 = walker.walkDontRun();
        if(map2.isEmpty()){
            return "<p1>No content found for resource: " + dirName + "</p1><br><p1>Probably \"" + dirName + "\" doesn't exist.</p1>";
        }
        
        
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
    
    
    
    private String getFileEntriesContentDeep(String dirName){
        Walker walker = new Walker();
        
        if("?default?".equals(dirName)){
            dirName = HOME_DIR;
        }else if(dirName != null && (dirName.startsWith("C:") || dirName.startsWith("D:") || dirName.startsWith("E:") || dirName.startsWith("F:") || dirName.startsWith("c:") || dirName.startsWith("d:") || dirName.startsWith("e:") || dirName.startsWith("f:"))){
           walker.setSourceFolder(dirName);
        }else{
            walker.appendSourceFolder(dirName);
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("<p1>getFileEntriesContentDeep()</p1>");
        
        List<FileEntry> list = walker.walkDontRun2();
        Collections.sort(list);
        
        sb.append(walker.walkerToHtmlFileEntryString());
        return sb.toString();
    }

    
    
    private String getFolderContent(String dirName) {

        StringBuilder sb = new StringBuilder();
        //File folder = new File("HOME_DIR" + File.separator + dirName);
        File folder = new File(HOME_DIR + dirName + "\\");
        System.out.println("=== folder = " + folder.toString());
        
        if("?default?".equals(dirName)){
            folder = new File(HOME_DIR);
            System.out.println("==== class DirResource: method getFolderContent()\"default\".equals(dirName)");
        }
        
        if(dirName != null && (dirName.startsWith("C:") || dirName.startsWith("D:") || dirName.startsWith("E:") || dirName.startsWith("F:"))){
            folder = new File(dirName);
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
            
            sb.append("<h3>Directory \"" + folder + "\" contains: </h3><ul>");
            for(File f : files){
                sb.append("<li>" + f.getName() + " - " + (f.isDirectory() ? "DIRECTORY" : "FILE"));
            }
            sb.append("</ul>");
        }
        return sb.toString();
    }
    
    private Walker findFileByFragment(String dirName, String fragment, String regexp) {
        Walker walker = new Walker();

        if("?default?".equals(dirName)){
            dirName = HOME_DIR;
        }else if(dirName != null && (dirName.startsWith("C:") || dirName.startsWith("D:") || dirName.startsWith("E:") || dirName.startsWith("F:") || dirName.startsWith("c:") || dirName.startsWith("d:") || dirName.startsWith("e:") || dirName.startsWith("f:"))){
           walker.setSourceFolder(dirName);
        }else{
            walker.appendSourceFolder(dirName);
        }

        Map<String, IDirectory.Type> map = walker.walkDontRun();
        Map<String, IDirectory.Type> mapFind = new LinkedHashMap<>();
        
        for(Map.Entry<String, IDirectory.Type> pair : map.entrySet()){
            String s = pair.getKey();
            IDirectory.Type type = pair.getValue();
            
            if(s != null && regexp == null){
                if(s.contains(fragment)){
                    mapFind.put(s, type);
                }
            }else if(s != null){
                Pattern p = Pattern.compile(fragment);
                Matcher m = p.matcher(s);
                if(m.matches()){
                    mapFind.put(s, type);
                }
            }
        }
        
        walker.setMap(mapFind);
        System.out.println("---=== class DirResource, findFileByFragment(), dirName = " + dirName + ", fragment = " + fragment);
        return walker;    
    }

//    private String findFileByFragment(String dirName, String fragment) {
//        File folder = new File(HOME_DIR + File.separator + dirName);
//
//        if("?default?".equals(dirName)){
//            folder = new File(HOME_DIR);
//        }
//        StringBuilder sb = new StringBuilder();
//        
//        File[] files = folder.listFiles(new FileSelector(fragment));
//        
//        if(files == null){
//            return "<h2>Folder " + dirName + " does not exist</h2>";
//        }
//        if(files.length == 0){
//            return "<h2>Nothing corresponding your fragment " + dirName + "</h2>";
//        }
//        
//        sb.append("<h3>Directory \"" + folder + "\" contains: </h3><ul>");
//        for(File f : files){
//            sb.append("<li>" + f.getName() + " - " + (f.isDirectory() ? "DIRECTORY" : "FILE"));
//        }
//        sb.append("</ul>");
//               
//        return sb.toString();     
//    }
    
    @GET
    @Path("hello") //сами дописали аннотацию
    @Produces("text/plain")//сами дописали аннотацию. И ПасПарам тоже
    public String hello() {
        return "date: " + (new Date()).toString();
    }


    /**
     * Retrieves representation of an instance of rws.DirResource
     * @return an instance of java.lang.String
     */
//    @GET
//    @Produces(MediaType.APPLICATION_XML)
//    public ReturnData getXml() {
//        return new ReturnData();
//    }
//    /**
//     * PUT method for updating or creating an instance of DirResource
//     * @param content representation for the resource
//     */
//    @PUT
//    @Consumes(MediaType.APPLICATION_XML)
//    public void putXml(String content) {
//    }
}
