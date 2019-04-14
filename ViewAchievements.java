import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.VBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class ViewAchievements {
	
	public static final int[] PAGE_ACHIEVEMENTS = { 1, 10, 50, 100, 250, 500, 1000, 5000, 10000 };
	public static final int[] BOOK_ACHIEVEMENTS = { 1, 5, 10, 25, 50, 100, 150, 250, 500 };
	private static final Label title = new Label("Achievements");
	private static final Button backBtn = new Button("Back");
	
	private static Circle p1, p10, p50, p100, p250, p500, p1000, p5000, p10000;
    private static Circle b1, b5, b10, b25, b50, b100, b150, b250, b500;
    private static int totalPagesRead;
	private static int totalBooksRead;
    private static boolean isDone;
    private static AnchorPane layout = new AnchorPane();
    private static Group group;
    private static Label pagesReadLabel = new Label();
    private static Label pagesUntilNextLabel = new Label();
    private static Label booksReadLabel = new Label();
    private static Label booksUntilNextLabel = new Label();
    
    public ViewAchievements() {
    	layout = new AnchorPane();    	
    	createUI();
    	
        Scene scene = new Scene(group,650,480);
        MAIN.mainStage.setScene(scene);
        MAIN.mainStage.setTitle("Achievements");
    }
    
    private void createUI() {
    	totalPagesRead = alldata.userStore.totalPagesRead;
    	totalBooksRead = alldata.userStore.totalBooksRead;
    	
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
        //loadLabels("Your pages read: " + totalPagesRead, 25, 329, 15);
        //loadLabels("Pages until next achievement: " + getPagesUntilNext(), 25, 360, 15);
        //loadLabels("Your books read: " + totalBooksRead, 337, 329, 15);
        //loadLabels("Books until next achievement: " + getBooksUntilNext(), 337, 360, 15);
        
       
        pagesReadLabel.setText("Your pages read: " + totalPagesRead);
        pagesReadLabel.setLayoutX(25);
        pagesReadLabel.setLayoutY(329);
        pagesReadLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        layout.getChildren().add(pagesReadLabel);
        
        pagesUntilNextLabel.setText("Pages until next achievement: " + getPagesUntilNext());
        pagesUntilNextLabel.setLayoutX(25);
        pagesUntilNextLabel.setLayoutY(360);
        pagesUntilNextLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        layout.getChildren().add(pagesUntilNextLabel);
        
        booksReadLabel.setText("Your books read: " + totalBooksRead);
        booksReadLabel.setLayoutX(337);
        booksReadLabel.setLayoutY(329);
        booksReadLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        layout.getChildren().add(booksReadLabel);
        
        booksUntilNextLabel.setText("Books until next achievement: " + getBooksUntilNext());
        booksUntilNextLabel.setLayoutX(337);
        booksUntilNextLabel.setLayoutY(360);
        booksUntilNextLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        layout.getChildren().add(booksUntilNextLabel);
        
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
        	MAIN.instantiate();
        	e.consume();
        });
        backBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
        	if (ev.getCode() == KeyCode.ENTER) {
        		backBtn.fire();
        		ev.consume(); 
        	}
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
        label.setFont(Font.font("Verdana", FontWeight.BOLD,fSize));
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
    
    public static void updateAchievements() {
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
    		achievementCompleted(true, pageAchievsUnlocked);
    		alldata.userStore.pageAchievsUnlocked = pageAchievsUnlocked;
    	}
    	if(bookAchievsUnlocked > alldata.userStore.bookAchievsUnlocked) {
    		achievementCompleted(false, bookAchievsUnlocked);
    		alldata.userStore.bookAchievsUnlocked = bookAchievsUnlocked;
    	}
    }
    
    private static void achievementCompleted(Boolean typeIsPages, int newIndex) {
    	String header;
    	String content;
    
		if(typeIsPages) {
			header = "New page achievement unlocked";
			if(newIndex < 8) content = "Achievement earnt: " + PAGE_ACHIEVEMENTS[newIndex] + " pages read.\nNext level: " + PAGE_ACHIEVEMENTS[newIndex+1] + " pages.";
			else content = "Achievement earnt: " + PAGE_ACHIEVEMENTS[newIndex] + " pages read.\nAll page achievements now completed!";
		}
		
		else {
			header = "New book achievement unlocked";
			if(newIndex < 8) content = "Achievement earnt: " + BOOK_ACHIEVEMENTS[newIndex] + " books read.\nNext level: " + BOOK_ACHIEVEMENTS[newIndex+1] + " books.";
			else content = "Achievement earnt: " + BOOK_ACHIEVEMENTS[newIndex] + " books read.\nAll book achievements now completed!";
		}
		
		MAIN.createAlert(AlertType.INFORMATION, "Achievement Progress", header, content);
    }
    
}
