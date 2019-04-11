import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;  

//TODO: CHANGE MENUS
//TODO: ACHIEVEMENTS
//TODO: VIEW BOOKS
//TODO: Average pages in add new book 
//TODO: Save functionality
//TODO: Back
//TODO: Reading progress update UI+functionality

public class MAIN extends Application {
	
	public static String username;
	public static store storageObj;
	public static Stage mainStage;
	public static Scene mainScene;
	public static Scene currentStage;

	
	public static Label title = new Label("Main Menu");
	public static Button viewTrackingData = new Button("View Tracking Data");
	public static Button manageBooks = new Button("Manage Books");
	
	public static Button targets = new Button("Targets");
	public static Button achievements = new Button("Achievements");
	public static Button back = new Button("Logout");
	public static Button save = new Button("Save");
	
	public static ArrayList<storage.userData> allusers = new ArrayList<>();
	
	public static void saveData() {
		storageObj.storeBookData(alldata.bookStore);
		int i = 0;
		for(storage.userData user: allusers) {
			i++;
			if(user.userID == alldata.userStore.userID) {
				allusers.set(i, alldata.userStore);
				storageObj.storeUserData(allusers);
				break;
			}
		}
		storageObj.storeTargetData(alldata.targetStore);
	}
	
	public static Map<String, Integer> getBooksRead(){
		Map<String, Integer> toReturn = new HashMap<String,Integer>();
		String data = alldata.userStore.booksCompletedOnADate;
		//System.out.println(data);
		//String data = "17-10-2013,5 29-03-2016,4 30-03-2017,8 31-03-2010,2";
		System.out.println(data);
		String[] tokens = data.split(" ", -1);
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
		System.out.println(finalData);
		
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
			if(item.title == booktitle) {
				return item;
			}
		}
		return null;
	}
	
	public static void instantiate() {
		title.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 15));
		title.setLayoutX(274);
		title.setLayoutY(15);
		
		//570 for save button
		back.setLayoutX(20);
		back.setLayoutY(278);
		//
		save.setLayoutX(570);
		save.setLayoutY(278);
		//
		viewTrackingData.setStyle("-fx-font: 15px Tahoma;");
		manageBooks.setStyle("-fx-font: 15px Tahoma;");
		//
		targets.setStyle("-fx-font: 15px Tahoma;");
		achievements.setStyle("-fx-font: 15px Tahoma;");
		//
		manageBooks.setPrefWidth(200);
		manageBooks.setPrefHeight(80);
		manageBooks.setLayoutX(120);
		manageBooks.setLayoutY(60);
		//
		viewTrackingData.setPrefWidth(200);
		viewTrackingData.setPrefHeight(80);
		viewTrackingData.setLayoutX(320);
		viewTrackingData.setLayoutY(60);
		//
		targets.setPrefWidth(200);		
		targets.setPrefHeight(80);
		targets.setLayoutX(120);
		targets.setLayoutY(140);
		//
		achievements.setPrefWidth(200);		
		achievements.setPrefHeight(80);
		achievements.setLayoutX(320);
		achievements.setLayoutY(140);
		
		setupHandles();
		Group root = new Group(manageBooks, viewTrackingData, targets, achievements, back, save, title);
		Scene mainScene = new Scene(root, 640, 320);
		mainStage.setScene(mainScene);
		mainStage.show();
	}
	

	@Override
	public void start(Stage stage) throws Exception {
		
		// TODO Auto-generated method stub
		
		//storageObj = new store(name);
		
		mainStage = stage;
		instantiate();
	}
	
	static void setupHandles() {
		
		manageBooks.setOnAction(e->{
			ManageBooksMenu.instantiate();
		});
		
		viewTrackingData.setOnAction(e->{
			ViewTrackingDataMenu.instantiate();
		});
		
		targets.setOnAction(e->{
			TargetsMenu.instantiate();	
		});
		
		achievements.setOnAction(e->{
			//Achievements x = new Achievements();
		});
		
		save.setOnAction(e->{	
			MAIN.saveData();
		});
	}
	
	
	public static void main(String args[]) {
		
		//MAIN.name = "user";
		storageObj = new store("user");
		try {
			alldata.bookStore = storageObj.retrieveBookData();
			allusers = storageObj.retrieveUserData();
			alldata.targetStore = storageObj.retrieveTargetData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		MAIN.username = "user";
	
		for(storage.userData item: allusers) {
			if(item.username.compareTo(MAIN.username) == 0) {
				alldata.userStore = item;				
			}
		}
		
		
	
		launch(args);
		
	
		   
	}
}
