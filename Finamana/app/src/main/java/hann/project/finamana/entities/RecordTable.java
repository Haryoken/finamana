package hann.project.finamana.entities;

/**
 * Created by DUCVINH on 4/10/2017.
 */

public class RecordTable {
    enum MONTH {JAN,FEB,MAR,APR,MAY,JUN,JUL,AUG,SEP,OCT,NOV,DEC}

   //FIELDS
    private int tableId; //Primary Key
    private RecordTable.MONTH month;
    private int year;
    private String username;//Foreign Key
    private double odd;

    public RecordTable(int tableId, MONTH month, int year, String username) {
        this.tableId = tableId;
        this.month = month;
        this.year = year;
        this.username = username;
    }

    public double getOdd() {
        return odd;
    }

    public void setOdd(double odd) {
        this.odd = odd;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public MONTH getMonth() {
        return month;
    }

    public void setMonth(MONTH month) {
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
}
