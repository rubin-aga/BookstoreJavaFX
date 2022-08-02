package Views;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import UI.DeleteButton;
import java.util.ArrayList;
import java.util.Calendar;
import Auxiliaries.FileHandler;
import Controllers.BookController;
import Models.Author;
import Models.Book;
import Models.BookToBill;
import Models.User;
import UI.BillButton;
import UI.CreateButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class LibView {
	private final BorderPane borderPane = new BorderPane();
	private final BorderPane billBorderPane = new BorderPane();
	private final BorderPane showBooksBPane = new BorderPane();
    private final TableView<Book> tableView = new TableView<>();
    private final static TableView<BookToBill> BillBookstableView = new TableView<>();
    
    public static TableView<BookToBill> getBillBookstableView() {
		return BillBookstableView;
	}
	private final HBox formPane = new HBox();
    private final HBox showBookFormPane = new HBox();
    private final TextField isbnField = new TextField();
    private final TextField delisbnField = new TextField();
    private final TextField titleField = new TextField();
    private final TextField purchasedPriceField = new TextField();
    private final TextField sellingPriceField = new TextField();
    private final TextField quantityField = new TextField();
    private final TextField delquantityField = new TextField();
    private final Button BillButton = new BillButton();
    private final TableColumn<Book, String> isbnCol = new TableColumn<>("ISBN");
    private final TableColumn<BookToBill, String> delBookisbnCol = new TableColumn<>("ISBN");
    private final TableColumn<BookToBill, Double> delBookSPCol = new TableColumn<>("Selling Price"); 
    private final TableColumn<BookToBill, Double> delBookTotalCol = new TableColumn<>("Total Price"); 
    private final TableColumn<Book, String> titleCol = new TableColumn<>("Title");
    private final TableColumn<Book, Float> purchasedPriceCol = new TableColumn<>("Purchased Price");
    private final TableColumn<Book, Float> sellingPriceCol = new TableColumn<>("Selling Price");
    private final TableColumn<Book, String> authorCol = new TableColumn<>("Author");
    private final TableColumn<Book, Integer> quantityCol = new TableColumn<>("Quantity");
    private final TableColumn<BookToBill, Integer> delBookquantityCol = new TableColumn<>("Quantity");
    private final static Label resultLabel = new Label("");
    private final SearchView searchView = new SearchView("Search for a book");
    private final TextField ISBN = new TextField();
    private final TextField quantity = new TextField();
    private final Button billBtn = new Button("Bill");
    private final Label errorLabel = new Label("");
    private final TableColumn<Book, String> categoryCol = new TableColumn<>("Category");
    private final TextField categoryField = new TextField();
    
    
	public TableColumn<Book, String> getCategoryCol() {
		return categoryCol;
	}
	public TextField getCategoryField() {
		return categoryField;
	}
	public TextField getDelisbnField() {
		return delisbnField;
	}
	public TextField getDelquantityField() {
		return delquantityField;
	}
	public TableColumn<BookToBill, Double> getDelBookTotalCol() {
		return delBookTotalCol;
	}
	public TableColumn<BookToBill, Double> getDelBookSPCol() {
		return delBookSPCol;
	}
	public TableColumn<BookToBill, String> getDelBookisbnCol() {
		return delBookisbnCol;
	}
	public TableColumn<BookToBill, Integer> getDelBookquantityCol() {
		return delBookquantityCol;
	}
	public TextField getQuantityField() {
        return quantity;
    }
    public Label getErrorLabel() {
        return errorLabel;
    }
    public TableView<Book> getTableView() {
        return tableView;
    }

    public TextField getIsbnField() {
        return isbnField;
    }

    public TextField getTitleField() {
        return titleField;
    }

    public TextField getPurchasedPriceField() {
        return purchasedPriceField;
    }

    public TextField getSellingPriceField() {
        return sellingPriceField;
    }

    public TableColumn<Book, String> getIsbnCol() {
        return isbnCol;
    }

    public TableColumn<Book, String> getTitleCol() {
        return titleCol;
    }

    public TableColumn<Book, Float> getPurchasedPriceCol() {
        return purchasedPriceCol;
    }

    public TableColumn<Book, Float> getSellingPriceCol() {
        return sellingPriceCol;
    }

    public static Label getResultLabel() {
        return resultLabel;
    }

    public SearchView getSearchView() {
        return searchView;
    }
    public Button getBillBtn() {
        return BillButton;
    }
    public TableColumn<Book, Integer> getQuantityCol(){
    	return quantityCol;
    }
    public LibView() {
        setTableView();
        setForm();
    }
    /*
    private void addListener(LibView view) {
        view.getBillBtn().setOnAction(e -> {
            String password = view.getPasswordField().getText();
            String username = view.getUsernameField().getText();
            User potentialUser = new User(username, password);
            if ((currentUser = User.getIfExists(potentialUser)) != null) {
                nextView.setCurrentUser(currentUser);
                primaryStage.setScene(new Scene(nextView.getView()));
            }
            else
                view.getErrorLabel().setText("Wrong username or password");
        });
    }
  */ 
    public static void parseDir(File dirPath)
    {
        File files[] = null;
        if(dirPath.isDirectory())
        {
            files = dirPath.listFiles();
            for(File dirFiles:files)
            {
                if(dirFiles.isDirectory())
                {
                    parseDir(dirFiles);
                }
                else
                {
                    if(dirFiles.getName().contains(".txt"))
                    {
                        
                    }
                }
            }
        }
    }
    private void setForm() {
        formPane.setPadding(new Insets(20));
        formPane.setSpacing(20);
        formPane.setAlignment(Pos.CENTER);
        Label isbnLabel = new Label("ISBN: ", isbnField);
        isbnLabel.setContentDisplay(ContentDisplay.TOP);
        Label titleLabel = new Label("Title: ", titleField);
        titleLabel.setContentDisplay(ContentDisplay.TOP);
        Label purchasedPriceLabel = new Label("Purchased price", purchasedPriceField);
        purchasedPriceLabel.setContentDisplay(ContentDisplay.TOP);
        Label sellingPriceLabel = new Label("Selling price", sellingPriceField);
        sellingPriceLabel.setContentDisplay(ContentDisplay.TOP);
        Label quantityLabel = new Label("Quantity", quantityField);
        quantityLabel.setContentDisplay(ContentDisplay.TOP);
        formPane.getChildren().addAll(BillButton);
        ArrayList<BookToBill> billBookList = new ArrayList<>();
        
        BillBookstableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        BillBookstableView.setEditable(false);
        delBookquantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        delBookquantityCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        delBookisbnCol.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        delBookisbnCol.setCellFactory(TextFieldTableCell.forTableColumn());
        delBookSPCol.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));
        delBookSPCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        delBookTotalCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        delBookTotalCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        BillBookstableView.getColumns().addAll(delBookisbnCol, delBookquantityCol, delBookSPCol, delBookTotalCol);
        Stage showBooksStage = new Stage();
        Button deleteBillBook = new Button("Delete Book from Bill");
    	Button close = new Button ("Close");
        showBookFormPane.getChildren().addAll(deleteBillBook, close);
        showBookFormPane.setPadding(new Insets(20));
        showBookFormPane.setSpacing(20);
        showBookFormPane.setAlignment(Pos.CENTER);
        showBooksBPane.setCenter(BillBookstableView);
        VBox showvBox = new VBox();
        showvBox.setAlignment(Pos.CENTER);
        showvBox.setSpacing(5);
        showvBox.getChildren().addAll(showBookFormPane, resultLabel);
        showBooksBPane.setBottom(showvBox);
        Scene sceneDel = new Scene(showBooksBPane, 450, 450);
        showBooksStage.setScene(sceneDel);
        BillButton.setOnAction(e ->{
        	Stage stage = new Stage();
        	TabPane tabPane = new TabPane();
        	Tab tab = new Tab();
        	tab.setText("Bill");
        	Button saveBook = new Button("Save to Bill");
        	Button showBooks = new Button("Show books in the bill");
        	Button finalBill = new Button("Finalize Bill");
        	
        	saveBook.setOnAction(ev -> {
                String ISBN = isbnField.getText();
                String quantityString = quantityField.getText();
                int quantity = Integer.parseInt(quantityString);
                Author author = new Author("", "");
                Book potentialBook = new Book(ISBN, "", 0, 0, author, 0, "");
                Book currentBook = null;
                if ((currentBook = Book.getIfExists(potentialBook)) != null) {
                	String newBillISBN = currentBook.getIsbn();
                	int newBillQuant = quantity;
                	if(currentBook.getQuantity()-quantity<0) {
                		getErrorLabel().setText("You are requesting more than the available stock");
                		quantityField.setText("");
                	}
                	else {
                	currentBook.setQuantity(currentBook.getQuantity()-quantity);
                	double newBillSellP = currentBook.getSellingPrice();
                	BookToBill newBookShown = new BookToBill(newBillISBN, newBillQuant, newBillSellP);
                    billBookList.add(newBookShown);
                    
                    //System.out.println(currentBook.getQuantity());
                    System.out.println(newBillISBN + " " +  quantity + " " + newBillSellP);
                	}
                }
                else
                	getErrorLabel().setText("No book with that ISBN found");
                isbnField.setText("");
                quantityField.setText("");
            });
        	
        	
            deleteBillBook.setOnAction(event -> {
                ObservableList<Integer> indices = LibView.getBillBookstableView().getSelectionModel().getSelectedIndices();
                for (int index: indices) {
//                	LibView.getBillBookstableView().getSelectionModel().getSelectedIndex()
                	Author author = new Author("", "");
                	Book potBook = new Book(LibView.getBillBookstableView().getItems().get(index).getISBN(), "" , 0, 0, author, 0, "");
                	Book currentBook = null;
                	int tempquant = LibView.getBillBookstableView().getItems().get(index).getQuantity();
                	if ((currentBook = Book.getIfExists(potBook)) != null) {
                		currentBook.setQuantity(currentBook.getQuantity()+tempquant);
                	}
//					authorView.getTableView().getItems().set(authorView.getTableView().getItems().indexOf(authorToEdit), authorToEdit);      
                	//billBookList.get(index).setQuantity(billBookList.get(index).getQuantity() + tempquant);
                	billBookList.remove(index);
                    //currentBook.setQuantity(currentBook.getQuantity()-quantity);
                    	
                }
                LibView.getBillBookstableView().setItems(FXCollections.observableArrayList(billBookList));
                LibView.getResultLabel().setTextFill(Color.DARKGREEN);
                LibView.getResultLabel().setText("Book deleted successfully!");
                
            });
            showBooks.setOnAction(ev -> {
        //	}
               BillBookstableView.setItems(FXCollections.observableArrayList(billBookList));
               
               
               showBooksStage.show();
               close.setOnAction(ee -> {
            	  showBooksStage.close();
               });
               
            });
        	finalBill.setOnAction(ev -> {
        		if(billBookList.size()==0) {
        			stage.close();
        		}
        		else {
                try {
                    FileHandler.overwriteCurrentListToFile(Book.DATA_FILE, Book.getBooks());
                    System.out.println("Updated Successfully");
                } catch (IOException ex) {
                    System.out.println("Failed");
                    ex.printStackTrace();
                }
                //File file = new File(Book.BILL_PATH);
                try {
                //if (!file.exists()) {
				//		file.createNewFile();		
				//	}
                
                //Date date = Calendar.getInstance().getTime();  
                //DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd--hh-mm-ss");  
                
                
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy--HH-mm-ss");  
                LocalDateTime now = LocalDateTime.now();
                String a = dtf.format(now);
                String strBill = dtf.format(now) + ".txt";
                final String BILL_PATH = "Bills/" + strBill;
                FileWriter fw = new FileWriter(BILL_PATH);
                BufferedWriter bw = new BufferedWriter(fw);
               // File stats = new File("stats.txt");
                int cnt = 2;
                double revenueBookTot=0.0;
                int totalQuant = 0;
                for(BookToBill b: billBookList) {
                	double revenueBook = 0.0;
                	revenueBook = b.getQuantity() * b.getSellPrice();
                	revenueBookTot+=revenueBook;
                	String strRev = String.valueOf(revenueBook);
                	totalQuant += b.getQuantity();
                	bw.write("Book ISBN: "+ b.getISBN() + "\nISBN Price Total: " + strRev);
                	bw.write("\n");
                	
                	cnt+=2;
                }
                System.out.println(cnt);
                String strRevTot = String.valueOf(revenueBookTot);
                bw.write("\nComplete Quantity of Books: \n" + totalQuant);
                bw.write("\nComplete Bill Price: \n" + strRevTot);
                
                bw.close();
                Path path = Paths.get("stats.txt");
                Path path2 = Paths.get(BILL_PATH);
                String currStat = Files.readAllLines(path).get(0);
                
                double currStatDouble = Double.parseDouble(currStat);
                currStatDouble += revenueBookTot;
                currStat = String.valueOf(currStatDouble);
                byte[] strToBytes = currStat.getBytes();
                Files.write(path, strToBytes);
                String test = Files.readAllLines(path2).get(cnt);
                System.out.println(test);
                System.out.println(currStat);
                }
                catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                try {
                    FileHandler.overwriteCurrentListToFile(Book.DATA_FILE, Book.getBooks());
                    System.out.println("Updated Successfully");
                } catch (IOException ex) {
                    System.out.println("Failed");
                    ex.printStackTrace();
                }
                isbnField.setText("");
                quantityField.setText("");
                billBookList.clear();
                stage.close();
        		}});
        	
        	VBox vBox = new VBox();
        	vBox.getChildren().addAll(isbnLabel, quantityLabel, saveBook, finalBill, showBooks,errorLabel);
        	vBox.setAlignment(Pos.CENTER);
        	vBox.setPadding(new Insets(20));
        	vBox.setSpacing(20);
        	billBorderPane.setCenter(vBox);
        	tab.setContent(vBox);
        	tabPane.getTabs().add(tab);
        	tab.setClosable(false);
			Scene scene = new Scene(tabPane, 250, 450);
			stage.setScene(scene);
			stage.show();
			}
			);
    }	
    private void setTableView() {
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.setEditable(false);
        tableView.setItems(FXCollections.observableArrayList(Book.getBooks()));

        isbnCol.setCellValueFactory(
                new PropertyValueFactory<>("isbn")
        );
        // to edit the value inside the table view
        isbnCol.setCellFactory(TextFieldTableCell.forTableColumn());

        titleCol.setCellValueFactory(
                new PropertyValueFactory<>("title")
        );
        titleCol.setCellFactory(TextFieldTableCell.forTableColumn());

        purchasedPriceCol.setCellValueFactory(
                new PropertyValueFactory<>("purchasedPrice")
        );
        purchasedPriceCol.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));

        sellingPriceCol.setCellValueFactory(
                new PropertyValueFactory<>("sellingPrice")
        );
        sellingPriceCol.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));

        quantityCol.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        quantityCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        
        authorCol.setCellValueFactory(
                new PropertyValueFactory<>("author")
        );
        
        ArrayList<Author> test1 = Author.getAuthors();
        int a = test1.size();
        String[] arr = new String[a];
        for(int i = 0; i<a;i++) {
        	arr[i] = test1.get(i).getFirstName() + " " + test1.get(i).getLastName();
        }
        authorCol.setCellFactory(ComboBoxTableCell.forTableColumn(arr));
        categoryCol.setCellValueFactory(
                new PropertyValueFactory<>("category")
        );
        categoryCol.setCellFactory(TextFieldTableCell.forTableColumn());
        tableView.getColumns().addAll(isbnCol, titleCol, purchasedPriceCol, sellingPriceCol, quantityCol, authorCol, categoryCol);
      }
    public Parent getView() {
        borderPane.setCenter(tableView);
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(5);
        vBox.getChildren().addAll(formPane, resultLabel);
        borderPane.setBottom(vBox);
        borderPane.setTop(searchView.getSearchPane());
        return borderPane;
    }
    
}
