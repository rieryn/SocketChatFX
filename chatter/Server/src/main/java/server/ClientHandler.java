package server;

import utils.Input;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {

    public Socket client;
    public String username;
    private Server server;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public ClientHandler(Socket client, Server server) throws IOException {
        this.client = client;
        this.server = server;
        outputStream = new ObjectOutputStream(client.getOutputStream());
        inputStream = new ObjectInputStream(client.getInputStream());
    }
    public ObjectOutputStream getOOS(){
        return this.outputStream;
    }

    @Override
    public void run() {
        try {
            server.updateClientlist();
        } catch (IOException e) {
            System.out.println("failed update clientlist");
            e.printStackTrace();
        }
        while (client.isConnected()) {
            System.out.println("listening");
            Input input =null;
            try {
                input = (Input) inputStream.readObject();
                if (input != null) {
                    System.out.println("received msg");
                    switch (input.getType()) {
                        case TEXT:
                            server.pushInput(input);
                            System.out.println(client.getRemoteSocketAddress() + input.getString());
                            input = null;
                            break;
                        case FILE:
                            server.pushInput(input);
                            System.out.println("got a file from "+client.getRemoteSocketAddress());
                            break;
                    }
                }
                //means the socket is closed PROBABLY OR THERE'S SOME OTHER ERROR ಥ_ಥ
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(client+": connection closed");
                try {
                    server.updateClientlist(this);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                //terminate thread
                return;
            }
        }
    }
}