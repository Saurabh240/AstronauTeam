package datamodels;

import java.io.Serializable;

public class possibleactions implements Serializable {

   private  String first_action;

   private String Second_action;

   private String Thrid_action;

   private String four_action;

    public possibleactions(String first_action, String second_action, String thrid_action, String four_action) {
        this.first_action = first_action;
        Second_action = second_action;
        Thrid_action = thrid_action;
        this.four_action = four_action;
    }

    public String getFirst_action() {
        return first_action;
    }

    public void setFirst_action(String first_action) {
        this.first_action = first_action;
    }

    public String getSecond_action() {
        return Second_action;
    }

    public void setSecond_action(String second_action) {
        Second_action = second_action;
    }

    public String getThrid_action() {
        return Thrid_action;
    }

    public void setThrid_action(String thrid_action) {
        Thrid_action = thrid_action;
    }

    public String getFour_action() {
        return four_action;
    }

    public void setFour_action(String four_action) {
        this.four_action = four_action;
    }
}
