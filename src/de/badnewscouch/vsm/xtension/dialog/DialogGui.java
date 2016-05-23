/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.badnewscouch.vsm.xtension.dialog;


import de.dfki.vsm.util.log.LOGConsoleLogger;
import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javax.swing.JFrame;

/**
 *
 * @author Patrick Gebhard
 */
public class DialogGui {
    
    private FXMLDocumentController mController;
    private Region mRootRegion;
    private Double mScaleFactor = 1.0d;
    // The JavaFX Panel
    final JFXPanel mJFXPanel = new JFXPanel();
    private JFrame mFrame;
    private DialogExecutor mExecutor;
    // Configurable Values
    private HashMap<String, String> emotions = new HashMap<>();
    // The singelton logger instance
    private final LOGConsoleLogger mLogger = LOGConsoleLogger.getInstance();
    
    public void init(DialogExecutor executor, HashMap<String, String> values) {
        mExecutor = executor;
        emotions = values;
        
        mFrame = new JFrame();
        mFrame.add(mJFXPanel);

        // Set Not Rezizable
        mFrame.setResizable(false);
        // Set Always On Top
        mFrame.setAlwaysOnTop(true);
        // Set Undecorated
        mFrame.setUndecorated(true);
        // Set Transparent
        mFrame.setBackground(new Color(0, 0, 0, 0));
        
        mFrame.setSize(600, 400);
        mFrame.setLocationRelativeTo(null);
        mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Platform.runLater(() -> initFX());
    }
    
    public void setVisible(boolean visible) {
        mFrame.setVisible(visible);
    }


    // interface for emotion
    public void setEmotion(String emotion) {
        mLogger.message("Got " + emotion);
        if (emotion.equalsIgnoreCase("emotion1")) {
            mController.emotion1.setSelected(true);
        }
        if (emotion.equalsIgnoreCase("emotion2")) {
            mController.emotion2.setSelected(true);
        }
        if (emotion.equalsIgnoreCase("emotion3")) {
            mController.emotion3.setSelected(true);
        }
        if (emotion.equalsIgnoreCase("emotion4")) {
            mController.emotion4.setSelected(true);
        }
        if (emotion.equalsIgnoreCase("emotion5")) {
            mController.emotion5.setSelected(true);
        }
        if (emotion.equalsIgnoreCase("emotion6")) {
            mController.emotion6.setSelected(true);
        }
        mFrame.repaint();
    }

    //interface for next
    public void next() {
        mController.updateListeners();
    }
    
    private void initFX() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/res/de/badnewscouch/vsm/xtension/dialog/FXMLDocument.fxml"));
        mController = new FXMLDocumentController();
        fxmlLoader.setController(mController);
        // add the VSM executor as a listener to the gui controller
        mController.addListener(mExecutor);
        
        try {
            mRootRegion = (Region) fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        // get root
        Group group = new Group(mRootRegion);

        // set root background
        mRootRegion.setStyle("-fx-background-color: #00000010;");

        //scaling
        group.setScaleX(group.getScaleX() * mScaleFactor);
        group.setScaleY(group.getScaleY() * mScaleFactor);

        // place centered
        StackPane rootPane = new StackPane();
        rootPane.getChildren().add(group);

        // set general background, note alpha value must > 0 to ensure modal feature
        rootPane.setStyle("-fx-background-color: #FFFFFF00;");
        

        // build scene 
        Scene scene = new Scene(rootPane,300,300);
        //scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        mJFXPanel.setScene(scene);

        // do personalisation
        for (String key : emotions.keySet()) {
            if (key.equalsIgnoreCase("emotion1")) {
                mController.emotion1.setText(emotions.get(key));
            }
            if (key.equalsIgnoreCase("emotion2")) {
                mController.emotion2.setText(emotions.get(key));
            }
            if (key.equalsIgnoreCase("emotion3")) {
                mController.emotion3.setText(emotions.get(key));
            }
            if (key.equalsIgnoreCase("emotion4")) {
                mController.emotion4.setText(emotions.get(key));
            }
            if (key.equalsIgnoreCase("emotion5")) {
                mController.emotion5.setText(emotions.get(key));
            }
            if (key.equalsIgnoreCase("emotion6")) {
                mController.emotion6.setText(emotions.get(key));
            }
        }
    }
}
