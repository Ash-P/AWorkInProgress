import java.nio.file.attribute.PosixFilePermission;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.VBox;


public class AchievementGUI {
	
	public static final int[] PAGE_ACHIEVEMENTS = { 1, 10, 50, 100, 250, 500, 1000, 5000, 10000 };
	public static final int[] BOOK_ACHIEVEMENTS = { 1, 5, 10, 25, 50, 100, 150, 250, 500 };
	private static int totalPagesRead = alldata.userStore.totalPagesRead;
	private static int totalBooksRead = alldata.userStore.totalBooksRead;
	private static int pageAchievsUnlocked = alldata.userStore.pageAchievsUnlocked;
	private static int bookAchievsUnlocked = alldata.userStore.bookAchievsUnlocked;
	private final Label title = new Label("Achievements");
	private final Button backBtn = new Button("Back");
	
	private static Circle p1, p10, p50, p100, p250, p500, p1000, p5000, p10000;
    private static Circle b1, b5, b10, b25, b50, b100, b150, b250, b500;
    private static boolean isDone;
    private static AnchorPane layout = new AnchorPane();
    private static Group group;

    
    //TODO check if has been awarded already when isDone=true
    //TODO alerts - for newly completed achievements
    
    public AchievementGUI() {
    	createUI();
    	
        Scene scene = new Scene(group,650,480);
        MAIN.mainStage.setScene(scene);
        MAIN.mainStage.setTitle("Achievements");
    }
    
