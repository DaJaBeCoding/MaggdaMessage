/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggdamessage.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;

/**
 *
 * @author DavidPrivat
 */
public class Connector extends Thread {

    private ProgressBar progressBar;
    private ObservableList<Connection> connections;
    private ObservableList<Connection> openConnections;

    public Connector(ObservableList<Connection> list) {
        connections = list;
        progressBar = new ProgressBar(0);
        progressBar.setTooltip(new Tooltip("Checking connections..."));
        openConnections = FXCollections.observableArrayList();
    }

    @Override
    public void run() {

        double counter = 0;
        for (Connection connection : connections) {
            if (connection.connect()) {
                openConnections.add(connection);
                connection.start();
            }
            counter++;
            progressBar.setProgress((counter / ((double) connections.size())) * 0.5);
        }

        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }

        counter = 0;

        for (Connection connection : openConnections) {
            try {
                connection.pingServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
            counter++;
            progressBar.setProgress(0.5 + (counter / ((double) openConnections.size())) * 0.5);
        }
        progressBar.setProgress(1);
        progressBar.setTooltip(new Tooltip("All connections checked!"));

    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void closeConnections() {
        for (Connection c : openConnections) {
            try {
                c.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public ObservableList<Connection> getConnections() {
        return connections;
    }
}
