package screens;

import Server.Serverconnection;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ResetPassword extends JDialog {

    private final Managment_system_main_screen_GUI managmentSystemMainScreenGui;
    private JPanel resetPanel;
    private JButton resetButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel titleLabel;

    public ResetPassword(Managment_system_main_screen_GUI managmentSystemMainScreenGui) {
        setTitle("איפוס סיסמא");
        this.managmentSystemMainScreenGui = managmentSystemMainScreenGui;
        setSize(new Dimension(400, 250));
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);

        // חיבור למסד נתונים


        // רכיבים
        resetPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        titleLabel = new JLabel("איפוס סיסמא לשחקן", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        usernameField = new JTextField();
        usernameField.setText("הכנס את שם המשתמש");

        passwordField = new JPasswordField();
        passwordField.setText("רשום סיסמא חדשה");

        resetButton = new JButton("איפוס הסיסמא");




        // פעולה על הכפתור
        resetButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String newPassword = new String(passwordField.getPassword());

            if (username.isEmpty() || newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "יש למלא את כל השדות במלואם!",
                        "שגיאה", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean isWorking = Resetpassword(username, newPassword);

            if (isWorking) {
                JOptionPane.showMessageDialog(this, "הסיסמא אופסה בהצלחה!",
                        "הצלחה", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "שם המשתמש לא נמצא!",
                        "שגיאה", JOptionPane.ERROR_MESSAGE);
            }
        });

        // הוספת רכיבים
        resetPanel.add(titleLabel);
        resetPanel.add(usernameField);
        resetPanel.add(passwordField);
        resetPanel.add(resetButton);

        add(resetPanel, BorderLayout.CENTER);
    }

    //פונקצייה לאיפוס סיסמא, מקבלת שם משתמש והסיסמא החדשה , ומעדכנת את הסיסמא החדשה בשרת עבור המשיתמש ששם המישתמש שלו זה  username
    public boolean Resetpassword(String username,String newpasword){

        boolean isworkied = false;

        try {


            Serverconnection serverconnection = Serverconnection.getInstance();
            serverconnection.connect();


            serverconnection.sendMessage("RESETPASSWORD"+username+","+newpasword);

            String Replay = serverconnection.readMessage();

            if(Replay.equals("OK")){
                isworkied = true;
            }else if(Replay.equals("FAIL")){
                isworkied = false;
            }



        }catch (IOException e){e.getMessage();}


        return isworkied;
    }




}
