package Models;

import java.io.IOException;
import java.util.ArrayList;

import Auxiliaries.FileHandler;

public class BookToBill {
	private String ISBN;
    private int quantity;
    private double sellPrice;
    private double totalPrice;
	public BookToBill(String ISBN, int quantity, double sellPrice) {
        setISBN(ISBN);
        setQuantity(quantity);
        setSellPrice(sellPrice);
    }
	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

    public String getISBN() {
        return ISBN;
    }

    public int getQuantity() {
        return quantity;
    }
    public double getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(double sellPrice) {
		this.sellPrice = sellPrice;
	}
	public double getTotalPrice() {
		totalPrice = 1.0 * quantity * sellPrice;
		return totalPrice;
	}
	

}


