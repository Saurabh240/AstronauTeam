package datamodels;


import java.io.Serializable;

//מחלקה שמייצגת סבב שהושלם במשחק
public class Rotation implements Serializable {

    private int score;

    private int Playersremain;

    private int LostPlayears;

    private int Abandonedtasks;

    private int completedTasks;


    public Rotation(int score, int playersremain, int lostPlayears, int abandonedtasks, int completedTasks) {
        this.score = score;
        Playersremain = playersremain;
        LostPlayears = lostPlayears;
        Abandonedtasks = abandonedtasks;
        this.completedTasks = completedTasks;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getPlayersremain() {
        return Playersremain;
    }

    public void setPlayersremain(int playersremain) {
        Playersremain = playersremain;
    }

    public int getLostPlayears() {
        return LostPlayears;
    }

    public void setLostPlayears(int lostPlayears) {
        LostPlayears = lostPlayears;
    }

    public int getAbandonedtasks() {
        return Abandonedtasks;
    }

    public void setAbandonedtasks(int abandonedtasks) {
        Abandonedtasks = abandonedtasks;
    }

    public int getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(int completedTasks) {
        this.completedTasks = completedTasks;
    }
}
