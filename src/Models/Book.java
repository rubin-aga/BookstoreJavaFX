package Models;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Auxiliaries.FileHandler;
import javafx.scene.control.Alert;

public class Book extends BaseModel implements Serializable{
    private String isbn;
    private String title;
    private float purchasedPrice;
    private float sellingPrice;
    private Author author;
    private int quantity;
    private String category;
    public static final String FILE_PATH = "books.ser";
    private static Date date = Calendar.getInstance().getTime();  
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd--hh-mm-ss");  
    public static String strBill = dateFormat.format(date) + ".txt";
    public static final String BILL_PATH = "Bills/" + strBill;
    public static final File DATA_FILE = new File(FILE_PATH);
    @Serial
    private static final long serialVersionUID = 1234567L;
    private static final ArrayList<Book> books = new ArrayList<>();
    
    public Book(){}

    public Book(String isbn, String title, float purchasedPrice, float sellingPrice, Author author, int quantity, String category) {
        this.isbn = isbn;
        this.title = title;
        this.purchasedPrice = purchasedPrice;
        this.sellingPrice = sellingPrice;
        this.author = author;
        this.quantity = quantity;
        this.category = category;
    }
    
    public static ArrayList<Book> getSearchResults(String searchText) {
        ArrayList<Book> searchResults = new ArrayList<>();
        for(Book book: getBooks()) {
        	Author auth = new Author("", "");
        	auth = book.getAuthor();
        	String authCheck = auth.getFullName();
        	if (book.getTitle().toLowerCase().contains(searchText.toLowerCase()) 
        	|| book.getIsbn().contains(searchText)
        	|| authCheck.toLowerCase().contains(searchText.toLowerCase())
        	|| book.getCategory().toLowerCase().contains(searchText)
        			) {
        		searchResults.add(book);
        	}
        }
        
        return searchResults;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Book) {
            Book other = (Book) obj;
            return other.getIsbn().equals(getIsbn());
        }
        return false;
    }
    
    public static Book getIfExists(Book potentialBook) {
        for(Book book: getBooks())
            if (book.equals(potentialBook))
                return book;
        return null;
    }

    
    public float getPurchasedPrice() {
        return purchasedPrice;
    }

    public void setPurchasedPrice(float purchasedPrice) {
        this.purchasedPrice = purchasedPrice;
    }

    public float getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    
    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
    public void setQuantity(int quantity) {
    	this.quantity = quantity;
    }
    
    public int getQuantity() {
    	return quantity;
    }

    public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
    public boolean saveInFile() {
        boolean saved = super.save(DATA_FILE);
        if (saved)
            books.add(this);
        return saved;
    }

    public boolean isValid() {
    	Alert alert = new Alert(Alert.AlertType.WARNING);
       if (!isbn.matches("^(?:ISBN(?:-13)?:?\\ )?(?=[0-9]{13}$|(?=(?:[0-9]+[-\\ ]){4})[-\\ 0-9]{17}$)97[89][-\\ ]?[0-9]{1,5}[-\\ ]?[0-9]+[-\\ ]?[0-9]+[-\\ ]?[0-9]$")) {
    	   alert.setTitle("Error");
    	   alert.setHeaderText("Problem with input.");
       	   alert.setContentText("Improper ISBN Value entered. Value was not edited");
       	   alert.showAndWait();
    	   return false;
       }
        if (sellingPrice<0){
        	alert.setTitle("Error");
     	   	alert.setHeaderText("Problem with input.");
     	   	alert.setContentText("Improper Selling Price Value entered. Value was not edited");
     	   	alert.showAndWait();
     	   return false;
        }
        if (purchasedPrice<0){
        	alert.setTitle("Error");
     	   	alert.setHeaderText("Problem with input.");
     	   	alert.setContentText("Improper Purchase Price Value entered. Value was not edited");
     	   	alert.showAndWait();
     	   return false;
        }
        if (!title.matches("^\\w++(?:[.,_:()\\s-](?![.\\s-])|\\w++)*$")) {
        	alert.setTitle("Error");
     	   	alert.setHeaderText("Problem with input.");
        	alert.setContentText("Improper Title entered. Value was not edited");
        	alert.showAndWait();
        	return false;
            }
        if (!category.matches("[\\w\\s]+")) {
        	alert.setTitle("Error");
     	   	alert.setHeaderText("Problem with input.");
        	alert.setContentText("Improper Category entered. Value was not edited");
        	alert.showAndWait();
        	return false;
            }
        return true;
        }
    //String isbn, String title, float purchasedPrice, float sellingPrice, Author author, int quantity) {
    @Override
    public String toString() {
        return "ISBN{" + getIsbn() + "title=" + getTitle() + ", quantity="+ getQuantity() + ", sellingPrice=" + getSellingPrice() + ", author=" + getAuthor() +'}';
    }

    @Override
    public boolean deleteFromFile() {
    	books.remove(this);
        try {
            FileHandler.overwriteCurrentListToFile(DATA_FILE, getBooks());
 //           overwriteCurrentListToFile();
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean exists(){
        for (Book b: books) {
            if (b.getIsbn().equals(this.getIsbn()))
                return true;
        }
        return false;
    }
    
    public boolean bExists(String isbntemp){
        for (Book b: books) {
            if (b.getIsbn().equals(isbntemp))
                return true;
        }
        return false;
    }
    
    
    public static ArrayList<Book> getBooks() {
        if (books.size() == 0) {
            try {
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FILE_PATH));
                while (true) {
                    Book temp = (Book) inputStream.readObject();
                    if (temp != null)
                        books.add(temp);
                    else
                        break;
                }
                inputStream.close();
            } catch (EOFException eofException) {
                System.out.println("End of book file reached!");
            }
            catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return books;
    }
}
