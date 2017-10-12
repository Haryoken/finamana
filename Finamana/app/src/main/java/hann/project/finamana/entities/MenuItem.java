package hann.project.finamana.entities;

/**
 * Created by DUCVINH on 13/10/2017.
 */

public class MenuItem {
    private String name;
    private String picID;


    public MenuItem(String name, String picID){
        this.name=name;
        this.picID=picID;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicID() {
        return picID;
    }

    public void setPicID(String picID) {
        this.picID = picID;
    }
}
