import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.joda.time.*;

// Move author genre to additional info, publisher, store all additional info under Description, 
public class AddABook {

	private boolean validData = true;
	
	//ESSENTIAL
	private final ComboBox<String> statusBox = new ComboBox<>();
	private final TextField bookTitleTxt = new TextField();
	private final TextField bookPagesTxt = new TextField();
	private final DatePicker dateStarted = new DatePicker();
	private final DatePicker dateCompleted = new DatePicker();
	private final TextField pagesReadTxt = new TextField();
	//PREVIOUSLY READ PAGES
	private final ComboBox<String> previouslyReadBox = new ComboBox<>();
	private final DatePicker dateRead = new DatePicker();
	private final TextField pagesOnDate = new TextField();
	private final Button addPreviouslyReadPagesBtn = new Button();
	//OPTIONAL
	private final Button additionalBookInfoBtn = new Button();
	private final TextField bookAuthorTxt = new TextField();
	private final TextField bookPublisherTxt = new TextField();
	private final TextField bookPublicationYear = new TextField();
	private final TextField bookGenreTxt = new TextField();
	private final TextArea bookDescriptionTxt = new TextArea();
	
	private final Button submitBtn = new Button();
	private final Button clearBtn = new Button();
	
	private Group primaryGroup = new Group();
	private Scene primaryScene;

	StringBuilder pagesOnDateToStore = new StringBuilder();
	
	
	public AddABook() {
		setupBookInfoUIElements();
		setupHandlers();

		primaryScene = new Scene(primaryGroup, 590, 440);
		MAIN.mainStage.setScene(primaryScene);
		MAIN.mainStage.setTitle("Add a Book");
	}
	
	private void addNodeToRootGroup(Node...nodesToAdd) {
		primaryGroup.getChildren().addAll(nodesToAdd);
	}
	
	// Create new grid using collection of nodes passed within parameter
	private void setupGridElement(Node... nodesToAdd) {
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);
		grid.getChildren().addAll(nodesToAdd);
		
