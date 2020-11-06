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

//TODO add a button to delete arc edges
//when re-drawing arc edges it re-draws incorrectly

//convention for buttons
//---->   1:add a vertex
//---->   2:add an edge
//---->   3:add a weighted edge
//---->   4:Delete a vertex
//---->   5:Delete an edge
//---->   6:Indicate root




public class Main extends Application {
    private static Visuals visuals;
    private static ConfigureScreen cs;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Graphs Visualised");
        primaryStage.setResizable(false);

        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double width = screenBounds.getWidth();
        double height = screenBounds.getHeight() ;

        Group group=new Group();//The main group which holds all the components
        Canvas canvas=new Canvas(width*0.6,height*0.7);//the drawable canvas
        canvas.setLayoutX(0);
        canvas.setLayoutY(200);
        group.getChildren().add(canvas);

        Canvas displayCanvas=new Canvas(600,400);//The canvas which is meant for display purposes only
        displayCanvas.setLayoutX(820);
        displayCanvas.setLayoutY(80);
        group.getChildren().add(displayCanvas);


        cs=new ConfigureScreen(group);
        cs.addButtons();//Adds all the buttons to the screen

        Scene scene = new Scene(group, screenBounds.getWidth()-5, screenBounds.getHeight()-10);
        scene.setFill(Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.show();

        visuals=new Visuals(canvas,displayCanvas);
    }





    public static void main(String[] args) {

        launch(args);

    }
}
