package Client;

import Message.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    public Socket socket;
    public ObjectInputStream sInput;
    public ObjectOutputStream sOutput;
    public ListenThread listen;

    public Client(String ip, int port) {
        try {
            this.socket = new Socket(ip, port);
            this.listen = new ListenThread(this);
            this.sInput = new ObjectInputStream(socket.getInputStream());
            this.sOutput = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Listen() {
        this.listen.start();
        Message msg = new Message(Message.Message_Type.Name);
        //msg.content = Game.ThisGame.txt_name.getText();
        this.Send(msg);
    }

    public void Stop() {
        try {
            if (this.socket != null) {
                this.listen.stop();
                this.socket.close();
                this.sOutput.flush();
                this.sOutput.close();
                this.sInput.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Send(Message msg) {
        try {
            this.sOutput.writeObject(msg);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}

class ListenThread extends Thread {

    Client client;

    public ListenThread(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        while (this.client.socket.isConnected()) {
            try {
                Message msg = (Message) this.client.sInput.readObject();
                switch (msg.type) {
                    case Name:
                        break;
                    case RivalConnected:
                        break;
                    case Disconnect:
                        break;
                }
            } catch (IOException ex) {
                Logger.getLogger(ListenThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ListenThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
