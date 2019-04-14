import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ViewTrackingDataMenu {

	private static final Label title = new Label("View Tracking Data");
	private static final Button graphs = new Button("Graphs");
	private static final Button statistics = new Button("Statistics");
	private static final Button saveBtn = new Button("Save");
	private static final Button backBtn = new Button("Back");

	Scene previousScene;

	public static void instantiate() {
		MAIN.mainStage.setTitle("Tracking Data Menu");
		title.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 15));
		title.setLayoutX(240);
		title.setLayoutY(15);

		backBtn.setLayoutX(20);
		backBtn.setLayoutY(278);

		saveBtn.setLayoutX(570);
		saveBtn.setLayoutY(278);
		//
		graphs.setStyle("-fx-font: 15px Tahoma;");
		statistics.setStyle("-fx-font: 15px Tahoma;");
		//
		graphs.setPrefWidth(200);
		graphs.setPrefHeight(80);
		graphs.setLayoutX(120);
		graphs.setLayoutY(120);
		//
		statistics.setPrefWidth(200);
		statistics.setPrefHeight(80);
		statistics.setLayoutX(320);
		statistics.setLayoutY(120);
		
		setupHandles();
		Group root = new Group(graphs, statistics, backBtn, saveBtn, title);
		Scene mainScene = new Scene(root, 640, 320);
		MAIN.mainStage.setScene(mainScene);		

	}

	static void setupHandles() {

		graphs.setOnAction(e -> {
			DisplayChart graph = new DisplayChart();
			e.consume();
		});
		graphs.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
			if (ev.getCode() == KeyCode.ENTER) {
				graphs.fire();
				ev.consume(); 
			}
		});
		
		statistics.setOnAction(e -> {
			ViewStatistics viewStatsObj = new ViewStatistics();
			e.consume();
		});
		statistics.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
			if (ev.getCode() == KeyCode.ENTER) {
				statistics.fire();
				ev.consume(); 
			}
		});
		
		backBtn.setOnAction(e -> {
			MAIN.instantiate();
			e.consume();
		});
		backBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
			if (ev.getCode() == KeyCode.ENTER) {
				backBtn.fire();
				ev.consume(); 
			}
		});
		
		saveBtn.setOnAction(e -> {
			MAIN.saveData();
		});
		saveBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
			if (ev.getCode() == KeyCode.ENTER) {
				saveBtn.fire();
				ev.consume(); 
			}
		});
		
	}
	
}