    private void createUI() {
        p1 = createACircle(40, 121, getPageIsMet(1));
        p10 = createACircle(125, 121, getPageIsMet(10));
        p50 = createACircle(216, 121, getPageIsMet(50));
        p100 = createACircle(40, 200, getPageIsMet(100));
        p250 = createACircle(125, 200, getPageIsMet(250));
        p500 = createACircle(216, 200, getPageIsMet(500));
        p1000 = createACircle(40, 276, getPageIsMet(1000));
        p5000 = createACircle(125, 276, getPageIsMet(5000));
        p10000 = createACircle(216, 276, getPageIsMet(10000));
        
        b1 = createACircle(363, 121, getBookIsMet(1));
        b5 = createACircle(454, 121, getBookIsMet(5));
        b10 = createACircle(538, 121, getBookIsMet(10));
        b25 = createACircle(363, 200, getBookIsMet(25));
        b50 = createACircle(454, 200, getBookIsMet(50));
        b100 = createACircle(538, 200, getBookIsMet(100));
        b150 = createACircle(363, 276, getBookIsMet(150));
        b250 = createACircle(454, 276, getBookIsMet(250));
        b500 = createACircle(538, 276, getBookIsMet(500));

        loadLabels("Pages Read", 14, 32, 30);
        loadLabels("Books Read", 337, 32, 30);
        loadLabels("1",36,112,13);
        loadLabels("10",117,112,13);
        loadLabels("50",209,112,13);
        loadLabels("100",29,191,13);
        loadLabels("250", 114,191,13);
        loadLabels("500",205,191,13);
        loadLabels("1000",26,267,13);
        loadLabels("5000",111,267,13);
        loadLabels("10000",198,267,13);
        loadLabels("1",359,112,13);
        loadLabels("5",450,112,13);
        loadLabels("10",531, 112,13);
        loadLabels("25", 356,191,13);
        loadLabels("50", 447,191,13);
        loadLabels("100", 528, 191,13);
        loadLabels("150",353,267,13);
        loadLabels("250", 444,267,13);
        loadLabels("500",528,267,13);     
        loadLabels("Your pages read: " + totalPagesRead, 25, 329, 15);
        loadLabels("Pages until next achievement: " + getPagesUntilNext(), 25, 360, 15);
        loadLabels("Your books read: " + totalBooksRead, 337, 329, 15);
        loadLabels("Books until next achievement: " + getBooksUntilNext(), 337, 360, 15);
        
        title.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 15));       
        title.setLayoutX(250);
        title.setLayoutY(15);
        
        backBtn.setLayoutX(15);
        backBtn.setLayoutY(438);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(30, 0, 0, 10));
        vbox.getChildren().addAll(layout);
        
        backBtn.setOnAction(e -> {
        	ManageBooksMenu.instantiate();
        });
        
        group = new Group(vbox, title, backBtn);
    }
    
    private static int getPagesUntilNext() {
    	int currentIndex = -1;
    	for(int i = PAGE_ACHIEVEMENTS.length-1; i>=0; i--) {
    		if(totalPagesRead >= PAGE_ACHIEVEMENTS[i]) {
    			currentIndex = i;
    			break;
    		}
    	}
    	if(currentIndex == PAGE_ACHIEVEMENTS.length-1) return 0;
    	return PAGE_ACHIEVEMENTS[currentIndex + 1] - totalPagesRead;
    }
    
    private static int getBooksUntilNext() {
    	int currentIndex = -1;
    	for(int i = BOOK_ACHIEVEMENTS.length-1; i>=0; i--) {
    		if(totalBooksRead >= BOOK_ACHIEVEMENTS[i]) {
    			currentIndex = i;
    			break;
    		}
    	}
    	if(currentIndex == BOOK_ACHIEVEMENTS.length-1) return 0;
    	return BOOK_ACHIEVEMENTS[currentIndex + 1] - totalBooksRead;
    }

    private static void loadLabels(String text, int layX, int layY, int fSize){
        Label label = new Label(text);
        label.setLayoutX(layX);
        label.setLayoutY(layY);
        label.setFont(Font.font("System", FontWeight.BOLD,fSize));
        label.setAlignment(Pos.CENTER_LEFT);

        layout.getChildren().add(label);
    }
    
    private static Boolean getPageIsMet(int value) {
    	if(totalPagesRead >= value) return true; 
    	return false;
    }
    
    private static Boolean getBookIsMet(int value) {
    	if(totalBooksRead >= value) return true; 
    	return false;
    }

    private static Circle createACircle(int layX, int layY, Boolean isMet){
        Circle circle = new Circle();
        circle.setRadius(26);
        circle.setLayoutX(layX);
        circle.setLayoutY(layY);
        
        if(isMet) circle.setFill(Color.GREEN);
        else circle.setFill(Color.GREY);

        layout.getChildren().add(circle);
        return circle;
    }
    
    public static void updateTargets() {
    	int totalPagesRead = alldata.userStore.totalPagesRead;
    	int totalBooksRead = alldata.userStore.totalBooksRead;
    	int pageAchievsUnlocked = alldata.userStore.pageAchievsUnlocked;
    	int bookAchievsUnlocked = alldata.userStore.bookAchievsUnlocked;
    	
    	
    	while(true) {
	    	if(pageAchievsUnlocked < 8 && totalPagesRead >= PAGE_ACHIEVEMENTS[pageAchievsUnlocked + 1]) {
	    		pageAchievsUnlocked++;
	    	} else break;
    	}
    	
    	while(true) {
	    	if(bookAchievsUnlocked < 8 && totalBooksRead >= BOOK_ACHIEVEMENTS[bookAchievsUnlocked + 1]) {
	    		bookAchievsUnlocked++;
	    	} else break;
    	}
    	if(pageAchievsUnlocked > alldata.userStore.pageAchievsUnlocked) {
    		createAlert(true, pageAchievsUnlocked);
    		alldata.userStore.pageAchievsUnlocked = pageAchievsUnlocked;
    	}
    	if(bookAchievsUnlocked > alldata.userStore.bookAchievsUnlocked) {
    		createAlert(false, bookAchievsUnlocked);
    		alldata.userStore.bookAchievsUnlocked = bookAchievsUnlocked;
    	}
    }
    
    private static void createAlert(Boolean typeIsPages, int newIndex) {
    	Alert dialog = new Alert(AlertType.INFORMATION);
		dialog.setTitle("Achievement Progress");
		if(typeIsPages) {
			dialog.setHeaderText("New page achievement unlocked");
			if(newIndex < 8) dialog.setContentText("Achievement earnt: " + PAGE_ACHIEVEMENTS[newIndex] + " pages read.\nNext level: " + PAGE_ACHIEVEMENTS[newIndex+1] + " pages.");
			else dialog.setContentText("Achievement earnt: " + PAGE_ACHIEVEMENTS[newIndex] + " pages read.\nAll page achievements now completed!");
		}
		else {
			dialog.setHeaderText("New book achievement unlocked");
			if(newIndex < 8) dialog.setContentText("Achievement earnt: " + BOOK_ACHIEVEMENTS[newIndex] + " books read.\nNext level: " + BOOK_ACHIEVEMENTS[newIndex+1] + " books.");
			else dialog.setContentText("Achievement earnt: " + BOOK_ACHIEVEMENTS[newIndex] + " books read.\nAll book achievements now completed!");
		}
		dialog.showAndWait(); //the dialog must be confirmed before continuing
    }
    
}