		addNodeToRootGroup(grid,submitBtn, clearBtn);
	}

	// Define properties of UI Elements related to entry of book information
	// Then setup grid with defined nodes
	private void setupBookInfoUIElements() {
		
		//ESSENTIAL NODES
		statusBox.setPromptText("Status");
		statusBox.getItems().add("Read Previously");
		statusBox.getItems().add("Currently Reading");
		statusBox.getItems().add("Want to Read");
		GridPane.setConstraints(statusBox, 0, 1);
		
		bookTitleTxt.setPromptText("Book Title");
		GridPane.setConstraints(bookTitleTxt, 0, 2);

		bookPagesTxt.setPromptText("Pages");
		GridPane.setConstraints(bookPagesTxt, 0, 3);
		
		dateStarted.setPromptText("Date Started");
		dateStarted.setEditable(false); //forces a date to be selected from the calendar (prevents future dates being entered manually)
		dateStarted.setDayCellFactory(picker -> new DateCell() { //disable future dates
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				setDisable(empty || date.compareTo(LocalDate.now()) > 0 );
			}
		});
		GridPane.setConstraints(dateStarted, 0, 4);
		
		dateCompleted.setPromptText("Date Completed");
		dateCompleted.setEditable(false); //forces a date to be selected from the calendar (prevents future dates being entered manually)
		dateCompleted.setDayCellFactory(picker -> new DateCell() { //disable future dates
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				setDisable(empty || date.compareTo(LocalDate.now()) > 0 );
			}
		});
		GridPane.setConstraints(dateCompleted, 0, 5);
		
		pagesReadTxt.setPromptText("Pages Read");
		pagesOnDate.setDisable(true);
		GridPane.setConstraints(pagesReadTxt, 0, 6);
		
		
		//PREVIOUSLY READ PAGES NODES
		previouslyReadBox.setPromptText("Previously Read Dates");
		previouslyReadBox.getItems().add("Specify manually");
		previouslyReadBox.getItems().add("Distribute evenly");
		previouslyReadBox.setDisable(true);
		GridPane.setConstraints(previouslyReadBox, 0, 7);
		
		dateRead.setPromptText("Date Read");
		dateRead.setEditable(false); //forces a date to be selected from the calendar (prevents future dates being entered manually)
		dateRead.setDayCellFactory(picker -> new DateCell() { //disable future dates
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				setDisable(empty || date.compareTo(LocalDate.now()) > 0 );
			}
		});
		GridPane.setConstraints(dateRead, 0, 8);
		
		pagesOnDate.setPromptText("Pages Read on Date");
		dateRead.setDisable(true);
		GridPane.setConstraints(pagesOnDate, 0, 9);
		
		addPreviouslyReadPagesBtn.setText("Add");
		addPreviouslyReadPagesBtn.setDisable(true);
		GridPane.setConstraints(addPreviouslyReadPagesBtn, 1, 9);
		
		
		//OPTIONAL NODES
		additionalBookInfoBtn.setText("View Optional Fields");
		GridPane.setConstraints(additionalBookInfoBtn, 7, 1);
		
		bookAuthorTxt.setPromptText("Author");
		GridPane.setConstraints(bookAuthorTxt, 7, 2);
		
		bookPublisherTxt.setPromptText("Publisher");
		GridPane.setConstraints(bookPublisherTxt, 7, 3);

		bookPublicationYear.setPromptText("Publication Year");
		GridPane.setConstraints(bookPublicationYear, 7, 4);

		bookGenreTxt.setPromptText("Genre");
		GridPane.setConstraints(bookGenreTxt, 7, 5);
		
		bookDescriptionTxt.setPromptText("Book Description...");
		GridPane.setConstraints(bookDescriptionTxt, 7, 6);
		bookDescriptionTxt.setPrefRowCount(3);
		bookDescriptionTxt.setPrefColumnCount(16);
	
		
		submitBtn.setText("Submit");
		submitBtn.setLayoutX(400);
		submitBtn.setLayoutY(330);
		
		clearBtn.setText("Clear");
		clearBtn.setLayoutX(460);
		clearBtn.setLayoutY(330);
		
		changeVisibility(bookDescriptionTxt, bookAuthorTxt, bookPublisherTxt, bookPublicationYear, bookGenreTxt);

		setupGridElement(statusBox, bookTitleTxt, bookPagesTxt, dateStarted, dateCompleted, pagesReadTxt, previouslyReadBox, dateRead, pagesOnDate, addPreviouslyReadPagesBtn,
				additionalBookInfoBtn, bookAuthorTxt, bookPublisherTxt, bookPublicationYear, bookGenreTxt, bookDescriptionTxt);
	}

	/*
	public void storeData() {
		storage.bookData toStore = new storage.bookData();
		
		toStore.title = bookTitleTxt.getText();
		toStore.dateStarted = dateStarted.getValue().toString();
		toStore.author = bookAuthorTxt.getText();
		
		String previousPagesRead;
	}
	*/
	
	//TODO - update
	public void storeUserData() {
		storage.userData toUpdate = new storage.userData();
		int x = alldata.userStore.totalBooksRead;
		int y = alldata.userStore.totalPagesRead;
		
		toUpdate.totalBooksRead = toUpdate.totalBooksRead + x;
		toUpdate.totalBooksRead = toUpdate.totalBooksRead + y;
		
		x = x + toUpdate.totalBooksRead; 
		y = y + toUpdate.totalPagesRead;
	}
	
	// Setup all action handlers
	private void setupHandlers() {
		addPreviouslyReadPagesBtn.setOnAction(e->{
	
			//dateRead.getValue().format();
			
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			String toReturn = dateRead.getValue().format(dateFormat).toString() + "," + pagesOnDate.getText() + " ";
			
			pagesOnDateToStore.append(toReturn);
			System.out.println(toReturn);
			
			
		});
		statusBox.setOnAction(e->{
			if(statusBox.getValue() == "Read Previously") { //status is 0
				dateStarted.setDisable(false);
				dateCompleted.setDisable(false);
				pagesReadTxt.setDisable(true);
				previouslyReadBox.setDisable(false);
				if(!(previouslyReadBox.getValue() == "Distribute evenly")) {
					pagesOnDate.setDisable(false);
					dateRead.setDisable(false);
					addPreviouslyReadPagesBtn.setDisable(false);
				}
			}
			else if(statusBox.getValue() == "Currently Reading") { //status is 1
				dateStarted.setDisable(false);
				dateCompleted.setDisable(true);
				pagesReadTxt.setDisable(false);
				previouslyReadBox.setDisable(false);
			}
			else if(statusBox.getValue() == "Want to Read") { //status is 2
				dateStarted.setDisable(true);
				dateCompleted.setDisable(true);
				pagesReadTxt.setDisable(true);
				previouslyReadBox.setDisable(true);
				dateRead.setDisable(true);
				pagesOnDate.setDisable(true);
				addPreviouslyReadPagesBtn.setDisable(true);
			}
		});
		
		previouslyReadBox.setOnAction(e->{
			if(previouslyReadBox.getValue() == "Specify manually") {
				pagesOnDate.setDisable(false);
				dateRead.setDisable(false);
				addPreviouslyReadPagesBtn.setDisable(false);
			}
			else {
				dateRead.setDisable(true);
				pagesOnDate.setDisable(true);
				addPreviouslyReadPagesBtn.setDisable(true);
			}
		});
		
		submitBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				storage.bookData newBook = new storage.bookData();
				
				if(previouslyReadBox.getValue() == "Distribute evenly") {
					distributeEvenly();
				}
				
				
				
				newBook.author = bookAuthorTxt.getText();
				int ID = alldata.bookStore.get(alldata.bookStore.size()-1).bookID + 1;
				newBook.bookID = ID;
				DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				Date date = new Date();
				newBook.dateAdded = dateFormat.format(date);
				newBook.dateCompleted = dateCompleted.getValue().toString();
				newBook.dateStarted = dateStarted.getValue().toString();
				newBook.genre = bookGenreTxt.getText();
				newBook.pages = Integer.parseInt(bookPagesTxt.getText());
				newBook.publicationYear = bookPublicationYear.getText();
				newBook.pagesRead = Integer.parseInt(pagesReadTxt.getText());
				newBook.pagesReadOnADate = pagesOnDateToStore.toString();
				newBook.publisher = bookPublisherTxt.getText();
				newBook.status = Integer.parseInt(statusBox.getValue());
				newBook.title = bookTitleTxt.getText();
				newBook.description = bookDescriptionTxt.getText();
				
				if(newBook.status == 0 || newBook.status == 1) {
					if(newBook.pages == newBook.pagesRead) AddATarget.updateTargets(newBook.pagesRead, newBook.bookID, true);
					else AddATarget.updateTargets(newBook.pagesRead, newBook.bookID, false);
				}		
				
				alldata.bookStore.add(newBook);
			}

		});
			
		clearBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				statusBox.valueProperty().set(null);
				bookTitleTxt.clear();
				bookPagesTxt.clear();
				dateStarted.valueProperty().set(null);
				dateCompleted.valueProperty().set(null);
				pagesReadTxt.clear();
				previouslyReadBox.valueProperty().set(null);
				dateRead.valueProperty().set(null);
				pagesOnDate.clear();
				bookAuthorTxt.clear();
				bookPublisherTxt.clear();
				bookPublicationYear.clear();
				bookGenreTxt.clear();
				bookDescriptionTxt.clear();
				
				dateStarted.setDisable(false);
				dateCompleted.setDisable(false);
				pagesReadTxt.setDisable(false);
				previouslyReadBox.setDisable(true);
				dateRead.setDisable(true);
				pagesOnDate.setDisable(true);
				addPreviouslyReadPagesBtn.setDisable(true);
			}
		});
		
		additionalBookInfoBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if(additionalBookInfoBtn.getText() == "View Optional Fields") additionalBookInfoBtn.setText("Hide Optional Fields");
				else additionalBookInfoBtn.setText("View Optional Fields");
				changeVisibility(bookDescriptionTxt, bookGenreTxt, bookAuthorTxt, bookPublicationYear, bookPublisherTxt);
			}
		});
	
		bookTitleTxt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (BookDataValidation.validBookDataString(bookTitleTxt.getText())) {
					bookTitleTxt.setStyle("-fx-text-inner-color: red;");
					validData = false;
				} else {
					validData = true;
					bookTitleTxt.setStyle("-fx-text-inner-color: black;");
				}
			}
		});
		
		bookAuthorTxt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (BookDataValidation.validBookDataString(bookAuthorTxt.getText())) {
					bookAuthorTxt.setStyle("-fx-text-inner-color: red;");
					validData = false;
				} else {
					validData = true;
					bookAuthorTxt.setStyle("-fx-text-inner-color: black;");
				}
			}
		});
		
		bookPublisherTxt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (BookDataValidation.validBookDataString(bookPublisherTxt.getText())) {
					bookPublisherTxt.setStyle("-fx-text-inner-color: red;");
					validData = false;
				} else {
					validData = true;
					bookPublisherTxt.setStyle("-fx-text-inner-color: black;");
				}
			}
		});
		
		bookGenreTxt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (BookDataValidation.validBookDataString(bookGenreTxt.getText())) {
					bookGenreTxt.setStyle("-fx-text-inner-color: red;");
					validData = false;
				} else {
					validData = true;
					bookGenreTxt.setStyle("-fx-text-inner-color: black;");
				}
			}
		});
		
		bookDescriptionTxt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (BookDataValidation.validBookDataString(bookDescriptionTxt.getText())) {
					bookDescriptionTxt.setStyle("-fx-text-inner-color: red;");
					validData = false;
				} else {
					validData = true;
					bookDescriptionTxt.setStyle("-fx-text-inner-color: black;");
				}
			}
		});
		
		bookPagesTxt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (BookDataValidation.validBookDataInteger(bookPagesTxt.getText())) {
					bookPagesTxt.setStyle("-fx-text-inner-color: red;");
					validData = false;
				} else {
					validData = true;
					bookPagesTxt.setStyle("-fx-text-inner-color: black;");
				}
			}
		});
		
		pagesReadTxt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (BookDataValidation.validBookDataInteger(pagesReadTxt.getText())) {
					pagesReadTxt.setStyle("-fx-text-inner-color: red;");
					validData = false;
				} else {
					validData = true;
					pagesReadTxt.setStyle("-fx-text-inner-color: black;");
				}
			}
		});
		
		pagesOnDate.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (BookDataValidation.validBookDataInteger(pagesOnDate.getText())) {
					pagesOnDate.setStyle("-fx-text-inner-color: red;");
					validData = false;
				} else {
					validData = true;
					pagesOnDate.setStyle("-fx-text-inner-color: black;");
				}
			}
		});
		
		bookPublicationYear.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (BookDataValidation.validBookDataInteger(bookPublicationYear.getText())) {
					bookPublicationYear.setStyle("-fx-text-inner-color: red;");
					validData = false;
				} else {
					validData = true;
					bookPublicationYear.setStyle("-fx-text-inner-color: black;");
				}
			}
		});
		
	}

	
	public void distributeEvenly() {
		if(statusBox.getValue() == "Read Previously") {
		
			Date dateSS = java.util.Date.from(dateStarted.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			Date dateEE = java.util.Date.from(dateCompleted.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			DateTime dateS = new DateTime(dateSS);
			DateTime dateE = new DateTime(dateEE);
			int days = Days.daysBetween(dateS, dateE).getDays();
			int pagesOnEachDay = Integer.parseInt(pagesReadTxt.getText()) / days;
			
			DateTime current_day = new DateTime(dateS);
			
			while(!(current_day.isEqual(dateE))) {
				current_day = current_day.plusDays(1);
				System.out.println(current_day);
			}
		}
	}
	
	
	
	// Invert visibility of nodes passed within parameter
	private void changeVisibility(Node... nodesToChange) {
		for (Node node : nodesToChange) {
			if (node.isVisible()) {
				node.setVisible(false);
			} else {
				node.setVisible(true);
			}
		}
	}

	
	/*
	@Override
	public void start(Stage mainStage) throws Exception {

		setupBookInfoUIElements();
		setupHandlers();

		primaryScene = new Scene(primaryGroup, 560, 360);
		mainStage.setScene(primaryScene);
		mainStage.show();
	}

	public static void main(String args[]) {
		launch(args);
	}
	*/
}
