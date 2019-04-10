

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DisplayChart {
	static Map<String, Integer> dataToDisplay = new HashMap<>();
	static int xStartRange;
	static int xEndRange;
	static int xInterval;
	static String xLabel;
	static String yLabel;
	
	private final Label timeScaleLbl = new Label();
	private final Label booksPagesReadLbl = new Label();
	private final ComboBox<String> timeScale = new ComboBox<String>();
	private final ComboBox<String> booksPagesRead = new ComboBox<String>();
	private final Button refresh = new Button("Generate graph");
	public static Stage Mstage;
	String title = "Progress";
	
	DisplayChart(){
		
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();

		
		refresh.setLayoutX(380);
		refresh.setLayoutY(82);
		
		timeScale.getItems().add("Yearly");
		timeScale.getItems().add("Monthly");
		timeScale.getItems().add("Weekly");
		timeScale.setLayoutX(260);
		timeScale.setLayoutY(112);
		timeScaleLbl.setLayoutX(120);
		timeScaleLbl.setLayoutY(112);
		booksPagesRead.getItems().add("Books");
		booksPagesRead.getItems().add("Pages");
		
		booksPagesReadLbl.setLayoutX(120);
		booksPagesReadLbl.setLayoutY(82);
		booksPagesRead.setLayoutX(260);
		booksPagesRead.setLayoutY(82);
		
		
	
		
		timeScaleLbl.setText("Time Scale: ");
		booksPagesReadLbl.setText("Books read/Pages read: ");
		
		//GenerateGraph graph = new GenerateGraph(xAxis, yAxis, title, dataToDisplay);
		//graph.setLayoutX(50);
		//graph.setLayoutY(50);
		LineChart graph = new LineChart(xAxis, yAxis);
		graph.setLayoutX(50);
		graph.setLayoutY(140);
		Group root = new Group(booksPagesRead,booksPagesReadLbl, timeScale, timeScaleLbl,graph, refresh);

		Scene scene = new Scene(root, 640, 600);
		setupHandles();
		//Mstage.setTitle("Progress Chart");
		//Mstage.setScene(scene);
		//Mstage.show();
		MAIN.mainStage.setScene(scene);
	}
	
	public void createGraph() {
		
	
	
		
		
		
		if(booksPagesRead.getValue() == "Books") {
			//final NumberAxis xAxis = new NumberAxis(xStartRange, xEndRange, xInterval);
			NumberAxis xAxis = null;
			NumberAxis yAxis = new NumberAxis();
			//xAxis.setLabel(xLabel);
			yAxis.setLabel("Books");
			
			dataToDisplay.clear();
			
			
			switch(timeScale.getValue()) {
			
			case "Yearly":
				dataToDisplay = getBooksByFormat(0);
				xAxis = new NumberAxis(2000, Calendar.getInstance().get(Calendar.YEAR)+5, 5);
				xAxis.setLabel("Year");
				break;
			case "Monthly":
				dataToDisplay = getBooksByFormat(1);
				xAxis = new NumberAxis(01, 12, 1);
				xAxis.setLabel("Month of Year");
				break;
			case "Weekly":
				dataToDisplay = getBooksByFormat(2);
				xAxis = new NumberAxis(1, Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH), 1);
				xAxis.setLabel("Day of Month");
				break;
			
			}
			GenerateGraph graph = new GenerateGraph(xAxis, yAxis, title, dataToDisplay);
			graph.setLayoutX(50);
			graph.setLayoutY(180);
			Group root = new Group(graph, booksPagesRead,booksPagesReadLbl, timeScale, timeScaleLbl, refresh);
			Scene scene = new Scene(root, 640, 600);	
			MAIN.mainStage.setScene(scene);
			
		}
		if(booksPagesRead.getValue() == "Pages") {
			NumberAxis xAxis = null;
			NumberAxis yAxis = new NumberAxis();
			
			yAxis.setLabel("Pages");
			
			dataToDisplay.clear();
			
			
			switch(timeScale.getValue()) {
			
			case "Yearly":
				dataToDisplay = getPagesByFormat(0);
				xAxis = new NumberAxis(2000, Calendar.getInstance().get(Calendar.YEAR)+5, 5);
				xAxis.setLabel("Year");
				break;
			case "Monthly":
				dataToDisplay = getPagesByFormat(1);
				xAxis = new NumberAxis(01, 12, 1);
				xAxis.setLabel("Month of Year");
				break;
			case "Weekly":
				dataToDisplay = getPagesByFormat(2);
				xAxis = new NumberAxis(1, Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH), 1);
				xAxis.setLabel("Day of Month");
				break;
			}
		
			
			GenerateGraph graph = new GenerateGraph(xAxis, yAxis, title, dataToDisplay);
			graph.setLayoutX(50);
			graph.setLayoutY(180);
			
			Group root = new Group(graph, booksPagesRead,booksPagesReadLbl, timeScale, timeScaleLbl, refresh);
			Scene scene = new Scene(root, 640, 600);

			
			MAIN.mainStage.setScene(scene);
			
		}
	}
	
	public Map<String, Integer> getBooksByFormat(int format) {
		Map<String, Integer> temp = MAIN.getBooksRead();
		Map<String, Integer> toReturn = new HashMap<>();
		for (Map.Entry<String, Integer> entry : temp.entrySet()) {
			SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MM-yyyy");
			Date y = null;
			try {
				y = formatter2.parse(entry.getKey());
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(y);
			int toStore =0;
			switch(format) {
			case 0:
				toStore = cal.get(Calendar.YEAR);
				break;
			case 1:
				toStore = cal.get(Calendar.MONTH) + 1;
				break;
			case 2:
				toStore = cal.get(Calendar.DAY_OF_WEEK) + 1;
				break;
			}
			//toStore = cal.get(Calendar.YEAR);
			System.out.println(toStore);
			toReturn.put(Integer.toString(toStore), entry.getValue());
			
		}
		
		return toReturn;
	}
	
	public Map<String, Integer> getPagesByFormat(int format) {
		Map<String, Integer> temp = MAIN.getPagesRead();
		Map<String, Integer> toReturn = new HashMap<>();
		for (Map.Entry<String, Integer> entry : temp.entrySet()) {
			SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MM-yyyy");
			Date y = null;
			try {
				y = formatter2.parse(entry.getKey());
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(y);
			int toStore =0;
			switch(format) {
			case 0:
				toStore = cal.get(Calendar.YEAR);
				break;
			case 1:
				toStore = cal.get(Calendar.MONTH) + 1;
				break;
			case 2:
				toStore = cal.get(Calendar.DAY_OF_WEEK) + 1;
				break;
			}
			//toStore = cal.get(Calendar.YEAR);
			System.out.println(toStore);
			toReturn.put(Integer.toString(toStore), entry.getValue());		
		}
		
		return toReturn;
	}
private void setupHandles() {
		
	
	
		refresh.setOnAction(e -> {
			System.out.println(booksPagesRead.getValue());
			createGraph();
			
			
		});
		/*submitBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				changeVisibility(dateRead, pagesReadTxt, addPreviouslyReadPagesBtn);
			}

		});*/
}
	public static void main(String args[]) {
		
		//Application.launch(DisplayChart.class, args);
		
	}
}
