package com.philstar.app.qrcodegenerator;

import com.google.zxing.WriterException;
import gradle.GradleProject;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class MainApp extends Application {
    private String typeString;

    @Override
    public void start(Stage stage) {
        BorderPane borderPane = new BorderPane();


        // QR Code View
        QrCodeView qrCodeView = new QrCodeView(200, 200);
        borderPane.setCenter(qrCodeView);


        // Generate ToolBar
        ToolBar toolBar = new ToolBar();

        TextField textField = new TextField();
        Button generateButton = new Button("Generate");

        toolBar.getItems().add(new Label("Text: "));
        toolBar.getItems().add(textField);
        toolBar.getItems().add(generateButton);

        HBox.setHgrow(textField, Priority.ALWAYS);

        generateButton.setOnAction(e -> {
            String utf8String = textField.getText().strip().replaceAll("\\p{C}", "");
            // remove non-printable characters
            String qrString = new String(utf8String.getBytes(), StandardCharsets.ISO_8859_1);

            try {
                qrCodeView.setCode(typeString + qrString);
            } catch (WriterException ex) {
                throw new RuntimeException(ex);
            }
        });


        // Type ToolBar
        ToolBar typeToolBar = new ToolBar();

        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton radioButtonLoading = new RadioButton("LOADING");
        RadioButton radioButtonGeneric = new RadioButton("Generic");
        radioButtonLoading.setToggleGroup(toggleGroup);
        radioButtonGeneric.setToggleGroup(toggleGroup);

        radioButtonLoading.setSelected(true);
        typeString = "LOADING ";

        radioButtonLoading.setOnAction(e -> typeString = "LOADING ");
        radioButtonGeneric.setOnAction(e -> typeString = "");

        typeToolBar.getItems().add(radioButtonLoading);
        typeToolBar.getItems().add(radioButtonGeneric);




        VBox vBox = new VBox();
        vBox.getChildren().add(toolBar);
        vBox.getChildren().add(typeToolBar);

        borderPane.setTop(vBox);




        Scene scene = new Scene(borderPane, 400, 400);

        stage.setTitle("QR Code Generator " + GradleProject.ApplicationVersion);
        stage.setScene(scene);
        stage.show();


        // Set icons
        stage.getIcons().add(new Image(Objects.requireNonNull(
                MainApp.class.getResourceAsStream("/qrcode16.png"))));
        stage.getIcons().add(new Image(Objects.requireNonNull(
                MainApp.class.getResourceAsStream("/qrcode32.png"))));
        stage.getIcons().add(new Image(Objects.requireNonNull(
                MainApp.class.getResourceAsStream("/qrcode48.png"))));
        stage.getIcons().add(new Image(Objects.requireNonNull(
                MainApp.class.getResourceAsStream("/qrcode64.png"))));
        stage.getIcons().add(new Image(Objects.requireNonNull(
                MainApp.class.getResourceAsStream("/qrcode256.png"))));
    }


    public static void main(String[] args) {
        launch(args);
    }

}