import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.joda.time.*;

// Move author genre to additional info, publisher, store all additional info under Description, 
public class AddNewBook extends Application {

	private boolean validData = true;
	
	private final ComboBox<String> status = new ComboBox<>();
	
	//private final Label bookPublicationDateLbl = new Label();
	//private final Label bookReadingStartDateLbl = new Label();

	private final TextField bookTitleTxt = new TextField();
	private final TextField bookAuthorTxt = new TextField();
	private final TextField bookPublisherTxt = new TextField();
	private final TextField bookPrintLengthTxt = new TextField(); // pretty crude way of accepting integers - find
																	// better node
	private final TextField bookGenreTxt = new TextField();
	private final TextField pagesOnDate = new TextField();
	
	
	private final TextArea bookDescriptionTxt = new TextArea();

	private final ComboBox<String> previouslyReadBtn = new ComboBox<>();
	//private final Button previouslyReadBtn = new Button();
	private final Button additionalBookInfoBtn = new Button();
	private final Button addPreviouslyReadPagesBtn = new Button();
	private final Button submitBtn = new Button();
	private final Button clearBtn = new Button();
	
	private final DatePicker dateStarted = new DatePicker();
	private final DatePicker dateCompleted = new DatePicker(); // optional
	private final TextField bookPublicationDate = new TextField();

	private final DatePicker dateRead = new DatePicker();
	private final TextField pagesReadTxt = new TextField(); // pretty crude way of accepting integers - find better node

	private Group primaryGroup = new Group();
	private Scene primaryScene;

	StringBuilder pagesOnDateToStore = new StringBuilder();
	
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
		status.setPromptText("Status");
		status.getItems().add("Read Previously");
		status.getItems().add("Currently Reading");
		status.getItems().add("Want to Read");
		
		
		previouslyReadBtn.getItems().add("Specify Manually");
		previouslyReadBtn.getItems().add("Distribute evenly");
		
		GridPane.setConstraints(status, 0, 1);
		// Default nodes
		bookTitleTxt.setPromptText("Book Title");
		GridPane.setConstraints(bookTitleTxt, 0, 2);

		bookAuthorTxt.setPromptText("Author");
		GridPane.setConstraints(bookAuthorTxt, 7, 2);
		
		bookPublisherTxt.setPromptText("Publisher");
		GridPane.setConstraints(bookPublisherTxt, 7, 3);

		bookPrintLengthTxt.setPromptText("Pages");
		GridPane.setConstraints(bookPrintLengthTxt, 0, 3);

		bookGenreTxt.setPromptText("Genre");
		GridPane.setConstraints(bookGenreTxt, 7, 5);
	

		

		GridPane.setConstraints(bookPublicationDate, 7, 4);
		bookPublicationDate.setPromptText("Publication Year");
		//bookReadingStartDateLbl.setText("Date Started");
		//GridPane.setConstraints(bookReadingStartDateLbl, 0, 4);
		
		
		dateStarted.setPromptText("Date Started");
		GridPane.setConstraints(dateStarted, 0, 4);
		dateCompleted.setPromptText("Date Completed");
		GridPane.setConstraints(dateCompleted, 0, 5);
		
		GridPane.setConstraints(previouslyReadBtn, 0, 7);
		previouslyReadBtn.setPromptText("Specify Manually");
		previouslyReadBtn.setDisable(true);
		submitBtn.setText("Submit");
		submitBtn.setLayoutX(400);
		submitBtn.setLayoutY(280);
		//GridPane.setConstraints(submitBtn, 7, 15);
		
		clearBtn.setText("Clear");
		clearBtn.setLayoutX(460);
		clearBtn.setLayoutY(280);
		//GridPane.setConstraints(clearBtn, 8, 15);
		
		//addNodeToRootGroup(submitBtn, clearBtn);
		
		// Additional book info nodes
		additionalBookInfoBtn.setText("Additional info");
		GridPane.setConstraints(additionalBookInfoBtn, 7, 1);
		bookDescriptionTxt.setPrefRowCount(3);





		bookDescriptionTxt.setPromptText("Book Description...");
		GridPane.setConstraints(bookDescriptionTxt, 7, 6);
		bookDescriptionTxt.setPrefColumnCount(16);
		
		changeVisibility(bookDescriptionTxt, bookAuthorTxt, bookPublisherTxt, bookPublicationDate, bookGenreTxt);
		
		// Previously/currently reading nodes
		GridPane.setConstraints(dateRead, 0, 8);
		dateRead.setPromptText("Date Read");
		
		dateRead.setDisable(true);
		GridPane.setConstraints(pagesOnDate, 0, 9);
		pagesOnDate.setPromptText("Pages Read on Date");
		
		pagesOnDate.setDisable(true);
		pagesReadTxt.setPromptText("Pages Read");
		GridPane.setConstraints(pagesReadTxt, 0, 6);

