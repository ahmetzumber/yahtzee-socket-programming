package Server;

import Message.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SClient {

    Socket socket;
    String name = "NoName";
    ObjectOutputStream sOutput;
    ObjectInputStream sInput;

    // dinleme threadi
    SCListenThread scListenThread;

    //rakip client
    SClient rival;

    //eşleşme durumu
    public boolean paired = false;

    public SClient(Socket socket) {

        try {
            this.socket = socket;
            this.scListenThread = new SCListenThread(this);
            this.sOutput = new ObjectOutputStream(this.socket.getOutputStream());
            this.sInput = new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void listen() {
        this.scListenThread.start();
    }

    //client mesaj gönderme
    public void Send(Message message) {
        try {
            this.sOutput.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    class SCListenThread extends Thread {

        SClient sclient;

        public SCListenThread(SClient sclient) {
            this.sclient = sclient;
        }

        @Override
        public void run() {
            while (!this.sclient.socket.isClosed()) {
                try {
                    Object msg = this.sclient.sInput.readObject();
                    System.out.println("SClient Message: " + msg.toString());
                } catch (IOException ex) {
                    Logger.getLogger(SCListenThread.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(SCListenThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public class SPairingThread extends Thread {

        SClient sclient;

        public SPairingThread(SClient sclient) {
            this.sclient = sclient;
        }

        @Override
        public void run() {
            while (this.sclient.paired == false && this.sclient.socket.isConnected()) {
                try {
                    //lock mekanizması
                    //sadece bir client içeri grebilir
                    //diğerleri release olana kadar bekler
                    Server.pairingSemp.acquire(1);

                    //client eğer eşleşmemişse 
                    if (!sclient.paired) {
                        SClient selectedPair = null;
                        while (selectedPair == null && this.sclient.socket.isConnected()) {
                            for (SClient i : Server.sclients) {
                                if (!i.equals(this.sclient) && i.paired == false) {
                                    //eşleşme sağlandı ve gerekli işaretlemeler yapıldı
                                    selectedPair = i;
                                    this.sclient.rival = selectedPair;
                                    selectedPair.rival = this.sclient;
                                    selectedPair.paired = true;
                                    this.sclient.paired = true;
                                    break;
                                }
                            }
                            //sürekli dönmesin 1 saniyede bir dönsün
                            sleep(1000);
                        }
                        // eşleşme oldu
                        Message msg1 = new Message(Message.Message_Type.RivalConnected);
                        msg1.content = sclient.name;
                        Server.Send(sclient.rival, msg1);

                        Message msg2 = new Message(Message.Message_Type.RivalConnected);
                        msg1.content = sclient.name;
                        Server.Send(sclient.rival, msg2);
                    }
                    //lock mekanizmasını servest bırak
                    //bırakılmazsa deadlock olur.
                    Server.pairingSemp.release(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SPairingThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

}
