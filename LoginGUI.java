import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginGUI{

	private static final Label appName = new Label("Face-In-Book");
	private static final Button loginBtn = new Button("Login");
	private static TextField usernameTxt = new TextField();
	private static AnchorPane layout;
	private static Scene scene;

    public static void instantiate() {
    	MAIN.mainStage.setTitle("Login");
        appName.setPrefHeight(159);
        appName.setPrefWidth(330);
        appName.setLayoutX(141);
        appName.setLayoutY(40);
        appName.setFont(Font.font("System", FontWeight.SEMI_BOLD,48));
        appName.setAlignment(Pos.TOP_CENTER);
        appName.setPickOnBounds(true);

        usernameTxt.setPromptText("Username");
        usernameTxt.setFont(Font.font("System", FontWeight.NORMAL,13));
        usernameTxt.setLayoutX(220);
        usernameTxt.setLayoutY(180);
        
        loginBtn.setFont(Font.font("System", FontWeight.NORMAL,13));
        loginBtn.setLayoutX(275);
        loginBtn.setLayoutY(220);
        
        layout = new AnchorPane();	
        layout.getChildren().addAll(appName, usernameTxt, loginBtn);
        
        setupHandles();
        
        scene = new Scene(layout, 600, 300);
        MAIN.mainStage.setScene(scene);
    }
    
    public static void setupHandles() {
    	
    	loginBtn.setOnAction( e -> {
    		String username = usernameTxt.getText();
    	  	if(!username.trim().equals("") && BookDataValidation.validBookDataString(username)) {
    	  		System.out.println(username);
    	  		readFile(username);
    	  	}
    	  	e.consume();
    	});
    	
    	loginBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
            	loginBtn.fire();
            	ev.consume(); 
            }
        });
    	
    	usernameTxt.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
            	loginBtn.fire();
               ev.consume(); 
            }
        });
    	
    }
    
    public static void readFile(String Username) {
        List<String> fileLines = new ArrayList<>();
        File file = new File("userStore.txt");//directory
        
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine())
                fileLines.add(scanner.nextLine());
        } catch(FileNotFoundException e) {
        }
        
        checkUsername(fileLines, Username);
    }

    public static void checkUsername(List<String> fileLines, String username){
        for (String line : fileLines) {
            boolean isPresent = line.contains(username);
            if(isPresent) {
            	MAIN.createAlert(AlertType.INFORMATION, "Warning!", "Existing user logged in successfully", "Welcome back " + username + "!");
                MAIN.begin(username);
               	return;
            }
        }
     
        createUser(username);
        MAIN.createAlert(AlertType.INFORMATION, "Warning!", "New user created successfully", "Welcome " + username + "!");
    }

    public static void createUser(String Username) {
    	storage.userData newUser = new storage.userData();
    	newUser.userID = MAIN.allusers.get(MAIN.allusers.size()-1).userID + 1;
    	newUser.username = Username;
    	newUser.bookAchievsUnlocked = -1;
    	newUser.pageAchievsUnlocked = -1;
    	newUser.totalBooksRead = 0;
    	newUser.totalPagesRead = 0;
    	newUser.booksCompletedOnADate = "";
    	
    	store.storeSingleUser(newUser);
    	MAIN.begin(Username); 	
    }
    
    private static void displayWindow(String message){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Warning!");
        window.setWidth(250);

        Label label = new Label(message);
        Button close = new Button("Close");
        close.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().add(label);
        layout.getChildren().add(close);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
