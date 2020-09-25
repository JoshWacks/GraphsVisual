package mainPackage;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;

public class ConfigureScreen {

    private static Button addVertexBtn;
    private static Button addEdgeBtn;
    private static Group group;
    private static Button viewListBtn;
    private static Button viewMatrixBtn;
    private static Button checkCompleteBtn;
    private static Button checkConnectedBtn;
    private static Button addWeightedEdgeBtn;
    private static Button getMWSPBtn;
    private static Button colourGraphBtn;
    private static Button deleteEdgeBtn;
    private static Button deleteVertexBtn;

    private static ArrayList<Button>buttons=new ArrayList<>();

    private static int btnSelected=0;

    public ConfigureScreen(Group gr){
        group=gr;
    }

    public void addButtons(){
        BackgroundFill backgroundFill=new BackgroundFill(Color.DARKCYAN,new CornerRadii(3),null);
        BackgroundFill backgroundFillDel=new BackgroundFill(Color.TOMATO,new CornerRadii(3),null);
        Background background=new Background(backgroundFill);
        makeVertexBtn(background);
        makeEdgeBtn(background);
        makeWeightedEdgeBtn(background);
        makeViewListBtn();
        makeViewMatrixBtn();
        makeCompleteBtn();
        makeConnectedBtn();
        makeMWSPBtn();
        makeColourBtn();
        makeDeleteEdgeBtn(new Background(backgroundFillDel));
        makeDeleteVertexBtn(new Background(backgroundFillDel));

        group.getChildren().add(addVertexBtn);
        group.getChildren().add(addEdgeBtn);
        group.getChildren().add(viewListBtn);
        group.getChildren().add(viewMatrixBtn);
        group.getChildren().add(checkCompleteBtn);
        group.getChildren().add(checkConnectedBtn);
        group.getChildren().add(addWeightedEdgeBtn);
        group.getChildren().add(getMWSPBtn);
        group.getChildren().add(colourGraphBtn);
        group.getChildren().add(deleteEdgeBtn);
        group.getChildren().add(deleteVertexBtn);
    }
    public static int getBtnSelected(){
        return btnSelected;
    }

    public static Group getGroup(){
        return group;
    }

