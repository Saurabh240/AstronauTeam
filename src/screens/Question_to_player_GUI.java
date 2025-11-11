package screens;

import javax.swing.*;
import java.awt.*;

/** מסך שמציג שאלה לשחקן בכל תור
 *
 */
public class Question_to_player_GUI extends JFrame {

    private JLabel textArea;
    private HintTextField Answer;
    private JButton send_Button;
    private JPanel QestionAnswer;

    public Question_to_player_GUI(String Qestion) {
        setTitle("שאלה לשחקן");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        QestionAnswer = new JPanel();
        QestionAnswer.setLayout(new BorderLayout());


        textArea = new JLabel(Qestion, SwingConstants.CENTER);
        textArea.setFont(new Font("Ariel", Font.ITALIC, 20));
        textArea.setPreferredSize(new Dimension(200, 50));
        QestionAnswer.add(textArea, BorderLayout.NORTH);


        Answer = new HintTextField("כתוב כאן את התשובה...");
        Answer.setFont(new Font("Ariel", Font.PLAIN, 14));
        Answer.setPreferredSize(new Dimension(250, 25));
        JPanel answerPanel = new JPanel();
        answerPanel.add(Answer);
        QestionAnswer.add(answerPanel, BorderLayout.CENTER);


        send_Button = new JButton("שליחה");
        send_Button.setPreferredSize(new Dimension(140, 30));
        send_Button.addActionListener(e -> {
            String ans = Answer.getText().trim();
            JOptionPane.showMessageDialog(this,  ans);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(send_Button);
        QestionAnswer.add(buttonPanel, BorderLayout.SOUTH);

        add(QestionAnswer, BorderLayout.CENTER);
    }



}
