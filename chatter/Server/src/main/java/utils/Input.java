package utils;
import java.io.*;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.*;


public class Input implements Serializable {
    public inputType type;
    private String text;
    private ArrayList<String> userlist;
    private byte[] bytes;
    private String filename;
    private String username;
    private String userIcon;
    private String timestamp;

    //set the type of input
    public enum inputType {
        FILE, IMAGE, TEXT, USERLIST
    }
    public void setType(inputType type){
        this.type = type;
    }
    //getters
    public inputType getType() {
        return type;
    }
    public String getString() {
        return text;
    }
    public ArrayList<String> getUserlist(){
        return userlist;
    }
    public byte[] getByteArray(){
        return bytes;
    }
    public String getFilename(){
        return filename;
    }
    public String getUsername(){return username;}
    public String getTimestamp(){return timestamp;}
    public String getUserIcon(){ return userIcon;}

    //setters
    public void setFile(byte[] bytes, String filename){
        this.bytes=bytes;
        this.filename=filename;
    }

    public void setUserlist(ArrayList<String> userlist){
        userlist.add("userlist is null");
        if(userlist!=null){
        this.userlist = userlist;}
    }
    public void setString(String msg) {
        this.text = msg;
    }
    public void setUser(String username){this.username = username;}
    public void setUserIcon(String icon){this.userIcon = icon;}
    public void setTimestamp(String timestamp){this.timestamp=timestamp;}

}
