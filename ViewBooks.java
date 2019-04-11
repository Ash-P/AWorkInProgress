import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class ViewBooks {
	public Label title = new Label("View Book Data");
	public Button back = new Button("Back");
	public ComboBox<String> categoryComboBox = new ComboBox<>();
	public ComboBox<String> items = new ComboBox<>();

	public TableView<storage.bookData> bookTable = new TableView<>();

	public ViewBooks() {
		categoryComboBox.setPromptText("Category");
		categoryComboBox.getItems().add("Status");
		categoryComboBox.getItems().add("Author");
		categoryComboBox.getItems().add("Publisher");
		categoryComboBox.getItems().add("Genre");

		TableColumn<storage.bookData, Integer> bookStatus = new TableColumn<>("Status");
		bookStatus.setMaxWidth(40);
		bookStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

		TableColumn<storage.bookData, String> bookName = new TableColumn<>("Title");
		bookName.setCellValueFactory(new PropertyValueFactory<>("title"));
		bookName.setMinWidth(100);

		TableColumn<storage.bookData, Integer> bookPages = new TableColumn<>("Book Pages");
		bookPages.setCellValueFactory(new PropertyValueFactory<>("pages"));

		TableColumn<storage.bookData, Integer> bookPagesRead = new TableColumn<>("Pages Read");
		bookPagesRead.setCellValueFactory(new PropertyValueFactory<>("pagesRead"));

		TableColumn<storage.bookData, String> bookDateAdded = new TableColumn<>("Date Added");
		bookDateAdded.setCellValueFactory(new PropertyValueFactory<>("dateAdded"));

		TableColumn<storage.bookData, String> bookDateStarted = new TableColumn<>("Date Started");
		bookDateStarted.setCellValueFactory(new PropertyValueFactory<>("dateStarted"));

		TableColumn<storage.bookData, String> bookDateCompleted = new TableColumn<>("DateCompleted");
		bookDateCompleted.setCellValueFactory(new PropertyValueFactory<>("dateCompleted"));

		TableColumn<storage.bookData, String> bookAuthor = new TableColumn<>("Book Author");
		bookAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));

		TableColumn<storage.bookData, String> bookPublisher = new TableColumn<>("Publisher");
		bookPublisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));

		TableColumn<storage.bookData, String> bookPublishYear = new TableColumn<>("Published Year");
		bookPublishYear.setCellValueFactory(new PropertyValueFactory<>("publicationYear"));

		TableColumn<storage.bookData, String> bookGenre = new TableColumn<>("Book Genre");
		bookGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));

		TableColumn<storage.bookData, String> bookDescription = new TableColumn<>("Book Description");

		bookDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		// ObservableList<storage.bookData> x = new ObservableList<>();
		// FXCollections.observableArrayList().add(e);

		// TableView<storage.bookData> bookTable = new TableView<>();
		bookTable.setItems(getBooks());
		bookTable.getColumns().addAll( Arrays.asList(bookStatus, bookName, bookPages, bookPagesRead, bookDateAdded, bookDateStarted,
				bookDateCompleted, bookAuthor, bookPublisher, bookPublishYear, bookGenre, bookDescription) );

		// bookTable.getColumns().addAll(bookStatus);

		title.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 15));
		title.setLayoutX(440);
		title.setLayoutY(15);

		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(90, 0, 0, 10));
		vbox.getChildren().addAll(categoryComboBox, items, bookTable);
		setupHandles();
		Group root = new Group(vbox, title);
		Scene scene = new Scene(root, 1004, 580);
		MAIN.mainStage.setScene(scene);

	}

	public ObservableList<storage.bookData> getBooks() {
		ObservableList<storage.bookData> books = FXCollections.observableArrayList();
		for (storage.bookData item : alldata.bookStore) {
			books.add(item);
		}

		return books;
	}

	public ObservableList<storage.bookData> getBooksByAuthor(String author) {
		ObservableList<storage.bookData> books = FXCollections.observableArrayList();
		for (storage.bookData item : alldata.bookStore) {
			if (item.author == author) {
				books.add(item);
			}
		}

		return books;
	}

	public ObservableList<storage.bookData> getBooksByPublisher(String publisher) {
		ObservableList<storage.bookData> books = FXCollections.observableArrayList();
		for (storage.bookData item : alldata.bookStore) {
			if (item.publisher == publisher) {
				books.add(item);
			}
		}

		return books;
	}
	
	void setupHandles() {

		categoryComboBox.setOnAction(e -> {

			items.setValue(null);
			items.getItems().clear();
			bookTable.setItems(getBooks());
			
			if (categoryComboBox.getValue() == "Status") {
				for (storage.bookData book : alldata.bookStore) {
					if(book.status == 0) items.getItems().add("Previous");
					else if(book.status == 1) items.getItems().add("Current");
					else if(book.status == 2) items.getItems().add("Future");
				}
			}
			else if (categoryComboBox.getValue() == "Author") {
				for (storage.bookData book : alldata.bookStore) items.getItems().add(book.author);
			}
			else if (categoryComboBox.getValue() == "Publisher") {
				for (storage.bookData book : alldata.bookStore) items.getItems().add(book.publisher);
			}
			else if (categoryComboBox.getValue() == "Genre") {
				for (storage.bookData book : alldata.bookStore) items.getItems().add(book.genre);
			}

		});

		items.setOnAction(e->{
			
			if (categoryComboBox.getValue() == "Author") {
				bookTable.getItems().clear();
				bookTable.setItems(getBooksByAuthor(items.getValue()));
			}
			else if(categoryComboBox.getValue() == "Publisher") {
				bookTable.getItems().clear();
				bookTable.setItems(getBooksByPublisher(items.getValue()));
			}
			
			
		});
		
		
		back.setOnAction(e -> {
			// MAIN.mainStage.setScene(MAIN.mainScene);
			MAIN.instantiate();
		});

	}
}