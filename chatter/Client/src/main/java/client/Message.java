package client;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Message {
    public String text;
    public ImageView image;

    public Message(String text, String imageURL) throws FileNotFoundException {
        this.text = text;
        System.out.println("text added");

        Image image = new Image(new FileInputStream(imageURL));
        this.image = new ImageView(image);
        this.image.setFitHeight(20);
        this.image.setFitWidth(20);
        System.out.println("imageview added");

    }
}