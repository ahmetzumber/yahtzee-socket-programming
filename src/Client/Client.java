package Client;

import Message.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import views.GameScene;
import views.Login;

public class Client {

    public static Socket socket;
    public static ObjectInputStream sInput;
    public static ObjectOutputStream sOutput;
    public static boolean isPaired = false;
    public static ListenThread listen;

    public static void Start(String ip, int port) {
        try {
            // server ve socket baglantilari
            Client.socket = new Socket(ip, port);
            Client.listen = new ListenThread();
            Client.sInput = new ObjectInputStream(Client.socket.getInputStream());
            Client.sOutput = new ObjectOutputStream(Client.socket.getOutputStream());
            Client.listen.start();
            
            Message msg = new Message(Message.Message_Type.Name);
            msg.content = Login.nameTxt.getText();
            Client.Send(msg);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void  Stop() {
        try {
            if (Client.socket != null) {
                Client.listen.stop();
                Client.socket.close();
                Client.sOutput.flush();
                Client.sOutput.close();
                Client.sInput.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void Display(String message) {
        System.out.println(message);
    }
    
    public static void  Send(Message msg) {
        try {
            Client.sOutput.writeObject(msg);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

class ListenThread extends Thread {

    @Override
    public void run() {
        while (Client.socket.isConnected()) {
            try {
                Message msg = (Message) Client.sInput.readObject();
                switch (msg.type) {
                    case Name:
                        break;
                    case RivalRequest:
                        break;
                    case RivalConnected:
                        String rivalName = (String)msg.content;
                        Client.isPaired = true;
                        Login.control.setText("Eslestirildi...........");
                        Login.gs.player1.setText("You");
                        Login.gs.player2.setText(rivalName);      
                        Login.gs.setVisible(true);
                        break;
                    case Disconnect:
                        break;
                    case GameControl:
                        Login.gs.roundControl = (int) msg.content;
                        if((int)msg.content == 1)
                            Login.gs.changeTurn(true);
                        break;
                    case Dice:
                }
            } catch (IOException ex) {
                Logger.getLogger(ListenThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ListenThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
