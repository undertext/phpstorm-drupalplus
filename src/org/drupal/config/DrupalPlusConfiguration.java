package org.drupal.config;

import com.intellij.openapi.components.PersistentStateComponent;
import org.drupal.settings.DrupalSettings;
import org.jetbrains.annotations.Nullable;


public class DrupalPlusConfiguration  implements PersistentStateComponent<DrupalSettings>{
    @Nullable
    @Override
    public DrupalSettings getState() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void loadState(DrupalSettings drupalSettings) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
