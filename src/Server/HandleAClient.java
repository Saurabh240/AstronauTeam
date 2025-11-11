package Server;

import database.Database;
import datamodels.User;
import screens.mission_card_GUI;

import java.io.*;
import java.net.Socket;
import java.util.Stack;

import java.io.*;
import java.net.Socket;
import java.util.Stack;



import database.Database;
import datamodels.User;
import screens.mission_card_GUI;

import java.io.*;
import java.net.Socket;
import java.util.Stack;

public class HandleAClient implements Runnable {
    private final Socket socket;
    private ObjectOutputStream objOut;
    private ObjectInputStream  objIn;
    private DataOutputStream   dataOut;
    private DataInputStream    dataIn;

    public HandleAClient(Socket socket) {
        this.socket = socket;
    }

    public void start() { new Thread(this).start(); }

    @Override
    public void run() {
        try {
            System.out.println("Handling client: " + socket.getInetAddress());

            // סדר חשוב: קודם ObjectOutputStream ואז ObjectInputStream
            objOut  = new ObjectOutputStream(socket.getOutputStream());
            objIn   = new ObjectInputStream (socket.getInputStream());
            dataOut = new DataOutputStream  (socket.getOutputStream());
            dataIn  = new DataInputStream   (socket.getInputStream());

            while (true) {
                String message = dataIn.readUTF();
                System.out.println("Received: " + message);

                if (message.startsWith("LOGIN:")) {
                    String[] parts = message.substring(6).split(",");
                    String username = parts[0];
                    String password = parts[1];
                    boolean ok = Database.login_user(username, password);
                    dataOut.writeUTF(ok ? "OK" : "FAIL");
                    dataOut.flush();

                } else if (message.startsWith("REGISTER:")) {
                    String[] p = message.substring(9).split(",");
                    String firstname = p[0];
                    String lastname  = p[1];
                    String username  = p[2];
                    String password  = p[3];
                    String role      = p[4];
                    User user = new User(username, password, firstname, lastname, role);
                    boolean ok = Database.registeruser(user);
                    dataOut.writeUTF(ok ? "OK" : "FAIL");
                    dataOut.flush();

                } else if (message.startsWith("USERNAME:")) {
                    String username = message.substring(9);
                    User user = Database.current(username);
                    objOut.writeObject(user);
                    objOut.flush();

                } else if (message.startsWith("RESETPASSWORD:")) {
                    String[] p = message.substring(14).split(",");
                    String username = p[0];
                    String newpass  = p[1];
                    boolean ok = Database.resetPassword(username, newpass);
                    dataOut.writeUTF(ok ? "OK" : "FAIL");
                    dataOut.flush();

                } else if (message.equals("START_GAME")) {
                    GameController.start();
                    dataOut.writeUTF("GAME_STARTED");
                    dataOut.flush();

                } else if (message.equals("PAUSE_ROTATION")) {
                    GameController.stop();
                    dataOut.writeUTF("Rotation_PAUSED");
                    dataOut.flush();

                } else if (message.equals("CANCEL_STOPED_ROTATION")) {
                    GameController.cancel();
                    dataOut.writeUTF("Rotation_stopped");
                    dataOut.flush();

                } else if (message.equals("Mission cards")) {
                    Stack<mission_card_GUI> cards = Database.displaycards();
                    objOut.writeObject(cards);
                    objOut.flush();

                } else if (message.equals("USERNAMES")) {
                    String[] usernames = Database.whoisconnectied();
                    objOut.writeObject(usernames);
                    objOut.flush();

                } else if (message.equalsIgnoreCase("PING")) {
                    dataOut.writeUTF("PONG");
                    dataOut.flush();

                } else if (message.equalsIgnoreCase("EXIT")) {
                    System.out.println("Client requested exit");
                    break;

                } else {
                    dataOut.writeUTF("UNKNOWN_COMMAND");
                    dataOut.flush();
                }
            }

        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
        } finally {
            try { socket.close(); } catch (IOException ignored) {}
        }
    }
}
