package org.drupal.config;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.drupal.settings.DrupalSettings;
import org.jetbrains.annotations.Nullable;

import java.util.BitSet;


/**
 * Persistent state configuration for Drupal Settings tab.
 *
 * @author Yaroslav Kharchenko
 */
@State(
        name = "drupalplus-configuration",
        storages = {
                @Storage(
                        file = StoragePathMacros.PROJECT_FILE
                )
                , @Storage(file = StoragePathMacros.PROJECT_CONFIG_DIR + "/drupalPlus.xml", scheme = StorageScheme.DIRECTORY_BASED)
        }
)
public class DrupalPlusConfiguration implements PersistentStateComponent<DrupalPlusConfiguration> {

    public String DRUPAL_VERSION = "Drupal 7";

    public String SCAN_SOURCES = getScanValue(true, true, true);

    public static DrupalPlusConfiguration getInstance(final Project project) {
        return ServiceManager.getService(project, DrupalPlusConfiguration.class);
    }

    @Nullable
    @Override
    public DrupalPlusConfiguration getState() {
        return this;
    }

    @Override
    public void loadState(DrupalPlusConfiguration drupalSettings) {
        XmlSerializerUtil.copyBean(drupalSettings, this);
    }

    /**
     * Return unique string value for checkboxes states.
     * Used for saving information about scanning settings.
     *
     * @param x is core chrckbox selected
     * @param y is contrib checkbox selected
     * @param z is custom checkbox selected
     * @return unique string value for checkboxes states
     */
    public static String getScanValue(boolean x, boolean y, boolean z) {
        String result = "";
        if (x) result += "1";
        else result += "0";

        if (y) result += "1";
        else result += "0";

        if (z) result += "1";
        else result += "0";

        return result;
    }
}
