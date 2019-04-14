import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class AddReadingProgress {

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private final ComboBox<String> bookTitleBox = new ComboBox<>();
	private final DatePicker datePicker = new DatePicker();
	private final TextField pagesTxt = new TextField();
	private final Button addBtn = new Button();
	private final Button clearBtn = new Button();
	private Scene scene;
	private Group group;
	private Alert dialog = new Alert(AlertType.INFORMATION);
	public static Button backBtn = new Button("Back");
	
	
	public AddReadingProgress() {
		createUI();
		setupHandles();
		scene = new Scene(group, 370, 180);
		MAIN.mainStage.setScene(scene);
		MAIN.mainStage.setTitle("Add Reading Progress");
	}
	
	public void createUI() {
		bookTitleBox.setPromptText("Book Title");
		
		for(storage.bookData b : alldata.bookStore) { //add books of status 1 and 2 only
			if(b.status == 1 || b.status == 2) bookTitleBox.getItems().add(b.title);
		}
		
		bookTitleBox.setEditable(true);
		GridPane.setConstraints(bookTitleBox, 0, 1);
		
		datePicker.setPromptText("Date Read");
		datePicker.setEditable(false); //forces a date to be selected from the calendar (prevents future dates being entered manually)
		datePicker.setDayCellFactory(picker -> new DateCell() { //disable future dates
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				setDisable(empty || date.compareTo(LocalDate.now()) > 0 );
			}
		});
		GridPane.setConstraints(datePicker, 0, 2);
		
		pagesTxt.setPromptText("Pages Read");
		GridPane.setConstraints(pagesTxt, 0, 3);
		
		addBtn.setText("Add");
		addBtn.setLayoutX(250);
		addBtn.setLayoutY(115);

		clearBtn.setText("Clear");
		clearBtn.setLayoutX(300);
		clearBtn.setLayoutY(115);
	
		backBtn.setLayoutX(15);
		backBtn.setLayoutY(138);
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);
		grid.getChildren().addAll(bookTitleBox,datePicker,pagesTxt);
		
		group = new Group(backBtn, grid,addBtn,clearBtn);
	}
	
	private void setupHandles() {
		//validates fields, adds reading progress
		addBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String bookTitle = bookTitleBox.getValue();
				String date = datePicker.getValue().format(DATE_FORMATTER);
				int pages = Integer.parseInt(pagesTxt.getText());
				storage.bookData book = MAIN.getSpecificBook(bookTitle);
				if(book != null) {
					int status = book.status;
					int pagesRemaining = book.pages - book.pagesRead;
					if(validateFields(status, pagesRemaining, book.dateStarted, date, pages)) {
						alldata.userStore.totalPagesRead += pages;
						book.pagesRead = book.pagesRead + pages;
						book.pagesReadOnADate +=  " " + date + "," + pages;
						if(book.pagesRead == book.pages) { 
							book.status = 0;
							book.dateCompleted = date;
							alldata.userStore.totalBooksRead++;
						}
						else if(book.pagesRead == 0) {
							book.status = 1;
							book.dateStarted = date;
						}
						AddATarget.updateTargets(pages, book.bookID, status == 0); //updates and checks for completion of targets
						//TODO update and check for completion of achievements
						
						//success dialog
						dialog.setTitle("Success");
						dialog.setHeaderText("Reading progress added successfully");
						dialog.setContentText("You have successfully added reading progress of\n" + pages + " pages of " + bookTitle + " on " + date + ".");
						dialog.showAndWait();
						return;
					}
				}
				MAIN.createAlert(AlertType.ERROR, "Error", "Reading progress validation unsuccessful", ""); //TODO
				dialog.setTitle("Failure");
				dialog.setHeaderText("Reading progress not added");
				dialog.setContentText("You have failed to successfully add reading progress to " + bookTitle + ".");
				dialog.showAndWait();
			}
		});
		addBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
			if (ev.getCode() == KeyCode.ENTER) {
				addBtn.fire();
				ev.consume(); 
			}
		});

		//resets all fields
		clearBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				bookTitleBox.valueProperty().set(null);
				datePicker.valueProperty().set(null);
				pagesTxt.clear();
			}
		});
		clearBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
			if (ev.getCode() == KeyCode.ENTER) {
				clearBtn.fire();
				ev.consume(); 
			}
		});
		
		backBtn.setOnAction(e->{
			ManageBooksMenu.instantiate();
			e.consume();
		});
		backBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
			if (ev.getCode() == KeyCode.ENTER) {
				backBtn.fire();
				ev.consume(); 
			}
		});
	}
	
	//TODO red text when pages is not a positive int
	
	private static Boolean validateFields(int bookStatus, int pagesRemaining, String dateStarted, String date, int pages) {	
		if(pages < 1) return false;
		if( !(bookStatus == 1 || bookStatus == 2) ) return false;
		if(pages > pagesRemaining) return false;
		if(dateStarted == null) return false; //TODO check what is dateStarted if type is not 1?? (is it null?)
		//if(bookStatus == 1 && ( LocalDate.parse(date, DATE_FORMATTER).compareTo(LocalDate.parse(dateStarted, DATE_FORMATTER)) < 0 )) return false; //rejects dates before dateStarted
		return true;
	}
}
