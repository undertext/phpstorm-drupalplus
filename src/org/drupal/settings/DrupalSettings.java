package org.drupal.settings;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.progress.util.DispatchThreadProgressWindow;
import com.intellij.openapi.project.Project;
import org.drupal.bundle.DrupalPlusBundle;
import org.drupal.config.DrupalPlusConfiguration;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;


import javax.swing.*;

/**
 * Plugin setting form.
 *
 * @author Yaroslav Kharchenko
 */
public class DrupalSettings implements Configurable {

    private final Project project;

    private JPanel mainPanel;
    private JComboBox cbDrupalVersion;
    private JCheckBox coreModulesCheckBox;
    private JCheckBox contribModulesCheckBox;
    private JCheckBox customModulesCheckBox;

    /**
     * Invoked by reflection
     */
    public DrupalSettings(Project project) {
        this.project = project;
    }

    @Nls
    @Override
    public String getDisplayName() {
        return DrupalPlusBundle.message("drupal.settings");
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return "preferences.lookFeel";
    }

    @Nullable
    @Override
    public JComponent createComponent() {

        cbDrupalVersion.addItem(DrupalPlusBundle.message("drupal.settings.version.drupal7"));
        return (JComponent) mainPanel;
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {

        boolean coreLevel=false;
        boolean contribLevel=false;
        boolean customLevel=false;

        final DrupalPlusConfiguration configuration = DrupalPlusConfiguration.getInstance(project);
        configuration.DRUPAL_VERSION=(String)cbDrupalVersion.getSelectedItem();
        if (coreModulesCheckBox.isSelected()) coreLevel=true;
        if (contribModulesCheckBox.isSelected()) contribLevel=true;
        if (customModulesCheckBox.isSelected()) customLevel=true;
        configuration.SCAN_SOURCES=DrupalPlusConfiguration.getScanValue(coreLevel,contribLevel,customLevel);
    }

    @Override
    public void reset() {

    }

    @Override
    public void disposeUIResources() {

    }

    public Icon getIcon() {
        return null;

    }
}
