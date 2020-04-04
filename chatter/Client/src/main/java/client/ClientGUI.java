package client;

import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

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
        System.out.println(getClass().getResource("LoginView.fxml"));

        this.primaryStage = primaryStage;

        System.out.println("loading fxml");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginView.fxml"));
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
    public static void main(String[] args){
        launch(args);
    }
}