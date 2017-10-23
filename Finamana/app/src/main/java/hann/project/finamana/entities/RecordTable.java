package hann.project.finamana.entities;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by DUCVINH on 4/10/2017.
 */

public class RecordTable implements Serializable{
    public enum MONTH {JAN,FEB,MAR,APR,MAY,JUN,JUL,AUG,SEP,OCT,NOV,DEC}

   //FIELDS
    private int tableId; //Primary Key
    //private String title;
    //private RecordTable.MONTH month;
    private String month;
    private int year;
    private String username;//Foreign Key
    private double odd;
    private long createdDate;

    public RecordTable(int tableId, String month, int year, String username) {
        this.tableId = tableId;
        this.month = month;
        this.year = year;
        this.username = username;
    }
    public RecordTable( String month, int year, String username, long createdDate) {
        this.month = month;
        this.year = year;
        this.username = username;
        this.createdDate = createdDate;
    }
    public RecordTable(int tableId, String month, int year) {
        this.tableId = tableId;
        this.month = month;
        this.year = year;
    }
    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }

    public double getOdd() {
        return odd;
    }

    public void setOdd(double odd) {
        this.odd = odd;
    }

    public int getTableId() {
        return tableId;
    }

//    public void setTableId(int tableId) {
//        this.tableId = tableId;
//    }

//    public MONTH getMonth() {
//        return month;
//    }
//
//    public void setMonth(MONTH month) {
//        this.month = month;
//    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static MONTH parseMONTH(String monthString){
        switch (monthString){
            default:
                return null;
            case "January":
                return MONTH.JAN;
            case "February":
                return MONTH.FEB;
            case "March":
                return MONTH.MAR;
            case "April":
                return MONTH.APR;
            case "May":
                return MONTH.MAY;
            case "June":
                return MONTH.JUN;
            case "July":
                return MONTH.JUL;
            case "August":
                return MONTH.AUG;
            case "September":
                return MONTH.SEP;
            case "October":
                return MONTH.OCT;
            case "November":
                return MONTH.NOV;
            case "December":
                return MONTH.DEC;
        }
    }
}
