/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggdamessage.client;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 *
 * @author DavidPrivat
 */
public class ConnectionLabel extends Label{
    public final static double size = 60;
    public final static Font defaultFont = Font.font("courier new", FontWeight.NORMAL, FontPosture.ITALIC, 11);
    public final static Font successFont = Font.font("courier new",  FontWeight.BOLD, FontPosture.REGULAR, 11);
    
    public ConnectionLabel(String name) {
        super(name);
        setHover(true);
        setMinSize(size, size);
        setFont(defaultFont);
       
        setAlignment(Pos.CENTER);
        setWrapText(true);
        
        setSuccessfull(false);
    }
    
    public void setSuccessfull (boolean isSuccess) {
        if(isSuccess) {
            setFont(successFont);
            setTextFill(Color.GREEN);
        } else {
            setFont(defaultFont);
            setTextFill(Color.GRAY);
        }
    }
    
}
