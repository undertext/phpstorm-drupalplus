package org.drupal.settings;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;


import javax.swing.*;

/**
 * Plugin setting form.
 *
 * @author Yaroslav Kharchenko
 */
public class DrupalSettings implements Configurable {
    private JPanel mainPanel;

    @Nls
    @Override
    public String getDisplayName() {
        return "Drupal Plus Settings";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return "preferences.lookFeel";
    }

    @Nullable
    @Override
    public JComponent createComponent() {


        return (JComponent) mainPanel;
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {

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
