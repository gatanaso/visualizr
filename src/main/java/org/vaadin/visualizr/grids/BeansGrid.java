package org.vaadin.visualizr.grids;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;

/**
 * Visualizes the active beans in a vaadin grid.
 */
public class BeansGrid extends Grid {
  
  private static final String BEAN_PROPERTY_ID = "bean";

  public BeansGrid(List<Map> beans) {

    List<BeanItem> appBeans = buildBeanList(beans);
    
    BeanItemContainer<BeanItem> container = 
      new BeanItemContainer<BeanItem>(BeanItem.class, appBeans);
    
    setContainerDataSource(container);
    addBeanNameFilter(container);
    setSelectionMode(SelectionMode.NONE);
    setColumns(BEAN_PROPERTY_ID);
    setSizeFull();
  }

  private void addBeanNameFilter(BeanItemContainer<BeanItem> container) {

    TextField filterField = new TextField();
    filterField.setInputPrompt("Search...");
    filterField.setHeight("25px");
    filterField.setWidth("250px");
    filterField.addTextChangeListener(change -> {
      container.removeContainerFilters(BEAN_PROPERTY_ID);
      if (!change.getText().isEmpty())
        container.addContainerFilter(new SimpleStringFilter(BEAN_PROPERTY_ID, change.getText(), true, false));      
    });

    HeaderRow filterRow = getDefaultHeaderRow();
    HeaderCell cell = filterRow.getCell(BEAN_PROPERTY_ID);
    cell.setComponent(filterField);
  }

  private List<BeanItem> buildBeanList(List<Map> beans) {
    return beans.stream()
      .map(beanObject -> {
        Map beanMap = (LinkedHashMap) beanObject;
        String bean = (String) beanMap.get(BEAN_PROPERTY_ID);
        String type = (String) beanMap.get("type");
        String scope = (String) beanMap.get("scope");
        return new BeanItem(bean, type, scope);
      })
      .sorted()
      .collect(Collectors.toList());
  }

}
