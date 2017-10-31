package hann.project.finamana.controllers;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hann.project.finamana.entities.Record;
import hann.project.finamana.entities.RecordTable;
import hann.project.finamana.utils.DBHelper;

/**
 * Created by DUCVINH on 4/10/2017.
 */

public class TableListManager {

    DBHelper helper;
    private int tableId; //Primary Key
    private String title;
    private RecordTable.MONTH month;
    private int year;
    private String username;//Foreign Key
    private double odd;
    private Date createdDate;

    public TableListManager(Context context){
        helper = new DBHelper(context);
    }



    public List<RecordTable> getAllRecordTable(String username){
       return helper.getAllRecordTableByUser(username);
    }

    public boolean addTable(RecordTable table) {
        return helper.addTable(table);

    }
    public boolean removeTableFromList(RecordTable table){
        return helper.removeTable(table) && helper.removeRecordByTableId(table);
    }

}
