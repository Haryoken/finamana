package hann.project.finamana.controllers;

import hann.project.finamana.entities.Record;

/**
 * Created by DUCVINH on 4/10/2017.
 */

public interface ManageTable {
    public void addNewRecordToTable();
    public boolean removeRecordFromTable(int tableId, int RecordId);
    public boolean updateRecord(Record record);
    public void viewRecordDetails();
    public void sortByCategory();
    public void sortByDate();
}
