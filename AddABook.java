import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

// Move author genre to additional info, publisher, store all additional info under Description, 
public class AddABook {

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	// ESSENTIAL
	private final ComboBox<String> statusBox = new ComboBox<>();
	private final TextField bookTitleTxt = new TextField();
	private final TextField bookPagesTxt = new TextField();
	private final DatePicker dateStartedPicker = new DatePicker();
	private final DatePicker dateCompletedPicker = new DatePicker();
	private final TextField pagesReadTxt = new TextField();
	// PREVIOUSLY READ PAGES
	private final ComboBox<String> previouslyReadBox = new ComboBox<>();
	private final DatePicker dateReadPicker = new DatePicker();
	private final TextField pagesOnDateTxt = new TextField();
	private final Button addBtn = new Button("Add");
	// OPTIONAL
	private final Button additionalBookInfoBtn = new Button();
	private final TextField bookAuthorTxt = new TextField();
	private final TextField bookPublisherTxt = new TextField();
	private final TextField bookPublicationYearTxt = new TextField();
	private final TextField bookGenreTxt = new TextField();
	private final TextArea bookDescriptionTxt = new TextArea();

	private final Button submitBtn = new Button("Submit");
	private final Button clearBtn = new Button("Clear");
	private final Button backBtn = new Button("Back");
	
	private Group primaryGroup = new Group();
	private Scene primaryScene;
	
	private Boolean isValid;
	private String pagesReadOnADate = "";
	private StringBuilder pagesOnDateToStore = new StringBuilder();
	// manually specified pagesReadOnADate entries are stored here when the addBtn is
	// pressed

	public AddABook() {
		createUI();
		setupHandlers();

		primaryScene = new Scene(primaryGroup, 590, 440);
		MAIN.mainStage.setScene(primaryScene);
		MAIN.mainStage.setTitle("Add a Book");
	}

	private void addNodeToRootGroup(Node... nodesToAdd) {
		primaryGroup.getChildren().addAll(nodesToAdd);
	}

	// Create new grid using collection of nodes passed within parameter
	private void setupGridElement(Node... nodesToAdd) {
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);
		grid.getChildren().addAll(nodesToAdd);

