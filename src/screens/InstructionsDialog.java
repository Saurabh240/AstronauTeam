package screens;

import javax.swing.*;
import java.awt.*;

/**
 *מסך  ההוראות של המשחק , עולה אחרי שמיתחברים בהצלחה למשחק
 */

public class InstructionsDialog extends JDialog {

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private int currentPage = 0;
    private final String[] pageNames = {"page1", "page2", "page3","page4","page5","page6","page7","page8","page9"};


    public InstructionsDialog(JFrame parent,String username) {
        super(parent, "הוראות המשחק", true);
        setSize(500, 350);
        setLocationRelativeTo(parent);


        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // הוספת עמודים
        cardPanel.add(createPage("ברוך הבא למשחק AstronauTeam", "מטרתכם במשחק היא למלא את כל המשימות המחקר במשחק .בכל משימה יש להגיע עם מספר מוגרל של צעדים לכוכב מרוחק ,למלא אחר המשימה, ולחזור בחזרה לחללית המחקר ביחד עם כל אנשי הצוות , אך שימו לך החמצן לכל המשימה מוגבל ויש אתגרים בדרך ,להלן הוראות המשחק:"
                ), "page1");
        cardPanel.add(createPage("בחירת משימה", "בוחרים משימה מרשימת המשימות, המשימה כוללת את כל פירטי המשימה ."), "page2");
        cardPanel.add(createPage("מיתקדמים לכוכב היעד", "1. כל חברי הצוות נידרשים להיתמקם במשבצת היציאה לחלל שנמצאת במרכז הלוח\n. זורקים ביחד את קוביית המשחק , המספר שייתקבל הוא מספר הצעדים הכולל עבור כל הצוות\n. ניתן לחלק את מספר הצעדים בין כל אנשי הצוות על פי שיקול דעתכם\n. אך שימו לב חלק מאנשי הצוות נושאים כלים שרגישים לקרינה אלקטרומגנטית ולכן הם לא יוכלו לעבור דרך משבצות מסוימות וייאלצו למצוא דרך חלופית\n ניתן להיתקדם בכל סבב מספר קטן יותר של צעדים שהוגרלו אך הם אינם נישמרים לסבב הבא\n כאשר אנשי הצוות הנמצאים באותו מקום הם יכולים להעביר ביניהם את יחידות החמצן, אפילו בנקודת היציאה"), "page3");
        cardPanel.add(createPage("\"הגעה לכוכב היעד וחזרה לחללית המחקר","לאחר הגעת אנשי הצוות לכוכב היעד נערך הניסוי המדעי\n בעת המתנה של איש צוות על גבי כוכב מרוחק הוא אינו מטיל את הקובייה ואינו מעביר יחידות חמצן\n הגעה של כלל אנשי הצוות לכוכב נחשבת לביצוע הניסוי ומילוי המשימה והיא מקנה ניקוד\n כעת נידרשים הצוות לחזור למשבצת מימנה יצאו למשימה\n בכל שלב במהלך ביצוע המשימה נידרשים אנשי הצוות לאמוד את הסיכויים להצליח ולחזור לחללית המחקר\n במידה והמצב אינו מאפשר זאת באפשרותם לבחור בחילוץ או בנטישת המשימה\n בסיום המשימה יש לקחת משימה חדשה מערימת המשימות"),"page4");
        cardPanel.add(createPage("ניקוד המשחק","המטרה במשחק היא לצבור את הניקוד הגבוהה ביותר שניתן והא ניצבר על ידי מילוי כל המשימות ולכל משימה יש ניקוד:\n מילוי משימה: 5 נקודות ,כל אנשי הצוות הגיעו לכוכב המרוחק,לפחות אחד מאנשי הצוות הגיע לחללית או שהיתבצע חילוץ והוא היסתיים בצלחה .\n  איבוד איש צוות : פחות נקודה אחת , אובדן איש צוות מתרחש כאשר הגיע תורו לביצוע מהלך היתקדמות איך אין לו חמצן\n בסיום כל משימה יש לרשום בדף מעקב ביצוע המשימות את הניקוד של המשימה"
        ),"page5");
         cardPanel.add(createPage("חילוץ", ""),"page6");
         cardPanel.add(createPage("נטישת משימה","בכל שלב יכולים אנשי הצוות לנטוש מיידית את המשימה ולחזור לחללית המחקר,הנטישה מבטלת את האפשרות למלא אחר המשימה שוב, המשימה נכשלת ולכן לא ייתקבל ניקוד עבור המשימה גם אם כל אנשי הצוות הגיעו לכוכב המרוחק"),"page7");
         cardPanel.add(createPage("הבדלים בין חילוץ לנטישה","חילוץ מוריד ניקוד הנוכחית כתוצאה מאובדן אנשי צוות אך משאיר סיכוי להצלחת המשימה  והעלאה של הניקוד הכללי\n נטישת המשימה אינה מסכנת את אנשי הצוות את מבטלת את המשימה(וצבירת הניקוד עבורה) וכך מונעת את האפשרות למלא אחריה שוב"),"page8");
         cardPanel.add(createPage("מהלכים במשחק","התקדמות אנשי הצוות מוגבלת כתוצאה מרמות קרינה שונות המסכנות את אנשי הצוות ועלולה לפגוע בכלי עבודה בהם מעגלים אלקטרוניים רגישים , להלן הגבלות קרינה וסמלים נוספים"),"page9");


        //navigation buttons
        JButton nextBtn = new JButton("הבא");
        JButton prevBtn = new JButton("חזור");


        nextBtn.addActionListener(e -> {
            if (currentPage < pageNames.length - 1) {
                currentPage++;
                cardLayout.show(cardPanel, pageNames[currentPage]);
            }else {
                 Main_Screen_GUI mainScreen = new Main_Screen_GUI(username);
                 mainScreen.setVisible(true);
                 this.dispose();


            }
        });

        prevBtn.addActionListener(e -> {
            if (currentPage > 0) {
                currentPage--;
                cardLayout.show(cardPanel, pageNames[currentPage]);
            }
        });



        JPanel navPanel = new JPanel();
        navPanel.add(prevBtn);
        navPanel.add(nextBtn);


        getContentPane().add(cardPanel, BorderLayout.CENTER);
        getContentPane().add(navPanel, BorderLayout.SOUTH);
    }

    //create page of the instructions
    private JPanel createPage(String title, String text) {
        JPanel page = new JPanel(new BorderLayout());
        page.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JTextArea textArea = new JTextArea(text);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));

        page.add(titleLabel, BorderLayout.NORTH);
        page.add(new JScrollPane(textArea), BorderLayout.CENTER);
        return page;
    }
}
