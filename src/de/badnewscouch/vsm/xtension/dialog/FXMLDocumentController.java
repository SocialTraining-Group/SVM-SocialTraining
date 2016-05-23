/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.badnewscouch.vsm.xtension.dialog;

import de.badnewscouch.vsm.messages.Message;
import de.dfki.vsm.runtime.project.RunTimeProject;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

/**
 * FXML Controller class
 *
 * @author Patrick Gebhard
 */
public class FXMLDocumentController implements Initializable {

    public static DialogGui sDialogGui;
    public static RunTimeProject sRuntimeProject;
    
    @FXML
    protected Button nextbutton;
    @FXML
    protected CheckBox emotion1;
    @FXML
    protected CheckBox emotion2;
    @FXML
    protected CheckBox emotion3;
    @FXML
    protected CheckBox emotion4;
    @FXML
    protected CheckBox emotion5;
    @FXML
    protected CheckBox emotion6;
    @FXML
    protected CheckBox textCheckBox1;
    @FXML
    protected CheckBox textCheckBox2;
    @FXML
    protected CheckBox textCheckBox3;
    @FXML
    protected Label text1;
    @FXML
    protected Label text2;
    @FXML
    protected Label text3;

    // listeners for updates
    private ArrayList<QuestionnaireListener> mListeners = new java.util.ArrayList();

    @FXML
    protected TextArea emotionsregion;

    public void addListener(QuestionnaireListener listener) {
        if (!mListeners.contains(listener)) {
            mListeners.add(listener);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        disableEmotionsCheckBoxes();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                nextbutton.setDisable(true);
                
                ArrayList<String> mess = Message.messages.get(Message.sCounter);
                text1.setText(mess.get(0));
                text2.setText(mess.get(1));
                text3.setText(mess.get(2));
                
                Message.incrementCounter();
            }
        });

    }
    
    protected void updateListeners() {
        for (QuestionnaireListener l : mListeners) {
            l.updateOnUestionnaire(null);
        }
    }

    @FXML
    private void nextbuttoncheck(MouseEvent event) {
        //sRuntimeProject.setVariable("Beka", 100);
        Platform.runLater(new Runnable()
        {
            @Override
            public void run() 
            {
                System.err.println(Message.sCounter);
                if(Message.isTextLeft())
                {
                    ArrayList<String> mess = Message.messages.get(Message.sCounter);
                    text1.setText(mess.get(0));
                    text2.setText(mess.get(1));
                    text3.setText(mess.get(2)); 
                    Message.incrementCounter();
                }
                deselectCheckBoxes();
                nextbutton.setDisable(true);
                disableEmotionsCheckBoxes();
                
                sDialogGui.setVisible(false);
            }
            
        });
        updateListeners();
    }
    
    @FXML
    private void handleCheckBox1()
    {
        if(textCheckBox2.isSelected())
            textCheckBox2.setSelected(false);
        if(textCheckBox3.isSelected())
            textCheckBox3.setSelected(false);
        sRuntimeProject.setVariable("Beka", 1);
            enableEmotionsCheckBoxes();   
    }
    
     @FXML
    private void handleCheckBox2()
    {
        if(textCheckBox1.isSelected())
            textCheckBox1.setSelected(false);
        if(textCheckBox3.isSelected())
            textCheckBox3.setSelected(false);
        sRuntimeProject.setVariable("Beka", 2);
            enableEmotionsCheckBoxes();   
    }
    
      @FXML
    private void handleCheckBox3()
    {
        if(textCheckBox1.isSelected())
            textCheckBox1.setSelected(false);
        if(textCheckBox2.isSelected())
            textCheckBox2.setSelected(false);
        sRuntimeProject.setVariable("Beka", 3);
            enableEmotionsCheckBoxes();   
    }

    @FXML
    private void handleEmotion1() 
    {
        nextbutton.setDisable(false);
    }

    @FXML
    private void handleEmotion2() 
    {
        nextbutton.setDisable(false);
    }

    @FXML
    private void handleEmotion3() 
    {
       nextbutton.setDisable(false); 
    }

    @FXML
    private void handleEmotion4() 
    {
        nextbutton.setDisable(false);
    }

    @FXML
    private void handleEmotion5() 
    {
        nextbutton.setDisable(false);
    }

    @FXML
    private void handleEmotion6() 
    {
        nextbutton.setDisable(false);
    }
    
    private void enableEmotionsCheckBoxes()
    {
        Platform.runLater(new Runnable() 
        {
            @Override
            public void run() 
            {
               emotion1.setDisable(false);
               emotion2.setDisable(false);
               emotion3.setDisable(false);
               emotion4.setDisable(false);
               emotion5.setDisable(false);
               emotion6.setDisable(false);
            }
            
        });
    }
    
    private void disableEmotionsCheckBoxes()
    {
        Platform.runLater(new Runnable() 
        {
            @Override
            public void run() 
            {
               emotion1.setDisable(true);
               emotion2.setDisable(true);
               emotion3.setDisable(true);
               emotion4.setDisable(true);
               emotion5.setDisable(true);
               emotion6.setDisable(true);
            }
            
        });
    }
    
    private void deselectCheckBoxes()
    {
        if(emotion1.isSelected())
            emotion1.setSelected(false);
        if(emotion2.isSelected())
            emotion2.setSelected(false);
        if(emotion3.isSelected())
            emotion3.setSelected(false);
        if(emotion4.isSelected())
            emotion4.setSelected(false);
        if(emotion5.isSelected())
            emotion5.setSelected(false);
        if(emotion6.isSelected())
            emotion6.setSelected(false);
        if(textCheckBox1.isSelected())
            textCheckBox1.setSelected(false);
        if(textCheckBox2.isSelected())
            textCheckBox2.setSelected(false);
        if(textCheckBox3.isSelected())
            textCheckBox3.setSelected(false);
    }

    
}
