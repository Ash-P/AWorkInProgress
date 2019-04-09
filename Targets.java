import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;

public class Targets extends Application {

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
	private final ComboBox<String> targetTypeBox = new ComboBox<>();
	private final ComboBox<String> bookTitleBox = new ComboBox<>();
	private final DatePicker deadlineDatePicker = new DatePicker();
	private final TextField targetValueTxt = new TextField();
	private final ComboBox<String> createCalEventBox = new ComboBox<>();
	private final Button submitBtn = new Button();
	private final Button clearBtn = new Button();
	private Scene primaryScene;
	private Group primaryGroup = new Group();

	public void createAddATargetUI() {
		targetTypeBox.setPromptText("Type");
		targetTypeBox.getItems().add("Pages of a specific book");
		targetTypeBox.getItems().add("Pages across all books");
		targetTypeBox.getItems().add("Books");
		GridPane.setConstraints(targetTypeBox, 0, 1);

		bookTitleBox.setPromptText("Book Title");
		//for(storage.bookData b : alldata.bookStore) { //add books of status 1 and 2 only
		//	if(b.status == 1 || b.status == 2) bookTitleBox.getItems().add(b.title);
		//}
		bookTitleBox.setEditable(true);
		GridPane.setConstraints(bookTitleBox, 0, 2);

		deadlineDatePicker.setPromptText("Deadline Date");
		deadlineDatePicker.setDayCellFactory(picker -> new DateCell() { //disable past dates
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				LocalDate today = LocalDate.now();
				setDisable(empty || date.compareTo(today) < 0 );
			}
		});
		GridPane.setConstraints(deadlineDatePicker, 0, 3);

		targetValueTxt.setPromptText("Target Value");
		GridPane.setConstraints(targetValueTxt, 0, 4);

		createCalEventBox.setPromptText("Create a Google Calendar Event");
		createCalEventBox.getItems().add("Yes");
		createCalEventBox.getItems().add("No");
		GridPane.setConstraints(createCalEventBox, 0, 5);

		submitBtn.setText("Submit");
		submitBtn.setLayoutX(300);
		submitBtn.setLayoutY(170);

