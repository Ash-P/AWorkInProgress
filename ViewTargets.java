import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;;

public class ViewTargets {

	private final TableView<storage.targetData> table = new TableView<storage.targetData>();
	private Scene scene;
	private Group group;
	public static Button back = new Button("Back");
	TableColumn<storage.targetData, Integer> targetValueCol = new TableColumn<>();
	public ViewTargets() {
		createUI();
		setupEditableColumns();
		
	}
	

	
	private void createUI() {
		table.setEditable(true);
		
		
		
		TableColumn<storage.targetData, Integer> targetTypeCol = new TableColumn<storage.targetData, Integer>("Target Type");
		targetTypeCol.setCellValueFactory(new PropertyValueFactory<>("targetType"));
		targetTypeCol.setMinWidth(100);
		targetTypeCol.setEditable(true);
		
		/*
		targetTypeCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		
		targetTypeCol.setOnEditCommit((TableColumn.CellEditEvent<storage.targetData, Integer> e)->{
			int index = ((TableColumn.CellEditEvent<storage.targetData, Integer>) e).getTablePosition().getRow();
			((storage.targetData) e.getTableView().getItems().get(index)).setTargetType(e.getNewValue());
		});
		*/
		
		
		
		
		
		TableColumn<storage.targetData, Boolean> isCompleteCol = new TableColumn<storage.targetData, Boolean>("Completed");
		isCompleteCol.setCellValueFactory(new PropertyValueFactory<>("isComplete"));
		isCompleteCol.setMinWidth(90);
		
		
		
		
		TableColumn<storage.targetData, String> deadlineDateCol = new TableColumn<storage.targetData, String>("Deadline Date");
		deadlineDateCol.setCellValueFactory(new PropertyValueFactory<>("deadlineDate"));
		deadlineDateCol.setMinWidth(110);
		
		deadlineDateCol.setCellFactory(TextFieldTableCell.forTableColumn());
		deadlineDateCol.setOnEditCommit((TableColumn.CellEditEvent<storage.targetData, String> e)->{
			int index = ((TableColumn.CellEditEvent<storage.targetData, String>) e).getTablePosition().getRow();
			((storage.targetData) e.getTableView().getItems().get(index)).setDeadlineDate(e.getNewValue());
		});
		
		
		targetValueCol = new TableColumn<storage.targetData, Integer>("Target Value");
		targetValueCol.setCellValueFactory(new PropertyValueFactory<>("targetValue"));
		targetValueCol.setMinWidth(100);
		
		
		
		
		
		TableColumn<storage.targetData, Integer> valueRemainingCol = new TableColumn<storage.targetData, Integer>("Value Remaining");
		valueRemainingCol.setCellValueFactory(new PropertyValueFactory<>("valueRemaining"));
		valueRemainingCol.setMinWidth(140);
		
		ObservableList<storage.targetData> observableTargetList = FXCollections.observableArrayList();
		for(storage.targetData t : alldata.targetStore) observableTargetList.add(t);
		table.setItems(observableTargetList);
		
		table.setEditable(true);
		table.setPrefWidth(580);
		table.getColumns().addAll( Arrays.asList(targetTypeCol, isCompleteCol, deadlineDateCol, targetValueCol, valueRemainingCol) );
		
		
		
		GridPane.setConstraints(table, 0, 1);
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);
		grid.getChildren().addAll(table);
		
		group = new Group(grid);
		
		scene = new Scene(group, 600, 450);
		MAIN.mainStage.setScene(scene);
		MAIN.mainStage.setTitle("View Targets");
		
	             

	}
	
	
	public void setupEditableColumns() {
		targetValueCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		targetValueCol.setOnEditCommit((TableColumn.CellEditEvent<storage.targetData, Integer> e)->{
			int index = ((TableColumn.CellEditEvent<storage.targetData, Integer>) e).getTablePosition().getRow();
			((storage.targetData) e.getTableView().getItems().get(index)).setTargetValue(e.getNewValue());
			table.getItems().clear();
			table.getColumns().clear();
			createUI();
			
			
		});
	}
	
	
	
}
