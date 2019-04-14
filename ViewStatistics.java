import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ViewStatistics {

	private static final Label title = new Label("Statistics");
	private static final Button backBtn = new Button("Back");
	private static final Label label = new Label("Your tracking data statistics:");
	private static Label pagesLab = new Label();
	private static Label booksLab = new Label();
	private Scene scene;
	private Group group;
	
	public ViewStatistics() {
		createUI();
		scene = new Scene(group, 340, 170);
		MAIN.mainStage.setScene(scene);
		MAIN.mainStage.setTitle("View Statistics");
	}
	
	public void createUI() {
		
		pagesLab.setText("   Total pages read: " + alldata.userStore.totalPagesRead);
		booksLab.setText("   Total books read: " + alldata.userStore.totalBooksRead);
		
		GridPane.setConstraints(label, 0, 1);
		GridPane.setConstraints(pagesLab, 0, 2);
		GridPane.setConstraints(booksLab, 0, 3);
		
		label.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
		pagesLab.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
		booksLab.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
		
		title.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 15));       
        title.setLayoutX(110);
        title.setLayoutY(15);

		final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(50, 0, 0, 10));
		vbox.getChildren().addAll(label, pagesLab, booksLab);	
		
		backBtn.setLayoutX(10);
		backBtn.setLayoutY(128);
		
		backBtn.setOnAction(e->{
			ViewTrackingDataMenu.instantiate();
			e.consume();
		});
		backBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
			if (ev.getCode() == KeyCode.ENTER) {
				backBtn.fire();
				ev.consume(); 
			}
		});
		
	    group = new Group(vbox, backBtn, title);
	}
	
}
