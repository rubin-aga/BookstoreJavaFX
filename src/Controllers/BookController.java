package Controllers;
import Models.Author;
import javafx.application.Application; 
import javafx.scene.Group; 
import javafx.scene.Scene; 
import javafx.stage.Stage; 
import javafx.scene.chart.LineChart; 
import javafx.scene.chart.NumberAxis; 
import javafx.scene.chart.XYChart; 
import Models.Book;
import Models.BookToBill;
import Models.Role;
import Models.User;
import Views.BookView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import Auxiliaries.FileHandler;

public class BookController {
    private final BookView bookView;
    public BookController(BookView bookView) {
        this.bookView = bookView;
        setSaveListener();
        setDeleteListener();
        setEditListener();
        setSearchListener();
        setBillFinderListener();
    }

    private void setSaveListener() {
        bookView.getSaveBtn().setOnAction(e -> {
            String isbn = bookView.getIsbnField().getText();
            String title = bookView.getTitleField().getText();
            String category = bookView.getCategoryField().getText();
            float purchasedPrice = Float.parseFloat(bookView.getPurchasedPriceField().getText());
            float sellingPrice = Float.parseFloat(bookView.getSellingPriceField().getText());
            Author author = bookView.getAuthorsComboBox().getValue();
            int quantity = Integer.parseInt(bookView.getQuantityField().getText());
            Book book = new Book(isbn, title, purchasedPrice, sellingPrice, author, quantity, category);
            if(!book.exists()) {
            	if(book.isValid()) {
            		if (book.saveInFile()) {
            			try {  
            	            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy--HH-mm-ss");  
            	            LocalDateTime now = LocalDateTime.now();
            	            String a = dtf.format(now);
            	            String strBill = dtf.format(now) + ".txt";
            	            final String BILL_PATH = "Purchased/" + strBill;
            	            FileWriter fw = new FileWriter(BILL_PATH);
            	            BufferedWriter bw = new BufferedWriter(fw);
            	           // File stats = new File("stats.txt");
            	            double moneySpent = purchasedPrice * quantity;
            	            String moneySpentStr = String.valueOf(moneySpent);
            	            bw.write(moneySpentStr);
            	            bw.write("\n");
            	            bw.close();
            	          }
            	          catch(Exception ex) {
            	        	  ex.printStackTrace();
            	          }
            			bookView.getTableView().getItems().add(book);
            			bookView.getResultLabel().setText("Book created successfully");
            			bookView.getResultLabel().setTextFill(Color.DARKGREEN);
            			resetFields();
            		}	
            	}	
            	else{
            		bookView.getResultLabel().setText("Book creation failed");
            		bookView.getResultLabel().setTextFill(Color.DARKRED);
            }
           }
            else{
            	Alert alert = new Alert(Alert.AlertType.WARNING);
            	alert.setTitle("Error");
            	alert.setHeaderText("Problem with input.");
            	alert.setContentText("That book already exists. Book was not added");
            	alert.show();
            }
          
          /*
            Path path = Paths.get("stats.txt");
            Path path2 = Paths.get(BILL_PATH);
            String currStat = Files.readAllLines(path).get(0);
            double currStatDouble = Double.parseDouble(currStat);
            currStat = String.valueOf(currStatDouble);
            byte[] strToBytes = currStat.getBytes();
            Files.write(path, strToBytes);
            String test = Files.readAllLines(path2).get(cnt);
            System.out.println(test);
            System.out.println(currStat);
            */
            
            
        });
    }
    private void setDeleteListener() {
        bookView.getDeleteBtn().setOnAction(e -> {
            ObservableList<Book> booksInTable = bookView.getTableView().getItems();
            ObservableList<Integer> indices = bookView.getTableView().getSelectionModel().getSelectedIndices();
            for (int index: indices) {
            	booksInTable.get(index).deleteFromFile();
            }
            bookView.getTableView().setItems(FXCollections.observableArrayList(Book.getBooks()));
            bookView.getResultLabel().setTextFill(Color.DARKGREEN);
            bookView.getResultLabel().setText("Books deleted successfully!");
        });
    }
    
