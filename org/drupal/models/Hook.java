package org.drupal.models;

/**
 * Basic information about Drupal hook.
 *
 * @author Yaroslav Kharchenko
 */
public class Hook {

    public Hook(){

     }

    public Hook(String name){
        this.name=name;
    }

    //Name of the hook
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
