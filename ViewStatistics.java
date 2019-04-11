import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class ViewStatistics {

	private final TextArea textArea = new TextArea();
	private Scene scene;
	private Group group;
	
	public ViewStatistics() {
		createUI();
		scene = new Scene(group, 340, 160);
		MAIN.mainStage.setScene(scene);
		MAIN.mainStage.setTitle("View Statistics");
	}
	
	public void createUI() {
		textArea.setText("Your tracking data statistics:\n");
		//textArea.appendText("   Total pages read: " + alldata.userStore.totalPagesRead + ".\n");
		//textArea.appendText("   Total books read: " + alldata.userStore.totalBooksRead + ".\n");
		textArea.appendText("   Total pages read: [pages].\n");
		textArea.appendText("   Total books read: [books].\n");
		textArea.setEditable(false);
		textArea.setPrefSize(320, 100);
		GridPane.setConstraints(textArea, 0, 1);
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);
		grid.getChildren().addAll(textArea);
		
	    group = new Group(grid);
	}
	
}
