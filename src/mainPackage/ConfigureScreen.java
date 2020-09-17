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
    private static Button viewList;;
    private static Button viewMatrix;

    private static int btnSelected=0;

    public ConfigureScreen(Group gr){
        group=gr;
    }

    public void addButtons(){
        makeVertexBtn();
        makeEdgeBtn();
        makeViewListBtn();
        makeViewMatrixBtn();

        group.getChildren().add(addVertexBtn);
        group.getChildren().add(addEdgeBtn);
        group.getChildren().add(viewList);
        group.getChildren().add(viewMatrix);
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
        viewList=new Button("View Adjacency List");
        viewList.setTextFill(Color.BLACK);
        BackgroundFill backgroundFill=new BackgroundFill(Color.DARKORCHID,new CornerRadii(3),null);
        Background background=new Background(backgroundFill);
        viewList.setBackground(background);

        viewList.setFont(javafx.scene.text.Font.font(Font.SERIF, 24));
        Tooltip t = new Tooltip("Click here to view the current graph as an adjacency list");
        Tooltip.install(null, t);
        viewList.setTooltip(t);

        viewList.setLayoutX(1100);
        viewList.setLayoutY(10);
        viewList.setPrefWidth(250);
        viewList.setPrefHeight(30);

        viewList.setOnMouseClicked(event -> {
            Visuals.showList();
        });

    }

    private void makeViewMatrixBtn(){
        viewMatrix=new Button("View Matrix");
        viewMatrix.setTextFill(Color.BLACK);
        BackgroundFill backgroundFill=new BackgroundFill(Color.FIREBRICK,new CornerRadii(3),null);
        Background background=new Background(backgroundFill);
        viewMatrix.setBackground(background);

        viewMatrix.setFont(javafx.scene.text.Font.font(Font.SERIF, 24));
        Tooltip t = new Tooltip("Click here to view the current graph as matrix");
        Tooltip.install(null, t);
        viewMatrix.setTooltip(t);

        viewMatrix.setLayoutX(850);
        viewMatrix.setLayoutY(10);
        viewMatrix.setPrefWidth(200);
        viewMatrix.setPrefHeight(30);

        viewMatrix.setOnMouseClicked(event -> {
            Visuals.showMatrix();
        });

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
