package views;

import Utility.*;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import Client.*;
import Message.Message;
import Message.ScoreMessage;
import java.util.ArrayList;
import Message.ScoreMessage.Scores;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;

/**
 *
 * @author Ahmet
 */
public class GameScene extends javax.swing.JFrame {

    public static GameScene thisGame;
    public boolean finishState = false;
    // kimin baslayacagının controlü (1 olan baslar)
    public int roundControl;
    public static ArrayList<Score> myPoints;
    public static ArrayList<Score> rivalPoints;
    public static ArrayList<JToggleButton> activeButtons;

    Dice dices[] = new Dice[5];
    int rollCount = 0;

    public GameScene() {
        initComponents();
        myPoints = new ArrayList();
        rivalPoints = new ArrayList();
        activeButtons = new ArrayList();
        thisGame = this;
        initScores();
        disableRivalButtons(false);
        jPanel2.setBackground(Color.decode("#a0937d"));
        jPanel2.setSize(1190, 1015);
        dices[0] = new Dice(dice01lbl, 1);
        dices[1] = new Dice(dice02lbl, 2);
        dices[2] = new Dice(dice03lbl, 3);
        dices[3] = new Dice(dice04lbl, 4);
        dices[4] = new Dice(dice05lbl, 5);

        revalidate();
    }

    public Score getMyButtonByGivenType(Scores score_type) {
        // Verilen score türüne göre score objemi döndür
        for (Score myPoint : myPoints) {
            if (myPoint.getScore_type() == score_type) {
                return myPoint;
            }
        }
        return null;
    }

    public JButton getRivalButtonByGivenType(Scores score_type) {
        // Verilen score türüne göre rakibin butonuna erişmek için
        for (Score rivalPoint : rivalPoints) {
            if (rivalPoint.getScore_type() == score_type) {
                return rivalPoint.getButton();
            }
        }
        return null;
    }

    public void initScores() {

        // player 1 score
        myPoints.add(new Score(Scores.ONES, onesValue1));
        myPoints.add(new Score(Scores.TWOS, twosValue1));
        myPoints.add(new Score(Scores.THREES, threesValue1));
        myPoints.add(new Score(Scores.FOURS, foursValue1));
        myPoints.add(new Score(Scores.FIVES, fivesValue1));
        myPoints.add(new Score(Scores.SIXES, sixesValue1));
        myPoints.add(new Score(Scores.THREEKIND, threeKind1));
        myPoints.add(new Score(Scores.FOURKIND, fourKind1));
        myPoints.add(new Score(Scores.FULLHOUSE, full1));
        myPoints.add(new Score(Scores.SMALLSTR, smallS1));
        myPoints.add(new Score(Scores.LARGESTR, largeS1));
        myPoints.add(new Score(Scores.CHANCE, chance1));
        myPoints.add(new Score(Scores.YAHTZEE, yahtzee1));

        // player 2 scores
        rivalPoints.add(new Score(Scores.ONES, onesValue2));
        rivalPoints.add(new Score(Scores.TWOS, twosValue2));
        rivalPoints.add(new Score(Scores.THREES, threesValue2));
        rivalPoints.add(new Score(Scores.FOURS, foursValue2));
        rivalPoints.add(new Score(Scores.FIVES, fivesValue2));
        rivalPoints.add(new Score(Scores.SIXES, sixesValue2));
        rivalPoints.add(new Score(Scores.THREEKIND, threeKind2));
        rivalPoints.add(new Score(Scores.FOURKIND, fourKind2));
        rivalPoints.add(new Score(Scores.FULLHOUSE, full2));
        rivalPoints.add(new Score(Scores.SMALLSTR, smallS2));
        rivalPoints.add(new Score(Scores.LARGESTR, largeS2));
        rivalPoints.add(new Score(Scores.CHANCE, chance2));
        rivalPoints.add(new Score(Scores.YAHTZEE, yahtzee2));

        activeButtons.add(h1);
        activeButtons.add(h2);
        activeButtons.add(h3);
        activeButtons.add(h4);
        activeButtons.add(h5);

        addEventListenerToButtons();
    }

