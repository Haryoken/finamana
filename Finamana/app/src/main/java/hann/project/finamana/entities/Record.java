package hann.project.finamana.entities;

import java.sql.Date;

/**
 * Created by DUCVINH on 3/10/2017.
 */


public class Record {
    public enum CATEGORY{FOOD,BEVERAGE,FUEL,OUTFIT,SHOPPING,ENTERTAINMENT,SALARY,OTHER,DEBT};

    //FIELDS

    private int recordId; //Primary Key
    private String description;
    private double revenue;
    private double expense;
    private CATEGORY category;
    private int tableId;//Foreign Key
    private long recordDate;

    public Record(long recordDate, int recordId, String description, int tableId, CATEGORY category) {
        this.recordDate = recordDate;
        this.recordId = recordId;
        this.description = description;
        this.revenue =0;
        this.expense =0;
        this.tableId = tableId;
        this.category= category;
    }
    public Record(long recordDate, String description, int tableId, CATEGORY category) {
        this.recordDate = recordDate;

        this.description = description;
        this.revenue =0;
        this.expense =0;
        this.tableId = tableId;
        this.category= category;
    }
    public Record() {
    }

    public long getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(long recordDate) {
        this.recordDate = recordDate;
    }

    public int getRecordId() {
        return recordId;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }

    public Record.CATEGORY getCategory() {
        return category;
    }

    public void setCategory(Record.CATEGORY category) {
        this.category = category;
    }
    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public static Record.CATEGORY parseCATEGORY(String monthString){
        switch (monthString){
            //FOOD,BEVERAGE,FUEL,OUTFIT,SHOPPING,ENTERTAINMENT,SALARY,OTHER
            default:
                return null;
            case "FOOD":
                return CATEGORY.FOOD;
            case "BEVERAGE":
                return CATEGORY.BEVERAGE;
            case "FUEL":
                return CATEGORY.FUEL;
            case "OUTFIT":
                return CATEGORY.OUTFIT;
            case "SHOPPING":
                return CATEGORY.SHOPPING;
            case "ENTERTAINMENT":
                return CATEGORY.ENTERTAINMENT;
            case "SALARY":
                return CATEGORY.SALARY;
            case "OTHER":
                return CATEGORY.OTHER;
            case "DEBT":
                return CATEGORY.DEBT;
        }
    }
}
