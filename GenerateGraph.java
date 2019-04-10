

import java.util.Map;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class GenerateGraph extends LineChart<Number, Number>{
	
	GenerateGraph(final NumberAxis xAxis, final NumberAxis yAxis, String title, Map<String, Integer> avgPages){
		super(xAxis, yAxis);
		addXYPoint(avgPages);
	}
 
	private void addXYPoint(Map<String,Integer> dataToAdd) {	
		XYChart.Series<Number, Number> tempSeries = new XYChart.Series<>();		
		dataToAdd.forEach((k,v) -> tempSeries.getData().add(new XYChart.Data( Integer.parseInt(k) , v)));
		this.getData().add(tempSeries);
	}
}

