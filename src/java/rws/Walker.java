package rws;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Walker implements Walkable{
    public static final String HOME_DIR = "D:/barracuda/";
    File sourceFolder = new File(DirResource.HOME_DIR);
    Map<String, IDirectory.Type> map = new LinkedHashMap<>();
    List<FileEntry> fileEntries = new ArrayList<>();

    public Walker() {

    }

    void setSourceFolder(String dirName) {
        sourceFolder = new File(dirName);
    }
    
    String getSourceFolder(){
        return sourceFolder.toString();
    }
    
    public void appendSourceFolder(String dirName){
        String source = sourceFolder.toString() + File.separator +  dirName;
        sourceFolder = new File(source);
        System.out.println("========class Walker appendSourceFolder() sourceFolder = " + sourceFolder.toString());
    }
    
    public Map<String, IDirectory.Type> walkDontRun() {
        try {
            System.out.println("========class Walker walkDontRun() sourceFolder = " + sourceFolder.toString());
            Files.walkFileTree(sourceFolder.toPath(), new MyFileVisitor(map, fileEntries));
        } catch (IOException ex) {
            System.out.println("class Walker void walkDontRun() IOException");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        //------------testimg
        //System.out.println(mapToString(map, sourceFolder.toString()));
        //-----------end of testing
        return map;
    }
    
    public List<FileEntry> walkDontRun2() {
        try {
            System.out.println("========class Walker walkDontRun2() sourceFolder = " + sourceFolder.toString());
            Files.walkFileTree(sourceFolder.toPath(), new MyFileVisitor(map, fileEntries));
        } catch (IOException ex) {
            System.out.println("class Walker void walkDontRun2() IOException");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return fileEntries;
    }
    
    public Map<String, IDirectory.Type> getMap() {
        return map;
    }
    
    public void setMap(Map<String, IDirectory.Type> map) {
        this.map = map;
    }

    public List<FileEntry> getFileEntries() {
        return fileEntries;
    }
    
    @Override
    public String toString(){
        return "Walker: " + sourceFolder.toString();
    }
    
    public String walkerToHtmlString(){
        StringBuilder sb = new StringBuilder();
        sb.append("<ul>");
        for(Map.Entry<String, IDirectory.Type> pair : map.entrySet()){
            sb.append("<li>");
            sb.append(pair.getKey() + " : " + pair.getValue());
            sb.append("</li>");
        }
        sb.append("</ul>");
        return sb.toString();
    }
    
    public String walkerToHtmlFileEntryString(){
        StringBuilder sb = new StringBuilder();
        //sb.append("<ul>");
        for(FileEntry fileEntry : fileEntries){
            //sb.append("<li>");
            sb.append(fileEntry.toString());
            sb.append("<br>");
            //sb.append("</li>");
        }
        //sb.append("</ul>");
        return sb.toString();
    }
    
    private String mapToString(Map<String, IDirectory.Type> map, String dirName){
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
    
}

