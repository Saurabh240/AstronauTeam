package Server;


import java.io.*;
import java.net.Socket;


//מחלקה לטיפול בלקוח
public class ClientHandler implements Runnable {
    private Socket socket;
    private ObjectInputStream inputFromClient; // for reading from socket
    private ObjectOutputStream outputToClient;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;


    public ClientHandler (Socket socket) throws IOException {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            outputToClient = new ObjectOutputStream(socket.getOutputStream());
            inputFromClient = new ObjectInputStream(socket.getInputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());


            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }



    }









}
