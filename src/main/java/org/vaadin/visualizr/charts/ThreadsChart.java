package org.vaadin.visualizr.charts;

import java.util.Map;

import org.vaadin.visualizr.ui.VisualizrColor;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.Cursor;
import com.vaadin.addon.charts.model.DataLabels;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.HorizontalAlign;
import com.vaadin.addon.charts.model.PlotOptionsPie;
import com.vaadin.addon.charts.model.Title;
import com.vaadin.addon.charts.model.Tooltip;
import com.vaadin.addon.charts.model.VerticalAlign;
import com.vaadin.addon.charts.model.style.Style;

/**
 * System thread chart.
 */
public class ThreadsChart extends Chart {

  /**
   * Constructs the threads chart.
   * 
   * @param metrics the system metrics.
   */
  public ThreadsChart(Map<String, Object> metrics) {
    super(ChartType.PIE);

    Long threads = (Long) metrics.get("threads");
    Long daemon = (Long) metrics.get("threads.daemon");
    Long peak = (Long) metrics.get("threads.peak");
    Long totalStarted = (Long) metrics.get("threads.totalStarted");

    Configuration configuration = getConfiguration();
    
    Tooltip tooltip = new Tooltip();
    tooltip.setPointFormat("{point.y}");
    configuration.setTooltip(tooltip);    

    PlotOptionsPie plotOptions = buildPlotOptions();
    configuration.setPlotOptions(plotOptions);

    Title title = buidlTitle();
    title.setText(String.valueOf(totalStarted));
    configuration.setTitle(title);    
    
    DataSeries series = new DataSeries("Count");
    configuration.setSeries(series);
    
    DataSeriesItem threadsDataSeries = new DataSeriesItem("Threads", threads);
    threadsDataSeries.setColor(VisualizrColor.GLACIER_BLUE);
    series.add(threadsDataSeries);

    DataSeriesItem daemonDataSeries = new DataSeriesItem("Daemon threads", daemon);
    daemonDataSeries.setColor(VisualizrColor.ICE);
    series.add(daemonDataSeries);

    DataSeriesItem peakDataSeries = new DataSeriesItem("Peak threads", peak);
    peakDataSeries.setColor(VisualizrColor.WARM_GRAY);
    series.add(peakDataSeries);

    DataSeriesItem totalDataSeries = new DataSeriesItem("Total started threads", totalStarted);
    totalDataSeries.setColor(VisualizrColor.MIST);
    series.add(totalDataSeries);
    
    setSizeFull();    
  }

  private PlotOptionsPie buildPlotOptions() {
    PlotOptionsPie plotOptions = new PlotOptionsPie();
    plotOptions.setAllowPointSelect(true);
    plotOptions.setCursor(Cursor.POINTER);
    plotOptions.setInnerSize("200px");
    plotOptions.setSize("300px");
    DataLabels dataLabels = plotOptions.getDataLabels();
    dataLabels.setEnabled(true);
    dataLabels.setFormat("{point.name}: {point.y}");
    return plotOptions;
  }
  
  private Title buidlTitle() {
    Title title = new Title();
    title.setAlign(HorizontalAlign.CENTER);
    title.setVerticalAlign(VerticalAlign.MIDDLE);
    title.setY(6);
    Style titleStyle = new Style();
    titleStyle.setColor(VisualizrColor.MIST);
    title.setStyle(titleStyle);
    return title;
  }  
}
