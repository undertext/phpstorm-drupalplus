package org.drupal.settings;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.progress.util.DispatchThreadProgressWindow;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Comparing;
import com.intellij.ui.ListCellRendererWrapper;
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
        final DrupalPlusConfiguration configuration = DrupalPlusConfiguration.getInstance(project);

        if (mainPanel == null) {
            return false;
        }

        if (!cbDrupalVersion.getSelectedItem().equals(configuration.DRUPAL_VERSION)) {
            return true;
        }

        if (!getCheckboxesStatus().equals(configuration.SCAN_SOURCES)) {
            return true;
        }
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {
        final DrupalPlusConfiguration configuration = DrupalPlusConfiguration.getInstance(project);
        configuration.DRUPAL_VERSION = (String) cbDrupalVersion.getSelectedItem();
        configuration.SCAN_SOURCES = getCheckboxesStatus();
    }

    @Override
    public void reset() {
        final DrupalPlusConfiguration configuration = DrupalPlusConfiguration.getInstance(project);
        String checkboxesStatus = configuration.SCAN_SOURCES;
        if (checkboxesStatus.charAt(0) == '1') {
            coreModulesCheckBox.setSelected(true);
        } else {
            coreModulesCheckBox.setSelected(false);
        }

        if (checkboxesStatus.charAt(1) == '1') {
            contribModulesCheckBox.setSelected(true);
        } else {
            contribModulesCheckBox.setSelected(false);
        }

        if (checkboxesStatus.charAt(2) == '1') {
            customModulesCheckBox.setSelected(true);
        } else {
            customModulesCheckBox.setSelected(false);
        }

    }

    @Override
    public void disposeUIResources() {

    }

    public Icon getIcon() {
        return null;

    }

    /**
     * Wrapper function to get checkboxes state information.
     *
     * @return unique string value for checkboxes states
     */
    private String getCheckboxesStatus() {
        boolean coreLevel = false;
        boolean contribLevel = false;
        boolean customLevel = false;
        if (coreModulesCheckBox.isSelected()) coreLevel = true;
        if (contribModulesCheckBox.isSelected()) contribLevel = true;
        if (customModulesCheckBox.isSelected()) customLevel = true;
        return DrupalPlusConfiguration.getScanValue(coreLevel, contribLevel, customLevel);
    }
}
