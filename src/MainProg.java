import Controllers.LibController;

import Controllers.LoginController;
import Models.Author;
import Models.Role;
import Models.User;
import Views.LibView;
import Views.LoginView;
import Views.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;

public class MainProg extends Application {
    public static void main(String[] args) {
        //seedData();
        launch(args);
    }

    private static void seedData() {
      //  User admin = new User("admin", "Test2022", Role.ADMIN);
     //   User manager = new User("manager", "Test2022", Role.MANAGER);
   //     User librarian = new User("librarian", "Test2022", Role.LIBRARIAN);
    	User admin = new User("admin", "Test2022", "12/04/2002", "067 620 2133", "rubinaga02@gmail.com", "620", Role.ADMIN);
    	User manager = new User("manager", "Test2022", "12/04/2002", "067 620 2133", "rubinaga02@gmail.com", "620", Role.MANAGER);
    	User librarian = new User("admin1", "Test2022", "12/04/2002", "067 620 2133", "rubinaga02@gmail.com", "620", Role.LIBRARIAN);
        User newAdmin = new User("admin1", "Test2022", "12/04/2002", "067 620 2133", "rubinaga02@gmail.com", "620", Role.ADMIN);
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("users.ser"));
            outputStream.writeObject(admin);
            outputStream.writeObject(manager);
            outputStream.writeObject(librarian);
       //     outputStream.writeObject(newAdmin);
            System.out.println("Wrote users to the file users.dat successfully");
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("authors.ser"))) {
            outputStream.writeObject(new Author("Test1", "Test1"));
            outputStream.writeObject(new Author("Test2", "Test2"));
            outputStream.writeObject(new Author("Test3", "Test3"));
            System.out.println("Wrote authors to the file users.dat successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void start(Stage stage) {
        LoginView loginView = new LoginView();
        LoginController controller = new LoginController(loginView, new MainView(), stage);
        Scene scene = new Scene(loginView.getView(), 320, 240);
        stage.setTitle("Bookstore");
        stage.setScene(scene);
        stage.show();
     /*   if(cont.checkButton) {
        	Scene scene2 = new Scene(loginView.getView(), 320, 240);
        	stage.setScene(scene2);
        	stage.show();
        }*/
    }
}