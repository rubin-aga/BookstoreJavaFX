package Views;

import java.util.ArrayList;

import Models.Author;
import Models.Book;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ManagerView extends BookView {
	public ManagerView() {
		super();
		showAlert();
	}
	public void showAlert() {
		String a = "";
		boolean quantCheck = false;
		for(Book book: Book.getBooks()) {
            if (book.getQuantity()<5) {
            	quantCheck = true;
            	a += "You are missing " + book.getTitle()+ ". Current quantity is: " + book.getQuantity() + "\n"; 
            	System.out.println(a);
            }
		}
		if(quantCheck) {
		TabPane tabPane = new TabPane();
    	Tab tab = new Tab();
    	tab.setText("ALERT");
		Label firstLabel = new Label(a);
		VBox vb = new VBox();
		BorderPane bp = new BorderPane();
		Stage leastQuant = new Stage();
		vb.setPadding(new Insets(20));
		vb.setSpacing(20);
		vb.getChildren().add(firstLabel);
		bp.setCenter(vb);
		tab.setContent(vb);
		tabPane.getTabs().add(tab);
    	tab.setClosable(false);
		Scene checkScene = new Scene(tabPane, 350, 150);
		leastQuant.setScene(checkScene);
		leastQuant.show();
		}
		
	}
}

