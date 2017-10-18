package hann.project.finamana.entities;

import java.sql.Date;

/**
 * Created by DUCVINH on 3/10/2017.
 */


public class Record {
    enum CATEGORY{FOOD,BEVARAGE,FUEL,OUTFIT,SHOPPING,ENTERTAINMENT,SALARY,OTHER};

    //FIELDS

    private int recordId; //Primary Key
    private String description;
    private double revenue;
    private double expense;
    private Record.CATEGORY category;
    private int tableId;//Foreign Key
    private Date recordDate;

    public Record(Date recordDate, int recordId, String description, double revenue, double expense, int tableId) {
        this.recordDate = recordDate;
        this.recordId = recordId;
        this.description = description;
        this.revenue = revenue;
        this.expense = expense;
        this.tableId = tableId;
    }

    public Record() {
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
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
}
