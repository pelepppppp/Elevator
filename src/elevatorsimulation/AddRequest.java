/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elevatorsimulation;

/**
 *
 * @author 2ndyrGroupA
 */
public class AddRequest implements Runnable {
    
     private final RequestArray sharedArray;

    public AddRequest(RequestArray sharedArray) {
        this.sharedArray = sharedArray;
    }
     
     
    public void sendMessage(String mssg) {
        System.out.println(mssg);
        
    }
    

    @Override
    public void run() {
        for (int i=0; i<10; i++){
//           sharedArray.addMessage();
       }
    }
}
