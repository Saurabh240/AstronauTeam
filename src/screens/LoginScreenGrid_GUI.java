package screens;

import Server.Serverconnection;
import Gamelogic.UserBlocker;
import datamodels.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;


/** מסך ההיתחברות למשחק
 *
 */

public class LoginScreenGrid_GUI extends JFrame {

   private final Managment_system_main_screen_GUI managmentSystemMainScreenGui;
   private UserBlocker userBlocker;
   private HintTextField usernameField;
   private JButton loginButton,okbutton;
   private JLabel register;
   private HintPasswordField passwordField;
   private JDialog errormassage;
   private JLabel errorText;
   private ImageIcon imageIcon;
   private JPanel jPanel;
   private int count =0;
   private JLabel jLabel;

    public LoginScreenGrid_GUI(String title,Managment_system_main_screen_GUI managmentSystemMainScreenGui) {
        super(title);
        this.managmentSystemMainScreenGui = managmentSystemMainScreenGui;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        userBlocker = new UserBlocker();
        errormassage = new JDialog();
        errormassage.setLayout(new BorderLayout());
        imageIcon = new ImageIcon("C:\\Users\\shmuel\\Downloads\\לוגו.png");
        jPanel = new JPanel();
        okbutton = new JButton("אישור");
        okbutton.setPreferredSize(new Dimension(100,40));
        okbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.getWindowAncestor(okbutton).dispose();
            }
        });

        errorText = new JLabel("שם משתמש או סיסמא אינם תקינים");
        errorText.setFont(new Font("loginerrorfont",Font.PLAIN,20));


        errormassage.add(errorText,BorderLayout.CENTER);
        errormassage.add(okbutton,BorderLayout.SOUTH);
        errormassage.setPreferredSize(new Dimension(120,0));



        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;


        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("שם משתמש:"), gbc);



        gbc.gridx = 1;
        usernameField = new HintTextField("הכנס שם משתמש");
        usernameField.setPreferredSize(new Dimension(210,25));
        panel.add(usernameField, gbc);


        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("סיסמה:"), gbc);

        gbc.gridx = 1;
        passwordField = new HintPasswordField("הכנס סיסמא");
        passwordField.setPreferredSize(new Dimension(210,25));
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy =2;
        gbc.gridx = 1;
        register = new JLabel("אם אינך לא רשום ,לחץ כאן");
        register.setFont(new Font("font",Font.ITALIC,14));

        register.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                  register_screen_GUI register_screen = new register_screen_GUI(managmentSystemMainScreenGui);
                  register_screen.setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        panel.add(register,gbc);


        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginButton = new JButton("כניסה");
        loginButton.setPreferredSize(new Dimension(160,40));


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                boolean logedin = login_user(usernameField.getText().toString(),passwordField.getText().toString());

                if(logedin) {

                    User user = getUser(usernameField.getText().toString());

                    String role = user.getRole();

                    if(!userBlocker.canLogin(usernameField.getText().toString())){
                          JOptionPane.showMessageDialog(
                                  null,
                                  "משתמש עדיין במצב חסימה , אנא נסה שנית מאוחר יותר",
                                      "Error",
                                  JOptionPane.ERROR_MESSAGE
                          );
                    }else {

                        if (role.equals("Operator")) {
                            SwingUtilities.getWindowAncestor(loginButton).dispose();
                            Managment_system_main_screen_GUI managmentSystemMainScreenGui = new Managment_system_main_screen_GUI("127.0.0.1:3306",9999);
                            managmentSystemMainScreenGui.setVisible(true);

                        } else {

                            SwingUtilities.getWindowAncestor(loginButton).dispose();
                            managmentSystemMainScreenGui.addLog("התחבר בהצלחה"+usernameField.getText().toString()+"המישתמש:");

                            INstructionswindow_GUI iNstructionswindow = new INstructionswindow_GUI(usernameField.getText().toString());
                            iNstructionswindow.setVisible(true);
                            Main_Screen_GUI mainScreenGui = new Main_Screen_GUI(usernameField.getText().toString());


                        }
                    }
                }else if(!logedin &&count < 3) {
                      count++;
                    JOptionPane.showMessageDialog(LoginScreenGrid_GUI.this, "שם משתמש או סיסמא אינם נכונים ",
                            "פירטי התחברות שגויים", JOptionPane.ERROR_MESSAGE);

                }else {
                    Block_message_GUI blockMessageGui = new Block_message_GUI();
                    blockMessageGui.setVisible(true);
                    userBlocker.blockUser(usernameField.getText().toString(),600);

                }

            }
        });




        panel.add(loginButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
         jLabel = new JLabel(imageIcon);
        jPanel.setLayout(new CardLayout());

        jPanel.add(jLabel);




        panel.add(jPanel,gbc);


        panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        add(panel);
        setVisible(true);
    }


    //פונקציית היתחברות המישתמש
    public boolean login_user(String username, String password){
           if(username.isEmpty() || password.isEmpty()){
               JOptionPane.showMessageDialog(this,"אחד השדות רייק , מלא בבקשה את כל השדות  ");

           }
           boolean issucfull = false;

           try {

               String Username = username;
               String Password = password;

               Serverconnection serverconnection = Serverconnection.getInstance();
               serverconnection.connect();


               //שליחת הודעת התחברות לשרת
               serverconnection.sendMessage("LOGIN:"+username + "," + password);


               //קבלת תשובה מהשרת
               String reposne = serverconnection.readMessage();

               if(reposne.equals("OK")){
                   JOptionPane.showMessageDialog(this,"היתחברות הצליחה ");
                   issucfull = true;
               }else {
                   JOptionPane.showMessageDialog(this,"היתחברות נכשלה");

               }

           }catch (IOException e){
               e.getMessage();
           }

           return issucfull;
    }

    //פונקצייה להחזרת אובייקט המישתמש
    public User getUser(String username){

        User  user = null;
        try {

            Serverconnection serverconnection = Serverconnection.getInstance();
            serverconnection.connect();

            serverconnection.sendMessage("USERNAME:"+username);

            user = (User) serverconnection.readObject();
        }catch (IOException e){
            e.getMessage();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

         return user;
    }






}

