package org.vaadin.visualizr.charts;

import java.util.Map;

import org.vaadin.visualizr.ui.VisualizrColor;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.AxisTitle;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.PlotOptionsSeries;
import com.vaadin.addon.charts.model.Tooltip;
import com.vaadin.addon.charts.model.VerticalAlign;
import com.vaadin.addon.charts.model.YAxis;

/**
 * System non-heap memory chart.
 */
public class NonHeapMemoryChart extends Chart {

  /**
   * Constructs the system non-heap chart.
   * 
   * @param metrics
   *          the system metrics.
   */
  public NonHeapMemoryChart(Map<String, Object> metrics) {
    super(ChartType.BAR);

    Configuration configuration = getConfiguration();
    configuration.setTitle("");

    Tooltip tooltip = new Tooltip();
    tooltip.setFormatter("this.series.name +': '+ this.y +' MB'");
    configuration.setTooltip(tooltip);

    YAxis y = new YAxis();
    y.setMin(0);
    AxisTitle title = new AxisTitle("Nonheap memory (MB)");
    title.setAlign(VerticalAlign.MIDDLE);
    y.setTitle(title);
    configuration.addyAxis(y);

    Long nonheap = (Long) metrics.get("nonheap");
    Long used = (Long) metrics.get("nonheap.used");
    Long committed = (Long) metrics.get("nonheap.committed");
    Long init = (Long) metrics.get("nonheap.init");

    DataSeries series = new DataSeries("Nonheap");
    DataSeriesItem nonheapDataSeries = new DataSeriesItem("Nonheap", nonheap / 1000.0f);
    series.add(nonheapDataSeries);
    PlotOptionsSeries options = new PlotOptionsSeries();
    options.setColor(VisualizrColor.GLACIER_BLUE);
    series.setPlotOptions(options);
    configuration.addSeries(series);

    series = new DataSeries("Used");
    DataSeriesItem usedDataSeries = new DataSeriesItem("Used", used / 1000.0f);
    series.add(usedDataSeries);
    options = new PlotOptionsSeries();
    options.setColor(VisualizrColor.ICE);
    series.setPlotOptions(options);
    configuration.addSeries(series);

    series = new DataSeries("Committed");
    DataSeriesItem committedDataSeries = new DataSeriesItem("Committed", committed / 1000.0f);
    series.add(committedDataSeries);
    options = new PlotOptionsSeries();
    options.setColor(VisualizrColor.WARM_GRAY);
    series.setPlotOptions(options);
    configuration.addSeries(series);

    series = new DataSeries("Init");
    DataSeriesItem initDataSeries = new DataSeriesItem("Init", init / 1000.0f);
    series.add(initDataSeries);
    options = new PlotOptionsSeries();
    options.setColor(VisualizrColor.STONE);
    series.setPlotOptions(options);
    configuration.addSeries(series);

    setSizeFull();
  }
}
