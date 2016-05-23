package de.socialtraining.vsm.xtension.multiplechoice;

import de.dfki.vsm.model.config.ConfigFeature;
import de.dfki.vsm.model.project.PluginConfig;
import de.dfki.vsm.model.scenescript.ActionFeature;
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
import javax.swing.SwingUtilities;

/**
 *
 * @author Patrick Gebhard
 * @modified Social Training group
 */
public class MultiplechoiceExecutor extends ActivityExecutor implements MultiplechoiceListener {

    // The GUI
    MultiplechoiceGUI multiplechoiceGUI;
    // The current ActivityWorker
    ActivityWorker mActivityWorker = null;
    private final HashSet<ActivityWorker> mActivityWorkers = new HashSet<>();
    // Configuration values
    private final HashMap<String, String> mPersonalValues = new HashMap<>();
    // The singelton logger instance
    private final LOGConsoleLogger mLogger = LOGConsoleLogger.getInstance();

    public MultiplechoiceExecutor(PluginConfig config, RunTimeProject project) {
        super(config, project);
    }

    @Override
    public String marker(long id) {
        return "$(" + id + ")";
    }

    @Override
    public void execute(AbstractActivity activity) {
        /*if (activity instanceof SpeechActivity) {
            SpeechActivity sa = (SpeechActivity) activity;
            String text = sa.getTextOnly("$(").trim();
            LinkedList<String> timemarks = sa.getTimeMarks("$(");

            // If text is empty - assume activity has empty text but has marker activities registered
            if (text.isEmpty()) {
                for (String tm : timemarks) {
                    mLogger.warning("Directly executing activity at timemark " + tm);
                    mProject.getRunTimePlayer().getActivityScheduler().handle(tm);
                }
            }
        } else {
        */
            final String name = activity.getName();
            mLogger.warning("MULTIPLE CHOICE received action: " + name);
                    
            if (name.equalsIgnoreCase("show")) {
                multiplechoiceGUI.setVisible(true);
            }

            if (name.equalsIgnoreCase("hide")) {
                multiplechoiceGUI.setVisible(false);
            }
            
            if (name.equalsIgnoreCase("set")) {
                for (ActionFeature af : activity.getFeatureList()) {
                    if (af.getKey().equalsIgnoreCase("a")) {
                        multiplechoiceGUI.getController().mc_a.setText(af.getVal());
                        multiplechoiceGUI.getController().mc_a.setVisible(true);
                    }
                    else if (af.getKey().equalsIgnoreCase("b")) {
                        multiplechoiceGUI.getController().mc_b.setText(af.getVal());
                        multiplechoiceGUI.getController().mc_b.setVisible(true);
                    }
                    else if (af.getKey().equalsIgnoreCase("c")) {
                        multiplechoiceGUI.getController().mc_c.setText(af.getVal());
                        multiplechoiceGUI.getController().mc_c.setVisible(true);
                    }
                }
            }
            
            if (name.equalsIgnoreCase("wait")) {
                // wait until we got feedback
                mLogger.message("ActivityWorker for Multiplechoice waiting ....");
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

                mLogger.message("ActivityWorker for Multiplechoice done ....");
            }

            /*
            if (name.equalsIgnoreCase("name")) {
                for (ActionFeature af : activity.getFeatureList()) {
                    if (af.getKey().equalsIgnoreCase("value")) {
                        multiplechoiceGUI.setName(af.getVal());
                    }
                }
            }

            if (name.equalsIgnoreCase("age")) {
                for (ActionFeature af : activity.getFeatureList()) {
                    if (af.getKey().equalsIgnoreCase("value")) {
                        multiplechoiceGUI.setAge(af.getVal());
                    }
                }
            }

            if (name.equalsIgnoreCase("sex")) {
                for (ActionFeature af : activity.getFeatureList()) {
                    if (af.getKey().equalsIgnoreCase("value")) {
                        multiplechoiceGUI.setSex(af.getVal());
                    }
                }
            }

            if (name.equalsIgnoreCase("interviews")) {
                for (ActionFeature af : activity.getFeatureList()) {
                    if (af.getKey().equalsIgnoreCase("value")) {
                        multiplechoiceGUI.setJobinterviews(af.getVal());
                    }
                }
            }

            if (name.equalsIgnoreCase("strength")) {
                for (ActionFeature af : activity.getFeatureList()) {
                    if (af.getKey().equalsIgnoreCase("value")) {
                        multiplechoiceGUI.setStrength(af.getVal());
                    }
                }
            }

            if (name.equalsIgnoreCase("weakness")) {
                for (ActionFeature af : activity.getFeatureList()) {
                    if (af.getKey().equalsIgnoreCase("value")) {
                        multiplechoiceGUI.setWeakness(af.getVal());
                    }
                }
            }

            if (name.equalsIgnoreCase("next")) {
               multiplechoiceGUI.next();
            }
            */
        //}
    }

    @Override
    public void launch() {
        multiplechoiceGUI = new MultiplechoiceGUI();

        for (ConfigFeature cf : mConfig.getEntryList()) {
            mPersonalValues.put(cf.getKey(), cf.getValue());
        }

        SwingUtilities.invokeLater(() -> multiplechoiceGUI.init(this, mPersonalValues));
    }

    @Override
    public void unload() {
        // nothing
    }

    @Override
    public void updateOnMultiplechoice(HashMap<String, String> uservalues) {
        // Handle updates from the multiplechoice

        HashMap<String, AbstractValue> values = new HashMap<>();
        //values.put("name", new StringValue(uservalues.get("name")));
        //values.put("age", new IntValue(new Integer(uservalues.get("age"))));
        //values.put("sex", new StringValue(uservalues.get("sex")));

        try {
            StructValue struct = new StructValue(values);
            mProject.setVariable("multiplechoice", struct);
        } catch (Exception e) {
            // System.out.println("not running");
        }

        // Hide the multiplechoice window
        multiplechoiceGUI.setVisible(false);

        // End the Activity Worker
        synchronized (mActivityWorker) {
            mActivityWorkers.clear();
            mActivityWorker.notifyAll();
        }
    }
}
