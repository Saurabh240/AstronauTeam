package Server;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

import java.io.*;
import java.net.Socket;



import java.io.*;
import java.net.Socket;

public class Serverconnection {
    private static Serverconnection instance;
    private Socket socket;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;
    private ObjectInputStream objIn;
    private ObjectOutputStream objOut;

    private final String SERVER_IP = "127.0.0.1";
    private final int SERVER_PORT = 9999;

    private Serverconnection() {}

    public static synchronized Serverconnection getInstance() {
        if (instance == null) instance = new Serverconnection();
        return instance;
    }

    public synchronized void connect() throws IOException {
        if (socket != null && socket.isConnected() && !socket.isClosed()) return;

//        socket = new Socket(SERVER_IP, SERVER_PORT);

        socket = new Socket();
        socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT), 3000); // 3s timeout

        // סדר חשוב!
//        objOut = new ObjectOutputStream(socket.getOutputStream());
//        objIn  = new ObjectInputStream (socket.getInputStream());
        dataOut = new DataOutputStream(socket.getOutputStream());
        dataIn  = new DataInputStream (socket.getInputStream());

        System.out.println("Connected to server: " + SERVER_IP + ":" + SERVER_PORT);
    }

    public void sendMessage(String msg) throws IOException {
        dataOut.writeUTF(msg);
        dataOut.flush();
    }

    public String readMessage() throws IOException {
        return dataIn.readUTF();
    }

    public void sendObject(Object obj) throws IOException {
        objOut.writeObject(obj);
        objOut.flush();
    }

    public Object readObject() throws IOException, ClassNotFoundException {
        return objIn.readObject();
    }

    public synchronized void disconnect() {
        try { if (socket != null) socket.close(); } catch (IOException ignored) {}
    }
}
