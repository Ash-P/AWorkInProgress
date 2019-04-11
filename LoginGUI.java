import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.*;
import java.util.*;


public class LoginGUI{

    public static Button button;
    public static TextField enterUser;

  
    public void LoginGUI() {
       

 
        Label appName = new Label("Face-In-Book");
        appName.setPrefHeight(159);
        appName.setPrefWidth(330);
        appName.setLayoutX(141);
        appName.setLayoutY(40);
        appName.setFont(Font.font("System", FontWeight.SEMI_BOLD,48));
        appName.setAlignment(Pos.TOP_CENTER);
        appName.setPickOnBounds(true);

        button = new Button("Login");
        button.setFont(Font.font("System", FontWeight.NORMAL,13));
        button.setAlignment(Pos.CENTER_LEFT);
        button.setPickOnBounds(true);
        button.setPrefHeight(29);
        button.setPrefWidth(50);
        button.setLayoutX(370);
        button.setLayoutY(276);

        enterUser = new TextField();
        enterUser.setPromptText("Username");
        enterUser.setFont(Font.font("System", FontWeight.NORMAL,13));
        enterUser.setEditable(true);
        enterUser.setAlignment(Pos.CENTER_LEFT);
        enterUser.setPickOnBounds(true);
        enterUser.setPrefHeight(29);
        enterUser.setPrefWidth(200);
        enterUser.setLayoutX(220);
        enterUser.setLayoutY(227);

        setupHandles();
        Group root = new Group(button, appName, enterUser);
		Scene mainScene = new Scene(root, 640, 320);
		MAIN.mainStage.setScene(mainScene);
		
	


       
    }

    public static void instantiate() {
    	AnchorPane layout = new AnchorPane();

    	 
        Label appName = new Label("Face-In-Book");
        appName.setPrefHeight(159);
        appName.setPrefWidth(330);
        appName.setLayoutX(141);
        appName.setLayoutY(40);
        appName.setFont(Font.font("System", FontWeight.SEMI_BOLD,48));
        appName.setAlignment(Pos.TOP_CENTER);
        appName.setPickOnBounds(true);

        button = new Button("Login");
        button.setFont(Font.font("System", FontWeight.NORMAL,13));
        button.setAlignment(Pos.CENTER_LEFT);
        button.setPickOnBounds(true);
        button.setPrefHeight(29);
        button.setPrefWidth(50);
        button.setLayoutX(370);
        button.setLayoutY(276);

        enterUser = new TextField();
        enterUser.setPromptText("Username");
        enterUser.setFont(Font.font("System", FontWeight.NORMAL,13));
        enterUser.setEditable(true);
        enterUser.setAlignment(Pos.CENTER_LEFT);
        enterUser.setPickOnBounds(true);
        enterUser.setPrefHeight(29);
        enterUser.setPrefWidth(200);
        enterUser.setLayoutX(220);
        enterUser.setLayoutY(227);

        layout.getChildren().add(button);
        layout.getChildren().add(appName);
        layout.getChildren().add(enterUser);


        setupHandles();
        Scene scene = new Scene(layout, 600,400);
        MAIN.mainStage.setScene(scene);
    }
    
    public static void setupHandles() {
    	button.setOnAction(e->{
    		
    		
    		   String username = enterUser.getText();
               System.out.println(username);
               readFile(username);
    		
    	});
    	
    }
    
   

    public static void readFile(String Username){
        List<String> fileLines = new ArrayList<>();

        File file = new File("userStore.txt");//directory
        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine())
                fileLines.add(scanner.nextLine());

        } catch (FileNotFoundException e){
            System.out.println("File not found!");
        }
        checkUsername(fileLines, Username);
    }

    public static void checkUsername(List<String> fileLines, String Username){
        boolean found = false;

        for (String line : fileLines) {
            boolean isPresent = line.contains(Username);
            if(isPresent) {
                displayWindow("Name Found");//This can be replaced with open main menu
                found = true;
                MAIN.begin(Username);
                break;
            }

            if(line.startsWith("'1BookStore.txt'"))
                break;
        }

        if(!found)
        	
            displayWindow("Name not found");//This can be expanded to allow user to create a file/account

    }

    public void createUser() {
    	storage.userData newUser = new storage.userData();
    	
    	//newUser.
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
