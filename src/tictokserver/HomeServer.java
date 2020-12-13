package tictokserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HomeServer extends BorderPane {

    double onlineNumber = 0, offLineNumber = 0;
    protected final AnchorPane anchorPane;
    protected final FlowPane flowPane;
    protected final Label label;
    protected final Label label0;
    protected final Label label1;
    protected final ImageView stopButton;
    protected final ImageView newPlayerButton;
    protected final FlowPane flowPane0;
    protected final FlowPane flowPane1;
    protected static TextArea onlineText;
    protected static TextArea offlineText;
    protected static TextArea ongameText;
    protected PieChart pieChart;
    protected ObservableList<PieChart.Data> pieChartData;
    static ArrayList<String> arrOnline = new ArrayList<>();
    static ArrayList<String> arrOffline = new ArrayList<>();
    static ArrayList<String> arrOnGaming = new ArrayList<>();

    static private ListView<String> onlinwListView;
    static ObservableList onLineObservableList = FXCollections.observableArrayList();

    static private ListView<String> offlinwListView;
    static ObservableList offlineObservableList = FXCollections.observableArrayList();

    static private ListView<String> onGameListView;
    static ObservableList onGameObservableList = FXCollections.observableArrayList();

    public HomeServer(Stage stage) {

        //create object for start server
        ServerNetwork s = new ServerNetwork();

        anchorPane = new AnchorPane();
        flowPane = new FlowPane();
        label = new Label();
        label0 = new Label();
        label1 = new Label();
        stopButton = new ImageView();
        newPlayerButton = new ImageView();
        flowPane0 = new FlowPane();
        flowPane1 = new FlowPane();
        onlineText = new TextArea();
        offlineText = new TextArea();
        ongameText = new TextArea();
        pieChart = new PieChart();
        onlinwListView = new ListView<String>();
        offlinwListView = new ListView<String>();
        onGameListView = new ListView<String>();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(651.0);
        setPrefWidth(780.0);

        BorderPane.setAlignment(anchorPane, javafx.geometry.Pos.CENTER);
        anchorPane.setPrefHeight(200.0);
        anchorPane.setPrefWidth(300.0);

        flowPane.setLayoutX(29.0);
        flowPane.setLayoutY(14.0);
        flowPane.setPrefHeight(51.0);
        flowPane.setPrefWidth(729.0);

        label.setPrefHeight(56.0);
        label.setPrefWidth(234.0);
        label.setText("              Online");
        label.setTextFill(Color.GREEN);
        label.setTextAlignment(javafx.scene.text.TextAlignment.JUSTIFY);
        label.setWrapText(true);

        label0.setPrefHeight(53.0);
        label0.setPrefWidth(221.0);
        label0.setText("                   Offline");
        label0.setTextFill(Color.RED);

        label1.setPrefHeight(54.0);
        label1.setPrefWidth(252.0);
        label1.setText("                      On game");
        label1.setTextFill(Color.BLUE);

        stopButton.setFitHeight(150.0);
        stopButton.setFitWidth(200.0);
        stopButton.setLayoutX(603.0);
        stopButton.setLayoutY(478.0);
        stopButton.setPickOnBounds(true);
        stopButton.setPreserveRatio(true);
        stopButton.setImage(new Image(getClass().getResource("stop.png").toExternalForm()));

        newPlayerButton.setFitHeight(150.0);
        newPlayerButton.setFitWidth(200.0);
        newPlayerButton.setLayoutX(603.0);
        newPlayerButton.setLayoutY(478.0);
        newPlayerButton.setPickOnBounds(true);
        newPlayerButton.setPreserveRatio(true);
        newPlayerButton.setImage(new Image(getClass().getResource("addplayer.png").toExternalForm()));

        flowPane1.setLayoutX(0.0);
        flowPane1.setLayoutY(500.0);
        flowPane1.setPrefHeight(200.0);
        flowPane1.setPrefWidth(800.0);
        flowPane1.setPadding(new Insets(0.0, 0.0, 0.0, 50.0));

        flowPane0.setLayoutX(26.0);
        flowPane0.setLayoutY(85.0);
        flowPane0.setPrefHeight(359.0);
        flowPane0.setPrefWidth(729.0);
        flowPane0.setPadding(new Insets(0.0, 0.0, 0.0, 50.0));

        onlinwListView.setPrefHeight(30.0);
        onlinwListView.setPrefWidth(200.0);
        onlinwListView.setStyle("-fx-text-fill: green");
        FlowPane.setMargin(onlinwListView, new Insets(10.0));

        offlinwListView.setPrefHeight(330.0);
        offlinwListView.setPrefWidth(200.0);
        offlinwListView.setStyle("-fx-text-fill: green");
        FlowPane.setMargin(offlinwListView, new Insets(10.0));

        onGameListView.setPrefHeight(330.0);
        onGameListView.setPrefWidth(200.0);
        onGameListView.setStyle("-fx-text-fill: blue");
        FlowPane.setMargin(onGameListView, new Insets(10.0));

        ArrayList<String> arr = new ArrayList<>();
        arr = DataBaseHandling.getInstance().getOnLine();
        for (String c : arr) {
            onlineNumber++;
        }
        ArrayList<String> arr1 = new ArrayList<>();
        arr1 = DataBaseHandling.getInstance().getOffLine();
        for (String c : arr1) {
            offLineNumber++;
        }
        pieChart.setLayoutX(603.0);
        pieChart.setLayoutY(478.0);
        pieChart.setPrefHeight(150.0);
        pieChart.setPrefWidth(300.0);
        pieChart.setStartAngle(270);
        pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("OnLine \n" + (int) onlineNumber, onlineNumber),
                new PieChart.Data("OffLine \n" + (int) offLineNumber, offLineNumber));
        pieChart.setData(pieChartData);

        pieChartData.get(0).getNode().setStyle("-fx-pie-color: green");
        pieChartData.get(1).getNode().setStyle("-fx-pie-color: red");
        pieChart.setLabelsVisible(true);

        ScrollPane scrollPaneOnline = new ScrollPane();
        scrollPaneOnline.setContent(onlineText);
        scrollPaneOnline.setFitToWidth(true);
        scrollPaneOnline.setFitToHeight(true);
        ScrollPane scrollPaneOffline = new ScrollPane();
        scrollPaneOffline.setContent(offlineText);
        scrollPaneOffline.setFitToWidth(true);
        scrollPaneOffline.setFitToHeight(true);

        ScrollPane scrollPaneOnGame = new ScrollPane();
        scrollPaneOnGame.setContent(ongameText);
        scrollPaneOnGame.setFitToWidth(true);
        scrollPaneOnGame.setFitToHeight(true);

        setCenter(anchorPane);

        flowPane.getChildren().add(label);
        flowPane.getChildren().add(label0);
        flowPane.getChildren().add(label1);
        anchorPane.getChildren().add(flowPane);
        flowPane1.getChildren().add(pieChart);

        flowPane1.getChildren().add(newPlayerButton);
        flowPane1.getChildren().add(stopButton);
        anchorPane.getChildren().add(flowPane1);
        flowPane0.getChildren().add(onlinwListView);
        flowPane0.getChildren().add(offlinwListView);
        flowPane0.getChildren().add(onGameListView);
        anchorPane.getChildren().add(flowPane0);

        stopButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

//                DataBaseHandling.getInstance().clossingDataBase();
                DataBaseHandling.getInstance().stopServerOfflineAll();
                try {
                    ServerNetwork.myServerSocket.close();
                } catch (IOException ex) {
                    Logger.getLogger(HomeServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                Parent root = new StartServer1(stage);
                Scene scene = new Scene(root);
                stage.setTitle("Tic-Tok Server");
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.setScene(scene);
                stage.resizableProperty().setValue(Boolean.FALSE);
                stage.show();
            }
        });
        newPlayerButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Parent root = new RecordPlayer(stage);

                Scene scene = new Scene(root);
                stage.setTitle("Record New Player");
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.setScene(scene);
                stage.resizableProperty().setValue(Boolean.FALSE);
                stage.show();
                stage.show();
            }
        });

        Thread h = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    Platform.runLater(() -> setOnlineText());
                    Platform.runLater(() -> setOffText());
                    Platform.runLater(() -> setOnGamingText());

                    try {
                        Thread.currentThread().sleep(200);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(HomeServer.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        });
        h.start();

    }

    public static void setOnlineText() {

        onLineObservableList.removeAll(arrOnline);
        arrOnline.clear();
        arrOnline = DataBaseHandling.getInstance().getOnLine();
        onLineObservableList.addAll(arrOnline);
        onlinwListView.setItems(onLineObservableList);

    }

    public static void setOffText() {

        offlineObservableList.removeAll(arrOffline);
        arrOffline.clear();
        arrOffline = DataBaseHandling.getInstance().getOffLine();
        offlineObservableList.addAll(arrOffline);
        offlinwListView.setItems(offlineObservableList);
    }

    public static void setOnGamingText() {

        onGameObservableList.removeAll(arrOnGaming);
        arrOnGaming.clear();
        arrOnGaming = DataBaseHandling.getInstance().getOnGaming();
        onGameObservableList.addAll(arrOnGaming);
        onGameListView.setItems(onGameObservableList);
    }
}
