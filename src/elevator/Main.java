/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elevator;

import elevator.Gui.Elevator;
import elevator.Gui.FloorA;

/**
 *
 * @author bautistaph_sd2021
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Elevator().setVisible(true);
        new FloorA().setVisible(true);
    }
    
}
