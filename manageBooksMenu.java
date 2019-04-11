import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class manageBooksMenu {
	public static Label title = new Label("Manage Books");
	public static Button addReadingProgress = new Button("Add Reading Progress");
	public static Button addBook = new Button("Add Book");
	public static Button viewBooks = new Button("View Books");
	public static Button save = new Button("Save");
	public static Button back = new Button("Back");

	Scene previousScene;

	public static void instantiate() {
		title.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 15));
		title.setLayoutX(255);
		title.setLayoutY(15);

		// 570 for save button
		back.setLayoutX(20);
		back.setLayoutY(278);

		save.setLayoutX(570);
		save.setLayoutY(278);
		
		
		//
		addReadingProgress.setStyle("-fx-font: 15px Tahoma;");
		addBook.setStyle("-fx-font: 15px Tahoma;");
		viewBooks.setStyle("-fx-font: 15px Tahoma;");
		//
		addReadingProgress.setPrefWidth(200);
		addReadingProgress.setPrefHeight(80);
		addReadingProgress.setLayoutX(120);
		addReadingProgress.setLayoutY(60);
		//
		addBook.setPrefWidth(200);
		addBook.setPrefHeight(80);
		addBook.setLayoutX(320);
		addBook.setLayoutY(60);
		//
		viewBooks.setPrefWidth(400);
		viewBooks.setPrefHeight(80);
		viewBooks.setLayoutX(120);
		viewBooks.setLayoutY(140);
		
		setupHandles();

		Group root = new Group(save, back, addReadingProgress, addBook, title,viewBooks);
		Scene mainScene = new Scene(root, 640, 320);
		MAIN.mainStage.setScene(mainScene);

	}

	static void setupHandles() {

		save.setOnAction(e -> {

			MAIN.saveData();

		});
		back.setOnAction(e -> {
			MAIN.instantiate();
		});

		addReadingProgress.setOnAction(e -> {
			
			DisplayChart graph = new DisplayChart();
			
		});
		
		viewBooks.setOnAction(e ->{
			
			ViewBookData viewBookObj = new ViewBookData();
			
		});
	}
}
