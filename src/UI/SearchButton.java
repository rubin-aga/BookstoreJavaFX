package UI;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class SearchButton extends Button {
    public SearchButton() {
        super.setText("");
        super.setGraphic(getImage());
    }

    private ImageView getImage() {
        ImageView imageView = new ImageView("https://www.transparentpng.com/thumb/search-button/RwuGa6-button-search-png.png");
        imageView.setFitHeight(10.5);
        imageView.setFitWidth(10.5);
        return imageView;
    }
}