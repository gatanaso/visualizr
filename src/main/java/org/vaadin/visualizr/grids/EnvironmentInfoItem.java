package org.vaadin.visualizr.grids;

/**
 * Environment info item.
 */
public class EnvironmentInfoItem {
  
  private String caption;
  private String value;
  
  public EnvironmentInfoItem(String caption, String value) {
    this.caption = caption;
    this.value = value;
  }

  public String getCaption() {
    return caption;
  }

  public void setCaption(String caption) {
    this.caption = caption;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

}
