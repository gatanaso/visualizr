package org.vaadin.visualizr.charts;

import java.util.Map;

import org.vaadin.visualizr.ui.VisualizrColor;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.Cursor;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.HorizontalAlign;
import com.vaadin.addon.charts.model.PlotOptionsPie;
import com.vaadin.addon.charts.model.Title;
import com.vaadin.addon.charts.model.Tooltip;
import com.vaadin.addon.charts.model.VerticalAlign;
import com.vaadin.addon.charts.model.style.Style;

/**
 * System memory chart.
 */
public class MemroyChart extends Chart {

  private static final float FACTOR = 1000.0f;

  /**
   * Constructs the system memory chart.
   * 
   * @param metrics the system metrics.
   */  
  public MemroyChart(Map<String, Object> metrics) {
    super(ChartType.PIE);

    Long totalMemory = (Long) metrics.get("mem");
    Long freeMemory = (Long) metrics.get("mem.free");
    Long usedMemory = totalMemory - freeMemory;

    Configuration configuration = getConfiguration();

    Tooltip tooltip = new Tooltip();
    tooltip.setPointFormat("{point.percentage:.2f}%");
    configuration.setTooltip(tooltip);
    
    PlotOptionsPie plotOptions = buildPlotOptions();
    configuration.setPlotOptions(plotOptions);
    
    Title title = buidlTitle();
    String usedMemoryPercentage = String.format("%.2f%%", ((usedMemory / (double) totalMemory)) * 100);
    title.setText(usedMemoryPercentage);
    configuration.setTitle(title);
    
    DataSeries series = new DataSeries("Amount");
    configuration.setSeries(series);
    
    DataSeriesItem usedMemoryDataSeries = new DataSeriesItem("Used", usedMemory / FACTOR);
    usedMemoryDataSeries.setColor(VisualizrColor.GLACIER_BLUE);
    series.add(usedMemoryDataSeries);

    DataSeriesItem freeMemoryDataSeries = new DataSeriesItem("Free", freeMemory / FACTOR);
    freeMemoryDataSeries.setColor(VisualizrColor.WARM_GRAY);
    series.add(freeMemoryDataSeries);
    
    setSizeFull();
  }

  private PlotOptionsPie buildPlotOptions() {
    PlotOptionsPie plotOptions = new PlotOptionsPie();
    plotOptions.setAllowPointSelect(true);
    plotOptions.setCursor(Cursor.POINTER);
    plotOptions.setInnerSize("150px");
    plotOptions.setSize("300px");
    return plotOptions;
  }

  private Title buidlTitle() {
    Title title = new Title();
    title.setAlign(HorizontalAlign.CENTER);
    title.setVerticalAlign(VerticalAlign.MIDDLE);
    title.setY(6);
    Style titleStyle = new Style();
    titleStyle.setColor(VisualizrColor.GLACIER_BLUE);
    title.setStyle(titleStyle);
    return title;
  }

}
