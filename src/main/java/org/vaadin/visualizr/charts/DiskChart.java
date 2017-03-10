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
import com.vaadin.addon.charts.model.PlotOptionsPie;
import com.vaadin.addon.charts.model.Tooltip;

/**
 * Disk space chart.
 */
public class DiskChart extends Chart {

  private static final float GB_FACTOR = 1000000000.0f;

  /**
   * Constructs the disk space chart.
   * 
   * @param metrics
   *          the system metrics.
   */
  public DiskChart(Map<String, Object> metrics) {
    super(ChartType.PIE);

    Configuration configuration = getConfiguration();
    configuration.setTitle("");
    
    Tooltip tooltip = new Tooltip();
    tooltip.setPointFormat("{point.percentage:.2f}%");
    configuration.setTooltip(tooltip);

    PlotOptionsPie plotOptions = buildPlotOptions();
    configuration.setPlotOptions(plotOptions);

    DataSeries series = new DataSeries("Amount");
    configuration.setSeries(series);

    Long total = (Long) metrics.get("total");
    Long free = (Long) metrics.get("free");
    long used = total - free;

    DataSeriesItem usedSpaceDateSeries = new DataSeriesItem("Used disk space", used / GB_FACTOR);
    usedSpaceDateSeries.setColor(VisualizrColor.GLACIER_BLUE);
    series.add(usedSpaceDateSeries);

    DataSeriesItem freeSpaceDataSeries = new DataSeriesItem("Free disk space", free / GB_FACTOR);
    freeSpaceDataSeries.setColor(VisualizrColor.WARM_GRAY);
    series.add(freeSpaceDataSeries);

    setSizeFull();
  }

  private PlotOptionsPie buildPlotOptions() {
    PlotOptionsPie plotOptions = new PlotOptionsPie();
    plotOptions.setAllowPointSelect(true);
    plotOptions.setCursor(Cursor.POINTER);
    plotOptions.setShowInLegend(true);
    DataLabels dataLabels = plotOptions.getDataLabels();
    dataLabels.setEnabled(true);
    dataLabels.setFormat("{point.name}: {point.y:.2f} GB");
    return plotOptions;
  }
}
