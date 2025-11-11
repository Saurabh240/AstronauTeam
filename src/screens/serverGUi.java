package screens;

import Server.ClientHandler;
import Server.HandleAClient;
import database.Database;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javax.swing.*;
import java.awt.*;


public class serverGUi extends JFrame {
    private final JTextArea txtLog = new JTextArea();

    public serverGUi() {
        setTitle("Server GUI");
        setLayout(new BorderLayout());
        setSize(new Dimension(600, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        txtLog.setEditable(false);
        add(new JScrollPane(txtLog), BorderLayout.CENTER);
    }

    public void startServer() {
        appendLog("Server starting...");

        // התחברות DB פעם אחת
        Database.connect("root", "271296Asd!!!");

        // הפעלת שרת בחוט נפרד כדי לא לחסום את ה-GUI
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(9999)) {
                appendLog("Listening on port 9999");

                while (true) {
                    Socket socket = serverSocket.accept();
                    appendLog("Client connected: " + socket.getInetAddress());
                    HandleAClient client = new HandleAClient(socket);
                    client.start();
                }
            } catch (IOException e) {
                appendLog("Server error: " + e.getMessage());
            }
        }).start();
    }

    private void appendLog(String s) {
        SwingUtilities.invokeLater(() -> {
            txtLog.append(s + "\n");
            txtLog.setCaretPosition(txtLog.getDocument().getLength());
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            serverGUi gui = new serverGUi();
            gui.setVisible(true);
            gui.startServer();
        });
    }
}
