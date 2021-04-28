package Server;

import Message.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Server {
    
    public static Semaphore pairingSemp = new Semaphore(1,true);
    public static ArrayList<SClient> sclients;
    SListenThread listenThread;
    SPairingThread pairingThread;
    ServerSocket socket;
    int port;
    
    public Server(int port){
        try {
            this.port = port;
            this.sclients = new ArrayList();
            this.socket = new ServerSocket(this.port);
            this.listenThread = new SListenThread(this);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public void Listen(){
        this.listenThread.start();
    }
   
    
    public static void Send(SClient cl, Message msg) {
        try {
            cl.sOutput.writeObject(msg);
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