    private void setSearchListener() {
        bookView.getSearchView().getClearBtn().setOnAction(e -> {
            bookView.getSearchView().getSearchField().setText("");
            bookView.getTableView().setItems(FXCollections.observableArrayList(Book.getBooks()));
        });
        bookView.getSearchView().getSearchBtn().setOnAction(e -> {
            String searchText = bookView.getSearchView().getSearchField().getText();
            ArrayList<Book> searchResults = Book.getSearchResults(searchText);
            bookView.getTableView().setItems(FXCollections.observableArrayList(searchResults));
        });
    }
    
	private void setBillFinderListener() {
		Stage showBills = new Stage();
			bookView.getBillView().getClearBtn().setOnAction(e -> {
            bookView.getBillView().getSearchField().setText("");
            bookView.getBillView().getSearchField2().setText("");
        });
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy--HH-mm-ss");
		
        bookView.getBillView().getSearchBtn().setOnAction(e -> {
            String firstConstraint = bookView.getBillView().getSearchField().getText();
            String secondConstraint = bookView.getBillView().getSearchField2().getText();
            try {
            XYChart.Series series = new XYChart.Series(); 
            XYChart.Series series2 = new XYChart.Series();
            Date d1 = sdf.parse(firstConstraint);
            Date d2 = sdf.parse(secondConstraint);
            long difference_In_Time = d2.getTime() - d1.getTime();
            
            
            File folder = new File("Bills");
            File[] listOfFiles = folder.listFiles();
            String moneyLine = "";
            String sCurrentLine = "";
            String totalLines = "";
            String quantityLine = "";
            int cnt = 0;
            
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
            			  cnt++;
            			  series.getData().add(new XYChart.Data(cnt, graphMoney));
            		  }
            		  if(sCurrentLine.contains("Complete Quantity of Books:")) {
            			  quantityLine = br.readLine();
            			  graphQuantity = Integer.parseInt(quantityLine);
            			  series2.getData().add(new XYChart.Data(cnt, graphQuantity));
            		  }
            		  
            		   
                      //System.out.println(sCurrentLine);
            	//	  series.getData().add(new XYChart.Data(graphMoney, graphMoney));
                  }
            	  System.out.println(moneyLine);
              }
              totalLines+="Quantity in bill: " + quantityLine + " Money made in the bill" + moneyLine + "\n";
            }
               
            }
            NumberAxis xAxis = new NumberAxis(0, cnt, 1); 
            xAxis.setLabel("Bills accross the time frame(Money)"); 
              
            //Defining the y axis   
            NumberAxis yAxis = new NumberAxis   (0, 900, 50); 
            yAxis.setLabel("Money"); 
              
            //Creating the line chart 
            LineChart linechart = new LineChart(xAxis, yAxis);  
            
            NumberAxis xAxisQuant = new NumberAxis(0, cnt, 1); 
            xAxisQuant.setLabel("Bills accross the time frame(Quantity)"); 
              
            //Defining the y axis   
            NumberAxis yAxisQuant = new NumberAxis(0, 25, 2); 
            yAxisQuant.setLabel("Quantity"); 
              
            //Creating the line chart 
            LineChart linechartQuant = new LineChart(xAxisQuant, yAxisQuant);  
            
            //Prepare XYChart.Series objects by setting data 
           
            series.setName("Amount of money made"); 
            series2.setName("No of books sold");
              
       /*     series.getData().add(new XYChart.Data(1970, 15)); 
            series.getData().add(new XYChart.Data(0, 30)); 
            series.getData().add(new XYChart.Data(0, 60)); 
            series.getData().add(new XYChart.Data(0, 120)); 
            series.getData().add(new XYChart.Data(0, 240)); 
            series.getData().add(new XYChart.Data(2009, 300)); 
       */           
            //Setting the data to Line chart    
            linechart.getData().addAll(series);
            linechartQuant.getData().addAll(series2);
            Group root = new Group(linechart); 
            Group nextRoot = new Group(linechartQuant);
            TabPane tabPane = new TabPane();
        	Tab tab = new Tab();
        	tab.setText("BILLS");
    		Label firstLabel = new Label(totalLines);
    		VBox vb = new VBox();
    		BorderPane bp = new BorderPane();
    		vb.setPadding(new Insets(20));
    		vb.setSpacing(20);
    		vb.getChildren().addAll(root, nextRoot);
    		bp.setCenter(vb);
    		tab.setContent(vb);
    		tabPane.getTabs().add(tab);
        	tab.setClosable(false);
    		Scene checkScene = new Scene(tabPane, 700, 900);
    		showBills.setScene(checkScene);
    		showBills.show();
            }
            catch(Exception exc) {
            	exc.printStackTrace();
            }
         //   ArrayList<Author> searchResults = Author.getSearchResults(searchText);
         //   authorView.getTableView().setItems(FXCollections.observableArrayList(searchResults));
        });
    }

    
    private void setEditListener() {
    	Alert alert = new Alert(Alert.AlertType.WARNING);
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
        bookView.getTitleCol().setOnEditCommit(event -> {
            Book bookToEdit = event.getRowValue();
            System.out.println(event.getRowValue());
            int index = Book.getBooks().indexOf(bookToEdit);
            if(event.getNewValue().matches("^\\w++(?:[.,_:()\\s-](?![.\\s-])|\\w++)*$")) {
            	Book.getBooks().get(index).setTitle(event.getNewValue());	
            }
            else {
            	bookView.getTableView().getItems().set(bookView.getTableView().getItems().indexOf(bookToEdit), bookToEdit);
            	alert.setTitle("Error");
            	alert.setHeaderText("Problem with input.");
            	alert.setContentText("Improper Title Value entered. Value was not edited");
            	alert.show();
            }
            
        });
        bookView.getIsbnCol().setOnEditCommit(event -> {
            Book bookToEdit = event.getRowValue();
            System.out.println(event.getRowValue());
            int index = Book.getBooks().indexOf(bookToEdit);
            String a = event.getNewValue();
            if(event.getNewValue().matches("^(?:ISBN(?:-13)?:?\\ )?(?=[0-9]{13}$|(?=(?:[0-9]+[-\\ ]){4})[-\\ 0-9]{17}$)97[89][-\\ ]?[0-9]{1,5}[-\\ ]?[0-9]+[-\\ ]?[0-9]+[-\\ ]?[0-9]$")) {
            	if(!bookToEdit.bExists(a))
            		Book.getBooks().get(index).setIsbn(event.getNewValue());
            	else {
            		bookView.getTableView().getItems().set(bookView.getTableView().getItems().indexOf(bookToEdit), bookToEdit);
                	alert.setTitle("Error");
                	alert.setHeaderText("Problem with input.");
                	alert.setContentText("Book already exists. Value was not edited");
                	alert.show();
            	}
            }
            else {
            	bookView.getTableView().getItems().set(bookView.getTableView().getItems().indexOf(bookToEdit), bookToEdit);
            	alert.setTitle("Error");
            	alert.setHeaderText("Problem with input.");
            	alert.setContentText("Improper ISBN Value entered. Value was not edited");
            	alert.show();
            }
        });
        bookView.getPurchasedPriceCol().setOnEditCommit(event -> {
            Book bookToEdit = event.getRowValue();
            System.out.println(event.getRowValue());
            int index = Book.getBooks().indexOf(bookToEdit);
            if(event.getNewValue() >= 0) {
            	Book.getBooks().get(index).setPurchasedPrice(event.getNewValue());
            }
            else {
            	bookView.getTableView().getItems().set(bookView.getTableView().getItems().indexOf(bookToEdit), bookToEdit);
            	alert.setTitle("Error");
            	alert.setHeaderText("Problem with input.");
            	alert.setContentText("Value cannot be smaller than zero. Value was not edited");
            	alert.show();
            }
            
        });
        bookView.getSellingPriceCol().setOnEditCommit(event -> {
            Book bookToEdit = event.getRowValue();
            System.out.println(event.getRowValue());
            int index = Book.getBooks().indexOf(bookToEdit);
            if(event.getNewValue() >= 0) {
            	Book.getBooks().get(index).setSellingPrice(event.getNewValue());
            }
            else {
            	bookView.getTableView().getItems().set(bookView.getTableView().getItems().indexOf(bookToEdit), bookToEdit);
            	alert.setTitle("Error");
            	alert.setHeaderText("Problem with input.");
            	alert.setContentText("Value cannot be smaller than zero. Value was not edited");
            	alert.show();
            }
        });
        bookView.getQuantityCol().setOnEditCommit(event -> {
            Book bookToEdit = event.getRowValue();
            System.out.println(event.getRowValue());
            int index = Book.getBooks().indexOf(bookToEdit);
            if(event.getNewValue() >= 0) {
                Book.getBooks().get(index).setQuantity(event.getNewValue());
            }
            else {
            	bookView.getTableView().getItems().set(bookView.getTableView().getItems().indexOf(bookToEdit), bookToEdit);
            	alert.setTitle("Error");
            	alert.setHeaderText("Problem with input.");
            	alert.setContentText("Value cannot be smaller than zero. Value was not edited");
            	alert.show();
            }
        });
        bookView.getAuthorCol().setOnEditCommit(event -> {
        	//authorsComboBox.setValue(Author.getAuthors().get(0));
        	Book bookToEdit = event.getRowValue();
        	Author oldVal = bookToEdit.getAuthor();
        	String newVal = Author.getOneAuthor(event.getNewValue());
        	int index = Book.getBooks().indexOf(bookToEdit);
        	int idx = newVal.lastIndexOf(' ');
        	if (idx == -1)
        	    throw new IllegalArgumentException("Only a single name: " + newVal);
        	String firstName = newVal.substring(0, idx);
        	String lastName  = newVal.substring(idx + 1);
        	Author newAu = new Author(firstName, lastName);
        	Book.getBooks().get(index).setAuthor(newAu);
        	
        });
        bookView.getCategoryCol().setOnEditCommit(event -> {
            Book bookToEdit = event.getRowValue();
            System.out.println(event.getRowValue());
            int index = Book.getBooks().indexOf(bookToEdit);
            if(event.getNewValue().matches("[\\w\\s]+")) {
            	Book.getBooks().get(index).setCategory(event.getNewValue());	
            }
            else {
            	bookView.getTableView().getItems().set(bookView.getTableView().getItems().indexOf(bookToEdit), bookToEdit);
            	alert.setTitle("Error");
            	alert.setHeaderText("Problem with input.");
            	alert.setContentText("Improper Category Value entered. Value was not edited");
            	alert.show();
            }
        });
        // if the user clicks edit button, save the changes into the file
        bookView.getEditBtn().setOnAction(e -> {
            try {
                // todo change it
                FileHandler.overwriteCurrentListToFile(Book.DATA_FILE, Book.getBooks());
                bookView.getResultLabel().setText("Books were updated successfully");
            } catch (IOException ex) {
                bookView.getResultLabel().setText("Writing books to the file failed!");
                ex.printStackTrace();
            }
        });
    }
    private void resetFields() {
        bookView.getIsbnField().setText("");
        bookView.getTitleField().setText("");
        bookView.getPurchasedPriceField().setText("");
        bookView.getSellingPriceField().setText("");
        bookView.getQuantityField().setText("");
    }
}