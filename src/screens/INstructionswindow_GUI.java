package screens;

import javax.swing.*;
import java.awt.*;

/* חלון ההוראות עצמם של המשחק

 */

public class INstructionswindow_GUI extends JFrame {

    JPanel panel;

    public INstructionswindow_GUI(String username){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        panel.setLayout(new BorderLayout());


       InstructionsDialog instructionsDialog = new InstructionsDialog(this,username);

       instructionsDialog.setVisible(true);

        panel.add(instructionsDialog,BorderLayout.CENTER);
        setLayout(new FlowLayout());
        add(panel);
        setVisible(true);
    }




}

