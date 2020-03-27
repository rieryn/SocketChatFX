package client;

import util.Input;

import javafx.application.Application;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class ClientGUI extends Application {

    private static Stage primaryStage;
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Client client;

    public ClientGUI() throws IOException {
    }

    @Override
    public void stop(){
        System.out.println("Stage is closing");
        System.exit(0);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;

        System.out.println("loading fxml");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
        System.out.println("fxml loaded");
        Parent root = loader.load();


        primaryStage.setTitle("chatter/v1-1");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 300, 500,Color.TRANSPARENT));
        primaryStage.show();
    }
    public static Stage getStage(){
        return primaryStage;
    }
}