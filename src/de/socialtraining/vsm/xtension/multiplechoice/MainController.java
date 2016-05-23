/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.socialtraining.vsm.xtension.multiplechoice;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
public class MainController implements Initializable {

    @FXML
    protected Button mc_a;
	
    @FXML
    protected Button mc_b;
	
    @FXML
    protected Button mc_c;

    // internal variables

    // listeners for updates
    private ArrayList<MultiplechoiceListener> mListeners = new java.util.ArrayList();
    // collected values
    private HashMap<String, String> mValues = new HashMap();

    public void addListener(MultiplechoiceListener listener) {
        if (!mListeners.contains(listener)) {
            mListeners.add(listener);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mValues.put("mc_a", "multiplechoice a");
		mValues.put("mc_b", "multiplechoice a");
		mValues.put("mc_c", "multiplechoice a");

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

            }
        });

    }
    
    protected void updateListeners() {
        for (MultiplechoiceListener l : mListeners) {
            l.updateOnMultiplechoice(mValues);
        }
    }

}
