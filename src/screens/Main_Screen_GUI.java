package screens;

import Server.GameController;
import Server.Serverconnection;
import datamodels.User;
import datamodels.gameboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Stack;

/** המסך הראשי של המשחק שמכיל בתוכו את המסך של לוח המשחק, מסך הצ'אטים ,נקודות ...
 *
 */

public class Main_Screen_GUI extends JFrame {

    JPanel mainpanel, instructionspanel, missions,chat;
    JButton instructions_button, choosemission, cube;
    Stack<mission_card_GUI>missionCards;
    Chat_GUI chatGui;
    String user;
    User currentuser;
   Managment_system_main_screen_GUI managment_system;
    JLabel score;

    String scorenum ="0";
    serverGUi serverGUi;
    public Main_Screen_GUI(String username) {
        super("מסך ראשי");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        missionCards = GameController.missionCards();
        score = new JLabel("ניקוד:"+scorenum);
        score.setFont(new Font("Ariel",Font.ITALIC,16));


        cube = new JButton("הטלת קובייה");
        cube.setSize(new Dimension(100,100));


        cube.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(cube, steps());
            }
        });


        //הגדרת אזור הצ'ט עבור שחקן נוכחי
        currentuser = getcurrentuser(username);
        user = currentuser.getFirstname();
        chatGui = new Chat_GUI("localhost",9999,user);

        //הגדרת כפתור ההוראות שבלחיצה עליו יקפצוץ חלון ההוראות של המשחק
        instructionspanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        instructionspanel.setPreferredSize(new Dimension(180, 600));
        instructions_button = new JButton("הוראות");
        instructions_button.setPreferredSize(new Dimension(150, 40));
        instructions_button.addActionListener(e -> {
            INstructionswindow_GUI instructionsWindow = new INstructionswindow_GUI(username);
            instructionsWindow.setVisible(true);
        });
        instructionspanel.add(instructions_button);


        //הגדרת אזור ערימת כרטיסי המשימה
        missions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        choosemission = new JButton("בחר משימה");
        choosemission.setPreferredSize(new Dimension(200, 40));
        choosemission.addActionListener(e -> {
          mission_card_GUI mission_card =   choosemission(missionCards);
          mission_card.setVisible(true);
        });
        missions.setPreferredSize(new Dimension(800, 70));
        missions.add(choosemission);


        mainpanel = new JPanel(new BorderLayout());

        try {

            Serverconnection serverconnection = Serverconnection.getInstance();
            serverconnection.connect();


        }catch (IOException e){
            e.getMessage();
        }


        //  הגדרת לוח המשחק רנדומלי מהשרת

        gameboard  gameboard =  managment_system.StartnewGame();


        switch (gameboard.getName()){

            case  "flower":
              this.getContentPane().add(new FlowerHexGame.DrawingPanel(), BorderLayout.CENTER);
            case  "Triangle":
              this.getContentPane().add(new TriangleHexGame.DrawingPanel(), BorderLayout.CENTER);
            case  "circle":
                this.getContentPane().add(new CircleHexGame.DrawingPanel(), BorderLayout.CENTER);
            case  "hexagonal":
                this.getContentPane().add(new HexagonHexGame.DrawingPanel(), BorderLayout.CENTER);
            case "rhombus":
                this.getContentPane().add(new DiamondHexGame.DrawingPanel(), BorderLayout.CENTER);
        }




        chat = new JPanel();
        chat.add(chatGui);


        //הגדרת מיקום כל הרכיבים במסך הראשי
        add(instructionspanel, BorderLayout.WEST);
        add(mainpanel, BorderLayout.CENTER);
        add(missions, BorderLayout.PAGE_END);
        add(chat,BorderLayout.LINE_END);
        add(score,BorderLayout.NORTH);
        add(cube, BorderLayout.EAST);

        setVisible(true);
    }

    //פונקצייה להוצאת כרטיס משימה מערימת כרטיסי המשימה
    public mission_card_GUI choosemission(Stack<mission_card_GUI>missionCards){
        return missionCards.pop();
    }




    //החזרת המשתמש הנוכחי
    public User getcurrentuser(String username){

        User  user1 = null;
        try {

            Serverconnection serverconnection = Serverconnection.getInstance();
            serverconnection.connect();

            serverconnection.sendMessage("USERNAME:"+username);

            user1 = (User) serverconnection.readObject();
        }catch (IOException e){
            e.getMessage();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return user1;
    }

    public int steps(){
        int Steps =(int) (Math.random() * 6) +1;

         return Steps;
    }






}