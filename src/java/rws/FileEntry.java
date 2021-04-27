package rws;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class FileEntry implements Comparable<FileEntry>{
        IDirectory.Type type;
        int count;
        Path shortFileName;
        Path fullFileName;
        Path parentFolder;
        List<Path> children = new ArrayList<>();
        List<FileEntry> entryChildren = new ArrayList<>();
        
    public FileEntry(){
            
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < count; i++){
            sb.append("-----");
        }
        String dashes = sb.toString();
        String typ = type == IDirectory.Type.FILE ? "FIL" : "DIR";
        
        String result = "<p>";
        
        result = typ + dashes + this.shortFileName;
        if(this.entryChildren != null){
            for(FileEntry fileEntry : this.entryChildren){
                result = result + "<p>" + fileEntry.toString() + "</p>";
            }
        }
        return result + "</p>";
    }
    
    //old toString()
//    @Override
//    public String toString(){
//        StringBuilder sb = new StringBuilder();
//        for(int i = 0; i < count; i++){
//            sb.append("-----");
//        }
//        String dashes = sb.toString();
//        String typ = type == IDirectory.Type.FILE ? "FIL" : "DIR";
//        
//        String result = "";
//        
//        if((this.entryChildren != null && this.entryChildren.size() > 0) || this.type == IDirectory.Type.DIRECTORY){
//            
//            StringBuilder stringChildren = new StringBuilder();
//            for(FileEntry fileEntry : this.entryChildren){
//                stringChildren.append("<p>");
//                stringChildren.append(fileEntry.toString());
//                stringChildren.append("</p>");
//            }
//            
//            result = "<p>FileEntry = " + typ + dashes + shortFileName.toString() + "</p><p>" + stringChildren.toString() + "</p>";
//        }
//        
//        
//        return result;
//    }

    public IDirectory.Type getType() {
        return type;
    }

    public void setType(IDirectory.Type type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Path getShortFileName() {
        return shortFileName;
    }

    public void setShortFileName(Path shortFileName) {
        this.shortFileName = shortFileName;
    }

    public Path getFullFileName() {
        return fullFileName;
    }

    public void setFullFileName(Path fullFileName) {
        this.fullFileName = fullFileName;
    }

    public Path getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(Path parentFolder) {
        this.parentFolder = parentFolder;
    }

    public List<Path> getChildren() {
        return children;
    }

    public void setChildren(List<Path> children) {
        this.children = children;
    }
    
    public List<FileEntry> getEntryChildren() {
        return entryChildren;
    }

    public void setEntryChildren(List<FileEntry> entryChildren) {
        this.entryChildren = entryChildren;
    }

    @Override
    public int compareTo(FileEntry f) {
        if (parentFolder.compareTo(f.parentFolder) > 0) {
            return 1;
        } else if (parentFolder.compareTo(f.parentFolder) < 0) {
            return -1;
        } else {
            if (count > f.count) {
                return 1;
            } else if (count < f.count) {
                return -1;
            } else {
                return fullFileName.compareTo(f.fullFileName);
            }
        }
    }    
}
