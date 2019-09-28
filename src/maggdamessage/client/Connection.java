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
import maggdamessage.MaggdaMessage;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Label;
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

    public Connection(String ip) {
        name = null;
        ipAdress = ip;
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
            return false;
        }

        try {
            socket = new Socket(ipAdress, MaggdaMessage.port);
            connectionLabel.setSuccessfull(true);                   // If it reachs this point: SUCCESS!

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

            return true;

        } catch (IOException ex) {
            connectionLabel.setSuccessfull(false);
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
            while (((line = input.readUTF()) != null) && (running)) {
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
            System.out.println("[Client] Connection stopped.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void handlePONG(Command pongCommand) {
        Platform.runLater(() -> {
            connectionLabel.setText(pongCommand.getArgs()[0]);
        });

    }

    public synchronized void sendCommand(Command c) throws IOException {
        String commandAsString = c.toString();
        output.writeUTF(commandAsString);
        System.out.println("[Client] Command sent: " + commandAsString);

    }
    public void close() throws IOException {
        running = false;
        socket.close();
    }
    
    public void setConnectionName(String name) {
        Platform.runLater(()->{
            connectionLabel.setText(name);
        });
    }
    
    public String getIpAdress() {
        return ipAdress;
    }

}
