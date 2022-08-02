package Views;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import Controllers.AuthorController;
import Controllers.EmployeeController;
import Controllers.MyEnumConverter;
import Models.Author;
import Models.Role;
import Models.User;
import UI.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class EmployeeView extends View{
    private final BorderPane borderPane = new BorderPane();
    private final TableView<User> tableView = new TableView<>();
    private final HBox formPane = new HBox();
	private final TextField usernameField = new TextField();
	private final TextField birthdayField = new TextField();
    private final TextField phoneField = new TextField();
    private final TextField emailField = new TextField();
    private final TextField salaryField = new TextField();
    private final TextField accessLevelField = new TextField();
    private final TextField passwordField = new TextField();
    private final Button saveBtn = new CreateButton();
    private final Button deleteBtn = new DeleteButton();
    private final Button editBtn = new EditButton();
    private final TableColumn<User, String> usernameCol = new TableColumn<>("Username");
    private final TableColumn<User, String> birthdayCol = new TableColumn<>("Birthday");
    private final TableColumn<User, String> passwordCol = new TableColumn<>("Password");
	private final TableColumn<User, String> phoneCol = new TableColumn<>("Phone");
    private final TableColumn<User, String> emailCol = new TableColumn<>("Email");
    private final TableColumn<User, String> salaryCol = new TableColumn<>("Salary");
    private final TableColumn<User, String> accessLevelCol = new TableColumn<>("Access Level");
    private final TableColumn<User, Role> roleCol = new TableColumn<>("Role");
    private final ComboBox<Role> roleComboBox = new ComboBox<>();
    private final Label resultLabel = new Label("");
    private final SearchView searchView = new SearchView("Search for an employee");
    private final Button getStats = new Button("Get stats of library");
    private final BillView billView = new BillView("Start date", "End date");
  //  private final BookView bookView;
    
    
    public BillView getBillView() {
		return billView;
	}
	public Button getGetStats() {
		return getStats;
	}
	public ComboBox<Role> getRoleComboBox() {
        return roleComboBox;
    }
    public TableColumn<User, Role> getRoleCol() {
        return roleCol;
    }
    
    public TextField getUsernameField() {
 		return usernameField;
 	}

 	public TextField getPasswordField() {
 		return passwordField;
 	}
 	
    public SearchView getSearchView() {
        return searchView;
    }
    
    public TableColumn<User, String> getPhoneCol() {
		return phoneCol;
	}
    
    public TableColumn<User, String> getPasswordCol(){
    	return passwordCol;
    }
    
	public TableColumn<User, String> getEmailCol() {
		return emailCol;
	}

	public TableColumn<User, String> getSalaryCol() {
		return salaryCol;
	}

	public TableColumn<User, String> getAccessLevelCol() {
		return accessLevelCol;
	}

    public TableColumn<User, String> getUsernameCol() {
        return usernameCol;
    }
	public TableColumn<User, String> getBirthdayCol() {
		return birthdayCol;
	}

    public Button getEditBtn() {
        return editBtn;
    }

    public Button getDeleteBtn() {
        return deleteBtn;
    }

    public Label getResultLabel() {
        return resultLabel;
    }

    public TableView<User> getTableView() {
        return tableView;
    }

    public Button getSaveBtn() {
        return saveBtn;
    }
	public TextField getBirthdayField() {
		return birthdayField;
	}

	public TextField getPhoneField() {
		return phoneField;
	}

	public TextField getEmailField() {
		return emailField;
	}

	public TextField getSalaryField() {
		return salaryField;
	}

	public TextField getAccessLevelField() {
		return accessLevelField;
	}

    public EmployeeView() {
		setTableView();
        setForm();
        new EmployeeController(this);
    }

    private void setForm() {
        formPane.setPadding(new Insets(20));
        formPane.setSpacing(20);
        formPane.setAlignment(Pos.CENTER);
        Label usernameLabel = new Label("Username: ", usernameField);
        usernameLabel.setContentDisplay(ContentDisplay.TOP);
        Label accessLabel = new Label("Access Level: ", accessLevelField);
        accessLabel.setContentDisplay(ContentDisplay.TOP);
        Label birthdayLabel = new Label("Birthday: ", birthdayField);
        birthdayLabel.setContentDisplay(ContentDisplay.TOP);
        Label phoneLabel = new Label("Phone: ", phoneField);
        phoneLabel.setContentDisplay(ContentDisplay.TOP);
        Label emailLabel = new Label("Email: ", emailField);
        emailLabel.setContentDisplay(ContentDisplay.TOP);
        Label salaryLabel = new Label("Salary: ", salaryField);
        salaryLabel.setContentDisplay(ContentDisplay.TOP);
        Label passwordLabel = new Label("Password: ", passwordField);
        passwordLabel.setContentDisplay(ContentDisplay.TOP);
        Label roleLabel = new Label("Role", roleComboBox);
        roleComboBox.getItems().setAll(Role.ADMIN, Role.MANAGER, Role.LIBRARIAN);
        roleComboBox.setValue(Role.MANAGER);
        
        formPane.getChildren().addAll(usernameLabel, passwordLabel,roleLabel,birthdayLabel, phoneLabel
        		, emailLabel, salaryLabel, saveBtn, editBtn, deleteBtn);
    }

    private void setTableView() {
        // select multiple records in order to delete them
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.setEditable(true);
        tableView.setItems(FXCollections.observableArrayList(User.getUsers()));
        usernameCol.setCellValueFactory(
                new PropertyValueFactory<>("username")
        );
        usernameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        passwordCol.setCellValueFactory(
                new PropertyValueFactory<>("password")
        );
        passwordCol.setCellFactory(TextFieldTableCell.forTableColumn());
        
        accessLevelCol.setCellValueFactory(
                new PropertyValueFactory<>("Access Level")
        );
        accessLevelCol.setCellFactory(TextFieldTableCell.forTableColumn());
        
        birthdayCol.setCellValueFactory(
                new PropertyValueFactory<>("birthday")
        );
        birthdayCol.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneCol.setCellValueFactory(
                new PropertyValueFactory<>("phone")
        );
        phoneCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setCellValueFactory(
                new PropertyValueFactory<>("email")
        );
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        salaryCol.setCellValueFactory(
                new PropertyValueFactory<>("salary")
        );
        salaryCol.setCellFactory(TextFieldTableCell.forTableColumn());
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        roleCol.setCellFactory(ComboBoxTableCell.forTableColumn(Role.values()));
        
        tableView.getColumns().addAll(usernameCol, passwordCol, roleCol, birthdayCol, phoneCol, emailCol, salaryCol);
    }
    @Override
    public Parent getView() {
        borderPane.setCenter(tableView);
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(5);
        vBox.getChildren().addAll(formPane, resultLabel);
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(5);
        hBox.getChildren().addAll(searchView.getSearchPane(), getStats, billView.getStatPane());
        Stage showStats = new Stage();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy--HH-mm-ss");		
        getStats.setOnAction(ev -> {
        	
            String firstConstraint = getBillView().getSearchField().getText();
            String secondConstraint = getBillView().getSearchField2().getText();
            try {
            Date d1 = sdf.parse(firstConstraint);
            Date d2 = sdf.parse(secondConstraint);
            Date d3 = sdf.parse("01-01-2000--00-00-00");
            Date d4 = sdf.parse("01-02-2000--00-00-00");
            long month_Time = d4.getTime() - d3.getTime(); 
            long difference_In_Time = d2.getTime() - d1.getTime();
           // long mult = (difference_In_Time % month_Time);
            long mult = difference_In_Time;
            int mnCnt = 0;
            if(mult<month_Time) {
            	mnCnt = 1;
            }
            else {
            	while(mult>0) {
                	mult-=month_Time;
                	mnCnt++;
                }
            	mnCnt--;
            }
            
            
            System.out.println(month_Time);
            System.out.println(difference_In_Time);
            System.out.println(mult);
            System.out.println(mnCnt);
            File folder = new File("Bills");
            File folderPur = new File("Purchased");
            File[] listOfFiles = folder.listFiles();
            File[] listPurchased = folderPur.listFiles();
            String moneyLine = "";
            String sCurrentLine = "";
            String totalLines = "";
            String quantityLine = "";
            int cnt = 0;
            double finalMoney = 0;
            for (int i = 0; i < listOfFiles.length; i++) {
              File file = listOfFiles[i];
              Date fileDate = sdf.parse(file.getName());
              double graphMoney = 0.0;
              int graphQuantity = 0;
              
              if(fileDate.getTime()<=d2.getTime() && fileDate.getTime()>=d1.getTime()) {     	  
              BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
              if (file.isFile() && file.getName().endsWith(".txt")) {
            	  while ((sCurrentLine = br.readLine()) != null) 
                  {
            		  if(sCurrentLine.contains("Complete Bill Price:")) {
            			  moneyLine = br.readLine();  
            			  graphMoney = Double.parseDouble(moneyLine);
            			  finalMoney += graphMoney;
            			//  cnt++;
            		  }
            		/*  if(sCurrentLine.contains("Complete Quantity of Books:")) {
            			  quantityLine = br.readLine();
            			  graphQuantity = Integer.parseInt(quantityLine);
            		  }*/	   
                      //System.out.println(sCurrentLine);
            	//	  series.getData().add(new XYChart.Data(graphMoney, graphMoney));
                  }
            	  System.out.println(moneyLine);
              }
              totalLines+="Quantity in bill: " + quantityLine + " Money made in the bill" + moneyLine + "\n";
            }
               
            }
            String spentLine = "";
            double finalSpentMoney = 0.0;
            for(int i = 0; i<listPurchased.length; i++) {
            	File file2 = listPurchased[i];
            	Date fileDate = sdf.parse(file2.getName());
                double tempMoney = 0.0;
                int graphQuantity = 0;
                
                if(fileDate.getTime()<=d2.getTime() && fileDate.getTime()>=d1.getTime()) {     	  
                BufferedReader br = new BufferedReader(new FileReader(file2.getAbsolutePath()));
                if (file2.isFile() && file2.getName().endsWith(".txt")) {
              	  while ((sCurrentLine = br.readLine()) != null) 
                    {
              			  spentLine = sCurrentLine;  
              			  tempMoney = Double.parseDouble(spentLine);
              			  finalSpentMoney += tempMoney;
              			//  cnt++;
              		  }
              		/*  if(sCurrentLine.contains("Complete Quantity of Books:")) {
              			  quantityLine = br.readLine();
              			  graphQuantity = Integer.parseInt(quantityLine);
              		  }*/	   
                        //System.out.println(sCurrentLine);
              	//	  series.getData().add(new XYChart.Data(graphMoney, graphMoney));
                    }
              	  System.out.println(finalSpentMoney);
                }
           //     totalLines+="Quantity in bill: " + quantityLine + " Money made in the bill" + moneyLine + "\n";
              }
            PieChart.Data data[] = new PieChart.Data[2];
            data[0] = new PieChart.Data("Total money made: " + finalMoney, finalMoney);
            double moneyspent = 0.0;
            for(User u: User.getUsers()) {
            	double temp = Double.parseDouble(u.getSalary());
            	moneyspent += temp * mnCnt;
            }
            System.out.println(moneyspent);
            System.out.println(finalSpentMoney);
            
            data[1] = new PieChart.Data("Total money spent on employees = " + moneyspent + finalSpentMoney, moneyspent+finalSpentMoney);
            PieChart pie_chart = new PieChart(FXCollections.observableArrayList(data));
           // pie_chart.setLabelLineLength(10.0f);
            pie_chart.setLabelsVisible(true);
           // pie_chart.setStartAngle(20.0f);
            pie_chart.setClockwise(false);
            Group root = new Group(pie_chart);
    		Label firstLabel = new Label(totalLines);
    		VBox vb = new VBox();
    		//BorderPane bp = new BorderPane();
    		StackPane sp = new StackPane();
    		sp.getChildren().add(root);
    		//vb.setPadding(new Insets(20));
    		//vb.setSpacing(20);
    		//vb.getChildren().addAll(root);
    		//bp.setCenter(vb);
    	
    		Scene checkScene = new Scene(sp, 300, 300);
    		showStats.setScene(checkScene);
    		showStats.show();
            }
            catch(Exception exc) {
            	exc.printStackTrace();
            }
        });
        borderPane.setBottom(vBox);
        borderPane.setTop(hBox);
        return borderPane;
         //   ArrayList<Author> searchResults = Author.getSearchResults(searchText);
         //   authorView.getTableView().setItems(FXCollections.observableArrayList(searchResults));
    }
        
    }





