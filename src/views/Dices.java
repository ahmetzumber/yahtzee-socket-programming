/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author ahmetzumberoglu
 */
public class Dices {
    
    private ImageIcon dice01 = new ImageIcon("src/images/dice01.png");
    private ImageIcon dice02 = new ImageIcon("src/images/dice02.png");
    private ImageIcon dice03 = new ImageIcon("src/images/dice03.png");
    private ImageIcon dice04 = new ImageIcon("src/images/dice04.png");
    private ImageIcon dice05 = new ImageIcon("src/images/dice05.png");
    private ImageIcon dice06 = new ImageIcon("src/images/dice06.png");
    
    public Dices(){
        
    }
  
    public ImageIcon getDice01Image(int width, int height){
        Image scale = dice01.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon dice01pic = new ImageIcon(scale);
        return dice01pic;
    }
    
    public ImageIcon getDice02Image(int width, int height){
        Image scale = dice02.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon dicepic = new ImageIcon(scale);
        return dicepic;
    }
    
    public ImageIcon getDice03Image(int width, int height){
        Image scale = dice03.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon dicepic = new ImageIcon(scale);
        return dicepic;
    }
    
    public ImageIcon getDice04Image(int width, int height){
        Image scale01 = dice04.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon dice01pic = new ImageIcon(scale01);
        return dice01pic;
    }
    
    public ImageIcon getDice05Image(int width, int height){
        Image scale = dice05.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon dicepic = new ImageIcon(scale);
        return dicepic;
    }
    
    public ImageIcon getDice06Image(int width, int height){
        Image scale = dice06.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon dicepic = new ImageIcon(scale);
        return dicepic;
    }
    
    
    

}
