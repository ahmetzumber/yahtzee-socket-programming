/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.Image;
import java.util.ArrayList;
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
    private int WIDTH = 110, HEIGHT = 110;
    
    public Dices(){
        
    }
  
    public ImageIcon getDice01Image(){
        Image scale = dice01.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT);
        ImageIcon dice01pic = new ImageIcon(scale);
        return dice01pic;
    }
    
    public ImageIcon getDice02Image(){
        Image scale = dice02.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT);
        ImageIcon dicepic = new ImageIcon(scale);
        return dicepic;
    }
    
    public ImageIcon getDice03Image(){
        Image scale = dice03.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT);
        ImageIcon dicepic = new ImageIcon(scale);
        return dicepic;
    }
    
    public ImageIcon getDice04Image(){
        Image scale01 = dice04.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT);
        ImageIcon dice01pic = new ImageIcon(scale01);
        return dice01pic;
    }
    
    public ImageIcon getDice05Image(){
        Image scale = dice05.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT);
        ImageIcon dicepic = new ImageIcon(scale);
        return dicepic;
    }
    
    public ImageIcon getDice06Image(){
        Image scale = dice06.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT);
        ImageIcon dicepic = new ImageIcon(scale);
        return dicepic;
    }
    
    public ArrayList<ImageIcon> getIcons(){
        ArrayList<ImageIcon> icons = new ArrayList<>();
        icons.add(getDice01Image());
        icons.add(getDice02Image());
        icons.add(getDice03Image());
        icons.add(getDice04Image());
        icons.add(getDice05Image());
        icons.add(getDice06Image());
        return icons;
    }
    

}
