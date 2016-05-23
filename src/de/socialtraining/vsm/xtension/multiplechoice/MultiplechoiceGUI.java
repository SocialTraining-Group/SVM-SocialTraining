package de.socialtraining.vsm.xtension.multiplechoice;

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
 * @modified Social Training group
 */
public class MultiplechoiceGUI {
    
    private MainController mController;
    private Region mRootRegion;
    private Double mScaleFactor = 1.5d;
    // The JavaFX Panel
    final JFXPanel mJFXPanel = new JFXPanel();
    private JFrame mFrame;
    private MultiplechoiceExecutor mExecutor;
    // Configurable Values
    private HashMap<String, String> mPersonalValues = new HashMap<>();
    // The singelton logger instance
    private final LOGConsoleLogger mLogger = LOGConsoleLogger.getInstance();
    
    public void init(MultiplechoiceExecutor executor, HashMap<String, String> values) {
        mExecutor = executor;
        mPersonalValues = values;
        
        mFrame = new JFrame("Multiple Choice Selection");
        mFrame.add(mJFXPanel);

        // Set Not Rezizable
        mFrame.setResizable(false);
        // Set Always On Top
        mFrame.setAlwaysOnTop(true);
        // Set Undecorated
        mFrame.setUndecorated(true);
        // Set Transparent
        mFrame.setBackground(new Color(0, 0, 0, 0));
        
        mFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mFrame.setLocationRelativeTo(null);
        mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        mLogger.success("MultiplechoiceExecutor init done");
        
        Platform.runLater(() -> initFX());
    }
    
    public void setVisible(boolean visible) {
        mFrame.setVisible(visible);
    }

    //interface for next
    public MainController getController() {
        return mController;
    }
    
    private void initFX() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/res/de/socialtraining/vsm/xtension/multiplechoice/Main.fxml"));
        mController = new MainController();
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
        Scene scene = new Scene(rootPane);
        scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        mJFXPanel.setScene(scene);
        
        //mFrame.setVisible(true);
        
        mLogger.success("MultiplechoiceExecutor initFX done");
    }
}
