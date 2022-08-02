package Views;
import UI.ClearButton;
import UI.SearchButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class BillView {
    private final String searchLabel;
    private final String searchLabel2;
    private final TextField searchField = new TextField();
    private final TextField searchField2 = new TextField();
    private final Button searchBtn = new SearchButton();
    private final Button clearBtn = new ClearButton();
    private final HBox searchPane = new HBox();
    private final HBox searchPane2 = new HBox();
    public BillView(String searchLabel, String searchLabel2) {
        this.searchLabel = searchLabel;
        this.searchLabel2 = searchLabel2;
        setSearchForm();
        setStatForm();
    }
    public BillView() {
        this("", "");
    }

    public HBox getSearchPane() {
        return searchPane;
    }
//contains
    public TextField getSearchField() {
        return searchField;
    }

    public TextField getSearchField2() {
		return searchField2;
	}
	public Button getSearchBtn() {
        return searchBtn;
    }

    public Button getClearBtn() {
        return clearBtn;
    }
    
    public HBox getStatPane()
    {
    	return searchPane2;
    }
    private void setSearchForm() {
        Label label = new Label(searchLabel, searchField);
        Label label2 = new Label(searchLabel2, searchField2);
        label.setContentDisplay(ContentDisplay.RIGHT);
        label2.setContentDisplay(ContentDisplay.RIGHT);
        searchPane.setPadding(new Insets(20));
        searchPane.setSpacing(20);
        searchPane.setAlignment(Pos.CENTER);
        searchField.setPromptText("Type the start date ... ");
        searchField2.setPromptText("Type the end date ...");
        searchPane.getChildren().addAll(label, label2, searchBtn, clearBtn);
    }
    private void setStatForm() {
        Label label = new Label(searchLabel, searchField);
        Label label2 = new Label(searchLabel2, searchField2);
        label.setContentDisplay(ContentDisplay.RIGHT);
        label2.setContentDisplay(ContentDisplay.RIGHT);
        searchPane2.setPadding(new Insets(20));
        searchPane2.setSpacing(20);
        searchPane2.setAlignment(Pos.CENTER);
        searchField.setPromptText("Type the start date ... ");
        searchField2.setPromptText("Type the end date ...");
        searchPane2.getChildren().addAll(label, label2);
    }
    
    
    
}