import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ManageBooksMenu {
	public static final Label title = new Label("Manage Books");
	public static final Button addReadingProgress = new Button("Add Reading Progress");
	public static final Button addBook = new Button("Add a Book");
	public static final Button viewBooks = new Button("View Books");
	public static final Button saveBtn = new Button("Save");
	public static final Button backBtn = new Button("Back");

	Scene previousScene;

	public static void instantiate() {
		MAIN.mainStage.setTitle("Manage Books Menu");
		title.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 15));
		title.setLayoutX(255);
		title.setLayoutY(15);

		backBtn.setLayoutX(20);
		backBtn.setLayoutY(278);

		saveBtn.setLayoutX(570);
		saveBtn.setLayoutY(278);	
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
		
		setupHandlers();

		Group root = new Group(addBook, addReadingProgress, viewBooks, backBtn, saveBtn, title);
		Scene mainScene = new Scene(root, 640, 320);
		MAIN.mainStage.setScene(mainScene);

	}

	static void setupHandlers() {
		
		addBook.setOnAction(e -> {
			AddABook addBookObj = new AddABook();
			e.consume();
		});
		addBook.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
			if (ev.getCode() == KeyCode.ENTER) {
				addBook.fire();
				ev.consume(); 
			}
		});

		addReadingProgress.setOnAction(e -> {
			AddReadingProgress addReadProgObj = new AddReadingProgress();
			e.consume();
		});
		addReadingProgress.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
			if (ev.getCode() == KeyCode.ENTER) {
				addReadingProgress.fire();
				ev.consume(); 
			}
		});
		
		viewBooks.setOnAction(e ->{
			ViewBooks viewBooksObj = new ViewBooks();
			e.consume();
		});
		viewBooks.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
			if (ev.getCode() == KeyCode.ENTER) {
				viewBooks.fire();
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
