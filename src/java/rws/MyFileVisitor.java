package rws;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;

class MyFileVisitor extends SimpleFileVisitor<Path> implements Serializable{
    //private Map<String, IDirectory.Type> map = Walker.getMap();
     private Map<String, IDirectory.Type> map;

    public MyFileVisitor(Map<String, IDirectory.Type> map) {
        this.map = map;
    }

    @Override
    public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs) throws IOException {
        IDirectory.Type type = null;
        if (!Files.isDirectory(filePath)) {
            type = IDirectory.Type.FILE;
        }else if(Files.isDirectory(filePath)){
            type = IDirectory.Type.DIRECTORY;
        }
        map.put(filePath.toString(), type);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path filePath, IOException exc) throws IOException {
                IDirectory.Type type = null;
        if (!Files.isDirectory(filePath)) {
            type = IDirectory.Type.FILE;
        }else if(Files.isDirectory(filePath)){
            type = IDirectory.Type.DIRECTORY;
        }
        map.put(filePath.toString(), type);
        //return FileVisitResult.CONTINUE;
        return super.postVisitDirectory(filePath, exc); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
//    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
//        return super.visitFileFailed(file, exc); //To change body of generated methods, choose Tools | Templates.
//    }

//    @Override
//    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
//        return super.preVisitDirectory(dir, attrs); //To change body of generated methods, choose Tools | Templates.
//    }
    
    
    
}

