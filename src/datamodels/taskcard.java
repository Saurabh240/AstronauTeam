package datamodels;

import java.io.Serializable;

public class taskcard implements Serializable {


    private String title;

    private String destanationplanet;

    private int  oxygenunits;

    private String[]reqieredinstruments;

    private String taskcode;


    public taskcard(String title, String destanationplanet, int oxygenunits, String[] reqieredinstruments, String taskcode) {

        this.title = title;
        this.destanationplanet = destanationplanet;
        this.oxygenunits = oxygenunits;
        this.reqieredinstruments = reqieredinstruments;
        this.taskcode = taskcode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return destanationplanet;
    }

    public void setDescription(String description) {
        this.destanationplanet = description;
    }

    public int getDestanationplanet() {
        return oxygenunits;
    }

    public void setDestanationplanet(String doxygenunits) {
        this.oxygenunits = oxygenunits;
    }

    public String[] getReqieredinstruments() {
        return reqieredinstruments;
    }

    public void setReqieredinstruments(String[] reqieredinstruments) {
        this.reqieredinstruments = reqieredinstruments;
    }

    public String getTaskcode() {
        return taskcode;
    }

    public void setTaskcode(String taskcode) {
        this.taskcode = taskcode;
    }
}
