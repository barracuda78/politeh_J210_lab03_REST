/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rws;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ENVY
 */
@XmlRootElement
public class MyClass {
    private static String name = "barracuda";


    public MyClass() {
    }
    
    
    
    
    @Override
    public String toString(){
        return "MyClass: " + name;
    }
}
