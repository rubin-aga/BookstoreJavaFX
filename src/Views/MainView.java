package Views;
import Models.Role;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class MainView extends View {
	
    @Override
    public Parent getView() {
        BorderPane borderPane = new BorderPane();
        TabPane tabPane = new TabPane();
        Tab authorTab = new Tab("Authors");
        authorTab.setContent(new AuthorView().getView());
        Tab bookTab = new Tab("Books");
        bookTab.setContent(new BookView().getView());
        Tab librarianTab = new Tab("Librarian");
        librarianTab.setContent(new LibView().getView());
        Tab managerTab = new Tab("Manager");
        managerTab.setContent(new ManagerView().getView());
        Tab usersTab = new Tab("Users");
        usersTab.setContent(new EmployeeView().getView());
        Role currentRole = (getCurrentUser() != null ? getCurrentUser().getRole() : null);
        if (currentRole != null) {
            if (currentRole == Role.ADMIN)
                tabPane.getTabs().addAll(authorTab, bookTab, usersTab);
            if (currentRole == Role.MANAGER || currentRole == Role.ADMIN)
                tabPane.getTabs().addAll(managerTab, authorTab);
            tabPane.getTabs().addAll(librarianTab);
        }
        borderPane.setTop(tabPane);
        borderPane.setBottom(new StackPane(new Text(getCurrentUser().getUsername() + ", welcome to our bookstore")));
        return borderPane;
    }
}