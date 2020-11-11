package mainPackage;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

    private static Button addWeightedEdgeBtn;


    private static Button deleteEdgeBtn;
    private static Button deleteVertexBtn;
    private static Button setRootBtn;
    private static Button setDestBtn;

    private static ComboBox algorithmComboBox;
    private static Button runAlgorithmBtn;

    private static ComboBox  searchComboBox;
    private static Button searchBtn;

    private static ArrayList<Button>buttons=new ArrayList<>();

    private static int btnSelected=0;

    public ConfigureScreen(Group gr){
        group=gr;
    }

    public void addButtons(){
        BackgroundFill backgroundFill=new BackgroundFill(Color.DARKCYAN,new CornerRadii(3),null);//The two types of backgrounds used
        BackgroundFill backgroundFillDel=new BackgroundFill(Color.TOMATO,new CornerRadii(3),null);
        Background background=new Background(backgroundFill);
        makeVertexBtn(background);
        makeEdgeBtn(background);
        makeWeightedEdgeBtn(background);
        makeViewListBtn();
        makeViewMatrixBtn();

        makeDeleteEdgeBtn(new Background(backgroundFillDel));
        makeDeleteVertexBtn(new Background(backgroundFillDel));
        makeRootBtn();
        makeDestBtn();

        makeAlgBtn();
        makeAlgorithmBox();

        makeCombBox();
        makeSearchBtn();

        group.getChildren().add(addVertexBtn);
        group.getChildren().add(addEdgeBtn);
        group.getChildren().add(viewListBtn);
        group.getChildren().add(viewMatrixBtn);

        group.getChildren().add(addWeightedEdgeBtn);

        group.getChildren().add(deleteEdgeBtn);
        group.getChildren().add(deleteVertexBtn);
        group.getChildren().add(setRootBtn);
        group.getChildren().add(setDestBtn);

        group.getChildren().add(algorithmComboBox);
        group.getChildren().add(runAlgorithmBtn);

        group.getChildren().add(searchBtn);
        group.getChildren().add(searchComboBox);
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
                toggleBtnOff(setRootBtn,Color.FORESTGREEN);
                toggleBtnOff(setDestBtn,Color.BLUEVIOLET);
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
                toggleBtnOff(setRootBtn,Color.FORESTGREEN);
                toggleBtnOff(setDestBtn,Color.BLUEVIOLET);
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
                toggleBtnOff(setRootBtn,Color.FORESTGREEN);
                toggleBtnOff(setDestBtn,Color.BLUEVIOLET);
                btnSelected=3;
            }
        });
        buttons.add(addWeightedEdgeBtn);
    }

    private void makeRootBtn(){
        setRootBtn=new Button("Indicate Root");
        setRootBtn.setTextFill(Color.BLACK);
        BackgroundFill backgroundFill=new BackgroundFill(Color.FORESTGREEN,new CornerRadii(3),null);//The two types of backgrounds used
        Background background=new Background(backgroundFill);
        setRootBtn.setBackground(background);

        setRootBtn.setFont(javafx.scene.text.Font.font(Font.SERIF, 24));
        Tooltip t = new Tooltip("To indicate the root click here then on the vertex you would like to be the root");
        Tooltip.install(null, t);
        setRootBtn.setTooltip(t);

        setRootBtn.setLayoutX(5);
        setRootBtn.setLayoutY(90);
        setRootBtn.setPrefWidth(200);
        setRootBtn.setPrefHeight(40);

        setRootBtn.setOnMouseClicked(event -> {
            if(btnSelected==6){
                btnSelected=0;
                toggleBtnOff(setRootBtn,Color.FORESTGREEN);
            }
            else{
                toggleBtnOn(setRootBtn);
                toggleBtnOff(addEdgeBtn,Color.DARKCYAN);
                toggleBtnOff(addVertexBtn,Color.DARKCYAN);
                toggleBtnOff(addWeightedEdgeBtn,Color.DARKCYAN);
                toggleBtnOff(deleteVertexBtn,Color.TOMATO);
                toggleBtnOff(deleteEdgeBtn,Color.TOMATO);
                toggleBtnOff(setDestBtn,Color.BLUEVIOLET);
                btnSelected=6;
            }
        });
        buttons.add(setRootBtn);
    }

    private void makeDestBtn(){
        setDestBtn=new Button("Indicate Destination");
        setDestBtn.setTextFill(Color.BLACK);
        BackgroundFill backgroundFill=new BackgroundFill(Color.BLUEVIOLET,new CornerRadii(3),null);//The two types of backgrounds used
        Background background=new Background(backgroundFill);
        setDestBtn.setBackground(background);

        setDestBtn.setFont(javafx.scene.text.Font.font(Font.SERIF, 24));
        Tooltip t = new Tooltip("To indicate the destination click here then on the vertex you would like to be the destination");
        Tooltip.install(null, t);
        setDestBtn.setTooltip(t);

        setDestBtn.setLayoutX(220);
        setDestBtn.setLayoutY(90);
        setDestBtn.setPrefWidth(240);
        setDestBtn.setPrefHeight(40);

        setDestBtn.setOnMouseClicked(event -> {
            if(btnSelected==7){
                btnSelected=0;
                toggleBtnOff(setDestBtn,Color.BLUEVIOLET);
            }
            else{
                toggleBtnOn(setDestBtn);
                toggleBtnOff(setRootBtn,Color.FORESTGREEN);
                toggleBtnOff(addEdgeBtn,Color.DARKCYAN);
                toggleBtnOff(addVertexBtn,Color.DARKCYAN);
                toggleBtnOff(addWeightedEdgeBtn,Color.DARKCYAN);
                toggleBtnOff(deleteVertexBtn,Color.TOMATO);
                toggleBtnOff(deleteEdgeBtn,Color.TOMATO);

                btnSelected=7;
            }
        });
        buttons.add(setRootBtn);
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
                toggleBtnOff(setRootBtn,Color.FORESTGREEN);
                toggleBtnOff(setDestBtn,Color.BLUEVIOLET);
                btnSelected=4;
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
                toggleBtnOff(setRootBtn,Color.FORESTGREEN);
                toggleBtnOff(setDestBtn,Color.BLUEVIOLET);
                btnSelected=5;
            }
        });
        buttons.add(deleteEdgeBtn);
    }

    private void makeViewListBtn(){
        viewListBtn=new Button("View Adjacency List");
        viewListBtn.setTextFill(Color.BLACK);
        BackgroundFill backgroundFill=new BackgroundFill(Color.GOLD,new CornerRadii(3),null);
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
        BackgroundFill backgroundFill=new BackgroundFill(Color.GOLD,new CornerRadii(3),null);
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

//    private void makeCompleteBtn(){
//        checkCompleteBtn=new Button("Complete?");
//        checkCompleteBtn.setTextFill(Color.BLACK);
//        BackgroundFill backgroundFill=new BackgroundFill(Color.CORAL,new CornerRadii(3),null);
//        Background background=new Background(backgroundFill);
//        checkCompleteBtn.setBackground(background);
//
//        checkCompleteBtn.setFont(javafx.scene.text.Font.font(Font.SERIF, 20));
//        Tooltip t = new Tooltip("Click here to check if the graph is a complete graph");
//        Tooltip.install(null, t);
//        checkCompleteBtn.setTooltip(t);
//
//        checkCompleteBtn.setLayoutX(825);
//        checkCompleteBtn.setLayoutY(500);
//        checkCompleteBtn.setPrefSize(130,30);
//
//        checkCompleteBtn.setOnMouseClicked(event -> Visuals.checkCompleted());
//
//    }

//    private void makeConnectedBtn(){
//        checkConnectedBtn=new Button("Connected?");
//        checkConnectedBtn.setTextFill(Color.BLACK);
//        BackgroundFill backgroundFill=new BackgroundFill(Color.DARKKHAKI,new CornerRadii(3),null);
//        Background background=new Background(backgroundFill);
//        checkConnectedBtn.setBackground(background);
//
//        checkConnectedBtn.setFont(javafx.scene.text.Font.font(Font.SERIF, 20));
//        Tooltip t = new Tooltip("Click here to check if the graph is a connected graph");
//        Tooltip.install(null, t);
//        checkConnectedBtn.setTooltip(t);
//
//        checkConnectedBtn.setLayoutX(825);
//        checkConnectedBtn.setLayoutY(550);
//        checkConnectedBtn.setPrefSize(130,30);
//
//        checkConnectedBtn.setOnMouseClicked(event -> Visuals.checkConnected());
//
//    }
//
//    private void makeMWSPBtn(){
//        getMWSPBtn=new Button("Get Minimum Weighted Spanning Tree");
//        getMWSPBtn.setTextFill(Color.BLACK);
//        BackgroundFill backgroundFill=new BackgroundFill(Color.DODGERBLUE,new CornerRadii(3),null);
//        Background background=new Background(backgroundFill);
//        getMWSPBtn.setBackground(background);
//
//        getMWSPBtn.setFont(javafx.scene.text.Font.font(Font.SERIF, 20));
//        Tooltip t = new Tooltip("Click here to make the minimum weighted spanning tree of this graph if possible");
//        Tooltip.install(null, t);
//        getMWSPBtn.setTooltip(t);
//
//        getMWSPBtn.setLayoutX(985);
//        getMWSPBtn.setLayoutY(500);
//        getMWSPBtn.setPrefSize(370,30);
//
//        getMWSPBtn.setOnMouseClicked(event -> Visuals.makeMWST());
//
//    }

    private void makeAlgBtn(){
        runAlgorithmBtn=new Button("Run Algorithm");
        runAlgorithmBtn.setTextFill(Color.BLACK);
        BackgroundFill backgroundFill=new BackgroundFill(Color.DODGERBLUE,new CornerRadii(3),null);
        Background background=new Background(backgroundFill);
        runAlgorithmBtn.setBackground(background);

        runAlgorithmBtn.setFont(javafx.scene.text.Font.font(Font.SERIF, 20));
        Tooltip t = new Tooltip("Click here to run the selected algorithm");
        Tooltip.install(null, t);
        runAlgorithmBtn.setTooltip(t);


        runAlgorithmBtn.setLayoutX(1200);
        runAlgorithmBtn.setLayoutY(493);
        runAlgorithmBtn.setPrefSize(150,40);

        runAlgorithmBtn.setOnMouseClicked(event -> Visuals.callAlg());

    }

    private void makeAlgorithmBox (){

        algorithmComboBox=new ComboBox();
        algorithmComboBox.setStyle("-fx-font: 23px \"Serif\";");
        algorithmComboBox.getItems().addAll("Connected Graph?","Complete Graph?","Make MWSP", "Colour Graph","Make Shortest Path Tree\n(Dijkstra's Algorithm)");
        algorithmComboBox.setValue("Select An Algorithm");

        algorithmComboBox.setLayoutX(822);
        algorithmComboBox.setLayoutY(490);

        algorithmComboBox.setPrefSize(350,30);


    }

    private void makeSearchBtn(){
        searchBtn=new Button("Run Search");
        searchBtn.setTextFill(Color.BLACK);
        BackgroundFill backgroundFill=new BackgroundFill(Color.DARKSALMON,new CornerRadii(3),null);
        Background background=new Background(backgroundFill);
        searchBtn.setBackground(background);

        searchBtn.setFont(javafx.scene.text.Font.font(Font.SERIF, 20));
        Tooltip t = new Tooltip("Click here perform the selected search on on the current graph from the root");
        Tooltip.install(null, t);
        searchBtn.setTooltip(t);

        searchBtn.setLayoutX(1200);
        searchBtn.setLayoutY(553);
        searchBtn.setPrefWidth(150);
        searchBtn.setPrefHeight(40);

        searchBtn.setOnMouseClicked(event -> Visuals.callSearch());

    }



    private void makeCombBox (){

        searchComboBox=new ComboBox();
        searchComboBox.setStyle("-fx-font: 23px \"Serif\";");
        searchComboBox.getItems().addAll("DFS","BFS");
        searchComboBox.setValue("Select A Search");

        searchComboBox.setLayoutX(822);
        searchComboBox.setLayoutY(550);

        searchComboBox.setPrefSize(350,30);


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

    }//Visual Aspect so the user can see the button selected as on and to confirm it is off

    public static String getSearchSelected(){
        return (String) searchComboBox.getValue();
    }

    public static String getAlgorithmSelected(){return (String) algorithmComboBox.getValue();}
}
