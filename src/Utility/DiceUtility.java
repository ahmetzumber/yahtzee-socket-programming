/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

/**
 *
 * @author ahmetzumberoglu
 */
public class DiceUtility {
    
    public static int calculateOnes(Dice dices[]){
        int result = 0;
        for (Dice d : dices) 
            if(d.value == 1)
                result += 1;
        return result;
    }
    
    public static int calculateTwos(Dice dices[]){
        int result = 0;
        for (Dice d : dices) 
            if(d.value == 2)
                result += 2;
        return result;
    }
    
    public static int calculateThrees(Dice dices[]){
        int result = 0;
        for (Dice d : dices) 
            if(d.value == 3)
                result += 3;
        return result;
    }
    
    public static int calculateFours(Dice dices[]){
        int result = 0;
        for (Dice d : dices) 
            if(d.value == 4)
                result += 4;
        return result;
    }
    
    public static int calculateFives(Dice dices[]){
        int result = 0;
        for (Dice d : dices) 
            if(d.value == 5)
                result += 5;
        return result;
    }
    
    public static int calculateSixes(Dice dices[]){
        int result = 0;
        for (Dice d : dices) 
            if(d.value == 6)
                result += 6;
        return result;
    }
    
}
