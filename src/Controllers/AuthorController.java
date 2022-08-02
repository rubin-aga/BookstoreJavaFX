package Controllers;

import Auxiliaries.FileHandler;
import Models.Author;
import Models.Book;
import Views.AuthorView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AuthorController {
    private AuthorView authorView;
    public AuthorController(AuthorView authorView) {
        this.authorView = authorView;
        setSaveListener();
        setDeleteListener();
        setSearchListener();
        setEditListener();
    }

    private void setEditListener() {
        // anonymous inner class
   /*     authorView.getFirstNameCol().setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Author, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Author, String> event) {
                Author authorToEdit = event.getRowValue();
                int index = Author.getAuthors().indexOf(authorToEdit);
                Author.getAuthors().get(index).setFirstName(event.getNewValue());
            }
        });*/
        // with lambda now
    	Alert alert = new Alert(Alert.AlertType.WARNING);
        authorView.getLastNameCol().setOnEditCommit(event -> {
            Author authorToEdit = event.getRowValue();
            System.out.println(event.getRowValue());
            int index = Author.getAuthors().indexOf(authorToEdit);
            System.out.println(index);
            if(event.getNewValue().matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$")) {
            	Author.getAuthors().get(index).setLastName(event.getNewValue());	
            }
            else {
            	authorView.getTableView().getItems().set(authorView.getTableView().getItems().indexOf(authorToEdit), authorToEdit);            	
            	alert.setTitle("Error");
            	alert.setHeaderText("Problem with input.");
            	alert.setContentText("Improper Lastname Value entered.");
            	alert.show();
            }
        });
        authorView.getFirstNameCol().setOnEditCommit(event -> {
            Author authorToEdit = event.getRowValue();
            System.out.println(event.getRowValue());
            int index = Author.getAuthors().indexOf(authorToEdit);
            System.out.println(index);
            if(event.getNewValue().matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$")) {
            	Author.getAuthors().get(index).setFirstName(event.getNewValue());	
            }
            else {
            	authorView.getTableView().getItems().set(authorView.getTableView().getItems().indexOf(authorToEdit), authorToEdit);            	
            	alert.setTitle("Error");
            	alert.setHeaderText("Problem with input.");
            	alert.setContentText("Improper Firstname Value entered.");
            	alert.show();
            }
        });
        // if the user clicks edit button, save the changes into the file
        authorView.getEditBtn().setOnAction(e -> {
            try {
                // todo change it
                FileHandler.overwriteCurrentListToFile(Author.DATA_FILE, Author.getAuthors());
                authorView.getResultLabel().setText("Authors were updated successfully");
            } catch (IOException ex) {
                authorView.getResultLabel().setText("Writing authors to the file failed!");
                ex.printStackTrace();
            }
        });
    }

    private void setSearchListener() {
        authorView.getSearchView().getClearBtn().setOnAction(e -> {
            authorView.getSearchView().getSearchField().setText("");
            authorView.getTableView().setItems(FXCollections.observableArrayList(Author.getAuthors()));
        });
        authorView.getSearchView().getSearchBtn().setOnAction(e -> {
            String searchText = authorView.getSearchView().getSearchField().getText();
            ArrayList<Author> searchResults = Author.getSearchResults(searchText);
            authorView.getTableView().setItems(FXCollections.observableArrayList(searchResults));
        });
    }

    private void setDeleteListener() {
        authorView.getDeleteBtn().setOnAction(e -> {
            ObservableList<Author> authorsInTable = authorView.getTableView().getItems();
            ObservableList<Integer> indices = authorView.getTableView().getSelectionModel().getSelectedIndices();
            for (int index: indices)
                authorsInTable.get(index).deleteFromFile();
            authorView.getTableView().setItems(FXCollections.observableArrayList(Author.getAuthors()));
            authorView.getResultLabel().setTextFill(Color.DARKGREEN);
            authorView.getResultLabel().setText("Authors deleted successfully!");
        });
    }

    private void setSaveListener() {
        authorView.getSaveBtn().setOnAction(e -> {
        	String firstName = "";
        	String lastName = "";
            if((authorView.getFirstNameField().getText().matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$")) && 
            		(authorView.getLastNameField().getText().matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$"))
            		) {
                firstName = authorView.getFirstNameField().getText();
                lastName = authorView.getLastNameField().getText();
            }
            else {
            	Alert alert = new Alert(Alert.AlertType.WARNING);
            	alert.setTitle("Error");
            	alert.setHeaderText("Problem with input.");
            	alert.setContentText("Improper Input Values entered.");
            	alert.show();
            }
            
            Author author = new Author(firstName, lastName);
            if (author.saveInFile()){
                authorView.getTableView().getItems().add(author);
                authorView.getResultLabel().setText("Author created successfully!");
                authorView.getResultLabel().setTextFill(Color.DARKGREEN);
                authorView.getFirstNameField().setText("");
                authorView.getLastNameField().setText("");
            }
            else {
                authorView.getResultLabel().setText("Author creation failed!");
                authorView.getResultLabel().setTextFill(Color.DARKRED);
            }

        });
    }
    
    
}
