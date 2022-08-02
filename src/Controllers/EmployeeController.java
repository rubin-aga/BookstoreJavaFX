
package Controllers;

import Auxiliaries.FileHandler;
import Models.Author;
import Models.Book;
import Models.Role;
import Models.User;
import Views.AuthorView;
import Views.EmployeeView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.paint.Color;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class EmployeeController {
	
    private EmployeeView employeeView;
    public EmployeeController(EmployeeView employeeView) {
        this.employeeView = employeeView;
        setSaveListener();
        setDeleteListener();
        setSearchListener();
        setEditListener();
    }
    
    private void setEditListener() {
    	Alert alert = new Alert(Alert.AlertType.WARNING);
        employeeView.getUsernameCol().setOnEditCommit(event -> {
            User userToEdit = event.getRowValue();
            System.out.println(event.getRowValue());
            int index = User.getUsers().indexOf(userToEdit);
            System.out.println(index);
            if(event.getNewValue().matches("[\\w\\s]+")) {
            	User.getUsers().get(index).setUsername(event.getNewValue());	
            }
            else {
            	employeeView.getTableView().getItems().set(employeeView.getTableView().getItems().indexOf(userToEdit), userToEdit);
            	alert.setTitle("Error");
            	alert.setHeaderText("Problem with input.");
            	alert.setContentText("Improper Username Value entered.");
            	alert.show();
            }
        });
        employeeView.getPasswordCol().setOnEditCommit(event -> {
            User userToEdit = event.getRowValue();
            System.out.println(event.getRowValue());
            int index = User.getUsers().indexOf(userToEdit);
            System.out.println(index);
            if(event.getNewValue().matches("[\\w\\s]+")) {
            	User.getUsers().get(index).setPassword(event.getNewValue());
            }
            else {
            	employeeView.getTableView().getItems().set(employeeView.getTableView().getItems().indexOf(userToEdit), userToEdit);
            	alert.setTitle("Error");
            	alert.setHeaderText("Problem with input.");
            	alert.setContentText("Improper Password Value entered.");
            	alert.show();
            }
        });
        employeeView.getBirthdayCol().setOnEditCommit(event -> {
            User userToEdit = event.getRowValue();
            System.out.println(event.getRowValue());
            int index = User.getUsers().indexOf(userToEdit);
            System.out.println(index);
            if(event.getNewValue().matches("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((?:19|20)[0-9][0-9])")) {
            	User.getUsers().get(index).setBirthday(event.getNewValue());
            }
            else {
            	employeeView.getTableView().getItems().set(employeeView.getTableView().getItems().indexOf(userToEdit), userToEdit);
            	alert.setTitle("Error");
            	alert.setHeaderText("Problem with input.");
            	alert.setContentText("Improper Birthday Value entered.");
            	alert.show();
            }
        });
        employeeView.getPhoneCol().setOnEditCommit(event -> {
            User userToEdit = event.getRowValue();
            System.out.println(event.getRowValue());
            int index = User.getUsers().indexOf(userToEdit);
            System.out.println(index);
            if(event.getNewValue().matches("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$")) {
            	User.getUsers().get(index).setPhone(event.getNewValue());	
            }
            else {
            	employeeView.getTableView().getItems().set(employeeView.getTableView().getItems().indexOf(userToEdit), userToEdit);
            	alert.setTitle("Error");
            	alert.setHeaderText("Problem with input.");
            	alert.setContentText("Improper Phone Value entered.");
            	alert.show();
            }
            
        });
        employeeView.getEmailCol().setOnEditCommit(event -> {
            User userToEdit = event.getRowValue();
            System.out.println(event.getRowValue());
            int index = User.getUsers().indexOf(userToEdit);
            System.out.println(index);
            if(event.getNewValue().matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
            	User.getUsers().get(index).setEmail(event.getNewValue());
            }
            else {
            	employeeView.getTableView().getItems().set(employeeView.getTableView().getItems().indexOf(userToEdit), userToEdit);
            	alert.setTitle("Error");
            	alert.setHeaderText("Problem with input.");
            	alert.setContentText("Improper Email Value entered.");
            	alert.show();
            }
        });
        employeeView.getSalaryCol().setOnEditCommit(event -> {
            User userToEdit = event.getRowValue();
            System.out.println(event.getRowValue());
            int index = User.getUsers().indexOf(userToEdit);
            System.out.println(index);
            if(event.getNewValue().matches("^[1-9][0-9]*(\\.[0-9])?")) {
            	User.getUsers().get(index).setSalary(event.getNewValue());
            }
            else {
            	employeeView.getTableView().getItems().set(employeeView.getTableView().getItems().indexOf(userToEdit), userToEdit);
            	alert.setTitle("Error");
            	alert.setHeaderText("Problem with input.");
            	alert.setContentText("Improper Salary Value entered.");
            	alert.show();
            }
        });
        employeeView.getRoleCol().setOnEditCommit(e -> {
            User userToEdit = e.getRowValue();
            Role oldVal=userToEdit.getRole();
            userToEdit.setRole(e.getNewValue());
            if (userToEdit.isValid()){
                userToEdit.updateFile();
            }
            else {
                System.out.println("Edit value invalid! "+e.getNewValue());
                userToEdit.setRole(oldVal);
                employeeView.getTableView().getItems().set(employeeView.getTableView().getItems().indexOf(userToEdit), userToEdit);
                employeeView.getResultLabel().setText("Edit value invalid!");
                employeeView.getResultLabel().setTextFill(Color.DARKRED);
            }
        });
    
        // if the user clicks edit button, save the changes into the file
        employeeView.getEditBtn().setOnAction(e -> {
            try {
                // todo change it
                FileHandler.overwriteCurrentListToFile(User.DATA_FILE, User.getUsers());
                employeeView.getResultLabel().setText("Users were updated successfully");
            } catch (IOException ex) {
                employeeView.getResultLabel().setText("Writing users to the file failed!");
                ex.printStackTrace();
            }
        });
}

    private void setSearchListener() {
        employeeView.getSearchView().getClearBtn().setOnAction(e -> {
            employeeView.getSearchView().getSearchField().setText("");
            employeeView.getTableView().setItems(FXCollections.observableArrayList(User.getUsers()));
        });
        employeeView.getSearchView().getSearchBtn().setOnAction(e -> {
            String searchText = employeeView.getSearchView().getSearchField().getText();
            ArrayList<User> searchResults = User.getSearchResults(searchText);
            employeeView.getTableView().setItems(FXCollections.observableArrayList(searchResults));
        });
    }

    private void setDeleteListener() {
        employeeView.getDeleteBtn().setOnAction(e -> {
            ObservableList<User> usersInTable = employeeView.getTableView().getItems();
            ObservableList<Integer> indices = employeeView.getTableView().getSelectionModel().getSelectedIndices();
            for (int index: indices)
                usersInTable.get(index).deleteFromFile();
            employeeView.getTableView().setItems(FXCollections.observableArrayList(User.getUsers()));
            employeeView.getResultLabel().setTextFill(Color.DARKGREEN);
            employeeView.getResultLabel().setText("User deleted successfully!");
        });
    }

    private void setSaveListener() {
        employeeView.getSaveBtn().setOnAction(e -> {
        	String username = "";
        	String password = "";
        	String birthday = "";
        	String phone = "";
        	String email = "";
        	String salary = "";
        	Alert alert = new Alert(Alert.AlertType.WARNING);
       /*     if((employeeView.getUsernameField().getText().matches("[\\w\\s]+")) && 
            		(employeeView.getPasswordField().getText().matches("[\\w\\s]+"))&&
            		(employeeView.getBirthdayField().getText().matches("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((?:19|20)[0-9][0-9])"))&&
            		(employeeView.getPhoneField().getText().matches("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$"))&&
            		(employeeView.getEmailField().getText().matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$"))&&
            		(employeeView.getSalaryField().getText().matches("^[1-9][0-9]*(\\.[0-9])?"))) {
                username = employeeView.getUsernameField().getText();
                password = employeeView.getPasswordField().getText();
                birthday = employeeView.getBirthdayField().getText();
                phone = employeeView.getPhoneField().getText();
                email = employeeView.getEmailField().getText();
                salary = employeeView.getSalaryField().getText();
              //  String accessLevel = employeeView.getAccessLevelField().getText();
                }*/
            if((employeeView.getUsernameField().getText().matches("[\\w\\s]+"))){
            	username = employeeView.getUsernameField().getText();
            }
            else {
            	alert.setTitle("Error");
            	alert.setHeaderText("Problem with input.");
            	alert.setContentText("Improper Username Value entered.");
            	alert.show();
            }
            if((employeeView.getPasswordField().getText().matches("[\\w\\s]+"))){
            	password = employeeView.getPasswordField().getText();
            }
            else {
            	alert.setTitle("Error");
            	alert.setHeaderText("Problem with input.");
            	alert.setContentText("Improper Password Value entered.");
            	alert.show();
            }
            if((employeeView.getBirthdayField().getText().matches("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((?:19|20)[0-9][0-9])"))){
            	birthday = employeeView.getBirthdayField().getText();
            }
            else {
            	alert.setTitle("Error");
            	alert.setHeaderText("Problem with input.");
            	alert.setContentText("Improper Birthday Value entered.");
            	alert.show();
            }
            if((employeeView.getPhoneField().getText().matches("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$"))){
            	phone = employeeView.getPhoneField().getText();
            }
            else {
            	alert.setTitle("Error");
            	alert.setHeaderText("Problem with input.");
            	alert.setContentText("Improper Phone Value entered.");
            	alert.show();
            }
            if((employeeView.getEmailField().getText().matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"))){
            	
            	email = employeeView.getEmailField().getText();
            }
            else {
            	alert.setTitle("Error");
            	alert.setHeaderText("Problem with input.");
            	alert.setContentText("Improper Email Value entered.");
            	alert.show();
            }
            if((employeeView.getSalaryField().getText().matches("^[1-9][0-9]*(\\.[0-9])?"))){
            	salary = employeeView.getSalaryField().getText();
            }
            else {
            	alert.setTitle("Error");
            	alert.setHeaderText("Problem with input.");
            	alert.setContentText("Improper Salary Value entered.");
            	alert.show();
            }
            
            	Role role = employeeView.getRoleComboBox().getValue();
            
           /*     Role accessEnum = null;
                if(accessLevel.equals("HIGH"))
                	accessEnum = Role.ADMIN;
                else if(accessLevel.equals("MEDIUM"))
                	accessEnum = Role.MANAGER;
                else if(accessLevel.equals("LOW"))
                	accessEnum = Role.LIBRARIAN;
                
                else {
            	Alert alert = new Alert(Alert.AlertType.WARNING);
            	alert.setTitle("Error");
            	alert.setHeaderText("Problem with input.");
            	alert.setContentText("Improper Input Values entered.");
            	alert.showAndWait();
            }
            */
            
            User user = new User(username, password, birthday, phone, email, salary, role);
            if(!user.usernameExists()) {
            if (user.saveInFile()){
                employeeView.getTableView().getItems().add(user);
                employeeView.getResultLabel().setText("User created successfully!");
                employeeView.getResultLabel().setTextFill(Color.DARKGREEN);
                employeeView.getUsernameField().setText("");
                employeeView.getPasswordField().setText("");
                employeeView.getBirthdayField().setText("");
                employeeView.getPhoneField().setText("");
                employeeView.getEmailField().setText("");
                employeeView.getSalaryField().setText("");
            }
            else {
                employeeView.getResultLabel().setText("User creation failed!");
                employeeView.getResultLabel().setTextFill(Color.DARKRED);
            }
            }
            else {
            	alert.setTitle("Error");
            	alert.setHeaderText("Problem with input.");
            	alert.setContentText("Username already exists");
            	alert.show();
            }
            
        });
    }
}
