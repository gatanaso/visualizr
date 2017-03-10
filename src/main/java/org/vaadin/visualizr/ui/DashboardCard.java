package org.vaadin.visualizr.ui;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * A dashboard card component.
 */
public class DashboardCard extends CssLayout {

  public DashboardCard(Component body) {
    setStyles();
    VerticalLayout content = new VerticalLayout();
    addComponent(content);

    content.addComponent(body);
    content.setExpandRatio(body, 1.0f);
  }
  
  public DashboardCard(String title, Component body) {
    setStyles();
    VerticalLayout content = new VerticalLayout();
    addComponent(content);

    Label titleLbl = new Label(title);
    titleLbl.addStyleName("title");

    content.addComponents(titleLbl);
    content.addComponent(body);
    content.setExpandRatio(body, 1.0f);
  }


  private void setStyles() {
    setStyleName("visualizr-card");
    addStyleName("col-lg-6");
    addStyleName("col-md-12");
    addStyleName("col-sm-12");
  }
}
