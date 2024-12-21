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
    // UI Components
    TextField qrStringField;
    private QrCodeView qrCodeView;

    // Current Values
    private String typeString = "";
    private String qrText = "";
    private QrErrorCorrectionLevels.ErrCorLevel errorCorrectionLevel = QrErrorCorrectionLevels.M;


    @Override
    public void start(Stage stage) {
        BorderPane borderPane = new BorderPane();

        // QR Code View
        qrCodeView = new QrCodeView(200, 200);
        borderPane.setCenter(qrCodeView);

        // QR String View
        qrStringField = new TextField();
        qrStringField.setEditable(false);


        // Generate ToolBar
        ToolBar toolBar = new ToolBar();

        TextField textField = new TextField();

        toolBar.getItems().add(new Label("Text: "));
        toolBar.getItems().add(textField);

        HBox.setHgrow(textField, Priority.ALWAYS);

        textField.textProperty().addListener(
                (_, _, newValue) -> {
                    qrText = newValue;
                    showQrCode();
                });


        // Option ToolBar
        ToolBar optionToolBar = new ToolBar();

        // Type Radio Buttons: LOADING ... | ...
        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton radioButtonLoading = new RadioButton("LOADING");
        RadioButton radioButtonGeneric = new RadioButton("Generic");
        radioButtonLoading.setToggleGroup(toggleGroup);
        radioButtonGeneric.setToggleGroup(toggleGroup);

        radioButtonLoading.setSelected(true);
        typeString = "LOADING ";

        radioButtonLoading.setOnAction(_ -> {
            typeString = "LOADING ";
            showQrCode();
        });

        radioButtonGeneric.setOnAction(_ -> {
            typeString = "";
            showQrCode();
        });

        // Error Correction Level ComboBox
        ComboBox<QrErrorCorrectionLevels.ErrCorLevel> errCorLevelComboBox = new ComboBox<>();
        errCorLevelComboBox.setItems(QrErrorCorrectionLevels.getErrorCorrectionLevels());
        errCorLevelComboBox.getSelectionModel().select(QrErrorCorrectionLevels.M);
        errCorLevelComboBox.getSelectionModel().selectedItemProperty().addListener(
                (_, _, newValue) -> {
                    errorCorrectionLevel = newValue;
                    showQrCode();
                });

        // Build Option ToolBar
        optionToolBar.getItems().add(radioButtonLoading);
        optionToolBar.getItems().add(radioButtonGeneric);
        optionToolBar.getItems().add(new Separator());
        optionToolBar.getItems().add(new Label("ErrCor: "));
        optionToolBar.getItems().add(errCorLevelComboBox);


        VBox vBox = new VBox();
        vBox.getChildren().add(toolBar);
        vBox.getChildren().add(optionToolBar);

        borderPane.setTop(vBox);
        borderPane.setBottom(qrStringField);


        Scene scene = new Scene(borderPane, 400, 400);

        stage.setTitle("QR Code Generator " + GradleProject.ApplicationVersion);
        stage.setScene(scene);
        stage.show();

        showQrCode();


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


    private void showQrCode() {
        // String type, String code, ErrorCorrectionLevel level
        String utf8String = qrText.strip().replaceAll("\\p{C}", "");
        // remove non-printable characters
        String qrString = new String(utf8String.getBytes(), StandardCharsets.ISO_8859_1);

        try {
            qrCodeView.setCode(typeString + qrString);
            qrCodeView.setErrorCorrectionLevel(errorCorrectionLevel.level());
            qrStringField.setText(typeString + qrString);
        } catch (WriterException ex) {
            throw new RuntimeException(ex);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

}