package screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


/** מסך הצ'אטים של המשחק
 * בתוכו השחקנים יכולים להיתכתב,
 * נמצא בתוך המסך הראשי של המשחק
 */
public class Chat_GUI extends JPanel {


    private HintTextField inputtext;
    private JButton sendButton;
    private JTextArea textArea;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Chat_GUI(String serveraddress, int port,String user){

        setLayout(new BorderLayout());
        setSize(new Dimension(400,40));
        setVisible(true);
        textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(200,300));
        textArea.setFont(new Font("Italic",Font.ITALIC,16));;


        inputtext = new HintTextField("הכנס הודעה");
        inputtext.setPreferredSize(new Dimension(170,70));


        sendButton = new JButton("שלח");
        sendButton.setPreferredSize(new Dimension(30,20));
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendmessage(inputtext.getText().toString());
            }
        });

        add(textArea,BorderLayout.NORTH);

        add(inputtext,BorderLayout.CENTER);

        add(sendButton,BorderLayout.SOUTH);

        // connection to server
        try {
            socket = new Socket(serveraddress, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // receive messages
            new Thread(() -> {
                try {
                    String line;

                    while ((line = in.readLine()) != null) {
                        textArea.append(user+":"+line + "\n");
                    }
                } catch (IOException e) {
                    textArea.append("חיבור לשרת נותק.");
                }
            }).start();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "חיבור לשרת נכשל", "שגיאה", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    //function to send a message
    public void sendmessage(String text){
        String msg = inputtext.getText().trim();
        if (out != null) {
            out.println(text);
            inputtext.setText("");
        }
    }

}
