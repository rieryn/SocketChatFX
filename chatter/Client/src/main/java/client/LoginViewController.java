package client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.net.Socket;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ResourceBundle;


public class LoginViewController implements Initializable {
    private String hostname;
    private String username;
    private String icon;
    private int port;
    private String host;
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Client client;
    private Scene scene;
    private Stage stage;
    private String userIcon;
    private String decodedPath;



    @FXML
    private ImageView iconView;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField hostnameField;

    @FXML
    private TextField portField;

    @FXML
    private Button btLogin;

    //allows user to pick a icon
    @FXML
    void iconPicker(MouseEvent event) throws FileNotFoundException {
        FileChooser chooser = new FileChooser();
        File defaultFile = new File(System.getProperty("user.home"));
        System.out.println(System.getProperty("user.home"));
        System.out.println(System.getProperty(decodedPath));

        chooser.setTitle("Select profile picture");
        chooser.setInitialDirectory(new File(decodedPath));
        File selectedFile = chooser.showOpenDialog(iconView.getScene().getWindow());
        if(selectedFile!=null){
            this.userIcon = decodedPath+selectedFile.getName();
            Image image = new Image(new FileInputStream(userIcon));
            iconView.setImage(image);}
            System.out.println(userIcon);
    }

    @FXML
    void login(ActionEvent event) throws IOException {
        //initialize client thread
        host = hostnameField.getText();
        port =  Integer.parseInt(portField.getText());
        username = usernameField.getText();
        if(host!=null){
            client = new Client(host, port);
        }
        Thread t = new Thread(client);
        t.setDaemon(true);
        t.start();
        this.socket = client.chatSocket;
        this.outputStream = client.outputStream;
        this.inputStream = client.inputStream;
        //save settings in config.txt
        FileWriter writer = new FileWriter("config.txt", false);
        writer.write(username+System.lineSeparator()+host+System.lineSeparator()+port+System.lineSeparator());
        writer.flush();
        writer.close();
        System.out.println(username+System.lineSeparator()+host+System.lineSeparator()+port+System.lineSeparator());
        System.out.println("configurations saved to config.txt");

        System.out.println("loading fxml");
        //load fxml
        this.stage = (Stage) btLogin.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClientView.fxml"));
        System.out.println("fxml loaded");
        Parent root = loader.load();
        //initialize controller
        ClientViewController controller = (ClientViewController) loader.getController();
        controller.setClient(client);
        controller.setChatView();
        controller.setUsername(usernameField.getText());
        controller.setUserIcon(userIcon);
        controller.setSocket(port, host);
        client.setController(controller);
        System.out.println("log");
        stage.setMinWidth(1080);
        stage.setMinHeight(720);
        stage.setScene(new Scene(root, 1080, 720, Color.TRANSPARENT));
        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("initializing login");
        //rotate logo image
        RotateTransition rt = new RotateTransition(Duration.millis(1000), iconView);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
        //default icon
        String path = LoginViewController.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String parentFolder = new File(path).getParent();
        try {
            decodedPath = URLDecoder.decode(parentFolder, "UTF-8")+"/images/";
            System.out.println(decodedPath);
            this.userIcon = decodedPath+"logo.png";

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try{Image image = new Image(new FileInputStream(userIcon));
            iconView.setImage(image);}
        catch(FileNotFoundException e){System.out.println("error loading icon");}
        //read defaults from config.txt if it exits
        FileReader reader = null;
        try {
            reader = new FileReader("config.txt");
        } catch (FileNotFoundException e) {
            return;
        }
        BufferedReader bufferedReader = new BufferedReader(reader);
        try {
            usernameField.setText(bufferedReader.readLine());
        } catch (IOException e) {
            return;
        }
        try {
            hostnameField.setText(bufferedReader.readLine());
        } catch (IOException e) {
            return;
        }
        try {
            portField.setText(bufferedReader.readLine());
            reader.close();
        } catch (IOException e) {
            return;
        }
    }

    }
