package us.ascendtech.client.views.charts;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.annotations.component.Data;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import com.axellience.vuegwt.core.client.tools.JsUtils;
import elemental2.core.JsArray;
import elemental2.dom.DomGlobal;
import jsinterop.annotations.JsMethod;
import jsinterop.base.JsPropertyMap;
import us.ascendtech.highcharts.client.ChartOptions;
import us.ascendtech.highcharts.client.Highcharts;
import us.ascendtech.highcharts.client.SVGRenderer;
import us.ascendtech.highcharts.client.chartoptions.Title;
import us.ascendtech.highcharts.client.chartoptions.chart.Chart;
import us.ascendtech.highcharts.client.chartoptions.chart.ChartEvents;
import us.ascendtech.highcharts.client.chartoptions.series.Series;
import us.ascendtech.highcharts.client.chartoptions.shared.SeriesTypes;

@Component
public class ChartComponent implements IsVueComponent {

	@Data
	ChartOptions chartOptions;
	private Highcharts columnChart;

	@JsMethod
	public void createChart() {

		ChartEvents events = new ChartEvents();
		events.setClick(clickEvent -> columnChart.redraw(true));
		events.setRedraw(() -> DomGlobal.console.log("Redraw called!"));

		chartOptions = new ChartOptions();
		chartOptions.setChart(new Chart().setType(SeriesTypes.COLUMN.getSeriesType()).setEvents(events));
		chartOptions.setSeries(new JsArray<>(new Series().setData(new JsArray<>(29.9, 71.5, 106.4, 129.2, 144.0)).setStacking("normal").setStack("0"),
				new Series().setData(new JsArray<>(30, 176.0, 135.6, 148.5, 216.4)).setStacking("normal").setStack("0"),
				new Series().setData(new JsArray<>(106.4, 129.2, 144.0, 29.9, 71.5)).setStacking("normal").setStack("1"),
				new Series().setData(new JsArray<>(148.5, 216.4, 30, 176.0, 135.6)).setStacking("normal").setStack("1")));
		chartOptions.setTitle(new Title().setText("Simple Column Chart Example"));

		columnChart = Highcharts.chart("columnChart", chartOptions);

		try {
			SVGRenderer renderer = Highcharts.chart("mySVGChart", new ChartOptions().setTitle(new Title().setText("Raw SVG Example"))).getRenderer();
			JsPropertyMap<Object> rectAttrs = JsUtils.map("fill", "blue");
			rectAttrs.set("stroke", "black");
			rectAttrs.set("stroke-width", 1);
			renderer.rect(10, 10, 100, 50, 5, 2).attr(rectAttrs).add();
			JsPropertyMap<Object> circleAttrs = JsUtils.map("fill", "red");
			circleAttrs.set("stroke", "black");
			circleAttrs.set("stroke-width", 1);
			renderer.circle(100, 100, 50).attr(circleAttrs).on("click", () -> {
				DomGlobal.confirm("Clicked circle!");
				return true;
			}).add();
			JsPropertyMap<Object> textAttrs = JsUtils.map("rotation", 45);
			JsPropertyMap<Object> css = JsUtils.map("fontSize", "16pt");
			css.set("color", "green");
			renderer.text("Hello world", 200, 100, false).attr(textAttrs).css(css).add();
		}
		catch (Exception e) {
			DomGlobal.console.log(e);
		}

	}
}
