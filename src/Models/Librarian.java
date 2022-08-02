package Models;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Librarian extends User implements Serializable {
	@Serial
    private static final long serialVersionUID = 1234567L;
	User librarian = new User();
	private static final ArrayList<Librarian> librarians = new ArrayList<>();
	private double revenue = 0;
    public User getLibrarian() {
		return librarian;
	}
	public void setLibrarian(User librarian) {
		this.librarian = librarian;
	}
	public double getRevenue() {
		return revenue;
	}
	public void setRevenue(double revenue) {
		this.revenue = revenue;
	}

	public static ArrayList<Librarian> getLibrarians () {
        if (librarians.size() == 0){
            try {
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FILE_PATH));
                while (true) {
                    Librarian temp = (Librarian) inputStream.readObject();
                    if (temp == null)
                        break;
                    else if(temp.getRole()==Role.LIBRARIAN)
                    	librarians.add(temp);
                    else {
                    	continue;
                    }
                }
                inputStream.close();
            } catch (EOFException eofException) {
                System.out.println("End of file reached!");
            }
            catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return librarians;
    }
    
    public Librarian(User librarian, double revenue) {
    	setLibrarian(librarian);
    	setRevenue(revenue);
    }
    
    
    
    
    
    
}
