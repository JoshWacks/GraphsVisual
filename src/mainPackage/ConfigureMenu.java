package mainPackage;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Screen;

import java.io.IOException;


public class ConfigureMenu extends ConfigureScreen {
    protected static MenuBar menuBar=new MenuBar();
    protected static MenuItem menuItemSave = new MenuItem("Save");


    protected static void setMenu(){

        Menu m = new Menu("File");
        m.setId("menu");


        menuItemSave.setId("menuItem");
        m.getItems().add(menuItemSave);
        menuItemSave.setOnAction(event -> {
            Saving saving=new Saving();
            try {
                saving.performSave();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        menuBar.setId("menuBar");
        menuBar.setPrefWidth(Screen.getPrimary().getBounds().getWidth());
        menuBar.getMenus().add(m);

        group.getChildren().add(menuBar);
        menuBar.setVisible(false);


    }

    protected static void setVisible(double y){
        menuBar.setVisible(y < 90);
        menuItemSave.setVisible(y<90);

    }
}
