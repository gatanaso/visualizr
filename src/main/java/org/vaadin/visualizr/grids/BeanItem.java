package org.vaadin.visualizr.grids;

/**
 * Application bean item.
 */
public class BeanItem implements Comparable<BeanItem> {
  
  private String bean;
  private String type;
  private String scope;
  
  public BeanItem(String bean, String type, String scope) {
    this.bean = bean;
    this.type = type;
    this.scope = scope;
  }

  public String getBean() {
    return bean;
  }

  public void setBean(String bean) {
    this.bean = bean;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getScope() {
    return scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  @Override
  public int compareTo(BeanItem other) {
    return bean.compareTo(other.bean);
  }
  
}
