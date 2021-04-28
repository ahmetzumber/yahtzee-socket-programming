package Server;

import Message.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Server {
    ServerSocket socket;
    int port;
    
    public Server(int port){
        try {
            this.port = port;
            this.socket = new ServerSocket(this.port);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void Listen(){
        while(!this.socket.isClosed()){
            try {
                System.out.println("server is listening");
                Socket nClient = this.socket.accept();
                System.out.println("client connected");
                ObjectOutputStream cOutput = new ObjectOutputStream(nClient.getOutputStream());
                ObjectInputStream cInput = new ObjectInputStream(nClient.getInputStream());
                
                Object message = cInput.readObject();
                cOutput.writeObject("Hellooooğ");
                
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
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
