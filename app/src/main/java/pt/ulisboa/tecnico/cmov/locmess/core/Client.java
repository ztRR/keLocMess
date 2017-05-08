package pt.ulisboa.tecnico.cmov.locmess.core;


/**
 * Created by Jammy on 05/05/2017.
 */


import android.net.wifi.p2p.WifiP2pManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import pt.ulisboa.tecnico.cmov.locmess.HomeActivity;


/**
 * A simple Swing-based client for the chat server.  Graphically
 * it is a frame with a text field for entering messages and a
 * textarea to see the whole dialog.
 *
 * The client follows the Chat Protocol which is as follows.
 * When the server sends "SUBMITNAME" the client replies with the
 * desired screen name.  The server will keep sending "SUBMITNAME"
 * requests as long as the client submits screen names that are
 * already in use.  When the server sends a line beginning
 * with "NAMEACCEPTED" the client is now allowed to start
 * sending the server arbitrary strings to be broadcast to all
 * chatters connected to the server.  When the server sends a
 * line beginning with "MESSAGE " then all characters following
 * this string should be displayed in its message area.
 */
public class Client {

    public BufferedReader in;
    public PrintWriter out;
    public Thread clientThread;
    public Socket clientSocket;

    /**
     * Constructs the client by laying out the GUI and registering a
     * listener with the textfield so that pressing Return in the
     * listener sends the textfield contents to the server.  Note
     * however that the textfield is initially NOT editable, and
     * only becomes editable AFTER the client receives the NAMEACCEPTED
     * message from the server.
     */
    public Client() {
        clientThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    clientSocket = new Socket("192.168.1.68",9001);
                    in = new BufferedReader(new InputStreamReader(
                            clientSocket.getInputStream()));
                    out = new PrintWriter(clientSocket.getOutputStream(), true);

                    // Process all messages from server, according to the protocol.
                    while (true) {
                        String line = in.readLine();
                        if (line.startsWith("SUBMITNAME")) {
                            out.println(getName());
                        } else if (line.startsWith("NAMEACCEPTED")) {
                            out.println("KUIAR");
                        } else if (line.startsWith("MESSAGE")) {
                            String text = line.substring(8);
                            clientSocket.close();
                            break;
                            //messageArea.append(line.substring(8) + "\n");
                        }
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        clientThread.start();
        clientThread.stop();
    }

    /**
     * Prompt for and return the address of the server.
     */
    private String getServerAddress() {
        return null;
    }
    private Socket socket;

    /**
     * Prompt for and return the desired screen name.
     */
    private String getName() {
        return "ya";
    }

    /**
     * Connects to the server then enters the processing loop.
     */
    public void run() throws IOException {

    }

    /**
     * Runs the client as an application with a closeable frame.
     */
}

