package io.owen.plugin.easycomment.settings;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by owen_q on 21/02/2019.
 */
public class ProjectSettingsPage implements SearchableConfigurable, Configurable.NoScroll {
    private JPanel myGeneralPanel;
    private JPanel myLombokPanel;
    private JPanel myECPannel;

//    private JCheckBox myEnableLombokInProject;
//
//    private JCheckBox myEnableValSupport;
//    private JCheckBox myEnableBuilderSupport;
//    private JCheckBox myEnableLogSupport;
//    private JCheckBox myEnableConstructorSupport;
//    private JCheckBox myEnableDelegateSupport;
//    private JPanel mySettingsPanel;
//    private JCheckBox myEnableLombokVersionWarning;
//    private JCheckBox myMissingLombokWarning;
//    private JPanel mySupportPanel;

    private JTextArea commentFormatInput;

    private PropertiesComponent myPropertiesComponent;

    private PluginStateManager stateManager;

    public ProjectSettingsPage() {
        this.stateManager = PluginStateManager.getInstance();
    }

    public ProjectSettingsPage(PropertiesComponent myPropertiesComponent) {
        this.myPropertiesComponent = myPropertiesComponent;
        this.stateManager = PluginStateManager.getInstance();
    }

    private void init(){
//        this.stateManager = PluginStateManager.getInstance();

        commentFormatInput.setText(this.stateManager.getFormat());

//        myEnableConstructorSupport.setSelected(ProjectSettings.isEnabled(myPropertiesComponent, ProjectSettings.IS_CONSTRUCTOR_ENABLED));
//
//        myEnableLombokVersionWarning.setSelected(ProjectSettings.isEnabled(myPropertiesComponent, ProjectSettings.IS_LOMBOK_VERSION_CHECK_ENABLED, false));
//        myMissingLombokWarning.setSelected(ProjectSettings.isEnabled(myPropertiesComponent, ProjectSettings.IS_MISSING_LOMBOK_CHECK_ENABLED, false));
    }

    @NotNull
    @Override
    public String getId() {
        return getDisplayName();
    }

    @Nullable
    @Override
    public Runnable enableSearch(String option) {
        return null;
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "EasyComment Plugin";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        init();

        return myGeneralPanel;
    }

    @Override
    public boolean isModified() {
        return !(commentFormatInput.getText().equals(this.stateManager.getFormat()));
    }

    @Override
    public void apply() throws ConfigurationException {
        this.stateManager.setFormat(commentFormatInput.getText());
    }

    @Override
    public void reset() {
        init();
    }

    @Override
    public void disposeUIResources() {
    }
}
