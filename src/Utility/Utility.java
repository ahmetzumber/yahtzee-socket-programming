/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author ahmetzumberoglu
 */
public class Utility {
    
    private static int WIDTH = 110, HEIGHT = 110;
    
    public static ImageIcon getImageIcon(int value){       
        String sourcePath = "src/images/dice0"+value+".png";    
        ImageIcon imgIcon = new ImageIcon(sourcePath);
        Image scale = imgIcon.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT);
        return new ImageIcon(scale);     
        
    }

}
