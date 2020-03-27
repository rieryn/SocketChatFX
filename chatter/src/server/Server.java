package server;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import util.Input;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

public class Server {

    private final int port = 8080;
    private ServerSocket socket;
    private ArrayList<ClientHandler> clientList;
    private ObjectOutputStream outputStream;

    public Server(){
        try {
            socket = new ServerSocket(port);
            socket.setReuseAddress(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String serverAddress() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    public void startServer() throws IOException {
        System.out.println("chat server started on "+ serverAddress());
        System.out.println("listening on port "+port);
        clientList = new ArrayList<>();
        //continuously listen for client connections
        while(true)
        {
            Socket client = socket.accept();
            System.out.println("New client: " + client.getRemoteSocketAddress());
            //start thread for new client
            ClientHandler handler = new ClientHandler(client,this);
            clientList.add(handler);
            Thread t = new Thread(handler);
            t.start();
            updateClientlist();
            System.out.println("Total clients: " + clientList.size());

        }
    }
    public void updateClientlist(ClientHandler handler) throws IOException {
        clientList.remove(handler);
        //pushUserList();
    }
    public void updateClientlist() throws IOException {
        //pushUserList();
    }

        public void pushUserList() throws IOException {
        System.out.println("pushing userlist");
        ArrayList<String> userList = new ArrayList<String>();
        userList.add("new test");
        Iterator<ClientHandler> clientlist=clientList.iterator();
        while(clientlist.hasNext()){
            ClientHandler handler = clientlist.next();
            userList.add("test");
            System.out.println("test");
        }
        if (userList!=null){
            Input input = new Input();
            input.setType(Input.inputType.USERLIST);
            input.setUserlist(userList);
            pushInput(input);}
        else {
            System.out.print("userlist is null");
        }
    }

    //handler for output stream CALL THIS FOR EVERYTHING
    public synchronized void pushInput(Input input) throws IOException {
        Iterator<ClientHandler> clientlist=clientList.iterator();
        while(clientlist.hasNext())
        {
            ClientHandler handler = clientlist.next();
            if( !handler.client.isClosed() )
            {
                outputStream = handler.getOOS();
                System.out.println("pushing message");
                outputStream.writeObject(input);
                outputStream.reset();
                outputStream.flush();
                System.out.println("pushed message");
            }
        }
    }

     public static void main(String[] args) throws IOException {
        new Server().startServer();
    }
}