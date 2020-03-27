package client;

import com.jfoenix.controls.JFXTextArea;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import util.Input;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.scene.control.*;

public class ClientViewController implements Initializable {

    private Client client;
    private ObservableList<String> chatLog;
    private String username;
    private String userImage;
    private String userStatus;
    public ObservableList<Message> messages;
    private String userStatusMessage;
    private MediaPlayer mediaPlayer;

    @FXML
    private Label bannerLabel;

    @FXML
    private MenuBar menubar;

    @FXML
    private Menu fileMenu;

    @FXML
    private MenuItem btAddFile;

    @FXML
    private MenuItem btAddImage;

    @FXML
    private MenuItem btSaveChat;

    @FXML
    private MenuItem btLogout;

    @FXML
    private Menu optionMenu;

    @FXML
    private MenuItem btProfile;

    @FXML
    private MenuItem btSettings;

    @FXML
    private Menu gameMenu;

    @FXML
    private MenuItem btBattleships;

    @FXML
    private Menu musicMenu;

    @FXML
    private MenuItem btPlayMusic;

    @FXML
    private MenuItem btMuteMusic;

    @FXML
    private MenuItem btAddMusic;

    @FXML
    private Menu toolMenu;

    @FXML
    private MenuItem btScheduler;

    @FXML
    private Menu aboutMenu;

    @FXML
    private MenuItem btAbout;

    @FXML
    private ListView<?> userView;

    @FXML
    private ListView<Message> chatView;

    @FXML
    private Button addButton;

    @FXML
    private JFXTextArea messageField;

    @FXML
    private Button btSend;

    @FXML
    private ListView<?> channelView;

    @FXML
    private ImageView userIcon;

    @FXML
    private Label usernameField;

    @FXML
    private ComboBox<String> statusMenu;

    @FXML
    private Circle statusIndicator;

    @FXML
    private TextField statusField;

    @FXML
    private Circle connectionIndicator;

    @FXML
    private Label portLabel;

    @FXML
    private Label hostnameLabel;

    @FXML
    void setBtPlayMusic(ActionEvent e){
        Media sound = new Media(Paths.get("jingle.mp3").toUri().toString());
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        mediaPlayer = new MediaPlayer(sound);

        mediaPlayer.play();
    }
    @FXML
    void setBtMuteMusic(ActionEvent e){
        mediaPlayer.stop();
    }

    @FXML
    void btSendFile(ActionEvent event) throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File selectedFile = chooser.showOpenDialog(addButton.getScene().getWindow());
        if(selectedFile!=null){
        client.sendFile(selectedFile);}
    }

    @FXML
    void btSendMessage(ActionEvent event) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        client.sendString(messageField.getText(), username, userImage, timeStamp);
        messageField.clear();
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        client.outputStream.close();
        client.inputStream.close();
        Platform.exit();
        System.exit(0);
    }
    @FXML
    void setStatus(ActionEvent event) {

    }
    public void setUsername(String username)
    {   this.username = username;
        usernameField.setText(username);
    }
    public void setClient(Client client){
        this.client = client;
    }
    public void setChatView(){
        chatView.setItems(client.messages);
    }
    public void receivedFile(Input input) throws IOException {
        FileChooser chooser = new FileChooser();
        String fileName = input.getFilename();
        //hacky way to display filename
        chooser.setInitialFileName(fileName+" ");
        chooser.setTitle("Received file, save as...");
        File selectedFile = chooser.showSaveDialog(addButton.getScene().getWindow());
        if(selectedFile!=null){
            client.readFileFromBytes(input, selectedFile);
        }
    }
    public void setUserIcon(String icon) throws FileNotFoundException {
        this.userImage = icon;
        Image image = new Image(new FileInputStream(userImage));
        userIcon.setImage(image);
    }
    public void setSocket(int port, String host){
        portLabel.setText(String.valueOf(port));
        hostnameLabel.setText(host);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username = "Default";
        userImage= System.getProperty("user.dir") + "/src/client/images/logo.png";
        userStatus = "Online";
        statusIndicator.setFill(Color.web("#a7ea52"));
        connectionIndicator.setFill(Color.web("#a7ea52"));
        statusMenu.getItems().addAll(
                "Online", "Away", "Busy"
                );

        statusMenu.setOnAction((e) -> {
            System.out.println(statusMenu.getSelectionModel().getSelectedItem());
        });
        chatView.setCellFactory(l -> new ListCell<Message>() {
            @Override
            public void updateItem(Message item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                    setStyle("-fx-background-color: #e6f0fb");

                } else {
                    setText(item.text);
                    setGraphic(item.image);
                    setStyle("-fx-background-color: #edddf6");
                }
            }
        });
    }
}