    private void makeVertexBtn(Background background){
        addVertexBtn=new Button("Vertex");
        addVertexBtn.setTextFill(Color.BLACK);
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
                toggleBtnOff(addVertexBtn,Color.DARKCYAN);
            }
            else{
                toggleBtnOn(addVertexBtn);
                toggleBtnOff(addWeightedEdgeBtn,Color.DARKCYAN);
                toggleBtnOff(addEdgeBtn,Color.DARKCYAN);
                toggleBtnOff(deleteVertexBtn,Color.TOMATO);
                toggleBtnOff(deleteEdgeBtn,Color.TOMATO);
                btnSelected=1;
            }
        });
        buttons.add(addVertexBtn);
    }

    private void makeEdgeBtn(Background background){
        addEdgeBtn=new Button("Edge");
        addEdgeBtn.setTextFill(Color.BLACK);
        addEdgeBtn.setBackground(background);

        addEdgeBtn.setFont(javafx.scene.text.Font.font(Font.SERIF, 24));
        Tooltip t = new Tooltip("To add an edge click on two vertices");
        Tooltip.install(null, t);
        addEdgeBtn.setTooltip(t);

        addEdgeBtn.setLayoutX(125);
        addEdgeBtn.setLayoutY(150);
        addEdgeBtn.setPrefWidth(100);
        addEdgeBtn.setPrefHeight(40);

        addEdgeBtn.setOnMouseClicked(event -> {
            if(btnSelected==2){
                btnSelected=0;
                toggleBtnOff(addEdgeBtn,Color.DARKCYAN);
            }
            else{
                toggleBtnOn(addEdgeBtn);
                toggleBtnOff(addWeightedEdgeBtn,Color.DARKCYAN);
                toggleBtnOff(addVertexBtn,Color.DARKCYAN);
                toggleBtnOff(deleteVertexBtn,Color.TOMATO);
                toggleBtnOff(deleteEdgeBtn,Color.TOMATO);
                btnSelected=2;
            }
        });
        buttons.add(addEdgeBtn);
    }

    private void makeWeightedEdgeBtn(Background background){
        addWeightedEdgeBtn=new Button("Weighted Edge");
        addWeightedEdgeBtn.setTextFill(Color.BLACK);
        addWeightedEdgeBtn.setBackground(background);

        addWeightedEdgeBtn.setFont(javafx.scene.text.Font.font(Font.SERIF, 24));
        Tooltip t = new Tooltip("To add an weighted edge select two vertices then enter the weight in the popup ");
        Tooltip.install(null, t);
        addWeightedEdgeBtn.setTooltip(t);

        addWeightedEdgeBtn.setLayoutX(245);
        addWeightedEdgeBtn.setLayoutY(150);
        addWeightedEdgeBtn.setPrefWidth(200);
        addWeightedEdgeBtn.setPrefHeight(40);

        addWeightedEdgeBtn.setOnMouseClicked(event -> {
            if(btnSelected==3){
                btnSelected=0;
                toggleBtnOff(addWeightedEdgeBtn,Color.DARKCYAN);
            }
            else{
                toggleBtnOn(addWeightedEdgeBtn);
                toggleBtnOff(addEdgeBtn,Color.DARKCYAN);
                toggleBtnOff(addVertexBtn,Color.DARKCYAN);
                toggleBtnOff(deleteVertexBtn,Color.TOMATO);
                toggleBtnOff(deleteEdgeBtn,Color.TOMATO);
                btnSelected=3;
            }
        });
        buttons.add(addWeightedEdgeBtn);
    }

    private void makeDeleteVertexBtn(Background background){
        deleteVertexBtn=new Button("Delete Vertex");
        deleteVertexBtn.setTextFill(Color.BLACK);
        deleteVertexBtn.setBackground(background);

        deleteVertexBtn.setFont(javafx.scene.text.Font.font(Font.SERIF, 24));
        Tooltip t = new Tooltip("To delete an vertex click here then select an vertex \nAll connected edges will also be deleted");
        Tooltip.install(null, t);
        deleteVertexBtn.setTooltip(t);

        deleteVertexBtn.setLayoutX(465);
        deleteVertexBtn.setLayoutY(150);
        deleteVertexBtn.setPrefWidth(170);
        deleteVertexBtn.setPrefHeight(40);

        deleteVertexBtn.setOnMouseClicked(event -> {
            if(btnSelected==4){
                btnSelected=0;
                toggleBtnOff(deleteVertexBtn,Color.TOMATO);
            }
            else{
                toggleBtnOn(deleteVertexBtn);
                toggleBtnOff(addEdgeBtn,Color.DARKCYAN);
                toggleBtnOff(addVertexBtn,Color.DARKCYAN);
                toggleBtnOff(addWeightedEdgeBtn,Color.DARKCYAN);
                toggleBtnOff(deleteEdgeBtn,Color.TOMATO);
                btnSelected=3;
            }
        });
        buttons.add(deleteVertexBtn);
    }


    private void makeDeleteEdgeBtn(Background background){
        deleteEdgeBtn=new Button("Delete Edge");
        deleteEdgeBtn.setTextFill(Color.BLACK);
        deleteEdgeBtn.setBackground(background);

        deleteEdgeBtn.setFont(javafx.scene.text.Font.font(Font.SERIF, 24));
        Tooltip t = new Tooltip("To delete an edge click here then select an edge");
        Tooltip.install(null, t);
        deleteEdgeBtn.setTooltip(t);

        deleteEdgeBtn.setLayoutX(655);
        deleteEdgeBtn.setLayoutY(150);
        deleteEdgeBtn.setPrefWidth(160);
        deleteEdgeBtn.setPrefHeight(40);

        deleteEdgeBtn.setOnMouseClicked(event -> {
            if(btnSelected==5){
                btnSelected=0;
                toggleBtnOff(deleteEdgeBtn,Color.TOMATO);
            }
            else{
                toggleBtnOn(deleteEdgeBtn);
                toggleBtnOff(addEdgeBtn,Color.DARKCYAN);
                toggleBtnOff(addVertexBtn,Color.DARKCYAN);
                toggleBtnOff(addWeightedEdgeBtn,Color.DARKCYAN);
                toggleBtnOff(deleteVertexBtn,Color.TOMATO);
                btnSelected=5;
            }
        });
        buttons.add(deleteEdgeBtn);
    }

    private void makeViewListBtn(){
        viewListBtn=new Button("View Adjacency List");
        viewListBtn.setTextFill(Color.BLACK);
        BackgroundFill backgroundFill=new BackgroundFill(Color.FORESTGREEN,new CornerRadii(3),null);
        Background background=new Background(backgroundFill);
        viewListBtn.setBackground(background);

        viewListBtn.setFont(javafx.scene.text.Font.font(Font.SERIF, 24));
        Tooltip t = new Tooltip("Click here to view the current graph as an adjacency list");
        Tooltip.install(null, t);
        viewListBtn.setTooltip(t);

        viewListBtn.setLayoutX(1100);
        viewListBtn.setLayoutY(20);
        viewListBtn.setPrefWidth(250);
        viewListBtn.setPrefHeight(30);

        viewListBtn.setOnMouseClicked(event -> Visuals.showList());
        buttons.add(viewListBtn);
    }

    private void makeViewMatrixBtn(){
        viewMatrixBtn=new Button("View Matrix");
        viewMatrixBtn.setTextFill(Color.BLACK);
        BackgroundFill backgroundFill=new BackgroundFill(Color.FORESTGREEN,new CornerRadii(3),null);
        Background background=new Background(backgroundFill);
        viewMatrixBtn.setBackground(background);

        viewMatrixBtn.setFont(javafx.scene.text.Font.font(Font.SERIF, 24));
        Tooltip t = new Tooltip("Click here to view the current graph as matrix");
        Tooltip.install(null, t);
        viewMatrixBtn.setTooltip(t);

        viewMatrixBtn.setLayoutX(850);
        viewMatrixBtn.setLayoutY(20);
        viewMatrixBtn.setPrefWidth(200);
        viewMatrixBtn.setPrefHeight(30);

        viewMatrixBtn.setOnMouseClicked(event -> Visuals.showMatrix());
        buttons.add(viewMatrixBtn);
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

    private void makeMWSPBtn(){
        getMWSPBtn=new Button("Get Minimum Weighted Spanning Tree");
        getMWSPBtn.setTextFill(Color.BLACK);
        BackgroundFill backgroundFill=new BackgroundFill(Color.DODGERBLUE,new CornerRadii(3),null);
        Background background=new Background(backgroundFill);
        getMWSPBtn.setBackground(background);

        getMWSPBtn.setFont(javafx.scene.text.Font.font(Font.SERIF, 20));
        Tooltip t = new Tooltip("Click here to make the minimum weighted spanning tree of this graph if possible");
        Tooltip.install(null, t);
        getMWSPBtn.setTooltip(t);

        getMWSPBtn.setLayoutX(985);
        getMWSPBtn.setLayoutY(500);
        getMWSPBtn.setPrefWidth(370);
        getMWSPBtn.setPrefHeight(5);

        getMWSPBtn.setOnMouseClicked(event -> Visuals.makeMWST());

    }

    private void makeColourBtn(){
        colourGraphBtn=new Button("Colour Graph");
        colourGraphBtn.setTextFill(Color.BLACK);
        BackgroundFill backgroundFill=new BackgroundFill(Color.GOLD,new CornerRadii(3),null);
        Background background=new Background(backgroundFill);
        colourGraphBtn.setBackground(background);

        colourGraphBtn.setFont(javafx.scene.text.Font.font(Font.SERIF, 20));
        Tooltip t = new Tooltip("Click here to colour the graph using the graph colouring algorithm");
        Tooltip.install(null, t);
        colourGraphBtn.setTooltip(t);

        colourGraphBtn.setLayoutX(985);
        colourGraphBtn.setLayoutY(550);
        colourGraphBtn.setPrefWidth(370);
        colourGraphBtn.setPrefHeight(5);

        colourGraphBtn.setOnMouseClicked(event -> Visuals.colourGraph());

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
