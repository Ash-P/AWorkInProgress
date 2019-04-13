import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.joda.time.*;

// Move author genre to additional info, publisher, store all additional info under Description, 
public class AddABook {

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private boolean validData = true;

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
	private final Button addBtn = new Button();
	// OPTIONAL
	private final Button additionalBookInfoBtn = new Button();
	private final TextField bookAuthorTxt = new TextField();
	private final TextField bookPublisherTxt = new TextField();
	private final TextField bookPublicationYearTxt = new TextField();
	private final TextField bookGenreTxt = new TextField();
	private final TextArea bookDescriptionTxt = new TextArea();

	private final Button submitBtn = new Button();
	private final Button clearBtn = new Button();

	public static Button back = new Button("Back");
	
	private Group primaryGroup = new Group();
	private Scene primaryScene;

	StringBuilder pagesOnDateToStore = new StringBuilder();
	// manual specified pagesReadOnADate entries are stored here when the addBtn is
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

		addNodeToRootGroup(grid, submitBtn, clearBtn, back);
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
		dateReadPicker.setEditable(false); // forces a date to be selected from the calendar (prevents future dates
											// being entered manually)
		dateReadPicker.setDayCellFactory(picker -> new DateCell() { // disable future dates
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				setDisable(empty || date.compareTo(LocalDate.now()) > 0);
			}
		});
		dateReadPicker.setDisable(true);
		GridPane.setConstraints(dateReadPicker, 0, 8);

		pagesOnDateTxt.setPromptText("Pages Read on Date");
		pagesOnDateTxt.setDisable(true);
		GridPane.setConstraints(pagesOnDateTxt, 0, 9);

		addBtn.setText("Add");
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

		submitBtn.setText("Submit");
		submitBtn.setLayoutX(400);
		submitBtn.setLayoutY(330);

		clearBtn.setText("Clear");
		clearBtn.setLayoutX(460);
		clearBtn.setLayoutY(330);

		back.setLayoutX(15);
		back.setLayoutY(390);
		
		
		changeVisibility(bookDescriptionTxt, bookAuthorTxt, bookPublisherTxt, bookPublicationYearTxt, bookGenreTxt);

		setupGridElement(statusBox, bookTitleTxt, bookPagesTxt, dateStartedPicker, dateCompletedPicker, pagesReadTxt,
				previouslyReadBox, dateReadPicker, pagesOnDateTxt, addBtn, additionalBookInfoBtn, bookAuthorTxt,
				bookPublisherTxt, bookPublicationYearTxt, bookGenreTxt, bookDescriptionTxt);
	}

	private void addNewPreviousBook(String title, int pages, String dateStarted, String dateCompleted,
			String pagesReadOnADate, String author, String publisher, int publicationYear, String genre,
			String description) {
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

	private void addNewCurrentBook(String title, int pages, String dateStarted, int pagesRead, String pagesReadOnADate,
			String author, String publisher, int publicationYear, String genre, String description) {
		storage.bookData newBook = new storage.bookData();

		newBook.bookID = getHighestBookID() + 1;
		newBook.status = 1;
		newBook.dateAdded = DATE_FORMAT.format(new Date());

		newBook.title = title;
		newBook.pages = pages;
		newBook.dateStarted = dateStarted;
		newBook.dateCompleted = null;
		newBook.pagesRead = pagesRead;
		newBook.pagesReadOnADate = pagesReadOnADate;

		newBook.author = author;
		newBook.publisher = publisher;
		newBook.publicationYear = publicationYear;
		newBook.genre = genre;
		newBook.description = description;

		alldata.bookStore.add(newBook);
		alldata.userStore.totalPagesRead += pages;

		AddATarget.updateTargets(newBook.pagesRead, newBook.bookID, false);
	}

	private void addNewFutureBook(String title, int pages, String author, String publisher, int publicationYear,
			String genre, String description) {
		storage.bookData newBook = new storage.bookData();

		newBook.bookID = getHighestBookID() + 1;
		newBook.status = 2;
		newBook.dateAdded = DATE_FORMAT.format(new Date());

		newBook.title = title;
		newBook.pages = pages;
		newBook.dateStarted = null;
		newBook.dateCompleted = null;
		newBook.pagesRead = 0;
		newBook.pagesReadOnADate = null;

		newBook.author = author;
		newBook.publisher = publisher;
		newBook.publicationYear = publicationYear;
		newBook.genre = genre;
		newBook.description = description;

		alldata.bookStore.add(newBook);
	}

	// Setup all action handlers
	private void setupHandlers() {
		statusBox.setOnAction(e -> {
			if (statusBox.getValue() == "Read Previously") {
				dateStartedPicker.setDisable(false);
				dateCompletedPicker.setDisable(false);
				pagesReadTxt.setDisable(true);
				previouslyReadBox.setDisable(false);
				if (!(previouslyReadBox.getValue() == "Distribute evenly")) {
					pagesOnDateTxt.setDisable(false);
					dateReadPicker.setDisable(false);
					addBtn.setDisable(false);
				}
				pagesReadTxt.clear();
			} else if (statusBox.getValue() == "Currently Reading") {
				dateStartedPicker.setDisable(false);
				dateCompletedPicker.setDisable(true);
				pagesReadTxt.setDisable(false);
				previouslyReadBox.setDisable(false);

				dateCompletedPicker.valueProperty().set(null);

			} else if (statusBox.getValue() == "Want to Read") {
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
			} else {
				dateReadPicker.setDisable(true);
				pagesOnDateTxt.setDisable(true);
				addBtn.setDisable(true);
			}
		});

		submitBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
		
		
				Boolean isValid = true;
				// ESSENTIAL FIELDS FOR ALL BOOKS
				int status = -1;
				String title = null;
				int pages = -1;
				// OPTIONAL FIELDS FOR ALL BOOKS
				String author = null;
				String publisher = null;
				int publicationYear = -1;
				String genre = null;
				String description = null;

				
				
				// GET VALUES OF THE FIELDS THAT APPLY TO ALL BOOKS
				if (!bookTitleTxt.getText().isEmpty())
					title = bookTitleTxt.getText();
				else
					isValid = false;

				if (!bookPagesTxt.getText().isEmpty()) {
					try {
						pages = Integer.parseInt(bookPagesTxt.getText());
					} catch (NumberFormatException e) {
						isValid = false;
					}
				} else
					isValid = false;

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
					} catch (NumberFormatException e) {
						isValid = false;
					}
				}

				if (statusBox.getValue() == "Read Previously") {
					// ADDITIONAL ESSENTIAL FIELDS FOR STATUS 0
					String dateStarted = null;
					String dateCompleted = null;
					String pagesReadOnADate = null;

					// GET VALUES OF THE ADDITIONAL ESSENTIAL FIELDS FOR BOOKS OF STATUS 0
					if (!(dateStartedPicker.getValue() == null))
						dateStarted = dateStartedPicker.getValue().format(DATE_FORMATTER);
					else
						isValid = false;

					if (!(dateCompletedPicker.getValue() == null))
						dateCompleted = dateCompletedPicker.getValue().format(DATE_FORMATTER);
					else
						isValid = false;

					if (isValid && previouslyReadBox.getValue().equals("Distribute evenly")) {
						try {
							pagesReadOnADate = distributeEvenly(pages, LocalDate.parse(dateStarted, DATE_FORMATTER),
									LocalDate.parse(dateCompleted, DATE_FORMATTER));
						} catch (NullPointerException e) {
							System.out.println("Null exception");
						}
					}

					else if (previouslyReadBox.getValue().equals("Specify manually")) {
						if (!pagesOnDateToStore.toString().isEmpty())
							pagesReadOnADate = pagesOnDateToStore.toString();
						else
							isValid = false;
					} else
						isValid = false;

					if (isValid
							&& BookDataValidation.validateFieldsBasic(title, pages, author, publisher, publicationYear,
									genre, description)
							&& BookDataValidation.validateFieldsPrevious(pages, dateStarted, dateCompleted,
									pagesReadOnADate)) {
						addNewPreviousBook(title, pages, dateStarted, dateCompleted,
						pagesReadOnADate, author, publisher, publicationYear, genre, description);
						AchievementGUI.updateTargets();
						createAlert(AlertType.INFORMATION, "Validation Successful", "A new book has been created",
								"The book " + title + " has been successfully added to the system.");
						clearFields();
					} else
						createAlert(AlertType.ERROR, "Validation Unsuccessful", "Validation Unsuccessful",
								"The book has not been successfully validated.");
				}

				else if (statusBox.getValue() == "Currently Reading") {
					// ADDITIONAL ESSENTIAL FIELDS FOR STATUS 1
					String dateStarted = null;
					int pagesRead = -1;
					String pagesReadOnADate = null;

					// GET VALUES OF THE ADDITIONAL ESSENTIAL FIELDS FOR BOOKS OF STATUS 1
					if (!(dateStartedPicker.getValue() == null))
						dateStarted = dateStartedPicker.getValue().format(DATE_FORMATTER);
					else
						isValid = false;

					if (!pagesReadTxt.getText().isEmpty()) {
						try {
							pagesRead = Integer.parseInt(pagesReadTxt.getText());
						} catch (NumberFormatException e) {
							isValid = false;
						}
					} else
						isValid = false;

					if (isValid && previouslyReadBox.getValue().equals("Distribute evenly")) {
						pagesReadOnADate = distributeEvenly(pagesRead, LocalDate.parse(dateStarted, DATE_FORMATTER),
								LocalDate.now());
					} else if (previouslyReadBox.getValue().equals("Specify manually")) {
						if (!pagesOnDateToStore.toString().isEmpty())
							pagesReadOnADate = pagesOnDateToStore.toString();
						else
							isValid = false;
					} else
						isValid = false;

					if (isValid
							&& BookDataValidation.validateFieldsBasic(title, pages, author, publisher, publicationYear,
									genre, description)
							&& BookDataValidation.validateFieldsCurrent(pages, pagesRead, pagesReadOnADate)) {
						addNewCurrentBook(title, pages, dateStarted, pagesRead, pagesReadOnADate,
						author, publisher, publicationYear, genre, description);
						AchievementGUI.updateTargets();
						createAlert(AlertType.INFORMATION, "Validation Successful", "A new book has been created",
								"The book " + title + " has been successfully added to the system.");
						clearFields();
					} else
						createAlert(AlertType.ERROR, "Validation Unsuccessful", "Validation Unsuccessful",
								"The book has not been successfully validated.");
				}

				else if (statusBox.getValue() == "Want to Read") {
					// NO ADDITIONAL FIELDS APPLY TO BOOKS OF STATUS 2

					if (isValid && BookDataValidation.validateFieldsBasic(title, pages, author, publisher,
							publicationYear, genre, description)) {
						addNewFutureBook(title, pages, author, publisher, publicationYear, genre, description);
						AchievementGUI.updateTargets();
						createAlert(AlertType.INFORMATION, "Validation Successful", "A new book has been created",
								"The book " + title + " has been successfully added to the system.");
						clearFields();
					} else
						createAlert(AlertType.ERROR, "Validation Unsuccessful", "Validation Unsuccessful",
								"The book has not been successfully validated.");
				}
			
			}
		

		});

		clearBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				clearFields();
			}
		});

		addBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Boolean isValid = true;
				int status = -1;
				String title = null;
				int pages = -1;
				String dateRead = null;
				int pagesOnDate = -1;

				if (!bookPagesTxt.getText().isEmpty()) {
					try {
						pages = Integer.parseInt(bookPagesTxt.getText());
					} catch (NumberFormatException e) {
						isValid = false;
					}
				} else
					isValid = false;

				if (!(dateReadPicker.getValue() == null))
					dateRead = dateReadPicker.getValue().format(DATE_FORMATTER).toString();
				else
					isValid = false;

				if (!pagesOnDateTxt.getText().isEmpty()) {
					try {
						pagesOnDate = Integer.parseInt(pagesOnDateTxt.getText());
					} catch (NumberFormatException e) {
						isValid = false;
					}
				} else
					isValid = false;

				if (statusBox.getValue() == "Read Previously") {
					String dateStarted = null;
					String dateCompleted = null;
					String pagesReadOnADate = null;

					// GET VALUES OF THE ADDITIONAL ESSENTIAL FIELDS FOR BOOKS OF STATUS 0
					if (!(dateStartedPicker.getValue() == null))
						dateStarted = dateStartedPicker.getValue().format(DATE_FORMATTER);
					else
						isValid = false;

					if (!(dateCompletedPicker.getValue() == null))
						dateCompleted = dateCompletedPicker.getValue().format(DATE_FORMATTER);
					else
						isValid = false;

					if (isValid && previouslyReadBox.getValue().equals("Distribute evenly")) {
						pagesReadOnADate = distributeEvenly(pages, LocalDate.parse(dateStarted, DATE_FORMATTER),
								LocalDate.parse(dateCompleted, DATE_FORMATTER));
					} /*else if (previouslyReadBox.getValue().equals("Specify manually")) {
						if (isValid && !pagesOnDateToStore.toString().isEmpty())// CHANGED HERE, ISSUE: Checking if pagesReadOnADate isnt empty 
																								when it should be at this point
							pagesReadOnADate = pagesOnDateToStore.toString();
						else
							isValid = false;
					} //else
						//isValid = false;
					*/
					if (isValid && BookDataValidation.validateProgressFieldsPast(pages, dateStarted, dateCompleted,
							dateRead, pagesOnDate, pagesReadOnADate)) {
						pagesOnDateToStore.append(dateRead + "," + pagesOnDate + " ");
						createAlert(AlertType.INFORMATION, "Validation Successful",
								"New reading progress has been addded",
								"Added " + pagesOnDate + " pages to the system.");
						System.out.println(dateRead + "," + pagesOnDate + " "); // Test
						clearFields();
					} else
						createAlert(AlertType.ERROR, "Validation Unsuccessful", "Validation Unsuccessful",
								"Reading progress has not been successfully validated.");
				}

				else if (statusBox.getValue() == "Currently Reading") {
					// ADDITIONAL ESSENTIAL FIELDS FOR STATUS 1
					String dateStarted = null;
					int pagesRead = -1;
					String pagesReadOnADate = null;

					// GET VALUES OF THE ADDITIONAL ESSENTIAL FIELDS FOR BOOKS OF STATUS 1
					if (!(dateStartedPicker.getValue() == null))
						dateStarted = dateStartedPicker.getValue().format(DATE_FORMATTER);
					else
						isValid = false;

					if (!pagesReadTxt.getText().isEmpty()) {
						try {
							pagesRead = Integer.parseInt(pagesReadTxt.getText());
						} catch (NumberFormatException e) {
							isValid = false;
						}
					} else
						isValid = false;

					if (isValid && previouslyReadBox.getValue().equals("Distribute evenly")) {
						pagesReadOnADate = distributeEvenly(pagesRead, LocalDate.parse(dateStarted, DATE_FORMATTER),
								LocalDate.now());
					} else if (previouslyReadBox.getValue().equals("Specify manually")) {
						if (!pagesOnDateToStore.toString().isEmpty())
							pagesReadOnADate = pagesOnDateToStore.toString();
						else
							isValid = false;
					} else
						isValid = false;

					if (isValid && BookDataValidation.validateProgressFieldsCurrent(pages, dateStarted, pagesRead,
							dateRead, pagesOnDate, pagesReadOnADate)) {
						pagesOnDateToStore.append(dateRead + "," + pagesOnDate + " ");
						createAlert(AlertType.INFORMATION, "Validation Successful",
								"New reading progress has been addded",
								"Added " + pagesOnDate + " pages to the system.");
						System.out.println(dateRead + "," + pagesOnDate + " "); // Test
						clearFields();
					} else
						createAlert(AlertType.ERROR, "Validation Unsuccessful", "Validation Unsuccessful",
								"Reading progress has not been successfully validated.");
				}
			}
		});

		additionalBookInfoBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (additionalBookInfoBtn.getText() == "View Optional Fields")
					additionalBookInfoBtn.setText("Hide Optional Fields");
				else
					additionalBookInfoBtn.setText("View Optional Fields");
				changeVisibility(bookDescriptionTxt, bookGenreTxt, bookAuthorTxt, bookPublicationYearTxt,
						bookPublisherTxt);
			}
		});

		bookTitleTxt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (BookDataValidation.validBookDataString(bookTitleTxt.getText())) {
					validData = true;
					bookTitleTxt.setStyle("-fx-text-inner-color: black;");
				} else {
					validData = false;
					bookTitleTxt.setStyle("-fx-text-inner-color: red;");
				}
			}
		});

		bookAuthorTxt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (BookDataValidation.validBookDataString(bookAuthorTxt.getText())) {
					validData = true;
					bookAuthorTxt.setStyle("-fx-text-inner-color: black;");
				} else {
					validData = false;
					bookAuthorTxt.setStyle("-fx-text-inner-color: red;");
				}
			}
		});

		bookPublisherTxt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (BookDataValidation.validBookDataString(bookPublisherTxt.getText())) {
					validData = true;
					bookPublisherTxt.setStyle("-fx-text-inner-color: black;");
				} else {
					validData = false;
					bookPublisherTxt.setStyle("-fx-text-inner-color: red;");
				}
			}
		});

		bookGenreTxt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (BookDataValidation.validBookDataString(bookGenreTxt.getText())) {
					validData = true;
					bookGenreTxt.setStyle("-fx-text-inner-color: black;");
				} else {
					validData = false;
					bookGenreTxt.setStyle("-fx-text-inner-color: red;");
				}
			}
		});

		bookDescriptionTxt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (BookDataValidation.validBookDataString(bookDescriptionTxt.getText())) {
					validData = true;
					bookDescriptionTxt.setStyle("-fx-text-inner-color: black;");
				} else {
					validData = false;
					bookDescriptionTxt.setStyle("-fx-text-inner-color: red;");
				}
			}
		});

		bookPagesTxt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (BookDataValidation.validBookDataInteger(bookPagesTxt.getText())) {
					validData = true;
					bookPagesTxt.setStyle("-fx-text-inner-color: black;");
				} else {
					validData = false;
					bookPagesTxt.setStyle("-fx-text-inner-color: red;");
				}
			}
		});

		pagesReadTxt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (BookDataValidation.validBookDataInteger(pagesReadTxt.getText())) {
					validData = true;
					pagesReadTxt.setStyle("-fx-text-inner-color: black;");
				} else {
					validData = false;
					pagesReadTxt.setStyle("-fx-text-inner-color: red;");
				}
			}
		});

		pagesOnDateTxt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (BookDataValidation.validBookDataInteger(pagesOnDateTxt.getText())) {
					validData = true;
					pagesOnDateTxt.setStyle("-fx-text-inner-color: black;");
				} else {
					validData = false;
					pagesOnDateTxt.setStyle("-fx-text-inner-color: red;");
				}
			}
		});

		bookPublicationYearTxt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (BookDataValidation.validBookDataInteger(bookPublicationYearTxt.getText())) {
					validData = true;
					bookPublicationYearTxt.setStyle("-fx-text-inner-color: black;");
				} else {
					validData = false;
					bookPublicationYearTxt.setStyle("-fx-text-inner-color: red;");
				}
			}
		});
		
		back.setOnAction(e -> {
			ManageBooksMenu.instantiate();
		});
	}

	private void clearFields() {
		statusBox.valueProperty().set(null);
		bookTitleTxt.clear();
		bookPagesTxt.clear();
		dateStartedPicker.valueProperty().set(null);
		dateCompletedPicker.valueProperty().set(null);
		pagesReadTxt.clear();
		previouslyReadBox.valueProperty().set(null);
		dateReadPicker.valueProperty().set(null);
		pagesOnDateTxt.clear();
		bookAuthorTxt.clear();
		bookPublisherTxt.clear();
		bookPublicationYearTxt.clear();
		bookGenreTxt.clear();
		bookDescriptionTxt.clear();

		pagesOnDateToStore = new StringBuilder();

		dateStartedPicker.setDisable(false);
		dateCompletedPicker.setDisable(false);
		pagesReadTxt.setDisable(false);
		previouslyReadBox.setDisable(true);
		dateReadPicker.setDisable(true);
		pagesOnDateTxt.setDisable(true);
		addBtn.setDisable(true);
	}

	private static void createAlert(AlertType type, String title, String header, String content) {
		Alert dialog = new Alert(type);
		dialog.setTitle(title);
		dialog.setHeaderText(header);
		dialog.setContentText(content);
		dialog.showAndWait(); // the dialog must be confirmed before continuing
	}

	public static int getHighestBookID() {
		int counter = 0;
		for (storage.bookData b : alldata.bookStore) {
			if (b.bookID > counter)
				counter = b.bookID;
		}
		return counter;
	}

	

	// TODO: Infinite loop when setting current day as end date, add pages of last
	// day
	public String distributeEvenly(int pages, LocalDate startDate, LocalDate endDate) {

		StringBuilder pagesOnDateToStore = new StringBuilder();

		Date dateEE = java.util.Date.from(endDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		System.out.println(dateEE.toString());

		DateTime dateE = new DateTime(dateEE);

		Date dateSS = java.util.Date.from(startDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		DateTime dateS = new DateTime(dateSS);
		int days = Days.daysBetween(dateS, dateE).getDays();
		int pagesOnEachDay;
		pagesOnEachDay = pages / days;

		DateTime current_day = new DateTime(dateS);

		// DateTimeFormatter standardFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		SimpleDateFormat standardFormat = new SimpleDateFormat("dd-MM-yyyy");
		String date = null;
		Date finalFormat = null;
		String toReturn;

		int pagesOnLastDay = pages - (pagesOnEachDay * (days - 1));

		while (!(current_day.isEqual(dateE))) {

			date = Integer.toString(current_day.getDayOfMonth()) + "-" + Integer.toString(current_day.getMonthOfYear())
					+ "-" + Integer.toString(current_day.getYear());
			// dateTest = LocalDate.parse(date, dateFormat);

			try {
				finalFormat = standardFormat.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			current_day = current_day.plusDays(1);
			
			if((dateE.isEqual(current_day.plusDays(1)))) {
				toReturn = standardFormat.format(finalFormat).toString() + "," + Integer.toString(pagesOnLastDay) + " ";
			}else {
				toReturn = standardFormat.format(finalFormat).toString() + "," + Integer.toString(pagesOnEachDay) + " ";
			}
		
			// System.out.println(toReturn);
			pagesOnDateToStore.append(toReturn);
		}
	
		System.out.println(pagesOnDateToStore.toString());
		
		
		
		
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