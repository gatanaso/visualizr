package org.vaadin.visualizr.grids;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Grid;

/**
 * Application environment information.
 */
public class EnvironmentInfoGrid extends Grid {

  public EnvironmentInfoGrid(Map<String, Object> environmentDataMap) {
    
    List<EnvironmentInfoItem> environmentProperties = getEnvironmentProperteis(environmentDataMap);    

    BeanItemContainer<EnvironmentInfoItem> container = 
      new BeanItemContainer<EnvironmentInfoItem>(EnvironmentInfoItem.class, environmentProperties);
    
    setContainerDataSource(container);
    setSelectionMode(SelectionMode.NONE);
    setSizeFull();

  }

  private List<EnvironmentInfoItem> getEnvironmentProperteis(Map<String, Object> environmentDataMap) {

    Map<String, String> systemPropertiesMap = 
      (Map<String, String>) environmentDataMap.get("systemProperties");
    
    List<EnvironmentInfoItem> environmentProperties = 
      convertToListOfInfoItems(systemPropertiesMap);
    
    Map<String, String> systemEnvironmentMap = 
      (Map<String, String>) environmentDataMap.get("systemEnvironment");
    
    environmentProperties.addAll(convertToListOfInfoItems(systemEnvironmentMap));

    Map<String, String> refreshMap = 
      (Map<String, String>) environmentDataMap.get("refresh");

    environmentProperties.addAll(convertToListOfInfoItems(refreshMap));
    return environmentProperties;
  }

  private List<EnvironmentInfoItem> convertToListOfInfoItems(Map<String, String> propertyMap) {
    return propertyMap.entrySet().stream()
      .map(entry -> new EnvironmentInfoItem(entry.getKey(), entry.getValue()))
      .collect(Collectors.toList());
  }
  
}
