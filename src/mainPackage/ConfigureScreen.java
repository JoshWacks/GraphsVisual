package mainPackage;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.awt.*;

public class ConfigureScreen {

    private static Button addVertexBtn;
    private static Button addEdgeBtn;
    private static Group group;
    private static Button viewListBtn;
    private static Button viewMatrixBtn;
    private static Button checkCompleteBtn;
    private static Button checkConnectedBtn;

    private static int btnSelected=0;

    public ConfigureScreen(Group gr){
        group=gr;
    }

    public void addButtons(){
        makeVertexBtn();
        makeEdgeBtn();
        makeViewListBtn();
        makeViewMatrixBtn();
        makeCompleteBtn();
        makeConnectedBtn();

        group.getChildren().add(addVertexBtn);
        group.getChildren().add(addEdgeBtn);
        group.getChildren().add(viewListBtn);
        group.getChildren().add(viewMatrixBtn);
        group.getChildren().add(checkCompleteBtn);
        group.getChildren().add(checkConnectedBtn);
    }
    public static int getBtnSelected(){
        return btnSelected;
    }

    public static Group getGroup(){
        return group;
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

    private void makeViewListBtn(){
        viewListBtn=new Button("View Adjacency List");
        viewListBtn.setTextFill(Color.BLACK);
        BackgroundFill backgroundFill=new BackgroundFill(Color.DARKORCHID,new CornerRadii(3),null);
        Background background=new Background(backgroundFill);
        viewListBtn.setBackground(background);

        viewListBtn.setFont(javafx.scene.text.Font.font(Font.SERIF, 24));
        Tooltip t = new Tooltip("Click here to view the current graph as an adjacency list");
        Tooltip.install(null, t);
        viewListBtn.setTooltip(t);

        viewListBtn.setLayoutX(1080);
        viewListBtn.setLayoutY(10);
        viewListBtn.setPrefWidth(250);
        viewListBtn.setPrefHeight(30);

        viewListBtn.setOnMouseClicked(event -> {
            Visuals.showList();
        });

    }

    private void makeViewMatrixBtn(){
        viewMatrixBtn=new Button("View Matrix");
        viewMatrixBtn.setTextFill(Color.BLACK);
        BackgroundFill backgroundFill=new BackgroundFill(Color.FIREBRICK,new CornerRadii(3),null);
        Background background=new Background(backgroundFill);
        viewMatrixBtn.setBackground(background);

        viewMatrixBtn.setFont(javafx.scene.text.Font.font(Font.SERIF, 24));
        Tooltip t = new Tooltip("Click here to view the current graph as matrix");
        Tooltip.install(null, t);
        viewMatrixBtn.setTooltip(t);

        viewMatrixBtn.setLayoutX(850);
        viewMatrixBtn.setLayoutY(10);
        viewMatrixBtn.setPrefWidth(200);
        viewMatrixBtn.setPrefHeight(30);

        viewMatrixBtn.setOnMouseClicked(event -> Visuals.showMatrix());

    }

    private void makeCompleteBtn(){
        checkCompleteBtn=new Button("Complete?");
        checkCompleteBtn.setTextFill(Color.BLACK);
        BackgroundFill backgroundFill=new BackgroundFill(Color.CORAL,new CornerRadii(3),null);
        Background background=new Background(backgroundFill);
        checkCompleteBtn.setBackground(background);

        checkCompleteBtn.setFont(javafx.scene.text.Font.font(Font.SERIF, 20));
        Tooltip t = new Tooltip("Click here to check if the graph is a complete graph");
        Tooltip.install(null, t);
        checkCompleteBtn.setTooltip(t);

        checkCompleteBtn.setLayoutX(825);
        checkCompleteBtn.setLayoutY(500);
        checkCompleteBtn.setPrefWidth(130);
        checkCompleteBtn.setPrefHeight(5);

        checkCompleteBtn.setOnMouseClicked(event -> Visuals.checkCompleted());
    }

    private void makeConnectedBtn(){
        checkConnectedBtn=new Button("Connected?");
        checkConnectedBtn.setTextFill(Color.BLACK);
        BackgroundFill backgroundFill=new BackgroundFill(Color.DARKKHAKI,new CornerRadii(3),null);
        Background background=new Background(backgroundFill);
        checkConnectedBtn.setBackground(background);

        checkConnectedBtn.setFont(javafx.scene.text.Font.font(Font.SERIF, 20));
        Tooltip t = new Tooltip("Click here to check if the graph is a connected graph");
        Tooltip.install(null, t);
        checkConnectedBtn.setTooltip(t);

        checkConnectedBtn.setLayoutX(825);
        checkConnectedBtn.setLayoutY(550);
        checkConnectedBtn.setPrefWidth(130);
        checkConnectedBtn.setPrefHeight(5);

        checkConnectedBtn.setOnMouseClicked(event -> Visuals.checkConnected());

    }

    private void toggleBtnOn(Button button){

        BackgroundFill backgroundFill=new BackgroundFill(Color.LIME,new CornerRadii(3),null);
        Background background=new Background(backgroundFill);
        button.setBackground(background);

    }

    private void toggleBtnOff(Button button,Color original){

        BackgroundFill backgroundFill=new BackgroundFill(original,new CornerRadii(4),null);
        Background background=new Background(backgroundFill);
        button.setBackground(background);

    }
}
