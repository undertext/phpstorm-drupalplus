package org.drupal.config;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.drupal.settings.DrupalSettings;
import org.jetbrains.annotations.Nullable;

import java.util.BitSet;


/**
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
    public int SCAN_SOURCES = getScanValue(true,true,true);

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

    public static int getScanValue(boolean x, boolean y, boolean z){
        int result=0;
        if (x) result+=100;
        if (y) result+=10;
        if (z) result+=1;
        return result;
    }
}
