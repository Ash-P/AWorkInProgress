import com.sun.corba.se.spi.monitoring.StatisticsAccumulator;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ViewTrackingDataMenu {

	public static Label title = new Label("View Tracking Data");
	public static Button graphs = new Button("Graphs");
	public static Button statistics = new Button("Statistics");
	public static Button save = new Button("Save");
	public static Button back = new Button("Back");

	Scene previousScene;

	public static void instantiate() {
		title.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 15));
		title.setLayoutX(240);
		title.setLayoutY(15);

		back.setLayoutX(20);
		back.setLayoutY(278);

		save.setLayoutX(570);
		save.setLayoutY(278);
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
		//
		setupHandles();

		Group root = new Group(graphs, statistics, back, save, title);
		Scene mainScene = new Scene(root, 640, 320);
		MAIN.mainStage.setScene(mainScene);

	}

	static void setupHandles() {

		graphs.setOnAction(e -> {
			DisplayChart graph = new DisplayChart();
		});
		
		statistics.setOnAction(e -> {
			ViewStatistics viewStatsObj = new ViewStatistics();
		});
		
		back.setOnAction(e -> {
			MAIN.instantiate();
		});
		
		save.setOnAction(e -> {
			MAIN.saveData();
		});
		
	}
}
