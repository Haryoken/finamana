package hann.project.finamana.controllers;

import android.content.Context;
import android.widget.ArrayAdapter;

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
    public boolean updateTableOdd(RecordTable table){
        return helper.updateTableOdd(table);
    }
    public int getCategoryPosition(Record.CATEGORY category, ArrayAdapter<Record.CATEGORY> adapter){

        for(int i = 0; i < adapter.getCount(); i++){
            if(category == adapter.getItem(i)){
                return i;
            }
        }
        return -1;
    }
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
    public boolean removeRecordFromTable(Record record) {
        return helper.removeRecordFromTable(record);
    }

    @Override
    public boolean updateRecord(Record record) {
        return helper.updateRecord(record);
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
