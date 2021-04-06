/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import java.util.Random;
import javax.swing.JLabel;

/**
 *
 * @author ahmetzumberoglu
 */
public class Dice {
    
    JLabel label;
    int value;
    
    public Dice(JLabel label, int value){
        this.label = label;
        this.value = value;
        this.label.setIcon(Utility.getImageIcon(this.value));
    }
    
    public void shuffle(){ 
        this.value = new Random().nextInt(5) + 1;
        this.label.setIcon(Utility.getImageIcon(this.value));
    }
    
    public JLabel getLabel(){
        return this.label;
    }
    
}
