
package rws;

import java.io.File;
import java.io.FilenameFilter;


public class FileSelector implements FilenameFilter{

    private String fragment;
    private boolean regexp;

    public FileSelector(String fragment) {
        this.fragment = fragment;
        regexp = false;
    }

    public FileSelector(String fragment, boolean regexp) {
        this.fragment = fragment;
        this.regexp = regexp;
    }
    
    
    
    @Override
    public boolean accept(File dir, String fileName) { //fileName - это имя очередного йфайла.
        if(regexp){
            return fileName.matches(fragment);
        }else{
            return fileName.contains(fragment);
        }
        
    }
    
}
