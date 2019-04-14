import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class TargetsMenu {
	public static final Label title = new Label("Targets");
	public static final Button addTarget = new Button("Add a Target");
	public static final Button viewTargets = new Button("View Targets");
	public static final Button saveBtn = new Button("Save");
	public static final Button backBtn = new Button("Back");
	public static Scene previousScene;

	public static void instantiate() {
		MAIN.mainStage.setTitle("Targets Menu");
		title.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 15));
		title.setLayoutX(285);
		title.setLayoutY(15);

		backBtn.setLayoutX(20);
		backBtn.setLayoutY(278);

		saveBtn.setLayoutX(570);
		saveBtn.setLayoutY(278);		
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
		
		setupHandlers();

		Group root = new Group(addTarget, viewTargets, backBtn, saveBtn, title);
		Scene mainScene = new Scene(root, 640, 320);
		MAIN.mainStage.setScene(mainScene);

	}

	static void setupHandlers() {

		addTarget.setOnAction(e -> {	
			AddATarget addTargetObj = new AddATarget();
			e.consume();
		});
		addTarget.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
			if (ev.getCode() == KeyCode.ENTER) {
				addTarget.fire();
				ev.consume(); 
			}
		});
		
		viewTargets.setOnAction(e -> {
			ViewTargets viewTargetsObj = new ViewTargets();
			e.consume();
		});
		viewTargets.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
			if (ev.getCode() == KeyCode.ENTER) {
				viewTargets.fire();
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