		clearBtn.setText("Clear");
		clearBtn.setLayoutX(360);
		clearBtn.setLayoutY(170);

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);
		grid.getChildren().addAll(targetTypeBox,bookTitleBox,deadlineDatePicker,targetValueTxt,createCalEventBox,submitBtn,clearBtn);

		primaryGroup.getChildren().addAll(grid,submitBtn,clearBtn);
	}

	private void setupHandles() {

		//hides bookTitleBox unless type is 1
		targetTypeBox.setOnAction(e->{
			if(!(targetTypeBox.getValue().equals("Pages of a specific book"))) {
				bookTitleBox.setDisable(true);
			}
			else {
				bookTitleBox.setDisable(false);
			}
		});

		//validates fields, adds a new target and creates Google Calendar CSV file
		submitBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				int targetType = 0;
				if(targetTypeBox.getValue().equals("Pages of a Specific Book")) targetType = 1;
				else if(targetTypeBox.getValue().equals("Pages across all Books")) targetType = 2;
				else if(targetTypeBox.getValue().equals("Books")) targetType = 3;
				//type, date, bookID, value
				String deadlineDate = deadlineDatePicker.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
				int bookID = getBookID(bookTitleBox.getValue());
				int targetValue = Integer.parseInt(targetValueTxt.getText());
				if(validateTargetCreationFields(targetType, deadlineDate, bookID, targetValue)) {
					storage.targetData target = addNewTarget(targetType, deadlineDate, bookID, targetValue);
					if(createCalEventBox.getValue().equals("Yes")) {
						CreateCSV.createCSV(target);
					}
				}
			}
		});

		//resets all fields
		clearBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				targetTypeBox.valueProperty().set(null);
				bookTitleBox.valueProperty().set(null);
				bookTitleBox.setDisable(false);
				deadlineDatePicker.valueProperty().set(null);
				targetValueTxt.clear();
			}
		});

	}

	public static int getBookID(String bookTitle) {
		for(storage.bookData b : alldata.bookStore) {
			if(b.title == bookTitle) return b.bookID;
		}
		return 0; //indicates error
	}

	private static Boolean validateTargetCreationFields(int targetType, String deadlineDate, int bookID, int targetValue) {
		//TODO :
		//no semicolons
		//targetType is 1, 2 or 3 (i.e. not 0)
		//targetValue is positive
		//if type if 1, targetValue must be <= pages remaining of book
		return true;
	}

	private static storage.targetData addNewTarget(int targetType, String deadlineDate, int bookID, int targetValue) {
		storage.targetData target = new storage.targetData();
		target.targetID = 1; //TODO get next ID value
		target.targetType = targetType;
		target.isComplete = false;
		target.deadlineDate = deadlineDate;
		target.bookID = bookID;
		target.targetValue = targetValue;
		target.valueRemaining = targetValue;
		alldata.targetStore.add(target);
		return target;
	}

	public void createViewTargetsUI() {
		TableView<storage.targetData> tableView = getTargetsTable();
		//TODO create GUI
	}

	private static TableView<storage.targetData> getTargetsTable() {
		TableColumn<storage.targetData, String> targetIDCol = new TableColumn<storage.targetData, String>("Target ID");
		TableColumn<storage.targetData, String> targetTypeCol = new TableColumn<storage.targetData, String>("Target Type");
		TableColumn<storage.targetData, String> isCompleteCol = new TableColumn<storage.targetData, String>("Completed");
		TableColumn<storage.targetData, String> deadlineDateCol = new TableColumn<storage.targetData, String>("Deadline Date");
		TableColumn<storage.targetData, String> bookTitleCol = new TableColumn<storage.targetData, String>("Book Title");
		TableColumn<storage.targetData, String> targetValueCol = new TableColumn<storage.targetData, String>("Target Value");
		TableColumn<storage.targetData, String> valueRemainingCol = new TableColumn<storage.targetData, String>("Value Remaining");

		TableView<storage.targetData> table = new TableView<storage.targetData>();
		table.setEditable(false);
		table.getColumns().addAll( Arrays.asList(targetIDCol, targetTypeCol, isCompleteCol, deadlineDateCol, bookTitleCol, targetValueCol, valueRemainingCol) );

		ObservableList<storage.targetData> observableTargetList = FXCollections.observableArrayList(alldata.targetStore);
		table.setItems(observableTargetList);

		//TODO remove/hide targetID column

		return table;
	}

	/**
	 * Called whenever a book (of type 0 or 1) or reading progress is added
	 *
	 * Updates valueRemaining and isComplete for all relevant targets
	 * Checks for target completion, calls targetCompleted if any found
	 *
	 * @param pageProgress : the number of new pages read
	 * @param bookID : the ID of the book
	 * @param bookComplete : true if the book has just been completed (status is 0), false otherwise
	 */
	public static void updateTargets(int pageProgress, int bookID, Boolean bookComplete){
		ArrayList<storage.targetData> targetList = alldata.targetStore;
		for(storage.targetData t : targetList){
			if(t.isComplete) continue;
			if(t.targetType == 1 && t.bookID == bookID) {
				t.valueRemaining -= pageProgress;
				if(t.valueRemaining <= 0) {
					t.valueRemaining = 0;
					t.isComplete = true; //may not have been completed in time
					Date date = Calendar.getInstance().getTime();
					try {
						if(DATE_FORMAT.parse(t.deadlineDate).after(date)) {
							targetCompleted(t);
						}
					} catch (ParseException e) {
					}
				}
			}
			else if(t.targetType == 2) {
				t.valueRemaining -= pageProgress;
				if(t.valueRemaining <= 0) {
					t.valueRemaining = 0;
					t.isComplete = true;
					Date date = Calendar.getInstance().getTime();
					try {
						if(DATE_FORMAT.parse(t.deadlineDate).after(date)) {
							targetCompleted(t);
						}
					} catch (ParseException e) {
					}
				}
			}
			else if(t.targetType == 3 && bookComplete) {
				t.valueRemaining--;
				if(t.valueRemaining == 0) {
					t.isComplete = true;
					Date date = Calendar.getInstance().getTime();
					try {
						if(DATE_FORMAT.parse(t.deadlineDate).after(date)) {
							targetCompleted(t);
						}
					} catch (ParseException e) {
					}
				}
			}
		}
	}

	private static void targetCompleted(storage.targetData target) {
		Alert dialog = new Alert(AlertType.INFORMATION);
		dialog.setTitle("Information Dialog");
		dialog.setHeaderText(null);
		dialog.setContentText("I have a great message for you!");

		dialog.showAndWait();
	}

	@Override
	public void start(Stage mainStage) throws Exception {
		createAddATargetUI();
		setupHandles();

		primaryScene = new Scene(primaryGroup, 560, 360);
		mainStage.setScene(primaryScene);
		mainStage.show();
	}

	public static void main(String args[]) {
		launch(args);
	}
}
