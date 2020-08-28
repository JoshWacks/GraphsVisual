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
    private static Button addVertexBtn;
    private static Button addEdgeBtn;
    private static int btnSelected=0;

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
        addButtons(group);

        Scene scene = new Scene(group, screenBounds.getWidth()-5, screenBounds.getHeight()-5);
        scene.setFill(Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static int getBtnSelected(){
        return btnSelected;
    }

    private void addButtons(Group group){
        makeVertexBtn();
        makeEdgeBtn();
        group.getChildren().add(addVertexBtn);
        group.getChildren().add(addEdgeBtn);
    }

    private void makeVertexBtn(){
        addVertexBtn=new Button("Vertex");
        addVertexBtn.setTextFill(Color.BLACK);
        BackgroundFill backgroundFill=new BackgroundFill(Color.LIGHTBLUE,new CornerRadii(3),null);
        Background background=new Background(backgroundFill);
        addVertexBtn.setBackground(background);

        addVertexBtn.setFont(javafx.scene.text.Font.font(Font.SERIF, 24));
        Tooltip t = new Tooltip("To add a vertex to the board click a valid position on the board");
        Tooltip.install(null, t);
        addVertexBtn.setTooltip(t);

        addVertexBtn.setLayoutX(5);
        addVertexBtn.setLayoutY(150);
        addVertexBtn.setPrefWidth(100);
        addVertexBtn.setPrefHeight(40);

        addVertexBtn.setOnMouseClicked(event -> {
            if(btnSelected==1){
                btnSelected=0;
                toggleBtnOff(addVertexBtn,Color.LIGHTBLUE);
            }
            else{
                toggleBtnOn(addVertexBtn);
                toggleBtnOff(addEdgeBtn,Color.ORANGE);
                btnSelected=1;
            }
        });
    }

    private void makeEdgeBtn(){
        addEdgeBtn=new Button("Edge");
        addEdgeBtn.setTextFill(Color.BLACK);
        BackgroundFill backgroundFill=new BackgroundFill(Color.ORANGE,new CornerRadii(3),null);
        Background background=new Background(backgroundFill);
        addEdgeBtn.setBackground(background);

        addEdgeBtn.setFont(javafx.scene.text.Font.font(Font.SERIF, 24));
        Tooltip t = new Tooltip("To add an edge click on two vertices");
        Tooltip.install(null, t);
        addEdgeBtn.setTooltip(t);

        addEdgeBtn.setLayoutX(130);
        addEdgeBtn.setLayoutY(150);
        addEdgeBtn.setPrefWidth(100);
        addEdgeBtn.setPrefHeight(40);

        addEdgeBtn.setOnMouseClicked(event -> {
            if(btnSelected==2){
                btnSelected=0;
                toggleBtnOff(addEdgeBtn,Color.ORANGE);
            }
            else{
                toggleBtnOn(addEdgeBtn);
                toggleBtnOff(addVertexBtn,Color.LIGHTBLUE);
                btnSelected=2;
            }
        });
    }

    private void toggleBtnOn(Button button){

        BackgroundFill backgroundFill=new BackgroundFill(Color.LIME,new CornerRadii(3),null);
        Background background=new Background(backgroundFill);
        button.setBackground(background);

    }

    private void toggleBtnOff(Button button,Color original){

        BackgroundFill backgroundFill=new BackgroundFill(original,new CornerRadii(3),null);
        Background background=new Background(backgroundFill);
        button.setBackground(background);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
