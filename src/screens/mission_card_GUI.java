package screens;

import javax.swing.*;
import java.awt.*;

/** מסך של כרטיס משימה
 *
 */
public class mission_card_GUI extends JDialog {

    private JLabel distanationplanet;
    private JLabel missioncode;
    private JLabel Oxygenunits, subject;
    private JLabel Instruments;
    private JPanel planet, Codemission, oxygen, Subjectt, instruments;

    public mission_card_GUI(String Subject, String Distanationplanet, int oxygenunits, String[] requiredinstruments, String taskcode) {
        setTitle("כרטיס משימה");
        setModal(true);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        Font labelFont = new Font("Italic", Font.ITALIC, 14);

        // כותרות
        missioncode = new JLabel("קוד משימה: " + taskcode);
        missioncode.setFont(labelFont);
        Codemission = new JPanel();
        Codemission.add(missioncode);

        Oxygenunits = new JLabel("יחידות חמצן: " + oxygenunits);
        Oxygenunits.setFont(labelFont);
        oxygen = new JPanel();
        oxygen.add(Oxygenunits);

        subject = new JLabel("נושא המשימה: " + Subject);
        subject.setFont(labelFont);
        Subjectt = new JPanel();
        Subjectt.add(subject);

        distanationplanet = new JLabel("כוכב יעד: " + Distanationplanet);
        distanationplanet.setFont(labelFont);
        planet = new JPanel();
        planet.add(distanationplanet);

        // כלים נדרשים
        instruments = new JPanel();
        instruments.setLayout(new BoxLayout(instruments, BoxLayout.Y_AXIS));
        instruments.setBorder(BorderFactory.createTitledBorder("כלים נדרשים"));
        for (String instr : requiredinstruments) {
            JLabel label = new JLabel("- " + instr);
            label.setFont(new Font("Italic", Font.ITALIC, 13));
            instruments.add(label);
        }


        add(planet, BorderLayout.EAST);
        add(Codemission, BorderLayout.CENTER);
        add(oxygen, BorderLayout.SOUTH);
        add(Subjectt, BorderLayout.WEST);
        add(instruments, BorderLayout.NORTH);
    }


}
