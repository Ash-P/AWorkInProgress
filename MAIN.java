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
//TODO: TARGETS
//TODO: Average pages in add new book

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
	
	
	
	
	
	
	public static Map<String, Integer> getBooksRead(){
		Map<String, Integer> toReturn = new HashMap<String,Integer>();
		String data = alldata.userStore.booksCompletedOnADate;
		//System.out.println(data);
		//String data = "17-10-2013,5 29-03-2016,4 30-03-2017,8 31-03-2010,2";
		String[] tokens = data.split(" ", -1);
		for(int i=0;i<tokens.length;i++) {
			String[] x = tokens[i].split(",", 2);
			toReturn.put(x[0],Integer.parseInt(x[1]));
		}
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
		viewTrackingData.setStyle("-fx-font: 15px Tahoma;");
		manageBooks.setStyle("-fx-font: 15px Tahoma;");
		
		targets.setStyle("-fx-font: 15px Tahoma;");
		achievements.setStyle("-fx-font: 15px Tahoma;");
		//
		viewTrackingData.setPrefWidth(200);
		viewTrackingData.setPrefHeight(80);
		viewTrackingData.setLayoutX(120);
		viewTrackingData.setLayoutY(60);
		//
		manageBooks.setPrefWidth(200);
		manageBooks.setPrefHeight(80);
		manageBooks.setLayoutX(320);
		manageBooks.setLayoutY(60);
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
		Group root = new Group(back, viewTrackingData, manageBooks,targets, achievements, title);
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
		
		viewTrackingData.setOnAction(e->{
			DisplayChart x = new DisplayChart();
			//ViewTrackingData x = new ViewTrackingData(mainScene);
		});
		
		viewTrackingData.setOnAction(e->{
			ViewBookData x = new ViewBookData(mainScene);
		});
		
		targets.setOnAction(e->{
			//Targets x = new Targets(mainScene);
		});
		
	}
	
	
	public static void main(String args[]) {
		//Map<String, Integer> toReturn = getBooksRead();
		/*String x = "05-04-2014";
		 SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MM-yyyy");
		 Date y = null;
		try {
			y = formatter2.parse(x);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		Calendar cal = Calendar.getInstance();
		cal.setTime(y);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		
		System.out.println(day);
		
		*/
		
		//MAIN.name = "user";
		storageObj = new store("user");
		ArrayList<storage.userData> tempStore = null;
		try {
			alldata.bookStore = storageObj.retrieveBookData();
			tempStore = storageObj.retrieveUserData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		MAIN.username = "username";
	
		for(storage.userData item: tempStore) {
			if(item.username.compareTo(MAIN.username) == 0) {
				alldata.userStore = item;				
			}
		}
		
		launch(args);
		
	
		   
	}
}
