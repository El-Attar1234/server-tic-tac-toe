package tictokserver;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class RecordPlayer extends AnchorPane {

    protected final Pane pane;
    protected final Button btnRegister;
    protected final Button btnBack;
    protected final Label label;
    protected final Label label0;
    protected final Label label1;
    protected final Label label2;
    protected final TextField nameField;
    protected final TextField passwordField;
    protected final TextField emailField;
    protected final Label nameError;
    protected final Label emailError;
    protected final Label passwordError;
    protected final Label label3;
    protected final TextField confPasswoedField;
    protected final Label confPassWordError;

    public RecordPlayer(Stage stage) {

        pane = new Pane();
        btnRegister = new Button();
        btnBack = new Button();
        label = new Label();
        label0 = new Label();
        label1 = new Label();
        label2 = new Label();
        nameField = new TextField();
        passwordField = new TextField();
        emailField = new TextField();
        nameError = new Label();
        emailError = new Label();
        passwordError = new Label();
        label3 = new Label();
        confPasswoedField = new TextField();
        confPassWordError = new Label();

        setId("AnchorPane");
        setPrefHeight(800.0);
        setPrefWidth(700.0);

        pane.setPrefHeight(800.0);
        pane.setPrefWidth(700.0);

        btnRegister.setLayoutX(236.0);
        btnRegister.setLayoutY(569.0);
        btnRegister.setMnemonicParsing(false);
        btnRegister.setPrefHeight(36.0);
        btnRegister.setPrefWidth(208.0);
        btnRegister.setText("Record");
        btnRegister.setFont(new Font(24.0));

        btnBack.setLayoutX(236.0);
        btnBack.setLayoutY(673.0);
        btnBack.setMnemonicParsing(false);
        btnBack.setPrefHeight(36.0);
        btnBack.setPrefWidth(208.0);
        btnBack.setText("Cancel");
        btnBack.setFont(new Font(24.0));

        label.setLayoutX(258.0);
        label.setLayoutY(37.0);
        label.setPrefHeight(40.0);
        label.setPrefWidth(229.0);
        label.setText("Record New Player");
        label.setTextFill(javafx.scene.paint.Color.valueOf("#ca3bd7"));
        label.setFont(new Font(28.0));

        label0.setLayoutX(70.0);
        label0.setLayoutY(120.0);
        label0.setPrefHeight(30.0);
        label0.setPrefWidth(200.0);
        label0.setText("Enter Player Name");
        label0.setFont(new Font(20.0));

        label1.setLayoutX(70.0);
        label1.setLayoutY(230.0);
        label1.setPrefHeight(30.0);
        label1.setPrefWidth(200.0);
        label1.setText("Enter Player Email");
        label1.setFont(new Font(20.0));

        label2.setLayoutX(70.0);
        label2.setLayoutY(345.0);
        label2.setPrefHeight(30.0);
        label2.setPrefWidth(200.0);
        label2.setText("Enter Player Password");
        label2.setFont(new Font(20.0));

        nameField.setLayoutX(279.0);
        nameField.setLayoutY(116.0);
        nameField.setPrefHeight(38.0);
        nameField.setPrefWidth(395.0);

        passwordField.setLayoutX(279.0);
        passwordField.setLayoutY(342.0);
        passwordField.setPrefHeight(38.0);
        passwordField.setPrefWidth(395.0);

        emailField.setLayoutX(279.0);
        emailField.setLayoutY(226.0);
        emailField.setPrefHeight(38.0);
        emailField.setPrefWidth(395.0);

        nameError.setLayoutX(279.0);
        nameError.setLayoutY(165.0);
        nameError.setPrefHeight(26.0);
        nameError.setPrefWidth(395.0);
        nameError.setTextFill(javafx.scene.paint.Color.valueOf("#e11515"));
        nameError.setFont(new Font(15.0));

        emailError.setLayoutX(279.0);
        emailError.setLayoutY(278.0);
        emailError.setPrefHeight(26.0);
        emailError.setPrefWidth(395.0);
        emailError.setTextFill(javafx.scene.paint.Color.valueOf("#da1414"));
        emailError.setFont(new Font(15.0));

        passwordError.setLayoutX(279.0);
        passwordError.setLayoutY(393.0);
        passwordError.setPrefHeight(26.0);
        passwordError.setPrefWidth(395.0);
        passwordError.setTextFill(javafx.scene.paint.Color.valueOf("#e11414"));
        passwordError.setFont(new Font(15.0));

        label3.setLayoutX(68.0);
        label3.setLayoutY(461.0);
        label3.setPrefHeight(30.0);
        label3.setPrefWidth(200.0);
        label3.setText("Confirm The Password");
        label3.setFont(new Font(20.0));

        confPasswoedField.setLayoutX(278.0);
        confPasswoedField.setLayoutY(458.0);
        confPasswoedField.setPrefHeight(38.0);
        confPasswoedField.setPrefWidth(395.0);

        confPassWordError.setLayoutX(278.0);
        confPassWordError.setLayoutY(509.0);
        confPassWordError.setPrefHeight(26.0);
        confPassWordError.setPrefWidth(395.0);
        confPassWordError.setTextFill(javafx.scene.paint.Color.valueOf("#e11414"));
        confPassWordError.setFont(new Font(15.0));

        pane.getChildren().add(btnRegister);
        pane.getChildren().add(btnBack);
        getChildren().add(pane);
        getChildren().add(label);
        getChildren().add(label0);
        getChildren().add(label1);
        getChildren().add(label2);
        getChildren().add(nameField);
        getChildren().add(passwordField);
        getChildren().add(emailField);
        getChildren().add(nameError);
        getChildren().add(emailError);
        getChildren().add(passwordError);
        getChildren().add(label3);
        getChildren().add(confPasswoedField);
        getChildren().add(confPassWordError);

    }
}