		addNodeToRootGroup(grid, submitBtn, clearBtn, backBtn);
	}

	// Define properties of UI Elements related to entry of book information
	// Then setup grid with defined nodes
	private void createUI() {

		// ESSENTIAL NODES
		statusBox.setPromptText("Status");
		statusBox.getItems().add("Read Previously");
		statusBox.getItems().add("Currently Reading");
		statusBox.getItems().add("Want to Read");
		GridPane.setConstraints(statusBox, 0, 1);

		bookTitleTxt.setPromptText("Book Title");
		GridPane.setConstraints(bookTitleTxt, 0, 2);

		bookPagesTxt.setPromptText("Pages");
		GridPane.setConstraints(bookPagesTxt, 0, 3);

		dateStartedPicker.setPromptText("Date Started");
		dateStartedPicker.setEditable(false); // forces a date to be selected from the calendar (prevents future dates
												// being entered manually)
		dateStartedPicker.setDayCellFactory(picker -> new DateCell() { // disable future dates
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				setDisable(empty || date.compareTo(LocalDate.now()) > 0);
			}
		});
		dateStartedPicker.setDisable(true);
		GridPane.setConstraints(dateStartedPicker, 0, 4);

		dateCompletedPicker.setPromptText("Date Completed");
		dateCompletedPicker.setEditable(false); // forces a date to be selected from the calendar (prevents future dates
												// being entered manually)
		dateCompletedPicker.setDayCellFactory(picker -> new DateCell() { // disable future dates
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				setDisable(empty || date.compareTo(LocalDate.now()) > 0);
			}
		});
		dateCompletedPicker.setDisable(true);
		GridPane.setConstraints(dateCompletedPicker, 0, 5);

		pagesReadTxt.setPromptText("Pages Read");
		pagesReadTxt.setDisable(true);
		GridPane.setConstraints(pagesReadTxt, 0, 6);

		// PREVIOUSLY READ PAGES NODES
		previouslyReadBox.setPromptText("Previously Read Dates");
		previouslyReadBox.getItems().add("Specify manually");
		previouslyReadBox.getItems().add("Distribute evenly");
		previouslyReadBox.setDisable(true);
		GridPane.setConstraints(previouslyReadBox, 0, 7);

		dateReadPicker.setPromptText("Date Read");
		dateReadPicker.setEditable(false); // forces a date to be selected from the calendar (prevents future dates being entered manually)
		dateReadPicker.setDisable(true);
		GridPane.setConstraints(dateReadPicker, 0, 8);

		pagesOnDateTxt.setPromptText("Pages Read on Date");
		pagesOnDateTxt.setDisable(true);
		GridPane.setConstraints(pagesOnDateTxt, 0, 9);

		//addBtn.setText("Add");
		addBtn.setDisable(true);
		GridPane.setConstraints(addBtn, 1, 9);

		// OPTIONAL NODES
		additionalBookInfoBtn.setText("View Optional Fields");
		GridPane.setConstraints(additionalBookInfoBtn, 7, 1);

		bookAuthorTxt.setPromptText("Author");
		GridPane.setConstraints(bookAuthorTxt, 7, 2);

		bookPublisherTxt.setPromptText("Publisher");
		GridPane.setConstraints(bookPublisherTxt, 7, 3);

		bookPublicationYearTxt.setPromptText("Publication Year");
		GridPane.setConstraints(bookPublicationYearTxt, 7, 4);

		bookGenreTxt.setPromptText("Genre");
		GridPane.setConstraints(bookGenreTxt, 7, 5);

		bookDescriptionTxt.setPromptText("Book Description...");
		GridPane.setConstraints(bookDescriptionTxt, 7, 6);
		bookDescriptionTxt.setPrefRowCount(3);
		bookDescriptionTxt.setPrefColumnCount(16);

		submitBtn.setLayoutX(380);
		submitBtn.setLayoutY(330);

		clearBtn.setLayoutX(450);
		clearBtn.setLayoutY(330);

		backBtn.setLayoutX(15);
		backBtn.setLayoutY(398);
		
		
		changeVisibility(bookDescriptionTxt, bookAuthorTxt, bookPublisherTxt, bookPublicationYearTxt, bookGenreTxt);

		setupGridElement(statusBox, bookTitleTxt, bookPagesTxt, dateStartedPicker, dateCompletedPicker, pagesReadTxt,
				previouslyReadBox, dateReadPicker, pagesOnDateTxt, addBtn, additionalBookInfoBtn, bookAuthorTxt,
				bookPublisherTxt, bookPublicationYearTxt, bookGenreTxt, bookDescriptionTxt);
	}

	private void addNewPreviousBook(String title, int pages, String dateStarted, String dateCompleted, String pagesReadOnADate, String author, String publisher, int publicationYear, String genre, String description) {
		storage.bookData newBook = new storage.bookData();

		newBook.bookID = getHighestBookID() + 1;
		newBook.status = 0;
		newBook.dateAdded = DATE_FORMAT.format(new Date());

		newBook.title = title;
		newBook.pages = pages;
		newBook.dateStarted = dateStarted;
		newBook.dateCompleted = dateCompleted;
		newBook.pagesRead = pages;
		newBook.pagesReadOnADate = pagesReadOnADate;

		newBook.author = author;
		newBook.publisher = publisher;
		newBook.publicationYear = publicationYear;
		newBook.genre = genre;
		newBook.description = description;

		alldata.bookStore.add(newBook);
		alldata.userStore.totalPagesRead += pages;
		alldata.userStore.totalBooksRead++;
	
		AddATarget.updateTargets(newBook.pagesRead, newBook.bookID, true);
	}

	private void addNewCurrentBook(String title, int pages, String dateStarted, int pagesRead, String pagesReadOnADate, String author, String publisher, int publicationYear, String genre, String description) {
		storage.bookData newBook = new storage.bookData();

		newBook.bookID = getHighestBookID() + 1;
		newBook.status = 1;
		newBook.dateAdded = DATE_FORMAT.format(new Date());

		newBook.title = title;
		newBook.pages = pages;
		newBook.dateStarted = dateStarted;
		newBook.dateCompleted = "";
		newBook.pagesRead = pagesRead;
		newBook.pagesReadOnADate = pagesReadOnADate;

		newBook.author = author;
		newBook.publisher = publisher;
		newBook.publicationYear = publicationYear;
		newBook.genre = genre;
		newBook.description = description;

		alldata.bookStore.add(newBook);
		alldata.userStore.totalPagesRead += pagesRead;

		AddATarget.updateTargets(newBook.pagesRead, newBook.bookID, false);
	}

	private void addNewFutureBook(String title, int pages, String author, String publisher, int publicationYear, String genre, String description) {
		storage.bookData newBook = new storage.bookData();

		newBook.bookID = getHighestBookID() + 1;
		newBook.status = 2;
		newBook.dateAdded = DATE_FORMAT.format(new Date());

		newBook.title = title;
		newBook.pages = pages;
		newBook.dateStarted = "";
		newBook.dateCompleted = "";
		newBook.pagesRead = 0;
		newBook.pagesReadOnADate = "";

		newBook.author = author;
		newBook.publisher = publisher;
		newBook.publicationYear = publicationYear;
		newBook.genre = genre;
		newBook.description = description;

		alldata.bookStore.add(newBook);
	}

	private void setupHandlers() {
		
		statusBox.setOnAction(e -> {
			if(statusBox.getValue() == "Read Previously") {
				dateStartedPicker.setDisable(false);
				dateCompletedPicker.setDisable(false);
				pagesReadTxt.setDisable(true);				
				if(previouslyReadBox.getValue() == "Specify manually") {
					pagesOnDateTxt.setDisable(false);
					dateReadPicker.setDisable(false);
					addBtn.setDisable(false);
				}
				else {
					pagesOnDateTxt.setDisable(true);
					dateReadPicker.setDisable(true);
					addBtn.setDisable(true);
				}
				pagesReadTxt.clear();
			}
			else if(statusBox.getValue() == "Currently Reading") {
				dateStartedPicker.setDisable(false);
				dateCompletedPicker.setDisable(true);
				pagesReadTxt.setDisable(false);				
				if(previouslyReadBox.getValue() == "Specify manually") {
					pagesOnDateTxt.setDisable(false);
					dateReadPicker.setDisable(false);
					addBtn.setDisable(false);
				}
				else {
					pagesOnDateTxt.setDisable(true);
					dateReadPicker.setDisable(true);
					addBtn.setDisable(true);
				}
				dateCompletedPicker.valueProperty().set(null);
			}
			else if(statusBox.getValue() == "Want to Read") {
				dateStartedPicker.setDisable(true);
				dateCompletedPicker.setDisable(true);
				pagesReadTxt.setDisable(true);
				previouslyReadBox.setDisable(true);
				dateReadPicker.setDisable(true);
				pagesOnDateTxt.setDisable(true);
				addBtn.setDisable(true);

				dateStartedPicker.valueProperty().set(null);
				dateCompletedPicker.valueProperty().set(null);
				pagesReadTxt.clear();
				previouslyReadBox.valueProperty().set(null);
				dateReadPicker.valueProperty().set(null);
				pagesOnDateTxt.clear();
				pagesOnDateToStore = new StringBuilder();
			}
		});

		previouslyReadBox.setOnAction(e -> {
			if (previouslyReadBox.getValue() == "Specify manually") {
				pagesOnDateTxt.setDisable(false);
				dateReadPicker.setDisable(false);
				addBtn.setDisable(false);
				if(statusBox.getValue() != null) {
					if( statusBox.getValue() == "Read Previously" && dateStartedPicker.getValue() != null && dateCompletedPicker.getValue() != null) {
						dateReadPicker.setDayCellFactory(picker -> new DateCell() { // force dates between dateStarted and dateCompleted
							public void updateItem(LocalDate date, boolean empty) {
								super.updateItem(date, empty);
								setDisable(empty || date.compareTo(dateStartedPicker.getValue()) < 0 || date.compareTo(dateCompletedPicker.getValue()) > 0);
							}
						});
					}
					else if( statusBox.getValue() == "Currently Reading" && dateStartedPicker.getValue() != null ) {
						dateReadPicker.setDayCellFactory(picker -> new DateCell() { // force dates between dateStarted and the current date
							public void updateItem(LocalDate date, boolean empty) {
								super.updateItem(date, empty);
								setDisable(empty || date.compareTo(dateStartedPicker.getValue()) < 0 || date.compareTo(LocalDate.now()) > 0);
							}
						});
					}
				}
			} else {
				dateReadPicker.setDisable(true);
				pagesOnDateTxt.setDisable(true);
				addBtn.setDisable(true);
			}
		});
		dateStartedPicker.setOnAction(e -> {
			if(dateCompletedPicker.getValue() == null) {
				dateCompletedPicker.setDayCellFactory(picker -> new DateCell() { // disable dates before dateStarted
					public void updateItem(LocalDate date, boolean empty) {
						super.updateItem(date, empty);
						setDisable(empty || date.compareTo(dateStartedPicker.getValue()) < 0 || date.compareTo(LocalDate.now()) > 0);
					}
				});
			} else previouslyReadBox.setDisable(false);
			if(statusBox.getValue() == "Currently Reading") previouslyReadBox.setDisable(false);
		});
		
		dateCompletedPicker.setOnAction(e -> {
			if(dateStartedPicker.getValue() == null) {
				dateStartedPicker.setDayCellFactory(picker -> new DateCell() { // disable dates after dateCompleted
					public void updateItem(LocalDate date, boolean empty) {
						super.updateItem(date, empty);
						setDisable(empty || date.compareTo(dateCompletedPicker.getValue()) > 0);
					}
				});
			} else previouslyReadBox.setDisable(false);
		});
		
		submitBtn.setOnAction(e -> {
			isValid = true;
			// ESSENTIAL FIELDS FOR ALL BOOKS
			String title = "";
			int pages = -1;
			// OPTIONAL FIELDS FOR ALL BOOKS
			String author = "";
			String publisher = "";
			int publicationYear = -1;
			String genre = "";
			String description = "";
			
			// GET VALUES OF THE FIELDS THAT APPLY TO ALL BOOKS
			if(statusBox.getValue() == null) {
				MAIN.createAlert(AlertType.ERROR, "Error", "Book validation unsuccessful", "Book status not selected.\nIt must be selected to add a book.");
				isValid = false;
			}
			
			if (!bookTitleTxt.getText().isEmpty())
				title = bookTitleTxt.getText();
			else {
				MAIN.createAlert(AlertType.ERROR, "Error", "Book validation unsuccessful", "Book title not entered.\nIt must be specified for all books.");
				isValid = false;
			}

			if (!bookPagesTxt.getText().isEmpty()) {
				try {
					pages = Integer.parseInt(bookPagesTxt.getText());
				} catch (NumberFormatException ex) {
					MAIN.createAlert(AlertType.ERROR, "Error", "Book validation unsuccessful", "Book pages invalid.\nIt must be an integer number");
					isValid = false;
				}
			} else {
				MAIN.createAlert(AlertType.ERROR, "Error", "Book validation unsuccessful", "Book pages not entered.\nIt must be specified for all books.");
				isValid = false;
			}
				
			if (!bookAuthorTxt.getText().isEmpty())
				author = bookAuthorTxt.getText();
			if (!bookPublisherTxt.getText().isEmpty())
				publisher = bookPublisherTxt.getText();
			if (!bookGenreTxt.getText().isEmpty())
				genre = bookGenreTxt.getText();
			if (!bookDescriptionTxt.getText().isEmpty())
				description = bookDescriptionTxt.getText();
			if (!bookPublicationYearTxt.getText().isEmpty()) {
				try {
					publicationYear = Integer.parseInt(bookPublicationYearTxt.getText());
				} catch (NumberFormatException ex) {
					MAIN.createAlert(AlertType.ERROR, "Error", "Book validation unsuccessful", "Book publication year is invalid.\nIt must be an integer number.");
					isValid = false;
				}
			}
			
			if (statusBox.getValue() == "Read Previously") {
				// ADDITIONAL ESSENTIAL FIELDS FOR STATUS 0
				String dateStarted = "";
				String dateCompleted = "";

				// GET VALUES OF THE ADDITIONAL ESSENTIAL FIELDS FOR BOOKS OF STATUS 0
				if (!(dateStartedPicker.getValue() == null))
					dateStarted = dateStartedPicker.getValue().format(DATE_FORMATTER);
				else {
					MAIN.createAlert(AlertType.ERROR, "Error", "Book validation unsuccessful", "Date started not entered.\nIt must be specified for 'Read Previously' books.");
					isValid = false;
				}

				if (!(dateCompletedPicker.getValue() == null))
					dateCompleted = dateCompletedPicker.getValue().format(DATE_FORMATTER);
				else {
					MAIN.createAlert(AlertType.ERROR, "Error", "Book validation unsuccessful", "Date completed not entered.\nIt must be specified for 'Read Previously' books.");
					isValid = false;
				}

				if ( !previouslyReadBox.isDisabled() && (!previouslyReadBox.getValue().equals("Distribute evenly") && !previouslyReadBox.getValue().equals("Specify manually")) ) {
					MAIN.createAlert(AlertType.ERROR, "Error", "Book validation unsuccessful", "'Previously Read Fields' selection not made.\nThis must be specified.");
					isValid = false;
				}
				
				if (isValid && BookDataValidation.validateFieldsBasic(title, pages, author, publisher, publicationYear, genre, description) && BookDataValidation.validateFieldsPrevious(pages, pagesReadOnADate)) {
					addNewPreviousBook(title, pages, dateStarted, dateCompleted, pagesReadOnADate, author, publisher, publicationYear, genre, description);
					MAIN.createAlert(AlertType.INFORMATION, "Validation Successful", "A new book has been created", "The book " + title + " has been successfully added to the system.");
					ViewAchievements.updateAchievements();
					clearFields(false);
				}
			}

			else if (statusBox.getValue() == "Currently Reading") {
				// ADDITIONAL ESSENTIAL FIELDS FOR STATUS 1
				String dateStarted = "";
				int pagesRead = -1;

				// GET VALUES OF THE ADDITIONAL ESSENTIAL FIELDS FOR BOOKS OF STATUS 1
				if (!(dateStartedPicker.getValue() == null))
					dateStarted = dateStartedPicker.getValue().format(DATE_FORMATTER);
				else {
					MAIN.createAlert(AlertType.ERROR, "Error", "Book validation unsuccessful", "Date started not entered.\nIt must be specified for 'Currently Reading' books.");
					isValid = false;
				}

				if (!pagesReadTxt.getText().isEmpty()) {
					try {
						pagesRead = Integer.parseInt(pagesReadTxt.getText());
					} catch (NumberFormatException ex) {
						MAIN.createAlert(AlertType.ERROR, "Error", "Book validation unsuccessful", "Pages read invalid.\nIt must be an integer number");
						isValid = false;
					}
				} else {
					MAIN.createAlert(AlertType.ERROR, "Error", "Book validation unsuccessful", "Pages read not entered.\nIt must be specified for 'Currently Reading' books.");
					isValid = false;
				}

				if ( !previouslyReadBox.isDisabled() && (!previouslyReadBox.getValue().equals("Distribute evenly") && !previouslyReadBox.getValue().equals("Specify manually")) ) {
					MAIN.createAlert(AlertType.ERROR, "Error", "Book validation unsuccessful", "'Previously Read Fields' selection not made.\nThis must be specified.");
					isValid = false;
				}

				if (isValid && BookDataValidation.validateFieldsBasic(title, pages, author, publisher, publicationYear, genre, description) && BookDataValidation.validateFieldsCurrent(pages, pagesRead, pagesReadOnADate)) {
					addNewCurrentBook(title, pages, dateStarted, pagesRead, pagesReadOnADate, author, publisher, publicationYear, genre, description);
					MAIN.createAlert(AlertType.INFORMATION, "Validation Successful", "A new book has been created", "The book " + title + " has been successfully added to the system.");
					ViewAchievements.updateAchievements();
					clearFields(false);
				}
			}

			else if (statusBox.getValue() == "Want to Read") {
				// NO ADDITIONAL FIELDS APPLY TO BOOKS OF STATUS 2
				if (isValid && BookDataValidation.validateFieldsBasic(title, pages, author, publisher, publicationYear, genre, description)) {
					addNewFutureBook(title, pages, author, publisher, publicationYear, genre, description);
					MAIN.createAlert(AlertType.INFORMATION, "Validation Successful", "A new book has been created", "The book " + title + " has been successfully added to the system.");
					ViewAchievements.updateAchievements();
					clearFields(false);	
				}
			}
		});
		submitBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
			if (ev.getCode() == KeyCode.ENTER) {
				submitBtn.fire();
				ev.consume(); 
			}
		});

		clearBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				clearFields(false);
			}
		});
		clearBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
			if (ev.getCode() == KeyCode.ENTER) {
				clearBtn.fire();
				ev.consume(); 
			}
		});
		
		backBtn.setOnAction(e -> {
			ManageBooksMenu.instantiate();
			e.consume();
		});
		backBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
			if (ev.getCode() == KeyCode.ENTER) {
				backBtn.fire();
				ev.consume(); 
			}
		});

		addBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				isValid = true;
				int pages = -1;
				String dateRead = "";
				int pagesOnDate = -1;

				if (!bookPagesTxt.getText().isEmpty()) {
					try {
						pages = Integer.parseInt(bookPagesTxt.getText());
					} catch (NumberFormatException e) {
						MAIN.createAlert(AlertType.ERROR, "Error", "Reading history validation unsuccessful", "Book pages invalid.\nMust be a positive integer.");
						isValid = false;
					}
				} else {
					MAIN.createAlert(AlertType.ERROR, "Error", "Reading history validation unsuccessful", "Book pages not entered.\nMust be specified for adding reading history to 'Previously Read' and 'Currently Reading' books.");
					isValid = false;
				}

				if (!(dateReadPicker.getValue() == null))
					dateRead = dateReadPicker.getValue().format(DATE_FORMATTER).toString();
				else {
					MAIN.createAlert(AlertType.ERROR, "Error", "Reading history validation unsuccessful", "Date started not entered.\nIt must be specified for 'Previously Read' and 'Currently Reading' books.");
					isValid = false;
				}
					
				if (!pagesOnDateTxt.getText().isEmpty()) {
					try {
						pagesOnDate = Integer.parseInt(pagesOnDateTxt.getText());
					} catch (NumberFormatException e) {
						MAIN.createAlert(AlertType.ERROR, "Error", "Reading history validation unsuccessful", "Pages read on date invalid.\nMust be a positive integer.");
						isValid = false;
					}
				} else {
					MAIN.createAlert(AlertType.ERROR, "Error", "Reading history validation unsuccessful", "Pages read on date not enterd.\nMust be specified for adding reading progress to 'Previously Read' and 'Currently Reading' books.");
					isValid = false;
				}

				if (statusBox.getValue() == "Read Previously") {
					// ADDITIONAL ESSENTIAL FIELDS FOR STATUS 0
					String dateStarted = dateStartedPicker.getValue().format(DATE_FORMATTER);
					String dateCompleted = dateCompletedPicker.getValue().format(DATE_FORMATTER);
					
					if(isValid) {
						if (previouslyReadBox.getValue().equals("Distribute evenly")) {
							pagesReadOnADate = distributeEvenly(pages, LocalDate.parse(dateStarted, DATE_FORMATTER), LocalDate.parse(dateCompleted, DATE_FORMATTER));
							MAIN.createAlert(AlertType.INFORMATION, "Validation Successful","New reading progress has been addded","Distributed " + pages + " pages over all days from " + dateStarted + " to " + dateCompleted);
						}
						if (previouslyReadBox.getValue().equals("Specify manually")) {
							pagesOnDateToStore.append(dateRead + "," + pagesOnDate + " ");
							pagesReadOnADate = pagesOnDateToStore.toString();	
							MAIN.createAlert(AlertType.INFORMATION, "Validation Successful","New reading progress has been addded","Added " + pagesOnDate + " pages on " + dateRead + " to the system.\n" + (pages - getTotalPagesReadOnDates(pagesReadOnADate)) + " pages remaining."); 
							System.out.println(pagesReadOnADate); // Test
							clearFields(true);
						}
						else MAIN.createAlert(AlertType.ERROR, "Error", "Reading history validation unsuccessful", "Previously read dates option not selected.\nMust select either 'Specify Manually' or 'Distribute Evenly'.");
					}
					else MAIN.createAlert(AlertType.ERROR, "Error", "Reading history validation unsuccessful", "Previously read dates option not selected.\nMust select either 'Specify Manually' or 'Distribute Evenly'.");
				}

				else if (statusBox.getValue() == "Currently Reading") {
					// ADDITIONAL ESSENTIAL FIELDS FOR STATUS 1
					String dateStarted = dateStartedPicker.getValue().format(DATE_FORMATTER);;
					int pagesRead = -1;

					if (!pagesReadTxt.getText().isEmpty()) {
						try {
							pagesRead = Integer.parseInt(pagesReadTxt.getText());
						} catch (NumberFormatException e) {
							MAIN.createAlert(AlertType.ERROR, "Error", "Reading history validation unsuccessful", "Pages read invalid.\nMust be a positive integer.");
							isValid = false;
						}
					} else {
						MAIN.createAlert(AlertType.ERROR, "Error", "Reading history validation unsuccessful", "Pages read not enterd.\nMust be specified for adding reading progress to 'Currently Reading' books.");
						isValid = false;
					}
					
					if(isValid) {
						if (previouslyReadBox.getValue().equals("Distribute evenly")) {
							pagesReadOnADate = distributeEvenly(pagesRead, LocalDate.parse(dateStarted, DATE_FORMATTER), LocalDate.now());
							MAIN.createAlert(AlertType.INFORMATION, "Validation Successful","New reading progress has been addded","Distributed " + pagesRead + " pages over all days from " + dateStarted + " to " + LocalDate.now().format(DATE_FORMATTER));
						}
						if (previouslyReadBox.getValue().equals("Specify manually")) {
							pagesOnDateToStore.append(dateRead + "," + pagesOnDate + " ");
							pagesReadOnADate = pagesOnDateToStore.toString();	
							MAIN.createAlert(AlertType.INFORMATION, "Validation Successful","New reading progress has been addded","Added " + pagesOnDate + " pages on " + dateRead + " to the system.");
							System.out.println(pagesReadOnADate); // Test
							clearFields(true);
						}
						else MAIN.createAlert(AlertType.ERROR, "Error", "Reading history validation unsuccessful", "Previously read dates option not selected.\nMust select either 'Specify Manually' or 'Distribute Evenly'.");
					}
					else MAIN.createAlert(AlertType.ERROR, "Error", "Reading history validation unsuccessful", "Previously read dates option not selected.\nMust select either 'Specify Manually' or 'Distribute Evenly'.");
				}
			}
		});
		addBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
			if (ev.getCode() == KeyCode.ENTER) {
				addBtn.fire();
				ev.consume(); 
			}
		});
		
		additionalBookInfoBtn.setOnAction(e -> {
			if (additionalBookInfoBtn.getText() == "View Optional Fields") {
                additionalBookInfoBtn.setText("Hide Optional Fields");
                changeVisibility(bookDescriptionTxt, bookGenreTxt, bookAuthorTxt, bookPublicationYearTxt, bookPublisherTxt);
            }
			else {
                additionalBookInfoBtn.setText("View Optional Fields");
                changeVisibility(bookDescriptionTxt, bookGenreTxt, bookAuthorTxt, bookPublicationYearTxt, bookPublisherTxt);
            }
		});
		additionalBookInfoBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
			if (ev.getCode() == KeyCode.ENTER) {
				additionalBookInfoBtn.fire();
				ev.consume(); 
			}
		});

		bookTitleTxt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (BookDataValidation.validBookDataString(bookTitleTxt.getText())) {
					bookTitleTxt.setStyle("-fx-text-inner-color: black;");
				} else {
					bookTitleTxt.setStyle("-fx-text-inner-color: red;");
				}
			}
		});

		bookAuthorTxt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (BookDataValidation.validBookDataString(bookAuthorTxt.getText())) {
					bookAuthorTxt.setStyle("-fx-text-inner-color: black;");
				} else {
					bookAuthorTxt.setStyle("-fx-text-inner-color: red;");
				}
			}
		});

		bookPublisherTxt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (BookDataValidation.validBookDataString(bookPublisherTxt.getText())) {
					bookPublisherTxt.setStyle("-fx-text-inner-color: black;");
				} else {
					bookPublisherTxt.setStyle("-fx-text-inner-color: red;");
				}
			}
		});

		bookGenreTxt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (BookDataValidation.validBookDataString(bookGenreTxt.getText())) {
					bookGenreTxt.setStyle("-fx-text-inner-color: black;");
				} else {
					bookGenreTxt.setStyle("-fx-text-inner-color: red;");
				}
			}
		});

		bookDescriptionTxt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (BookDataValidation.validBookDataString(bookDescriptionTxt.getText())) {
					bookDescriptionTxt.setStyle("-fx-text-inner-color: black;");
				} else {
					bookDescriptionTxt.setStyle("-fx-text-inner-color: red;");
				}
			}
		});

		bookPagesTxt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (BookDataValidation.validBookDataInteger(bookPagesTxt.getText())) {
					bookPagesTxt.setStyle("-fx-text-inner-color: black;");
				} else {
					bookPagesTxt.setStyle("-fx-text-inner-color: red;");
				}
			}
		});

		pagesReadTxt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (BookDataValidation.validBookDataInteger(pagesReadTxt.getText())) {
					pagesReadTxt.setStyle("-fx-text-inner-color: black;");
				} else {
					pagesReadTxt.setStyle("-fx-text-inner-color: red;");
				}
			}
		});

		pagesOnDateTxt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (BookDataValidation.validBookDataInteger(pagesOnDateTxt.getText())) {
					pagesOnDateTxt.setStyle("-fx-text-inner-color: black;");
				} else {
					pagesOnDateTxt.setStyle("-fx-text-inner-color: red;");
				}
			}
		});

		bookPublicationYearTxt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (BookDataValidation.validBookDataInteger(bookPublicationYearTxt.getText())) {
					bookPublicationYearTxt.setStyle("-fx-text-inner-color: black;");
				} else {
					bookPublicationYearTxt.setStyle("-fx-text-inner-color: red;");
				}
			}
		});
	}

	private void clearFields(Boolean readProgOnly) {	
		if(readProgOnly) {
			dateReadPicker.valueProperty().set(null);
			pagesOnDateTxt.clear();
		}
		else {
			statusBox.valueProperty().set(null);
			bookTitleTxt.clear();
			bookPagesTxt.clear();
			dateStartedPicker.valueProperty().set(null);
			dateCompletedPicker.valueProperty().set(null);
			pagesReadTxt.clear();
			previouslyReadBox.valueProperty().set(null);
			
			bookAuthorTxt.clear();
			bookPublisherTxt.clear();
			bookPublicationYearTxt.clear();
			bookGenreTxt.clear();
			bookDescriptionTxt.clear();
	
			pagesReadOnADate = "";
			pagesOnDateToStore = new StringBuilder();
	
			dateStartedPicker.setDisable(true);
			dateCompletedPicker.setDisable(true);
			pagesReadTxt.setDisable(true);
			previouslyReadBox.setDisable(true);
			dateReadPicker.setDisable(true);
			pagesOnDateTxt.setDisable(true);
			addBtn.setDisable(true);
			
			dateStartedPicker.setDayCellFactory(picker -> new DateCell() { // disable future dates
				public void updateItem(LocalDate date, boolean empty) {
					super.updateItem(date, empty);
					setDisable(empty || date.compareTo(LocalDate.now()) > 0);
				}
			});
			
			dateCompletedPicker.setDayCellFactory(picker -> new DateCell() { // disable future dates
				public void updateItem(LocalDate date, boolean empty) {
					super.updateItem(date, empty);
					setDisable(empty || date.compareTo(LocalDate.now()) > 0);
				}
			});
		}
	}

	public static int getHighestBookID() {
		int counter = 0;
		for (storage.bookData b : alldata.bookStore) {
			if (b.bookID > counter)
				counter = b.bookID;
		}
		return counter;
	}
	
	//Returns the sum of the int parts of pagesReadOnADate
	public static int getTotalPagesReadOnDates(String pagesReadOnADate) {
		int counter = 0;
		if(!pagesReadOnADate.equals("")) {
			String pagesReadOnADateObjs[] = pagesReadOnADate.split(" ");
			for(String s : pagesReadOnADateObjs) {
				String dateValuePairObj[] = s.split(",");
				try {
					counter += Integer.parseInt(dateValuePairObj[1]);
				} catch (ArrayIndexOutOfBoundsException e) {
					return -1;
				}
			}
		}
		return counter;
	}

	public String distributeEvenly(int pages, LocalDate startDate, LocalDate endDate) {		
		StringBuilder pagesOnDateToStore = new StringBuilder();

		Date date1 = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date date2 = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		int days = 1 + (int) TimeUnit.DAYS.convert(date2.getTime() - date1.getTime(), TimeUnit.MILLISECONDS);

		int pagesOnEachDay = pages / days;
		if( !(pages / days == 0) ) pagesOnEachDay++;
		if(pagesOnEachDay * (days-1) >= pages){
			pagesOnEachDay--;
		}
		int pagesOnLastDay = pages - (pagesOnEachDay * (days-1));
		if (days > pages) {
			pagesOnEachDay = 0;
			pagesOnLastDay = pages;
		}
		
		System.out.println(days); //test
		System.out.println(pagesOnEachDay);
		System.out.println(pagesOnLastDay);
		
		LocalDate current_day = startDate;
		Date date = null;
		String toReturn;
		
		if(pagesOnEachDay > 0)
			while ( !(current_day.isAfter(endDate)) ) {
				date = Date.from(current_day.atStartOfDay(ZoneId.systemDefault()).toInstant());
				if(endDate.isEqual(current_day)) {
					toReturn = DATE_FORMAT.format(date).toString() + "," + Integer.toString(pagesOnLastDay) + " ";
				} else {
					toReturn = DATE_FORMAT.format(date).toString() + "," + Integer.toString(pagesOnEachDay) + " ";
				}
				current_day = current_day.plusDays(1);
				pagesOnDateToStore.append(toReturn);
			}
		else pagesOnDateToStore.append(DATE_FORMAT.format(Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant())).toString() + "," + Integer.toString(pagesOnLastDay));
		
		System.out.println(pagesOnDateToStore.toString()); //test
		return pagesOnDateToStore.toString();
	}

	// Invert visibility of passed nodes
	private void changeVisibility(Node... nodesToChange) {
		for (Node node : nodesToChange) {
			if (node.isVisible())
				node.setVisible(false);
			else
				node.setVisible(true);
		}
	}
}
