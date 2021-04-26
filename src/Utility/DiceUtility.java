/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import java.util.Arrays;

/**
 *
 * @author ahmetzumberoglu
 */
public class DiceUtility {
    
    // Three of a kind and four of a kind should be modified

    public static int calculateOnes(Dice dices[]) {
        int result = 0;
        for (Dice d : dices) {
            if (d.value == 1) {
                result += 1;
            }
        }
        return result;
    }

    public static int calculateTwos(Dice dices[]) {
        int result = 0;
        for (Dice d : dices) {
            if (d.value == 2) {
                result += 2;
            }
        }
        return result;
    }

    public static int calculateThrees(Dice dices[]) {
        int result = 0;
        for (Dice d : dices) {
            if (d.value == 3) {
                result += 3;
            }
        }
        return result;
    }

    public static int calculateFours(Dice dices[]) {
        int result = 0;
        for (Dice d : dices) {
            if (d.value == 4) {
                result += 4;
            }
        }
        return result;
    }

    public static int calculateFives(Dice dices[]) {
        int result = 0;
        for (Dice d : dices) {
            if (d.value == 5) {
                result += 5;
            }
        }
        return result;
    }

    public static int calculateSixes(Dice dices[]) {
        int result = 0;
        for (Dice d : dices) {
            if (d.value == 6) {
                result += 6;
            }
        }
        return result;
    }

    public static int threeOfKind(Dice dices[]) {
        int[] t = new int[6];
        t[dices[0].value - 1]++;
        t[dices[1].value - 1]++;
        t[dices[2].value - 1]++;
        t[dices[3].value - 1]++;
        t[dices[4].value - 1]++;
        for (int i = 0; i < 6; i++) {
            if (t[i] >= 3) {
                return (i + 1) * 3;
            }
        }
        return 0;
    }

    public static int fourOfKind(Dice dices[]) {
        int ones = 0, twos = 0, threes = 0, fours = 0, fives = 0, sixes = 0;
        for (Dice d : dices) {
            if (d.value == 1) ones++;   
            if (d.value == 2) twos++;         
            if (d.value == 3) threes++;      
            if (d.value == 4) fours++;    
            if (d.value == 5) fives++;    
            if (d.value == 6) sixes++;     
        }
        // if anyone has 4 same number than return 25 point
        if (ones == 4 || twos == 4 || threes == 4 || fours == 4 || fives == 4 || sixes == 4) 
            return 25;  
        return 0;
    }

    public static int fullHouse(Dice[] dices) {   
        int ones = 0, twos = 0, threes = 0, fours = 0, fives = 0, sixes = 0;
        for (int i = 0; i < 5; i++) {
            if (dices[i].value == 1) ones++;       
            if (dices[i].value == 2) twos++;  
            if (dices[i].value == 3) threes++;
            if (dices[i].value == 4) fours++;
            if (dices[i].value == 5) fives++;
            if (dices[i].value == 6) sixes++;
        }
        if((ones == 3 && (twos == 2 || threes == 2 || fours == 2
                || fives == 2 || sixes == 2)) || (twos == 3 && (ones == 2
                || threes == 2 || fours == 2 || fives == 2 || sixes == 2))
                || (threes == 3 && (ones == 2 || twos == 2 || fours == 2
                || fives == 2 || sixes == 2)) || (fours == 3 && (ones == 2
                || threes == 2 || twos == 2 || fives == 2 || sixes == 2))
                || (fives == 3 && (ones == 2 || threes == 2 || fours == 2
                || twos == 2 || sixes == 2)) || (sixes == 3 && (ones == 2
                || threes == 2 || fours == 2 || fives == 2 || twos == 2))){
            return 25;
        }
        return 0;
    }

    public static int smallStraight(Dice[] dices) {
        int ones = 0, twos = 0, threes = 0, fours = 0, fives = 0, sixes = 0;
        for (int i = 0; i < 5; i++) {
            if (dices[i].value == 1) ones++;
            if (dices[i].value == 2) twos++;   
            if (dices[i].value == 3) threes++;   
            if (dices[i].value == 4) fours++;  
            if (dices[i].value == 5) fives++;  
            if (dices[i].value == 6) sixes++;        
        }
        // 3 different combination of 4
        if ((ones == 1 && twos == 1 && threes == 1 && fours == 1)
                || (twos == 1 && threes == 1 && fours == 1 && fives == 1)
                || (threes == 1 && fours == 1 && fives == 1 && sixes == 1)) {
            return 30;
        }
        return 0;
    }

    public static int largeStraight(Dice[] dices) {
        int ones = 0, twos = 0, threes = 0, fours = 0, fives = 0, sixes = 0;
        for (int i = 0; i < dices.length; i++) {
            if (dices[i].value == 1) ones++;           
            if (dices[i].value == 2) twos++;          
            if (dices[i].value == 3) threes++;           
            if (dices[i].value == 4) fours++;           
            if (dices[i].value == 5) fives++;           
            if (dices[i].value == 6) sixes++;    
        }
        // 2 different combination of 5
        if ((ones == 1 && twos == 1 && threes == 1 && fours == 1 && fives == 1)
                || (twos == 1 && threes == 1 && fours == 1 && fives == 1 && sixes == 1)) {
            return 40;
        }
        return 0;
    }

    public static int chance(Dice[] dice) {
        int result = 0;
        for (Dice d : dice) 
            result += d.value;      
        return result;
    }

    public static int yahtzee(Dice dices[]){
        int ones = 0, twos = 0, threes = 0, fours = 0, fives = 0, sixes = 0;
        for (int i = 0; i < dices.length; i++) {
            if (dices[i].value == 1) ones++; 
            if (dices[i].value == 2) twos++;
            if (dices[i].value == 3) threes++;    
            if (dices[i].value == 4) fours++;         
            if (dices[i].value == 5) fives++;  
            if (dices[i].value == 6) sixes++;
        }
        if (ones == 5 || twos == 5 || threes == 5 || fours == 5 || fives == 5 || sixes == 5)
            return 50;      
        return 0;
    }

    
}
