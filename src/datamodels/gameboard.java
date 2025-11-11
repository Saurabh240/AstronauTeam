package datamodels;

import java.util.List;

//מחלקה לייצוג לוח משחק שנטען מהבסיס הנתונים
public class gameboard {

     private int id;

     private String name;

     private String level;


    public gameboard(int id, String name, String level) {
        this.id = id;
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



}
