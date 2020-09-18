package mainPackage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.*;

//TODO
//convention for buttons
//---->   1:add a vertex
//---->   2:add an edge


public class Main extends Application {
    private static Visuals visuals;
    private static ConfigureScreen cs;

    private static Button addVertexBtn;
    private static Button addEdgeBtn;
    private static int btnSelected=0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Graphs Visualised");

        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double width = screenBounds.getWidth();
        double height = screenBounds.getHeight() ;

        Group group=new Group();
        Canvas canvas=new Canvas(width*0.6,height*0.7);
        canvas.setLayoutX(0);
        canvas.setLayoutY(200);
        group.getChildren().add(canvas);

        Canvas displayCanvas=new Canvas(600,400);
        displayCanvas.setLayoutX(820);
        displayCanvas.setLayoutY(80);
        group.getChildren().add(displayCanvas);


        cs=new ConfigureScreen(group);
        cs.addButtons();
        visuals=new Visuals(canvas,displayCanvas);

        Scene scene = new Scene(group, screenBounds.getWidth()-5, screenBounds.getHeight()-5);
        scene.setFill(Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.show();
    }





    public static void main(String[] args) {

        launch(args);

    }
}