    public void changeTurn(boolean control) {

        // Benim sıramda iken clientın erişimi engellenir
        roll.setEnabled(control);

        for (JToggleButton activeButton : activeButtons) {
            activeButton.setEnabled(control);
        }

        // Zarların aktifligini tur degisimlerinde kontrol et
        for (Dice dice : dices) {
            dice.getLabel().setEnabled(control);
            if (dice.getLabel().isEnabled()) {
                dice.shuffle();
            }
        }

        // player1 buttons
        for (Score myPoint : myPoints) {
            if (!myPoint.isButtonChoosen) {
                myPoint.getButton().setEnabled(control);
            }
        }
    }

    public void disableRivalButtons(boolean control) {
        // player1 buttons
        onesValue2.setEnabled(control);
        twosValue2.setEnabled(control);
        threesValue2.setEnabled(control);
        foursValue2.setEnabled(control);
        fivesValue2.setEnabled(control);
        sixesValue2.setEnabled(control);
        threeKind2.setEnabled(control);
        fourKind2.setEnabled(control);
        full2.setEnabled(control);
        smallS2.setEnabled(control);
        largeS2.setEnabled(control);
        chance2.setEnabled(control);
        yahtzee2.setEnabled(control);
    }

    public void addEventListenerToButtons() {
        for (Score myPoint : myPoints) {
            myPoint.getButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    if (roundControl == 1) {
                        ScoreMessage score = new ScoreMessage(myPoint.getScore_type());
                        try {
                            score.content = Integer.parseInt(myPoint.getButton().getText());
                        } catch (Exception e) {
                            myPoint.getButton().setText("0");
                            score.content = 0;
                        }
                        roundControl = 0;
                        myPoint.isButtonChoosen = true;
                        Message msg = new Message(Message.Message_Type.CHANGE);
                        msg.content = score;
                        System.out.println("msg.content = " + msg.content);
                        Client.Send(msg);
                        System.out.println("mesajı yolladımmmmm");
                        bonusANDsumControl();
                        disableButtons();
                    }
                }
            });
        }
    }

    public void disableButtons() {
        for (Score myPoint : myPoints) {
            if (!myPoint.isButtonChoosen) {
                myPoint.getButton().setText("-");
            }
            myPoint.getButton().setEnabled(false);
        }
        roll.setEnabled(false);
        for (JToggleButton activeButton : activeButtons) {
            activeButton.setEnabled(false);
        }
    }

    public void bonusANDsumControl() {
        int bonusResult = 0;
        int sumResult = 0;
        int total = 0;
        if (getMyButtonByGivenType(Scores.ONES).isButtonChoosen
                && getMyButtonByGivenType(Scores.TWOS).isButtonChoosen
                && getMyButtonByGivenType(Scores.THREES).isButtonChoosen
                && getMyButtonByGivenType(Scores.FOURS).isButtonChoosen
                && getMyButtonByGivenType(Scores.FIVES).isButtonChoosen
                && getMyButtonByGivenType(Scores.SIXES).isButtonChoosen) {

            bonusResult += Integer.parseInt(getMyButtonByGivenType(Scores.ONES).getButton().getText());
            bonusResult += Integer.parseInt(getMyButtonByGivenType(Scores.TWOS).getButton().getText());
            bonusResult += Integer.parseInt(getMyButtonByGivenType(Scores.THREES).getButton().getText());
            bonusResult += Integer.parseInt(getMyButtonByGivenType(Scores.FOURS).getButton().getText());
            bonusResult += Integer.parseInt(getMyButtonByGivenType(Scores.FIVES).getButton().getText());
            bonusResult += Integer.parseInt(getMyButtonByGivenType(Scores.SIXES).getButton().getText());

            if (bonusResult >= 63) {
                sumResult = bonusResult + 35;
                bonusPlayer01.setText("35");
                sumPlayer01.setText(String.valueOf(sumResult));
            } else {
                sumResult = bonusResult;
                bonusPlayer01.setText("0");
                sumPlayer01.setText(String.valueOf(sumResult));
            }
            // TOTAL SCORE CONTROLS 
            if (getMyButtonByGivenType(Scores.THREEKIND).isButtonChoosen
                    && getMyButtonByGivenType(Scores.FOURKIND).isButtonChoosen
                    && getMyButtonByGivenType(Scores.FULLHOUSE).isButtonChoosen
                    && getMyButtonByGivenType(Scores.SMALLSTR).isButtonChoosen
                    && getMyButtonByGivenType(Scores.LARGESTR).isButtonChoosen
                    && getMyButtonByGivenType(Scores.CHANCE).isButtonChoosen
                    && getMyButtonByGivenType(Scores.YAHTZEE).isButtonChoosen) {

                total += Integer.parseInt(getMyButtonByGivenType(Scores.THREEKIND).getButton().getText());
                total += Integer.parseInt(getMyButtonByGivenType(Scores.FOURKIND).getButton().getText());
                total += Integer.parseInt(getMyButtonByGivenType(Scores.FULLHOUSE).getButton().getText());
                total += Integer.parseInt(getMyButtonByGivenType(Scores.SMALLSTR).getButton().getText());
                total += Integer.parseInt(getMyButtonByGivenType(Scores.LARGESTR).getButton().getText());
                total += Integer.parseInt(getMyButtonByGivenType(Scores.CHANCE).getButton().getText());
                total += Integer.parseInt(getMyButtonByGivenType(Scores.YAHTZEE).getButton().getText());

                // Total score hesaplandıktan sonra cliente mesaj olarak gönder
                total += sumResult;
                totalPlayer1.setText(String.valueOf(total));
                Message finishMsg = new Message(Message.Message_Type.FINISH);
                finishMsg.content = totalPlayer1.getText();
                Client.Send(finishMsg);
                
                if (finishState) {
                    int player2Total = Integer.parseInt(totalPlayer2.getText());
                    String finishStr = "";
                    
                    if(total > player2Total){
                        finishStr = "You winn !! Score: "+total;
                        JOptionPane.showMessageDialog(this,finishStr);
                    }else if(player2Total > total){
                        finishStr = "Your rival winn :( Rival Score: "+player2Total;
                        JOptionPane.showMessageDialog(this,finishStr);
                    }
                }

            }
        }
    }

    public int rivalScores(){
        int score = 0;
        for (Score rivalPoint : rivalPoints) {
            score += Integer.parseInt(rivalPoint.getButton().getText());
        }
        score += Integer.parseInt(sumPlayer02.getText());
        return score;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jFrame2 = new javax.swing.JFrame();
        jLabel2 = new javax.swing.JLabel();
        player1 = new javax.swing.JLabel();
        player2 = new javax.swing.JLabel();
        ones = new javax.swing.JLabel();
        twos = new javax.swing.JLabel();
        threes = new javax.swing.JLabel();
        fours = new javax.swing.JLabel();
        fives = new javax.swing.JLabel();
        Sixes = new javax.swing.JLabel();
        onesValue1 = new javax.swing.JButton();
        onesValue2 = new javax.swing.JButton();
        foursValue2 = new javax.swing.JButton();
        twosValue1 = new javax.swing.JButton();
        twosValue2 = new javax.swing.JButton();
        threesValue1 = new javax.swing.JButton();
        threesValue2 = new javax.swing.JButton();
        fivesValue1 = new javax.swing.JButton();
        fivesValue2 = new javax.swing.JButton();
        sixesValue2 = new javax.swing.JButton();
        bonus = new javax.swing.JLabel();
        threeKind = new javax.swing.JLabel();
        sum = new javax.swing.JLabel();
        threeKind1 = new javax.swing.JButton();
        sixesValue1 = new javax.swing.JButton();
        threeKind2 = new javax.swing.JButton();
        bonusPlayer2 = new javax.swing.JLabel();
        totalPlayer2 = new javax.swing.JLabel();
        sumPlayer02 = new javax.swing.JLabel();
        sumPlayer01 = new javax.swing.JLabel();
        fourKind2 = new javax.swing.JButton();
        fourKind1 = new javax.swing.JButton();
        fourKind = new javax.swing.JLabel();
        largeS = new javax.swing.JLabel();
        full2 = new javax.swing.JButton();
        full1 = new javax.swing.JButton();
        smallS1 = new javax.swing.JButton();
        chance1 = new javax.swing.JButton();
        smallS2 = new javax.swing.JButton();
        largeS2 = new javax.swing.JButton();
        fullH = new javax.swing.JLabel();
        smallS = new javax.swing.JLabel();
        chance2 = new javax.swing.JButton();
        largeS1 = new javax.swing.JButton();
        yahtzee2 = new javax.swing.JButton();
        yahtzee1 = new javax.swing.JButton();
        large = new javax.swing.JLabel();
        total = new javax.swing.JLabel();
        bonusPlayer01 = new javax.swing.JLabel();
        totalPlayer1 = new javax.swing.JLabel();
        largeS3 = new javax.swing.JLabel();
        dice01lbl = new javax.swing.JLabel();
        dice02lbl = new javax.swing.JLabel();
        dice03lbl = new javax.swing.JLabel();
        dice04lbl = new javax.swing.JLabel();
        dice05lbl = new javax.swing.JLabel();
        foursValue1 = new javax.swing.JButton();
        roll = new javax.swing.JButton();
        h5 = new javax.swing.JToggleButton();
        h1 = new javax.swing.JToggleButton();
        h2 = new javax.swing.JToggleButton();
        h3 = new javax.swing.JToggleButton();
        h4 = new javax.swing.JToggleButton();
        jPanel2 = new javax.swing.JPanel();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Y A H T Z E E");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximumSize(new java.awt.Dimension(1162, 1015));
        setMinimumSize(new java.awt.Dimension(1162, 1015));
        setPreferredSize(new java.awt.Dimension(1130, 1014));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        player1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        player1.setText("Player 1");
        getContentPane().add(player1, new org.netbeans.lib.awtextra.AbsoluteConstraints(848, 70, -1, -1));

        player2.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        player2.setText("Player 2");
        getContentPane().add(player2, new org.netbeans.lib.awtextra.AbsoluteConstraints(968, 70, -1, -1));

        ones.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        ones.setText("Ones");
        getContentPane().add(ones, new org.netbeans.lib.awtextra.AbsoluteConstraints(698, 108, 110, 30));

        twos.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        twos.setText("Twos");
        getContentPane().add(twos, new org.netbeans.lib.awtextra.AbsoluteConstraints(698, 153, 110, 32));

        threes.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        threes.setText("Threes");
        getContentPane().add(threes, new org.netbeans.lib.awtextra.AbsoluteConstraints(698, 205, 110, 32));

        fours.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        fours.setText("Fours");
        getContentPane().add(fours, new org.netbeans.lib.awtextra.AbsoluteConstraints(698, 260, 110, 32));

        fives.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        fives.setText("Fives");
        getContentPane().add(fives, new org.netbeans.lib.awtextra.AbsoluteConstraints(698, 311, 110, 32));

        Sixes.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        Sixes.setText("Sixes");
        getContentPane().add(Sixes, new org.netbeans.lib.awtextra.AbsoluteConstraints(698, 362, 110, 32));

        onesValue1.setText("-");
        onesValue1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onesValue1ActionPerformed(evt);
            }
        });
        getContentPane().add(onesValue1, new org.netbeans.lib.awtextra.AbsoluteConstraints(828, 108, 94, 33));

        onesValue2.setText("-");
        onesValue2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onesValue2ActionPerformed(evt);
            }
        });
        getContentPane().add(onesValue2, new org.netbeans.lib.awtextra.AbsoluteConstraints(958, 108, 94, 33));

        foursValue2.setText("-");
        foursValue2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                foursValue2ActionPerformed(evt);
            }
        });
        getContentPane().add(foursValue2, new org.netbeans.lib.awtextra.AbsoluteConstraints(958, 260, 94, 33));

        twosValue1.setText("-");
        twosValue1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                twosValue1ActionPerformed(evt);
            }
        });
        getContentPane().add(twosValue1, new org.netbeans.lib.awtextra.AbsoluteConstraints(828, 155, 94, 33));

        twosValue2.setText("-");
        twosValue2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                twosValue2ActionPerformed(evt);
            }
        });
        getContentPane().add(twosValue2, new org.netbeans.lib.awtextra.AbsoluteConstraints(958, 153, 94, 33));

        threesValue1.setText("-");
        threesValue1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                threesValue1ActionPerformed(evt);
            }
        });
        getContentPane().add(threesValue1, new org.netbeans.lib.awtextra.AbsoluteConstraints(828, 205, 94, 33));

        threesValue2.setText("-");
        threesValue2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                threesValue2ActionPerformed(evt);
            }
        });
        getContentPane().add(threesValue2, new org.netbeans.lib.awtextra.AbsoluteConstraints(958, 205, 94, 33));

        fivesValue1.setText("-");
        fivesValue1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fivesValue1ActionPerformed(evt);
            }
        });
        getContentPane().add(fivesValue1, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 310, 94, 33));

        fivesValue2.setText("-");
        fivesValue2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fivesValue2ActionPerformed(evt);
            }
        });
        getContentPane().add(fivesValue2, new org.netbeans.lib.awtextra.AbsoluteConstraints(958, 311, 94, 33));

        sixesValue2.setText("-");
        sixesValue2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sixesValue2ActionPerformed(evt);
            }
        });
        getContentPane().add(sixesValue2, new org.netbeans.lib.awtextra.AbsoluteConstraints(958, 362, 94, 35));

        bonus.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        bonus.setText("Bonus");
        getContentPane().add(bonus, new org.netbeans.lib.awtextra.AbsoluteConstraints(698, 424, 110, 32));

        threeKind.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        threeKind.setText("Three of a kind");
        getContentPane().add(threeKind, new org.netbeans.lib.awtextra.AbsoluteConstraints(698, 526, 110, 32));

        sum.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        sum.setText("Sum");
        getContentPane().add(sum, new org.netbeans.lib.awtextra.AbsoluteConstraints(698, 476, 110, 32));

        threeKind1.setText("-");
        threeKind1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                threeKind1ActionPerformed(evt);
            }
        });
        getContentPane().add(threeKind1, new org.netbeans.lib.awtextra.AbsoluteConstraints(826, 528, 94, 33));

        sixesValue1.setText("-");
        sixesValue1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sixesValue1ActionPerformed(evt);
            }
        });
        getContentPane().add(sixesValue1, new org.netbeans.lib.awtextra.AbsoluteConstraints(828, 364, 94, 33));

        threeKind2.setText("-");
        threeKind2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                threeKind2ActionPerformed(evt);
            }
        });
        getContentPane().add(threeKind2, new org.netbeans.lib.awtextra.AbsoluteConstraints(958, 528, 94, 33));

        bonusPlayer2.setText("             -");
        getContentPane().add(bonusPlayer2, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 425, 94, 33));

        totalPlayer2.setText("               -");
        getContentPane().add(totalPlayer2, new org.netbeans.lib.awtextra.AbsoluteConstraints(958, 895, 94, 33));

        sumPlayer02.setText("              -");
        getContentPane().add(sumPlayer02, new org.netbeans.lib.awtextra.AbsoluteConstraints(959, 477, 93, 33));

        sumPlayer01.setText("               -");
        getContentPane().add(sumPlayer01, new org.netbeans.lib.awtextra.AbsoluteConstraints(826, 477, 94, 33));

        fourKind2.setText("-");
        fourKind2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fourKind2ActionPerformed(evt);
            }
        });
        getContentPane().add(fourKind2, new org.netbeans.lib.awtextra.AbsoluteConstraints(958, 578, 94, 33));

        fourKind1.setText("-");
        fourKind1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fourKind1ActionPerformed(evt);
            }
        });
        getContentPane().add(fourKind1, new org.netbeans.lib.awtextra.AbsoluteConstraints(826, 578, 94, 33));

        fourKind.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        fourKind.setText("Four of a kind");
        getContentPane().add(fourKind, new org.netbeans.lib.awtextra.AbsoluteConstraints(698, 576, 110, 32));

        largeS.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        largeS.setText("Chance");
        getContentPane().add(largeS, new org.netbeans.lib.awtextra.AbsoluteConstraints(698, 788, 110, 32));

        full2.setText("-");
        full2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                full2ActionPerformed(evt);
            }
        });
        getContentPane().add(full2, new org.netbeans.lib.awtextra.AbsoluteConstraints(958, 629, 94, 33));

        full1.setText("-");
        full1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                full1ActionPerformed(evt);
            }
        });
        getContentPane().add(full1, new org.netbeans.lib.awtextra.AbsoluteConstraints(826, 631, 94, 33));

        smallS1.setText("-");
        smallS1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smallS1ActionPerformed(evt);
            }
        });
        getContentPane().add(smallS1, new org.netbeans.lib.awtextra.AbsoluteConstraints(826, 684, 94, 33));

        chance1.setText("-");
        chance1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chance1ActionPerformed(evt);
            }
        });
        getContentPane().add(chance1, new org.netbeans.lib.awtextra.AbsoluteConstraints(826, 790, 94, 33));

        smallS2.setText("-");
        smallS2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smallS2ActionPerformed(evt);
            }
        });
        getContentPane().add(smallS2, new org.netbeans.lib.awtextra.AbsoluteConstraints(958, 684, 94, 33));

        largeS2.setText("-");
        largeS2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                largeS2ActionPerformed(evt);
            }
        });
        getContentPane().add(largeS2, new org.netbeans.lib.awtextra.AbsoluteConstraints(958, 737, 94, 33));

        fullH.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        fullH.setText("Full House");
        getContentPane().add(fullH, new org.netbeans.lib.awtextra.AbsoluteConstraints(698, 629, 110, 32));

        smallS.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        smallS.setText("Small straight");
        getContentPane().add(smallS, new org.netbeans.lib.awtextra.AbsoluteConstraints(698, 682, 110, 32));

        chance2.setText("-");
        chance2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chance2ActionPerformed(evt);
            }
        });
        getContentPane().add(chance2, new org.netbeans.lib.awtextra.AbsoluteConstraints(958, 790, 94, 33));

        largeS1.setText("-");
        largeS1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                largeS1ActionPerformed(evt);
            }
        });
        getContentPane().add(largeS1, new org.netbeans.lib.awtextra.AbsoluteConstraints(826, 737, 94, 33));

        yahtzee2.setText("-");
        yahtzee2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yahtzee2ActionPerformed(evt);
            }
        });
        getContentPane().add(yahtzee2, new org.netbeans.lib.awtextra.AbsoluteConstraints(958, 843, 94, 33));

        yahtzee1.setText("-");
        yahtzee1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yahtzee1ActionPerformed(evt);
            }
        });
        getContentPane().add(yahtzee1, new org.netbeans.lib.awtextra.AbsoluteConstraints(826, 843, 94, 33));

        large.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        large.setText("Large straight");
        getContentPane().add(large, new org.netbeans.lib.awtextra.AbsoluteConstraints(698, 735, 110, 32));

        total.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        total.setText("TOTAL SCORE");
        getContentPane().add(total, new org.netbeans.lib.awtextra.AbsoluteConstraints(698, 894, 110, 32));

        bonusPlayer01.setText("               -");
        getContentPane().add(bonusPlayer01, new org.netbeans.lib.awtextra.AbsoluteConstraints(826, 424, 94, 33));

        totalPlayer1.setText("               -");
        getContentPane().add(totalPlayer1, new org.netbeans.lib.awtextra.AbsoluteConstraints(826, 895, 94, 33));

        largeS3.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        largeS3.setText("YAHTZEE");
        getContentPane().add(largeS3, new org.netbeans.lib.awtextra.AbsoluteConstraints(698, 841, 110, 32));

        dice01lbl.setText("jLabel1");
        getContentPane().add(dice01lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 70, 110, 110));

        dice02lbl.setText("jLabel1");
        getContentPane().add(dice02lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 210, 110, 110));

        dice03lbl.setText("jLabel1");
        getContentPane().add(dice03lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 350, 110, 110));

        dice04lbl.setText("jLabel1");
        getContentPane().add(dice04lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 490, 110, 110));

        dice05lbl.setText("jLabel1");
        getContentPane().add(dice05lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 630, 110, 110));

        foursValue1.setText("-");
        foursValue1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                foursValue1ActionPerformed(evt);
            }
        });
        getContentPane().add(foursValue1, new org.netbeans.lib.awtextra.AbsoluteConstraints(828, 260, 94, 33));

        roll.setText("ROLL DICES");
        roll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rollActionPerformed(evt);
            }
        });
        getContentPane().add(roll, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 810, 340, 70));

        h5.setText("H");
        h5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h5ActionPerformed(evt);
            }
        });
        getContentPane().add(h5, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 630, 40, 110));

        h1.setText("H");
        h1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h1ActionPerformed(evt);
            }
        });
        getContentPane().add(h1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 69, 40, 110));

        h2.setText("H");
        h2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h2ActionPerformed(evt);
            }
        });
        getContentPane().add(h2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 210, 40, 110));

        h3.setText("H");
        h3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h3ActionPerformed(evt);
            }
        });
        getContentPane().add(h3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 350, 40, 110));

        h4.setText("H");
        h4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h4ActionPerformed(evt);
            }
        });
        getContentPane().add(h4, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 490, 40, 110));

        jPanel2.setMaximumSize(new java.awt.Dimension(1162, 1015));
        jPanel2.setMinimumSize(new java.awt.Dimension(1162, 1015));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1170, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1020, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1170, 1020));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void onesValue1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onesValue1ActionPerformed


    }//GEN-LAST:event_onesValue1ActionPerformed

    private void onesValue2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onesValue2ActionPerformed

    }//GEN-LAST:event_onesValue2ActionPerformed

    private void twosValue1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_twosValue1ActionPerformed

    }//GEN-LAST:event_twosValue1ActionPerformed

    private void twosValue2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_twosValue2ActionPerformed

    }//GEN-LAST:event_twosValue2ActionPerformed

    private void threesValue1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_threesValue1ActionPerformed

    }//GEN-LAST:event_threesValue1ActionPerformed

    private void threesValue2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_threesValue2ActionPerformed

    }//GEN-LAST:event_threesValue2ActionPerformed

    private void foursValue1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_foursValue1ActionPerformed

    }//GEN-LAST:event_foursValue1ActionPerformed

    private void foursValue2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_foursValue2ActionPerformed

    }//GEN-LAST:event_foursValue2ActionPerformed

    private void fivesValue1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fivesValue1ActionPerformed

    }//GEN-LAST:event_fivesValue1ActionPerformed

    private void fivesValue2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fivesValue2ActionPerformed

    }//GEN-LAST:event_fivesValue2ActionPerformed

    private void sixesValue1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sixesValue1ActionPerformed

    }//GEN-LAST:event_sixesValue1ActionPerformed

    private void sixesValue2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sixesValue2ActionPerformed

    }//GEN-LAST:event_sixesValue2ActionPerformed

    private void rollActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rollActionPerformed
        rollCount++;

        // her oyuncunun bir elde 3 kere zar atma hakkı var
        if (rollCount == 3) {
            roll.setEnabled(false);
        }

        for (Dice d : dices) {
            if (d.getLabel().isEnabled()) {
                d.shuffle();
            }
        }

        // dünyanın en güzel kodu
        for (Score myPoint : myPoints) {
            if (!myPoint.isButtonChoosen) {
                myPoint.getButton().setText(String.valueOf(DiceUtility.CALCULATE(dices, myPoint.getScore_type())));
            }
        }

        revalidate();
    }//GEN-LAST:event_rollActionPerformed

    private void threeKind1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_threeKind1ActionPerformed

    }//GEN-LAST:event_threeKind1ActionPerformed

    private void threeKind2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_threeKind2ActionPerformed

    }//GEN-LAST:event_threeKind2ActionPerformed

    private void fourKind1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fourKind1ActionPerformed

    }//GEN-LAST:event_fourKind1ActionPerformed

    private void fourKind2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fourKind2ActionPerformed

    }//GEN-LAST:event_fourKind2ActionPerformed

    private void full1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_full1ActionPerformed

    }//GEN-LAST:event_full1ActionPerformed

    private void full2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_full2ActionPerformed

    }//GEN-LAST:event_full2ActionPerformed

    private void smallS1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smallS1ActionPerformed

    }//GEN-LAST:event_smallS1ActionPerformed

    private void smallS2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smallS2ActionPerformed

    }//GEN-LAST:event_smallS2ActionPerformed

    private void largeS1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_largeS1ActionPerformed

    }//GEN-LAST:event_largeS1ActionPerformed

    private void largeS2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_largeS2ActionPerformed

    }//GEN-LAST:event_largeS2ActionPerformed

    private void chance1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chance1ActionPerformed

    }//GEN-LAST:event_chance1ActionPerformed

    private void chance2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chance2ActionPerformed

    }//GEN-LAST:event_chance2ActionPerformed

    private void yahtzee1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yahtzee1ActionPerformed

    }//GEN-LAST:event_yahtzee1ActionPerformed

    private void yahtzee2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yahtzee2ActionPerformed

    }//GEN-LAST:event_yahtzee2ActionPerformed

    private void h1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h1ActionPerformed
        if (h1.isSelected()) {
            dice01lbl.setEnabled(false);
        } else {
            dice01lbl.setEnabled(true);
        }
    }//GEN-LAST:event_h1ActionPerformed

    private void h2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h2ActionPerformed
        if (h2.isSelected()) {
            dice02lbl.setEnabled(false);
        } else {
            dice02lbl.setEnabled(true);
        }
    }//GEN-LAST:event_h2ActionPerformed

    private void h3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h3ActionPerformed
        if (h3.isSelected()) {
            dice03lbl.setEnabled(false);
        } else {
            dice03lbl.setEnabled(true);
        }
    }//GEN-LAST:event_h3ActionPerformed

    private void h4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h4ActionPerformed
        if (h4.isSelected()) {
            dice04lbl.setEnabled(false);
        } else {
            dice04lbl.setEnabled(true);
        }
    }//GEN-LAST:event_h4ActionPerformed

    private void h5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h5ActionPerformed
        if (h5.isSelected()) {
            dice05lbl.setEnabled(false);
        } else {
            dice05lbl.setEnabled(true);
        }
    }//GEN-LAST:event_h5ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GameScene.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameScene.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameScene.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameScene.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GameScene gs = new GameScene();
                gs.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Sixes;
    private javax.swing.JLabel bonus;
    private javax.swing.JLabel bonusPlayer01;
    private javax.swing.JLabel bonusPlayer2;
    private javax.swing.JButton chance1;
    private javax.swing.JButton chance2;
    private javax.swing.JLabel dice01lbl;
    private javax.swing.JLabel dice02lbl;
    private javax.swing.JLabel dice03lbl;
    private javax.swing.JLabel dice04lbl;
    private javax.swing.JLabel dice05lbl;
    private javax.swing.JLabel fives;
    private javax.swing.JButton fivesValue1;
    private javax.swing.JButton fivesValue2;
    private javax.swing.JLabel fourKind;
    private javax.swing.JButton fourKind1;
    private javax.swing.JButton fourKind2;
    private javax.swing.JLabel fours;
    private javax.swing.JButton foursValue1;
    private javax.swing.JButton foursValue2;
    private javax.swing.JButton full1;
    private javax.swing.JButton full2;
    private javax.swing.JLabel fullH;
    private javax.swing.JToggleButton h1;
    private javax.swing.JToggleButton h2;
    private javax.swing.JToggleButton h3;
    private javax.swing.JToggleButton h4;
    private javax.swing.JToggleButton h5;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel large;
    private javax.swing.JLabel largeS;
    private javax.swing.JButton largeS1;
    private javax.swing.JButton largeS2;
    private javax.swing.JLabel largeS3;
    private javax.swing.JLabel ones;
    private javax.swing.JButton onesValue1;
    private javax.swing.JButton onesValue2;
    public javax.swing.JLabel player1;
    public javax.swing.JLabel player2;
    private javax.swing.JButton roll;
    private javax.swing.JButton sixesValue1;
    private javax.swing.JButton sixesValue2;
    private javax.swing.JLabel smallS;
    private javax.swing.JButton smallS1;
    private javax.swing.JButton smallS2;
    private javax.swing.JLabel sum;
    private javax.swing.JLabel sumPlayer01;
    private javax.swing.JLabel sumPlayer02;
    private javax.swing.JLabel threeKind;
    private javax.swing.JButton threeKind1;
    private javax.swing.JButton threeKind2;
    private javax.swing.JLabel threes;
    private javax.swing.JButton threesValue1;
    private javax.swing.JButton threesValue2;
    private javax.swing.JLabel total;
    public javax.swing.JLabel totalPlayer1;
    public javax.swing.JLabel totalPlayer2;
    private javax.swing.JLabel twos;
    private javax.swing.JButton twosValue1;
    private javax.swing.JButton twosValue2;
    private javax.swing.JButton yahtzee1;
    private javax.swing.JButton yahtzee2;
    // End of variables declaration//GEN-END:variables
}
