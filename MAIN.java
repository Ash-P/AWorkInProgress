import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;  

public class MAIN extends Application {
	
	public static final Label title = new Label("Main Menu");
	public static final Button viewTrackingDataBtn = new Button("View Tracking Data");
	public static final Button manageBooksBtn = new Button("Manage Books");
	public static final Button targetsBtn = new Button("Targets");
	public static final Button achievementsBtn = new Button("Achievements");
	public static final Button saveBtn = new Button("Save");
	public static ArrayList<storage.userData> allusers = new ArrayList<>();
	public static String username;
	public static store storageObj;
	public static Stage mainStage;
	public static Scene mainScene;
	public static Scene currentStage;
	
	
	public static void main(String args[]) {
		try {
			allusers = store.staticRetrieveUserData();
		} catch (IOException e) {
		}	
		launch(args);	   
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		mainStage = stage;
		instantiate();
		LoginGUI.instantiate();
	}
	
	public static void begin(String username) {
		MAIN.username = username;
		storageObj = new store(MAIN.username);
		
		try {
			allusers = storageObj.retrieveUserData();
			alldata.bookStore = storageObj.retrieveBookData();
			alldata.targetStore = storageObj.retrieveTargetData();
		} catch (IOException e) {
		}
		
		for(storage.userData item: allusers) {
			if(item.username.compareTo(MAIN.username) == 0) {
				alldata.userStore = item;				
			}
		}
		
		for(storage.bookData item: alldata.bookStore) {
			System.out.println(item.status);
		}
		
		instantiate();
	}
	
	public static void saveData() {	
		storageObj.storeBookData(alldata.bookStore);
		storageObj.storeTargetData(alldata.targetStore);
		
		for(storage.userData user: allusers) {
			if(user.userID == alldata.userStore.userID) {
				int indexToReplace = allusers.indexOf(user);
				allusers.set(indexToReplace, alldata.userStore);	
			}
		}
		
		storageObj.storeUserData(allusers);
	}
	
	public static Map<String, Integer> getBooksRead(){
		Map<String, Integer> toReturn = new HashMap<String,Integer>();
		String data = alldata.userStore.booksCompletedOnADate;
		String[] tokens = data.split(" ", -1);
		
		System.out.println(data); //test
		
		for(int i=0;i<tokens.length;i++) {
			String[] x = tokens[i].split(",", 2);
			toReturn.put(x[0],Integer.parseInt(x[1]));
		}
		
		System.out.println(data);
		return toReturn;	
	}

	public static Map<String, Integer> getPagesRead(){
		Map<String, Integer> toReturn = new HashMap<String,Integer>();
		StringBuilder data = new StringBuilder();	
		alldata.bookStore.forEach(e -> data.append(e.pagesReadOnADate + " "));
		String finalData = data.toString();
		
		System.out.println(finalData); //test
		
		String[] tokens = finalData.split(" ", -1);
		for(int i=0;i<tokens.length;i++) {
			String[] x = tokens[i].split(",", 2);
			try {
			toReturn.put(x[0],Integer.parseInt(x[1]));
			}catch(Exception e) {
				
			}
		}
		
		return toReturn;
	};
	
	public static int getBookID(String bookTitle) {
		for(storage.bookData b : alldata.bookStore) {
			if(b.title == bookTitle) return b.bookID;
		}
		return 0; //indicates error
	}
	
	public static String getBookTitle(int bookID) {
		for(storage.bookData b : alldata.bookStore) {
			if(b.bookID == bookID) return b.title;
		}
		return null; //indicates error
	}
	
	public static storage.bookData getSpecificBook(String booktitle) {	
		for(storage.bookData item: alldata.bookStore) {
			if(item.title == booktitle)return item;
		}
		return null;
	}
	
	/**
	 * @param type : AlertType
	 * @param title : String
	 * @param header : String
	 * @param content : String
	 */
	public static void createAlert(AlertType type, String title, String header, String content) {
		Alert dialog = new Alert(type);
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("file:icon.png"));
		dialog.setTitle(title);
		dialog.setHeaderText(header);
		dialog.setContentText(content);
		dialog.showAndWait(); // the dialog must be confirmed before continuing
	}
	
	public static void instantiate() {
		mainStage.setTitle("Main Menu");
		title.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 15));
		title.setLayoutX(274);
		title.setLayoutY(15);
		//
		saveBtn.setLayoutX(570);
		saveBtn.setLayoutY(278);
		//
		viewTrackingDataBtn.setStyle("-fx-font: 15px Tahoma;");
		manageBooksBtn.setStyle("-fx-font: 15px Tahoma;");
		//
		targetsBtn.setStyle("-fx-font: 15px Tahoma;");
		achievementsBtn.setStyle("-fx-font: 15px Tahoma;");
		//
		manageBooksBtn.setPrefWidth(200);
		manageBooksBtn.setPrefHeight(80);
		manageBooksBtn.setLayoutX(120);
		manageBooksBtn.setLayoutY(60);
		//
		viewTrackingDataBtn.setPrefWidth(200);
		viewTrackingDataBtn.setPrefHeight(80);
		viewTrackingDataBtn.setLayoutX(320);
		viewTrackingDataBtn.setLayoutY(60);
		//
		targetsBtn.setPrefWidth(200);		
		targetsBtn.setPrefHeight(80);
		targetsBtn.setLayoutX(120);
		targetsBtn.setLayoutY(140);
		//
		achievementsBtn.setPrefWidth(200);		
		achievementsBtn.setPrefHeight(80);
		achievementsBtn.setLayoutX(320);
		achievementsBtn.setLayoutY(140);
		
		setupHandlers();
		
		Group root = new Group(manageBooksBtn, viewTrackingDataBtn, targetsBtn, achievementsBtn, saveBtn, title);
		Scene mainScene = new Scene(root, 640, 320);
		mainStage.getIcons().add(new Image("file:icon.png"));
		mainStage.setScene(mainScene);
		mainStage.show();
	}
	
	static void setupHandlers() {
		
		manageBooksBtn.setOnAction(e->{
			ManageBooksMenu.instantiate();
			e.consume();
		});
		manageBooksBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
			if (ev.getCode() == KeyCode.ENTER) {
				manageBooksBtn.fire();
				ev.consume(); 
			}
		});
		
		viewTrackingDataBtn.setOnAction(e->{
			ViewTrackingDataMenu.instantiate();
			e.consume();
		});
		viewTrackingDataBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
			if (ev.getCode() == KeyCode.ENTER) {
				viewTrackingDataBtn.fire();
				ev.consume(); 
			}
		});
		
		targetsBtn.setOnAction(e->{
			TargetsMenu.instantiate();
			e.consume();
		});
		targetsBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
			if (ev.getCode() == KeyCode.ENTER) {
				targetsBtn.fire();
				ev.consume(); 
			}
		});
		
		achievementsBtn.setOnAction(e->{
			ViewAchievements x = new ViewAchievements();
			e.consume();
		});
		achievementsBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
			if (ev.getCode() == KeyCode.ENTER) {
				achievementsBtn.fire();
				ev.consume(); 
			}
		});
		
		saveBtn.setOnAction(e->{	
			MAIN.saveData();
			e.consume();
		});
		saveBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
			if (ev.getCode() == KeyCode.ENTER) {
				saveBtn.fire();
				ev.consume(); 
			}	
		});
	}
}
