/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.badnewscouch.vsm.xtension.dialog;

import de.badnewscouch.vsm.messages.Message;
import de.dfki.vsm.model.config.ConfigFeature;
import de.dfki.vsm.model.project.PluginConfig;
import de.dfki.vsm.model.scenescript.ActionFeature;
//import de.dfki.vsm.runtime.RunTimeInstance;
import de.dfki.vsm.runtime.activity.AbstractActivity;
import de.dfki.vsm.runtime.activity.SpeechActivity;
import de.dfki.vsm.runtime.activity.executor.ActivityExecutor;
import de.dfki.vsm.runtime.activity.scheduler.ActivityWorker;
import de.dfki.vsm.runtime.interpreter.value.AbstractValue;
import de.dfki.vsm.runtime.interpreter.value.BooleanValue;
import de.dfki.vsm.runtime.interpreter.value.IntValue;
import de.dfki.vsm.runtime.interpreter.value.StringValue;
import de.dfki.vsm.runtime.interpreter.value.StructValue;
import de.dfki.vsm.runtime.project.RunTimeProject;
import de.dfki.vsm.util.log.LOGConsoleLogger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import javafx.application.Platform;
import javax.swing.SwingUtilities;

/**
 *
 * @author Patrick Gebhard
 */
public class DialogExecutor extends ActivityExecutor implements QuestionnaireListener {

    // The GUI
    DialogGui mDialogGui;
    RunTimeProject mRunTimeProject;
    // The current ActivityWorker
    ActivityWorker mActivityWorker = null;
    private final HashSet<ActivityWorker> mActivityWorkers = new HashSet<>();
    // Configuration values
    private final HashMap<String, String> emotions = new HashMap<>();
    // The singelton logger instance
    private final LOGConsoleLogger mLogger = LOGConsoleLogger.getInstance();

    public DialogExecutor(PluginConfig config, RunTimeProject project) {
        super(config, project);
        mRunTimeProject = project;
        Message.putMessages();
    }

    @Override
    public String marker(long id) {
        return "$(" + id + ")";
    }

    @Override
    public void execute(AbstractActivity activity) {
        
            final String name = activity.getName();
            System.out.println("de.dfki.vsm.xtension.questionnaire.DialogExecutor.execute()   " + name);
            if (name.equalsIgnoreCase("show")) {
                mDialogGui.setVisible(true);
               
                mActivityWorker = (ActivityWorker) Thread.currentThread();
                mActivityWorkers.add(mActivityWorker);

                synchronized (mActivityWorker) {
                    while (mActivityWorkers.contains(mActivityWorker)) {
                        try {
                            mActivityWorker.wait();
                        } catch (InterruptedException exc) {
                            mLogger.failure(exc.toString());
                        }
                    }
                }
            }
            
            if(name.equalsIgnoreCase("hide"))
            {
                mDialogGui.setVisible(false);
            }


            if (name.equalsIgnoreCase("next")) {
               mDialogGui.next();
            }
        
    }

    @Override
    public void launch() {
        mDialogGui = new DialogGui();
  
        for (ConfigFeature cf : mConfig.getEntryList()) {
            emotions.put(cf.getKey(), cf.getValue());
        }

        SwingUtilities.invokeLater(() -> mDialogGui.init(this, emotions));
        FXMLDocumentController.sDialogGui = mDialogGui;
        FXMLDocumentController.sRuntimeProject = mRunTimeProject;
    }

    @Override
    public void unload() {
        // nothing
    }

    @Override
    public void updateOnUestionnaire(HashMap<String, String> uservalues) 
    {
        // Hide the Dialog window
        mDialogGui.setVisible(false);
        // End the Activity Worker
        synchronized (mActivityWorker) {
            mActivityWorkers.clear();
            mActivityWorker.notifyAll();
        }
    }
}
