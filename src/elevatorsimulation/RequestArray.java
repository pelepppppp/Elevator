/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elevatorsimulation;

import java.util.ArrayList;

/**
 *
 * @author 2ndyrGroupA
 */
public class RequestArray implements Runnable{
    private final ArrayList <String> array; 
   

    public RequestArray(int size) {
        this.array = new ArrayList();
    }
    
    public void addMessage(String mssg) {
        System.out.println(mssg);
        array.add(mssg);
        
        System.out.println("requests array: "+ array);
        
    }

    @Override
    public void run() {
//        array.add(null);
    }
    
    
}
