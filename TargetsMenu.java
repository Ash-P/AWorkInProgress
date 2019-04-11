import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TargetsMenu {
	public static Label title = new Label("Targets");
	public static Button addTarget = new Button("Add a Target");
	public static Button viewTargets = new Button("View Targets");
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
		addTarget.setStyle("-fx-font: 15px Tahoma;");
		viewTargets.setStyle("-fx-font: 15px Tahoma;");

		//
		addTarget.setPrefWidth(200);
		addTarget.setPrefHeight(80);
		addTarget.setLayoutX(120);
		addTarget.setLayoutY(120);
		//
		viewTargets.setPrefWidth(200);
		viewTargets.setPrefHeight(80);
		viewTargets.setLayoutX(320);
		viewTargets.setLayoutY(120);
		//
		setupHandles();

		Group root = new Group(addTarget, viewTargets, back, save, title);
		Scene mainScene = new Scene(root, 640, 320);
		MAIN.mainStage.setScene(mainScene);

	}

	static void setupHandles() {

		addTarget.setOnAction(e -> {	
			AddATarget addTargetObj = new AddATarget();
		});
		
		viewTargets.setOnAction(e -> {
			ViewTargets viewTargetsObj = new ViewTargets();
		});
		
		back.setOnAction(e -> {
			MAIN.instantiate();
		});
		
		save.setOnAction(e -> {
			MAIN.saveData();
		});
	}
}
