/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rws;

import java.util.Map;

/**
 *
 * @author ENVY
 */
public interface Walkable {
    Map<String, IDirectory.Type> walkDontRun();
    
    Map<String, IDirectory.Type> getMap();
    
    String walkerToHtmlString();
    
    
    
}
