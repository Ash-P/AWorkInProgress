import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class DisplayChart {
	static Map<String, Integer> dataToDisplay = new HashMap<>();
	static int xStartRange;
	static int xEndRange;
	static int xInterval;
	static String xLabel;
	static String yLabel;
	
	private final Label title = new Label("Graphs");
	private final Label timeScaleLbl = new Label();
	private final Label booksPagesReadLbl = new Label();
	private final ComboBox<String> timeScale = new ComboBox<String>();
	private final ComboBox<String> booksPagesRead = new ComboBox<String>();
	private final Button updateBtn = new Button("Generate graph");
	public static Stage Mstage;
	
	public static Button backBtn = new Button("Back");
	String progTitle = "Progress";
	
	DisplayChart(){
		MAIN.mainStage.setTitle("Graphs");
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		
		title.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 15));
		title.setLayoutX(260);
		title.setLayoutY(15);
		
		updateBtn.setLayoutX(380);
		updateBtn.setLayoutY(82);
		
		timeScale.getItems().add("Yearly");
		timeScale.getItems().add("Monthly");
		timeScale.getItems().add("Weekly");
		timeScale.setLayoutX(260);
		timeScale.setLayoutY(112);
		timeScaleLbl.setLayoutX(100);
		timeScaleLbl.setLayoutY(112);
		booksPagesRead.getItems().add("Books");
		booksPagesRead.getItems().add("Pages");
		
		booksPagesReadLbl.setLayoutX(100);
		booksPagesReadLbl.setLayoutY(82);
		booksPagesRead.setLayoutX(260);
		booksPagesRead.setLayoutY(82);
			
		timeScaleLbl.setText("Time Scale: ");
		booksPagesReadLbl.setText("Books read/Pages read: ");
		
		LineChart graph = new LineChart(xAxis, yAxis);
		graph.setLayoutX(50);
		graph.setLayoutY(140);
		
		backBtn.setLayoutX(15);
		backBtn.setLayoutY(560);
		
		Group root = new Group(title, booksPagesRead, booksPagesReadLbl, timeScale, timeScaleLbl, graph, updateBtn, backBtn);
		Scene scene = new Scene(root, 660, 600);
		
		setupHandlers();
		MAIN.mainStage.setScene(scene);
	}
	
	public void createGraph() {
		
		if(booksPagesRead.getValue() == "Books") {
			NumberAxis xAxis = null;
			NumberAxis yAxis = new NumberAxis();
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
			
			GenerateGraph graph = new GenerateGraph(xAxis, yAxis, progTitle, dataToDisplay);
			graph.setLayoutX(50);
			graph.setLayoutY(180);
			Group root = new Group(backBtn, graph, booksPagesRead,booksPagesReadLbl, timeScale, timeScaleLbl, updateBtn);
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
			
			GenerateGraph graph = new GenerateGraph(xAxis, yAxis, progTitle, dataToDisplay);
			graph.setLayoutX(50);
			graph.setLayoutY(180);
			
			Group root = new Group(backBtn, graph, booksPagesRead,booksPagesReadLbl, timeScale, timeScaleLbl, updateBtn);
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
			System.out.println(toStore);
			toReturn.put(Integer.toString(toStore), entry.getValue());		
		}	
		return toReturn;
	}
	
	private void setupHandlers() {	
		
		backBtn.setOnAction(e->{	
			ViewTrackingDataMenu.instantiate();
			e.consume();
		});
		backBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
			if (ev.getCode() == KeyCode.ENTER) {
				backBtn.fire();
				ev.consume(); 
			}
		});
	
		updateBtn.setOnAction(e -> {
			System.out.println(booksPagesRead.getValue());
			createGraph();		
		});
		updateBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
			if (ev.getCode() == KeyCode.ENTER) {
				updateBtn.fire();
				ev.consume(); 
			}
		});
		
	}

}
