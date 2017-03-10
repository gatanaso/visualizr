package org.vaadin.visualizr.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.BeansEndpoint;
import org.springframework.boot.actuate.endpoint.EnvironmentEndpoint;
import org.springframework.boot.actuate.endpoint.HealthEndpoint;
import org.springframework.boot.actuate.endpoint.MetricsEndpoint;
import org.springframework.boot.actuate.health.Health;
import org.vaadin.visualizr.charts.DiskChart;
import org.vaadin.visualizr.charts.HeapMemoryChart;
import org.vaadin.visualizr.charts.MemroyChart;
import org.vaadin.visualizr.charts.NonHeapMemoryChart;
import org.vaadin.visualizr.charts.ThreadsChart;
import org.vaadin.visualizr.grids.BeansGrid;
import org.vaadin.visualizr.grids.EnvironmentInfoGrid;

import com.vaadin.addon.charts.Chart;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

/**
 * The visualizr dashboard.
 */
@StyleSheet({"bootstrap.css", "visualizr.css"})
@SpringView(name = "${visualizr.endpoint}")
public class DashboardView extends CssLayout implements View {

  private HealthEndpoint healthEndpoint;
  private MetricsEndpoint metricsEndpoint;
  private BeansEndpoint beansEndpoint;
  private EnvironmentEndpoint environmentEndpoint;

  @Autowired
  public DashboardView(
      HealthEndpoint healthEndpoint,
      MetricsEndpoint metricsEndpoint,
      BeansEndpoint beansEndpoint, 
      EnvironmentEndpoint environmentEndpoint) {
    
    this.healthEndpoint = healthEndpoint;
    this.metricsEndpoint = metricsEndpoint;
    this.beansEndpoint = beansEndpoint;
    this.environmentEndpoint = environmentEndpoint;
    setPrimaryStyleName("container-fluid");
  }

  @Override
  public void enter(ViewChangeEvent event) {
    buildHealthStatisticsComponents();
    buildCharts();
  }

  private void buildHealthStatisticsComponents() {

    Map<String, Object> metrics = metricsEndpoint.invoke();

    Long uptime = (Long) metrics.get("uptime");
    String formattedUptime = formatUptime(uptime);
    
    DashboardCard serverUptime = new 
      DashboardCard(buildStatus("Server started " + formattedUptime, true));
    serverUptime.addStyleName("label");
    
    Long instanceUptimeValue = (Long) metrics.get("instance.uptime");
    String formattedInstanceUptime = formatUptime(instanceUptimeValue);

    DashboardCard instanceUptime = new 
        DashboardCard(buildStatus("Instance started " + formattedInstanceUptime, true));
    instanceUptime.addStyleName("label");

    long successRequests = 
      metrics.entrySet().stream()
        .filter(entry -> entry.getKey().startsWith("counter.status.2"))
        .mapToLong(entry -> (long) entry.getValue())
        .sum();

    DashboardCard totalOkRequests = 
        new DashboardCard(buildStatus(String.format("Success requests (2xx): %s", successRequests), true));
    totalOkRequests.addStyleName("label");

    long failedRequests = 
      metrics.entrySet().stream()
        .filter(entry -> entry.getKey().startsWith("counter.status.4"))
        .mapToLong(entry -> (long) entry.getValue())
        .sum();
    
    DashboardCard totalBadRequests = 
      new DashboardCard(buildStatus(String.valueOf(String.format("Bad requests (4xx): %s", failedRequests)), false));
    totalBadRequests.addStyleName("label");
    
    DashboardRow row1 = new DashboardRow();
    row1.addComponents(serverUptime, instanceUptime);
    
    DashboardRow row2 = new DashboardRow();
    row2.addComponents(totalOkRequests, totalBadRequests);    
    
    addComponents(row1, row2);
  }

  private String formatUptime(Long uptime) {
    PrettyTime prettyInstanceUptime = new PrettyTime(new Date(0 + uptime));
    return prettyInstanceUptime.format(new Date(0));
  }

  private Component buildStatus(String text, boolean status) {
    Label value = new Label(text);
    String statusValueClass = status == true ? "success" : "failure";
    value.addStyleName(statusValueClass);
    return value;
  }

  private void buildCharts() {

    Map<String, Object> metrics = metricsEndpoint.invoke();
    
    Chart memoryChart = new MemroyChart(metrics);
    DashboardCard memory = new DashboardCard("Memory usage", memoryChart);
    
    DashboardCard beansGrid = buildBeansGrid();

    Health applicationHealth = healthEndpoint.invoke();
    Health diskHealth = (Health) applicationHealth.getDetails().get("diskSpace");
    Chart diskSpace = new DiskChart(diskHealth.getDetails());
    DashboardCard disk = new DashboardCard("Disk space", diskSpace);

    Chart heapChart = new HeapMemoryChart(metrics);
    DashboardCard heap = new DashboardCard("Heap memory", heapChart);

    Chart nonHeapChart = new NonHeapMemoryChart(metrics);
    DashboardCard nonHeap = new DashboardCard("Non-heap memory", nonHeapChart);
    
    Map<String, Object> environmentDataMap = environmentEndpoint.invoke();
    EnvironmentInfoGrid envData = new EnvironmentInfoGrid(environmentDataMap);
    DashboardCard environmentInformation = new DashboardCard("Environment Information", envData);

    Chart threadsChart = new ThreadsChart(metrics);
    DashboardCard threads = new DashboardCard("Threads", threadsChart);
    
    DashboardRow row1 = new DashboardRow();
    row1.addComponents(memory, beansGrid);
    
    DashboardRow row2 = new DashboardRow();
    row2.addComponents(disk, heap);
    
    DashboardRow row3 = new DashboardRow();
    row3.addComponents(environmentInformation, threads);
    
    addComponents(row1, row2, row3);
  }

  private DashboardCard buildBeansGrid() {
    // re-factor
    List<Object> beansData = this.beansEndpoint.invoke();
    Map beansMap = (LinkedHashMap) beansData.get(0);
    List<Map> beans = (ArrayList) beansMap.get("beans");
    BeansGrid beansGrid = new BeansGrid(beans);
    
    return new DashboardCard("Application Beans", beansGrid);
  }  

}
