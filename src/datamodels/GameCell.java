package datamodels;

public class GameCell  {

    private int row;

    private int col;

    private boolean hasplayer;

    private boolean hasObstacle;

    private boolean hasmission;

    private boolean hasoxygen;

    private boolean isPowerCell;

    private String currctplayer;

    public GameCell(int row, int col, boolean hasplayer, boolean hasObstacle, boolean hasmission, boolean hasoxygen, boolean isPowerCell) {
        this.row = row;
        this.col = col;
        this.hasplayer = hasplayer;
        this.hasObstacle = hasObstacle;
        this.hasmission = hasmission;
        this.hasoxygen = hasoxygen;
        this.isPowerCell = isPowerCell;
        this.currctplayer = null;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean isHasplayer() {
        return hasplayer;
    }

    public void setHasplayer(boolean hasplayer) {
        this.hasplayer = hasplayer;
    }

    public boolean isHasObstacle() {
        return hasObstacle;
    }

    public void setHasObstacle(boolean hasObstacle) {
        this.hasObstacle = hasObstacle;
    }

    public boolean isHasmission() {
        return hasmission;
    }

    public void setHasmission(boolean hasmission) {
        this.hasmission = hasmission;
    }

    public boolean isHasoxygen() {
        return hasoxygen;
    }

    public void setHasoxygen(boolean hasoxygen) {
        this.hasoxygen = hasoxygen;
    }

    public boolean isPowerCell() {
        return isPowerCell;
    }

    public void setPowerCell(boolean powerCell) {
        isPowerCell = powerCell;
    }

    public String getCurrctplayer() {
        return currctplayer;
    }

    public void setCurrctplayer(String currctplayer) {
        this.currctplayer = currctplayer;
    }
}
