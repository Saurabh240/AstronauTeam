package Gamelogic;

import database.Database;
import datamodels.GameCell;
import datamodels.User;
import screens.cell_status_GUi;


/**  מחלקה שמייצגת את לוח המחשק הלוגי שעובד עם לוח המשחק ה גרפי ומכילה בתוכה את כל הפונקציונליות הנדרשת של המשחק
 * ייצוג של לוח המשחק (כל המשבצות)
 * ניהול מהלכים של השחקנים
 */
public class logic_game_board {


    private boolean paused = false;

    private Object[][]gameCells;

    private Database database;

    private int rows;

    private int cols;

    private int currentcountofplayers;



    private final String[]imagefile;

    public logic_game_board( GameCell[][] gameCells, Database database, int rows, int cols, int currentcountofplayers,String[]imagefile) {
        this.gameCells = gameCells;
        this.database = database;
        this.rows = rows;
        this.cols = cols;
        this.currentcountofplayers = currentcountofplayers;
        this.imagefile = imagefile;
    }

    //פונקצייה ליצירת סבבב חדש של המשחק
    public void initgameboard(){

        database = new Database("root","271296Asd!");
        gameCells = new GameCell[rows][cols];


    }



    //פונקצייה  ליצירת תנועה של השחקן על גבי לוח המשחק
    public void makemove(int row, int col, User player){
        if(gameCells[row][col] == " - "){
            gameCells[row][col] = player;
        }
    }

    //פונקצייה לבדיקה מה מופיע במשבצת נוכחית שמספר התור והשורה שלה מיתקבלים כפרמטרים
    public void checkwhathaveincurrentcell(int row,int col){

        if(gameCells[row][col]== "High radiation"){
            cell_status_GUi status = new cell_status_GUi("שים לב קרינה ברמה גבוהה, לא ניתן לעבור או לנחות במשבצת זו ,פנה לדרכים אחרות",imagefile[0]);
            status.setVisible(true);

        }else if(gameCells[row][col]== "Midum radiation"){
            cell_status_GUi statusGUi = new cell_status_GUi("שים לב קרינה ברמה בינונית ניתן לעבור רק עם הכלים הבאים",imagefile[1]);
            statusGUi.setVisible(true);

        }else if(gameCells[row][col]== "Low radiation"){
            cell_status_GUi status_gUi = new cell_status_GUi("שים לב קרינה ברמה נמוכה ,ניתן לעבור דרך משבצת זו  אך לא לנחות אלייה אלה אם כן קיימת חליפת מגן",imagefile[3]);
            status_gUi.setVisible(true);

        }else if(gameCells[row][col] == "Oxygen"){
            cell_status_GUi statusGUi = new cell_status_GUi("הגעת למשבצת חמצן  ,נחסכה זריקה של יחידת חמצן על ידי השחקן בסיום התור ",imagefile[4]);

        }else if(gameCells[row][col]== "Power cell"){
            cell_status_GUi cellStatusGUi = new cell_status_GUi("הגעת למשבצת כוח , השימוש בה יהיה אפשרי  החל מהתור הבא",imagefile[5]);

        }
    }

    //פונקצייה להשהיית המשחק
    public void pause() { paused = true; }
    //פונקצייה לביטול השהיית המשחק
    public void resume() { paused = false; }

    //פונקצייה לבדיקה האם המשחק מושהה
    public boolean isPaused() { return paused; }

    //פונקצייה להחזרת משבצת במספר תור ובמספר שורה שמיתקבלים כפרמטרים
    public GameCell currentgamecell(int row, int col){
        return (GameCell) gameCells[row][col];
    }




}
