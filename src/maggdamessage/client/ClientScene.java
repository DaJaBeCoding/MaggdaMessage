/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggdamessage.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author DavidPrivat
 */
public class ClientScene extends Scene{
    private VBox root;
    private ObservableList<ConnectionsOverviewPane> connectionPanes;
    public ClientScene() {
        super(new VBox());
        root = (VBox) getRoot();
        
        ConnectionsOverviewPane firstTestPane = new ConnectionsOverviewPane("firstTest.txt", "test");
        connectionPanes = FXCollections.observableArrayList();
        connectionPanes.addAll(firstTestPane);
        
        root.getChildren().addAll(connectionPanes);
        
        
    }
    
    public ObservableList<ConnectionsOverviewPane> getConnectionPanes() {
        return connectionPanes;
    }
}
