/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggdamessage.client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import maggdamessage.MaggdaMessage;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import maggdamessage.Command;

/**
 *
 * @author DavidPrivat
 */
public class Connection extends Thread {

    private ConnectionLabel connectionLabel;
    private String ipAdress;
    private String name;
    private Socket socket;
    private DataOutputStream output;
    private DataInputStream input;

    private boolean running;
    private boolean connected;
    
    private Chat chat;

    public Connection(String ip) {
        name = null;
        ipAdress = ip;
        connected = false;
        
        if (ip != null) {
            connectionLabel = new ConnectionLabel("unknown");
        } else {
            connectionLabel = new ConnectionLabel("");
        }
        running = true;

    }

    public ConnectionLabel getConnectionLabel() {
        return connectionLabel;
    }

    public boolean connect() {
        if (ipAdress == null || ipAdress.equals("")) {
            connected = false;
            return false;
        }

        try {
            socket = new Socket(ipAdress, MaggdaMessage.port);
            activate();               // If it reachs this point: SUCCESS!

            try {
                output = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {

                input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            chat = new Chat("Anonym", output);
            connected = true;
            return true;

        } catch (IOException ex) {
            deactivate();
            connected = false;
            return false;
        }

    }

    public void pingServer() throws IOException {
        sendCommand(new Command(Command.CommandType.PING, new String[]{System.getProperty("user.name")}));
    }

    @Override
    public void run() {
        String line = "";
        try {
            while ((running) && ((line = input.readUTF()) != null)) {
                System.out.println("[Client] Command received: " + line);
                try {
                    Command receivedCommand = new Command(line);

                    switch (receivedCommand.getCommandType()) {
                        case PING:

                            break;
                        case PONG:
                            handlePONG(receivedCommand);
                            break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("[Client] ConnectionLoop stopped.");
            socket.close();
            System.out.println("[Client] Connection stopped.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void handlePONG(Command pongCommand) {
        Platform.runLater(() -> {
            setConnectionName(pongCommand.getArgs()[0]);
        });

    }

    public synchronized void sendCommand(Command c) throws IOException {
        String commandAsString = c.toString();
        output.writeUTF(commandAsString);
        System.out.println("[Client] Command sent: " + commandAsString);

    }

    public void close() throws IOException {
        sendCommand(new Command(Command.CommandType.LOGOUT, new String[]{}));
        running = false;
        System.out.println("[Client] Set running to false.");

    }

    public void setConnectionName(String name) {
        if(!connected) {
            connect();
        }
        Platform.runLater(() -> {
            connectionLabel.setText(name);
            activate();
            chat.setPartnerName(name);
        });
    }
    
    public void activate() {
        if(chat == null) {
            chat = new Chat(name, output);
        }
        connectionLabel.setSuccessfull(true);
        connectionLabel.setOnMouseClicked((MouseEvent e)->{
            Client.setNewRoot(chat);
        });
    }

    public void deactivate() {
        Platform.runLater(() -> {
            connectionLabel.setText("Disconnected");
            connectionLabel.setSuccessfull(false);
        });
    }

    public String getIpAdress() {
        return ipAdress;
    }
    
    public Chat getChat() {
        return chat;
    }

}
