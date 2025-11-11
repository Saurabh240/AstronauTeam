package screens;

import Server.Serverconnection;
import datamodels.gameboard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalTime;

/** מסך מערכת הניהול של המשחק, אלייה יכול להיתחבר המפעיל של המשחק , ולנהל מישם את המישחק
 *
 */


public class Managment_system_main_screen_GUI extends JFrame {
    private JPanel mainScreen, buttons,players,chat;
    private JButton jButton1,jButton2,jButton3,jButton4,sendbutton;
    private JTable connectedplayers;
    private JTextField message;
     static JTextArea chatmessages,logsmrssages;
    private JScrollPane logs;
    private PrintWriter out;
    private Socket chatserversocket;
    private BufferedReader in;
    private boolean paused = false;




    public Managment_system_main_screen_GUI(String serveraddress, int port){
        setSize(new Dimension(800,800));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        //איפוס כל הרכיבים
        chat = new JPanel();
        mainScreen = new JPanel();
        buttons = new JPanel();
        chatmessages = new JTextArea();
        message = new JTextField();
        sendbutton = new JButton("שלח");
        sendbutton.setSize(new Dimension(200,50));
        mainScreen = new JPanel();
        logs = new JScrollPane();
        logsmrssages = new JTextArea(15,40);
        jButton1 = new JButton("הפעלת משחק חדש");
        jButton2 = new JButton("השהיית משחק נוכחי");
        jButton3 = new JButton("איפוס סיסמא לשחקן");
        jButton4 = new JButton("ביטול השהייה");
        players = new JPanel();
        String[] columns = {"ID", "שם משתמש", "סטטוס", "תור נוכחי"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        connectedplayers = new JTable(tableModel);




        buttons.setLayout(new FlowLayout());

        //הגדרת אזור הצ'אטים
        chat.setLayout(new BorderLayout());
        chat.add(new JScrollPane(chatmessages), BorderLayout.CENTER);

        JPanel chatInput = new JPanel(new BorderLayout());
        chatInput.add(message, BorderLayout.CENTER);
        chatInput.add(sendbutton, BorderLayout.EAST);
        chat.add(chatInput, BorderLayout.SOUTH);

        sendbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendmessage(message.getText().toString());
            }
        });

        //הגדדרת המסך המרכזי במערכת
        mainScreen.setLayout(new BorderLayout());
        mainScreen.setSize(new Dimension(200,200));
        mainScreen.setVisible(true);

        //הגדרת רשימת שחקנים מחוברים
        connectedplayers.setSize(200,80);



        //הגדרת אזור הלוגים
        logsmrssages.setLineWrap(true);
        logsmrssages.setWrapStyleWord(true);
        logsmrssages.setEditable(false);

        logs = new JScrollPane(logsmrssages);
        logs.setPreferredSize(new Dimension(200, 0));


        //הגדרת כפתורי המסך

        jButton1.setSize(100,80);
        jButton1.setVisible(true);
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartnewGame();
                addLog("הופעל משחק חדש ");
            }
        });

        jButton2.setSize(100,80);
        jButton2.setVisible(true);
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                stopRotaion();
                addLog("משחק נוכחי נעצר");
            }
        });

        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResetPassword resetPassword = new ResetPassword(new Managment_system_main_screen_GUI("127.0.0.1",9999));
                resetPassword.setVisible(true);

            }
        });

        jButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CancelStopedRotation();
                addLog("משחק נוכחי הוחזר לפעילות");
            }
        });

        buttons.add(jButton1);
        buttons.add(jButton2);
        buttons.add(jButton3);
        buttons.add(jButton4);


        add(chat, BorderLayout.SOUTH);
        add(new JScrollPane(logs), BorderLayout.EAST);
        add(new JScrollPane(connectedplayers), BorderLayout.CENTER);
        add(buttons, BorderLayout.NORTH);







        // connection to chat server
        try {
            chatserversocket = new Socket(serveraddress, port);
            out = new PrintWriter(chatserversocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(chatserversocket.getInputStream()));

            // receive messages
            new Thread(() -> {
                try {
                    String line;

                    while ((line = in.readLine()) != null) {
                        chatmessages.append(":מפעיל"+":"+line + "\n");
                    }
                } catch (IOException e) {
                    chatmessages.append("חיבור לשרת נותק.");
                }
            }).start();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "חיבור לשרת נכשל", "שגיאה", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }


     //פונקציה לשליחת הודעה בצ'אט
    public void sendmessage(String text){
        String msg = message.getText().trim();
        if (out != null) {
            out.println(text);
            message.setText("");
        }
    }
    // פונקציה להוספת הודעה ללוג
    public void addLog(String message) {
        SwingUtilities.invokeLater(() -> {
            logsmrssages.append("[" + LocalTime.now().withNano(0) + "] " + message + "\n");
            logsmrssages.setCaretPosition(logsmrssages.getDocument().getLength());
        });
    }

    //פונקצייה להשהיית המשחק
    public  void stopRotaion(){
       paused = true;

        try {
            // מקבל מופע יחיד של ServerConnection
            Serverconnection conn = Serverconnection.getInstance();
            conn.connect();

            // שולח פקודה לשרת להפעיל משחק
            conn.sendMessage("PAUSE_ROTATION");

            // מחכה לתגובה מהשרת
            String response = conn.readMessage();

            if (response.equals("Rotation_PAUSED")) {
                JOptionPane.showMessageDialog(this, "הסבב נעצר  על ידי המפעיל");
            } else {
                System.err.println("שגיאה, עצירת  הסבב על ידי המפעיל נכשלה");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error communicating with server!");
        }
    }

    //פונקצייה להחזרת המשחק

    public void CancelStopedRotation(){
        paused = false;


        try {
            // מקבל מופע יחיד של ServerConnection
            Serverconnection conn = Serverconnection.getInstance();
            conn.connect();

            // שולח פקודה לשרת להפעיל משחק
            conn.sendMessage("CANCEL_STOPED_ROTATION");

            // מחכה לתגובה מהשרת
            String response = conn.readMessage();

            if (response.equals("Rotation_stopped")) {
                JOptionPane.showMessageDialog(this, "הסבב נעצר ובוטל על ידי המפעיל");
            } else {
                System.err.println("שגיאה, עצירת וביטול הסבב על ידי המפעיל נכשלה");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error communicating with server!");
        }

    }


    //פונקצייה להפעלת משחק חדש
    public gameboard StartnewGame(){

       return   gameboardwithindex("START_GAME");

    }
    //מחזיר לוח משחק עם אינקס שמיתקבל כפרמטר
    public gameboard gameboardwithindex(String msg) {

        gameboard gameboard1 = null;
        try {
            Serverconnection serverconnection = Serverconnection.getInstance();
            serverconnection.connect();

            serverconnection.sendMessage(msg);


            gameboard1 = (gameboard) serverconnection.readObject();


        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error communicating with server!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return gameboard1;
    }

   public static void main(String[]args){
        Managment_system_main_screen_GUI managmentSystemMainScreenGui = new Managment_system_main_screen_GUI("127.0.0.1",9999);
        managmentSystemMainScreenGui.setVisible(true);
   }

   //מחזיר מספר רנדומלי בין  0 ל 30
    public int Randomnumber(){
        return (int) (Math.random()*30)+1;
    }


}
