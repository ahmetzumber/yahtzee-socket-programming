package Server;

import Message.Message;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Server {
    
    public static int port;
    public static int clientID = 0;
    public static ServerSocket socket;
    public static SListenThread listenThread;
    public static ArrayList<SClient> sclients = new ArrayList();
    public static Semaphore pairingSemp = new Semaphore(1,true);
    
    public static void Start(int port){
        try {
            Server.port = port;
            Server.socket = new ServerSocket(Server.port);
            Server.listenThread = new SListenThread();
            Server.listenThread.start();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void Send(SClient cl, Message msg) {
        try {
            cl.sOutput.writeObject(msg);
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

class SListenThread  extends Thread {
    
    @Override
    public void run() {
        while(!Server.socket.isClosed()){
            try {
                System.out.println("Listeningg...and client waiting....");
                Socket newSocket = Server.socket.accept();
                System.out.println("Client came...."); 
                SClient newSClient = new SClient(Server.clientID,newSocket);
                Server.clientID++;
                Server.sclients.add(newSClient);
                newSClient.listenThread.start();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }
    
}

