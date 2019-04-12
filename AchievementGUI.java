import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class AchievementGUI extends Application {

    private Circles p1;
    private Circles p10;
    private Circles p50;
    private Circles p100;
    private Circles p250;
    private Circles p500;
    private Circles p1000;
    private Circles p5000;
    private Circles p10000;
    private Circles b1;
    private Circles b5;
    private Circles b10;
    private Circles b25;
    private Circles b50;
    private Circles b100;
    private Circles b150;
    private Circles b250;
    private Circles b500;

    private boolean isDone;


    public static void  main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane layout = new AnchorPane();

        primaryStage.setTitle("Achievements");

        p1 = new Circles(layout,40,121);
        p10 = new Circles(layout,125,121);
        p50 = new Circles(layout,216,121);
        b1 = new Circles(layout,363,121);
        b5 = new Circles(layout,454,121);
        b10 = new Circles(layout,538,121);
        p100 = new Circles(layout,40,200);
        p250 = new Circles(layout,125,200);
        p500 = new Circles(layout,216,200);
        b25 = new Circles(layout,363,200);
        b50 = new Circles(layout,454,200);
        b100 = new Circles(layout,538,200);
        p1000 = new Circles(layout,40,276);
        p5000 = new Circles(layout,125,276);
        p10000 = new Circles(layout,216,276);
        b150 = new Circles(layout,363,276);
        b250 = new Circles(layout,454,276);
        b500 = new Circles(layout,538,276);


        checkTotalBooks();
        checkTotalPages();


        p1.createCircles();
        p10.createCircles();
        p50.createCircles();
        b1.createCircles();
        b5.createCircles();
        b10.createCircles();
        p100.createCircles();
        p250.createCircles();
        p500.createCircles();
        b25.createCircles();
        b50.createCircles();
        b100.createCircles();
        p1000.createCircles();
        p5000.createCircles();
        p10000.createCircles();
        b150.createCircles();
        b250.createCircles();
        b500.createCircles();

        loadLabels("1",36,112,13,layout);
        loadLabels("10",117,112,13,layout);
        loadLabels("50",209,112,13,layout);
        loadLabels("100",29,191,13,layout);
        loadLabels("250", 114,191,13,layout);
        loadLabels("500",205,191,13,layout);
        loadLabels("1000",26,267,13,layout);
        loadLabels("5000",111,267,13,layout);
        loadLabels("10000",198,267,13,layout);
        loadLabels("1",359,112,13,layout);
        loadLabels("5",450,112,13,layout);
        loadLabels("10",531, 112,13,layout);
        loadLabels("25", 356,191,13,layout);
        loadLabels("50", 447,191,13,layout);
        loadLabels("100", 528, 191,13,layout);
        loadLabels("150",353,267,13,layout);
        loadLabels("250", 444,267,13,layout);
        loadLabels("500",528,267,13,layout);
        loadLabels("Total Pages Read",14,32,32,layout);
        loadLabels("Total Books Read",337,32,32,layout);
        loadLabels("You have read: ",25,329,15,layout);
        loadLabels("Pages until next achievement: ",25,360,15,layout);
        loadLabels("You have read: ",337,329,15,layout);
        loadLabels("Books until next achievement: ",337,360,15,layout);

        Scene scene = new Scene(layout, 600,400);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public void loadLabels(String text, int layX, int layY, int fSize, AnchorPane layout){
        Label label = new Label(text);
        label.setLayoutX(layX);
        label.setLayoutY(layY);
        label.setFont(Font.font("System", FontWeight.BOLD,fSize));
        label.setAlignment(Pos.CENTER_LEFT);

        layout.getChildren().add(label);
    }

    public void checkTotalPages(){
        int pagesRead = alldata.userStore.totalPagesRead;

        if(pagesRead >= 0){
            p1.setDone(true);
        }
        else if(pagesRead >= 10){
            p10.setDone(true);
        }
        else if(pagesRead >= 50){
            p50.setDone(true);
        }
        else if(pagesRead >= 100){
            p100.setDone(true);
        }
        else if(pagesRead >= 250){
            p250.setDone(true);
        }
        else if(pagesRead >= 500){
            p500.setDone(true);
        }
        else if(pagesRead >= 1000){
            p1000.setDone(true);
        }
        else if(pagesRead >= 5000){
            p5000.setDone(true);
        }
        else if(pagesRead >= 10000){
            p10000.setDone(true);
        }

    }

    public void checkTotalBooks(){
        int booksRead = alldata.userStore.totalBooksRead;

        if(booksRead >= 1){
            b1.setDone(true);
        }
        else if(booksRead >= 5){
            b5.setDone(true);
        }
        else if(booksRead >= 10){
            b10.setDone(true);
        }
        else if(booksRead >= 25){
            b25.setDone(true);
        }
        else if(booksRead >= 50){
            b50.setDone(true);
        }
        else if(booksRead >= 100){
            b100.setDone(true);
        }
        else if(booksRead >= 150){
            b150.setDone(true);
        }
        else if(booksRead >= 250){
            b250.setDone(true);
        }
        else if(booksRead >= 500){
            b500.setDone(true);
        }

    }

    private class Circles extends Circle {

        private final AnchorPane layout;
        private final int layX;
        private final int layY;

        private Circles(AnchorPane layout, int layX, int layY){
            this.layout = layout;
            this.layX = layX;
            this.layY = layY;
            isDone = false;
        }

        public void createCircles(){
            Circle circle = new Circle();
            circle.setRadius(26);
            circle.setLayoutX(layX);
            circle.setLayoutY(layY);

            if(!isDone())
                circle.setFill(Color.WHITE);
            else
                circle.setFill(Color.GREEN);

            layout.getChildren().add(circle);

        }

        public boolean isDone() {
            return isDone;
        }

        public void setDone(boolean done) {
            isDone = done;
        }
    }
}
