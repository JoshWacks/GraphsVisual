package mainPackage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;



public class Main extends Application {
    private Visuals visuals;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Graphs Visualised");

        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double width = screenBounds.getWidth()*0.6;
        double height = screenBounds.getHeight() *0.7;

        Group group=new Group();
        Canvas canvas=new Canvas(width,height);
        canvas.setLayoutX(0);
        canvas.setLayoutY(screenBounds.getHeight()-height-26);
        group.getChildren().add(canvas);

        visuals=new Visuals(canvas);



        Scene scene = new Scene(group, screenBounds.getWidth()-5, screenBounds.getHeight()-5);
        scene.setFill(Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
