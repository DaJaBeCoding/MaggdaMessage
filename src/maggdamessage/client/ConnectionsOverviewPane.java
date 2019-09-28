/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggdamessage.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author DavidPrivat
 */
public class ConnectionsOverviewPane extends TitledPane {
    public final static String nullCharacter = "-";
    public final static String itemSeparator = "\\|";
    public final static String connectionsPath = "/maggdamessage/connections/";
    private ObservableList<ObservableList<Connection>> connectionList;          // First list = y  snd List = x
    private ObservableList<Connection> allValidConnections;

    private GridPane grid;
    private VBox root;
    private Connector connector;

    public ConnectionsOverviewPane(String fileName, String name)  {
        setExpanded(false);
        
        setText(name);
        try {
        connectionList = readConnections(fileName);
        } catch(IOException e) {
            e.printStackTrace();
        }
        grid = new GridPane();
        grid.setGridLinesVisible(true);

        int xCounter = 0;
        int yCounter = 0;

        for (ObservableList<Connection> currList : connectionList) {
            for (Connection currConnection : currList) {
               
                if (currConnection != null) {
                    grid.add(currConnection.getConnectionLabel(), xCounter, yCounter);
                } else {
                    grid.add(new Label("----"), xCounter, yCounter);
                }

                xCounter++;
            }
            xCounter = 0;
            yCounter++;
        }
        
        connector = new Connector(allValidConnections);
        
        root = new VBox();
        root.getChildren().addAll(connector.getProgressBar(), grid);
        setContent(root);
        connector.start();
    }

    private ObservableList<ObservableList<Connection>> readConnections(String fileName) throws IOException {
        allValidConnections = FXCollections.observableArrayList();
        InputStream in = getClass().getResourceAsStream(connectionsPath + fileName);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String line = "";
        ObservableList<ObservableList<Connection>> returnList = FXCollections.observableArrayList();

        while ((line = bufferedReader.readLine()) != null) {
            ObservableList<Connection> addList = FXCollections.observableArrayList();
            String[] elements = line.split(itemSeparator);
            for (String s : elements) {
                if (s.equals("") || s.equals(nullCharacter)) {
                    addList.add(new Connection(null));
                } else {
                    Connection addConn = new Connection(s);
                    addList.add(addConn);
                    allValidConnections.add(addConn);
                }
            }
            returnList.add(addList);
        }

        return returnList;
    }
    
    public Connector getConnector() {
        return connector;
    }

}