		addPreviouslyReadPagesBtn.setText("Add");
		GridPane.setConstraints(addPreviouslyReadPagesBtn, 1, 9);
		addPreviouslyReadPagesBtn.setDisable(true);
		//changeVisibility(dateRead, pagesReadTxt, addPreviouslyReadPagesBtn);

		
		setupGridElement(bookTitleTxt, bookAuthorTxt, bookPublisherTxt, bookPrintLengthTxt, bookGenreTxt,status,
		
				bookPublicationDate, dateStarted, previouslyReadBtn,dateRead, dateCompleted, 
				pagesReadTxt, additionalBookInfoBtn, addPreviouslyReadPagesBtn, bookDescriptionTxt, pagesOnDate);
	}

	public void storeData() {
		storage.bookData toStore = new storage.bookData();

		//gen random number
		//toStore.bookID =
		toStore.title = bookTitleTxt.getText();
		toStore.dateStarted = dateStarted.getValue().toString();
		toStore.author = bookAuthorTxt.getText();
		
		String previousPagesRead;
	}
	
	
	public void storeUserData() {
		storage.userData toUpdate = new storage.userData();
		int x = alldata.userStore.totalBooksRead;
		int y = alldata.userStore.totalPagesRead;
		
		toUpdate.totalBooksRead = toUpdate.totalBooksRead + x;
		toUpdate.totalBooksRead = toUpdate.totalBooksRead + y;
		
		x = x + toUpdate.totalBooksRead; 
		y = y + toUpdate.totalPagesRead;
	}
	
	// Setup all action handles
	private void setupHandles() {
		addPreviouslyReadPagesBtn.setOnAction(e->{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//dateRead.getValue().format();
			
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			String toReturn = dateRead.getValue().format(dateFormat).toString() + "," + pagesOnDate.getText() + " ";
			
			pagesOnDateToStore.append(toReturn);
			System.out.println(toReturn);
			
			
		});
		status.setOnAction(e->{
			
			if(status.getValue() == "Want to Read") {
				
				previouslyReadBtn.setDisable(true);
				pagesOnDate.setDisable(true);
				dateRead.setDisable(true);
				addPreviouslyReadPagesBtn.setDisable(true);
				
			}else {
				
				previouslyReadBtn.setDisable(false);
				pagesOnDate.setDisable(false);
				dateRead.setDisable(false);
				addPreviouslyReadPagesBtn.setDisable(false);
				
			}
			
			
		});
		
		
		
		
		submitBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				storage.bookData newBook = new storage.bookData();
				
				if(previouslyReadBtn.getValue() == "Distribute evenly") {
					
				}
				
				
				newBook.author = bookAuthorTxt.getText();
				int ID = alldata.bookStore.get(alldata.bookStore.size()-1).bookID;
				newBook.bookID = ID;
				DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				Date date = new Date();
				newBook.dateAdded = dateFormat.format(date);
				newBook.dateCompleted = dateCompleted.getValue().toString();
				newBook.dateStarted = dateStarted.getValue().toString();
				newBook.genre = bookGenreTxt.getText();
				newBook.pages = Integer.parseInt(bookPrintLengthTxt.getText());
				newBook.publicationYear = bookPublicationDate.getText();
				newBook.pagesRead = pagesReadTxt.getText();
				newBook.pagesReadOnADate = pagesOnDateToStore.toString();
				newBook.publisher = bookPublisherTxt.getText();
				newBook.status = Integer.parseInt(status.getValue());
				newBook.title = bookTitleTxt.getText();
				newBook.description = bookDescriptionTxt.getText();
				
				alldata.bookStore.add(newBook);
			}

		});
			

		additionalBookInfoBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				changeVisibility(bookDescriptionTxt, bookGenreTxt, bookAuthorTxt, bookPublicationDate, bookPublisherTxt);
			}

		});
		
		bookTitleTxt.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				// TODO Auto-generated method stub
		
				if (BookDataValidation.validBookDataString(bookTitleTxt.getText())) {
					bookTitleTxt.setStyle("-fx-text-inner-color: red;");
					validData = false;
				}else {
					validData = true;
					bookTitleTxt.setStyle("-fx-text-inner-color: black;");
				}
		
			}
			
		});
		
		
	}

	
	public void distributeEvenly() {
		if(status.getValue() == "Read Previously") {
		
			Date dateSS = java.util.Date.from(dateStarted.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			Date dateEE = java.util.Date.from(dateCompleted.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			DateTime dateS = new DateTime(dateSS);
			DateTime dateE = new DateTime(dateEE);
			int days = Days.daysBetween(dateS, dateE).getDays();
			int pagesOnEachDay = Integer.parseInt(pagesReadTxt.getText()) / days;
			
			DateTime current_day = new DateTime(dateS);
			while(current_day. != dateE) {
				
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

	@Override
	public void start(Stage mainStage) throws Exception {

		setupBookInfoUIElements();
		setupHandles();

		primaryScene = new Scene(primaryGroup, 560, 360);
		mainStage.setScene(primaryScene);
		mainStage.show();
	}

	public static void main(String args[]) {
		launch(args);
	}
}