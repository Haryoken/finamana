package hann.project.finamana.controllers;

import android.content.Context;

import java.util.List;

import hann.project.finamana.entities.Record;
import hann.project.finamana.entities.RecordTable;
import hann.project.finamana.utils.DBHelper;

/**
 * Created by DUCVINH on 21/10/2017.
 */

public class TableManager implements ManageRecord {
    Context context;
    DBHelper helper;
    public TableManager(Context context){
        this.context = context;
        helper = new DBHelper(this.context);
    }


    //TABLE INFO MANAGEMENT
    public RecordTable findTableById(int tableId){
        return helper.findTableById(tableId);
    }

    public List<Record> getAllRecordsByTableId(int tableId){
        return helper.getAllRecordsByTableId(tableId);
    }
    public double[] calculateTotal(List<Record> recordList){
        double totalRevenue=0;
        double totalExpense=0;
        double totalOdd = 0;
        for(Record record:recordList){
            totalExpense += record.getExpense();
            totalRevenue += record.getRevenue();
        }
        totalOdd = totalRevenue - totalExpense;
        double[] result = {totalRevenue,totalExpense,totalOdd};
        return result;
    }

    @Override
    public boolean addRecordToTable(Record record) {
        return helper.addRecordToTable(record);
    }

    //RECORD MANAGEMENT
    @Override
    public boolean removeRecordFromTable(int tableId, int RecordId) {
        return false;
    }

    @Override
    public boolean updateRecord(Record record) {
        return false;
    }

    @Override
    public void viewRecordDetails() {

    }

    @Override
    public void sortByCategory() {

    }

    @Override
    public void sortByDate() {

    }
}
