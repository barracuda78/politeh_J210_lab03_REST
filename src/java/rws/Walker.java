package rws;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.TreeMap;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Walker implements Walkable{
    File sourceFolder = new File(DirResource.HOME_DIR);
    private  Map<String, IDirectory.Type> map = new TreeMap<>();

    public Walker() {
         //map = this.walkDontRun();
    }
    
    void setSourceFolder(String dirName) {
        sourceFolder = new File(dirName);
    }
    
    public void appendSourceFolder(String dirName){
        String source = sourceFolder.toString() + File.separator +  dirName;
        sourceFolder = new File(source);
        System.out.println("========class Walker appendSourceFolder() sourceFolder = " + sourceFolder.toString());
    }
    
    public Map<String, IDirectory.Type> walkDontRun() {
        try {
            System.out.println("========class Walker walkDontRun() sourceFolder = " + sourceFolder.toString());
            Files.walkFileTree(sourceFolder.toPath(), new MyFileVisitor(map));
        } catch (IOException ex) {
            System.out.println("class Walker void walkDontRun() IOException");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return map;
    }
    
    public Map<String, IDirectory.Type> getMap() {
        return map;
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
    
    //testing-------------------------------------
        //метод из DirResourse:
//        public static String findAll(String dirName) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("<p1>ВОТ ОНО Ж<ul>\n");
//        Walker walker = new Walker();
//        Map<String, IDirectory.Type> map2 = walker.walkDontRun();
//        for(Map.Entry<String, IDirectory.Type> pair : map2.entrySet())  {
//            sb.append("<li>\n");
//            sb.append(pair.getKey());
//            sb.append(" : ");
//            sb.append(pair.getValue());
//            sb.append("</li>");
//        }
//        sb.append("</ul></p1>");
//        return sb.toString();
//    }
//    
//    public static void main(String[] args) {
//        String HOME_DIR = "D:\\barracuda\\";
//        String s = findAll(HOME_DIR);
//        System.out.println("ВЫВОД ТЕСТА: " + s);
//    }
    
    //end of testing ==============================


}

