package rws;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

//это просто класс. Сами прописали аннотацию.

@XmlRootElement
public class ReturnData  { //implements Serializable
    private String name;
    private String family;
    private int age;

    public ReturnData() {
        name = "Priya";
        family = "Shanti";
        age = 39;
    }

    @Override
    public String toString() {
        return "ReturnData{" + "name=" + name + ", family=" + family + ", age=" + age + '}';
    }


    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
    
}
