import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ManageBooksMenu {
	public static Label title = new Label("Manage Books");
	public static Button addReadingProgress = new Button("Add Reading Progress");
	public static Button addBook = new Button("Add a Book");
	public static Button viewBooks = new Button("View Books");
	public static Button save = new Button("Save");
	public static Button back = new Button("Back");

	Scene previousScene;

	public static void instantiate() {
		title.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 15));
		title.setLayoutX(255);
		title.setLayoutY(15);

		back.setLayoutX(20);
		back.setLayoutY(278);

		save.setLayoutX(570);
		save.setLayoutY(278);
		
		
		//
		addReadingProgress.setStyle("-fx-font: 15px Tahoma;");
		addBook.setStyle("-fx-font: 15px Tahoma;");
		viewBooks.setStyle("-fx-font: 15px Tahoma;");
		//
		addBook.setPrefWidth(200);
		addBook.setPrefHeight(80);
		addBook.setLayoutX(120);
		addBook.setLayoutY(60);
		//
		addReadingProgress.setPrefWidth(200);
		addReadingProgress.setPrefHeight(80);
		addReadingProgress.setLayoutX(320);
		addReadingProgress.setLayoutY(60);
		//
		viewBooks.setPrefWidth(400);
		viewBooks.setPrefHeight(80);
		viewBooks.setLayoutX(120);
		viewBooks.setLayoutY(140);
		
		setupHandles();

		Group root = new Group(addBook, addReadingProgress, viewBooks, back, save, title);
		Scene mainScene = new Scene(root, 640, 320);
		MAIN.mainStage.setScene(mainScene);

	}

	static void setupHandles() {
		
		addBook.setOnAction(e -> {
			AddABook addBookObj = new AddABook();
		});

		addReadingProgress.setOnAction(e -> {
			AddReadingProgress addReadProgObj = new AddReadingProgress();
		});
		
		viewBooks.setOnAction(e ->{
			ViewBooks viewBooksObj = new ViewBooks();	
		});
		
		back.setOnAction(e -> {
			MAIN.instantiate();
		});
		
		save.setOnAction(e -> {
			MAIN.saveData();
		});
	}
}
