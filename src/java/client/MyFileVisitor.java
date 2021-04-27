package client;

import rws.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class MyFileVisitor extends SimpleFileVisitor<Path> implements Serializable{
     private Map<String, IDirectory.Type> map;
     private List<FileEntry> fileEntries;

    public MyFileVisitor(Map<String, IDirectory.Type> map) {
        this.map = map;
    }

    public MyFileVisitor(Map<String, IDirectory.Type> map, List<FileEntry> fileEntries) {
        this.map = map;
        this.fileEntries = fileEntries;
    }
    

    private String dashesMaker(Path filePath){

        int count = filePath.getNameCount();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < count; i++){
            sb.append("-----");
        }
        return sb.toString();
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

        fileEntries.add(createFileEntry(filePath));
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
        
        fileEntries.add(createFileEntry(filePath));
        
        return super.postVisitDirectory(filePath, exc); //To change body of generated methods, choose Tools | Templates.
    }
    
    private FileEntry createFileEntry(Path filePath){
        IDirectory.Type type = null;
        if (!Files.isDirectory(filePath)) {
            type = IDirectory.Type.FILE;
        }else if(Files.isDirectory(filePath)){
            type = IDirectory.Type.DIRECTORY;
        }
        FileEntry fileEntry = new FileEntry();
        fileEntry.setType(type);
        int count = filePath.getNameCount();
        fileEntry.setCount(count);
        Path shortFileName = filePath.getName(count-1);   //------------>тут может быть ошибка!
        fileEntry.setShortFileName(shortFileName);
        Path fullFileName = filePath.getFileName();
        fileEntry.setFullFileName(fullFileName);
        Path parentFolder = filePath.getParent();
        fileEntry.setParentFolder(parentFolder);
        List<Path> children = new ArrayList<>();
        File[] childrenFiles = filePath.toFile().listFiles();
        
        List<FileEntry> entryChildren = new ArrayList<>();

        if(childrenFiles != null){
            for(File f : childrenFiles){
                if(f!= null){
                    children.add(f.toPath());
                    entryChildren.add(createFileEntry(f.toPath()));
                }
            }
        }
        fileEntry.setChildren(children);
        fileEntry.setEntryChildren(entryChildren);
        return fileEntry;
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

