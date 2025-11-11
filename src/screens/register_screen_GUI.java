package screens;

import Server.Serverconnection;
import datamodels.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


/** מסך ההרשמה למשחק ,דרכו שחקן יכול להירשם למשחק
 *
 *
 *
 */

public class register_screen_GUI extends JFrame {

     Managment_system_main_screen_GUI mainGui;
    private JPanel panel;
    private HintTextField firstname, lastname, username ;
    private HintPasswordField password, confirmpassword;
    private JComboBox role;
    private JButton registerButton,ok;
    private JDialog error;
    private TextArea textArea;

    public register_screen_GUI(Managment_system_main_screen_GUI mainGui) {
        super("הרשמה");
        this.mainGui  = mainGui;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        error = new JDialog();
        panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        textArea = new TextArea("ארעה שגיאה במילוי הפרטים אנא בדוק את הפרטים ונסה שוב ");
        textArea.setFont(new Font("errorfont",Font.PLAIN,20));
        textArea.setPreferredSize(new Dimension(120,0));

        ok = new JButton("אישור");
        ok.setPreferredSize(new Dimension(100,40));
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.getWindowAncestor(ok).dispose();
            }
        });
         error.setLayout(new BorderLayout());
        error.add(textArea,BorderLayout.CENTER);
        error.add(ok,BorderLayout.SOUTH);





        firstname = createHintField("שם פרטי");
        lastname = createHintField("שם משפחה");
        username = createHintField("שם משתמש");
        password = createPasswordHintField("סיסמה");
        confirmpassword = createPasswordHintField("אימות סיסמה");


        role = new JComboBox();
        role.setSize(new Dimension(30,10));
        role.addItem("שחקן");
        role.addItem("מפעיל");


        registerButton = new JButton("הרשמה");
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


              boolean registried =   regiisteruser(firstname.getText().toString(),lastname.getText().toString(),username.getText().toString(),password.getText().toString(),role.getSelectedItem().toString());

                if(registried){
                    SwingUtilities.getWindowAncestor(registerButton).dispose();
                     mainGui.addLog("נירשם בהצלחה"+username+"המישתמש:");

                }else {
                    error.setVisible(true);
                }
            }
        });


        // הוספת רכיבים
        panel.add(firstname);
        panel.add(Box.createVerticalStrut(10));
        panel.add(lastname);
        panel.add(Box.createVerticalStrut(10));
        panel.add(username);
        panel.add(Box.createVerticalStrut(10));
        panel.add(password);
        panel.add(Box.createVerticalStrut(10));
        panel.add(confirmpassword);
        panel.add(Box.createVerticalStrut(10));
        panel.add(role);
        panel.add(Box.createVerticalStrut(10));
        panel.add(registerButton);


        add(panel);
        setVisible(true);
    }

    //פונקצייה ליצירת שדה לבחירת שם פרטי, שם משפחה, שם משתמש
    private HintTextField createHintField(String hint) {
        HintTextField field = new HintTextField(hint);
        field.setMaximumSize(new Dimension(250, 30)); // גובה קבוע, רוחב מתרחב
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        field.setAlignmentY(Component.CENTER_ALIGNMENT);// ממרכז בתוך BoxLayout
        return field;
    }

    //פונקצייה ליצירת שדה לבחירת סיסמא
    private HintPasswordField createPasswordHintField(String hint){
        HintPasswordField field = new HintPasswordField(hint);
        field.setMaximumSize(new Dimension(250,30));
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        field.setAlignmentY(Component.CENTER_ALIGNMENT);

        return field;
    }


    //רישום משתמש חדש למערכת של המשחק
    public boolean regiisteruser(String firstname, String LastName, String username,String password,String role){
             boolean isworking = false;


          if(firstname.isEmpty() || LastName.isEmpty() || username.isEmpty() || password.isEmpty()){
              JOptionPane.showMessageDialog(this, "אחד השדות נותר ריק ,נא למלא בבקשה את כל השדות",
                      "שגיאה", JOptionPane.ERROR_MESSAGE);
              return false;
          }
          User user = new User(username,password,firstname,LastName,role);


          try {

              Serverconnection serverconnection = Serverconnection.getInstance();
              serverconnection.connect();


              serverconnection.sendMessage("REGISTER:"+firstname+","+LastName+","+username+","+password+","+role);

              String mesaagefromserver  = serverconnection.readMessage();

              if(mesaagefromserver.equals("OK")){
                  isworking = true;
              }

          }catch (IOException e){
              e.getMessage();
          }
            return isworking;
    }



}
