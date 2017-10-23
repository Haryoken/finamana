package hann.project.finamana.controllers;

import hann.project.finamana.entities.Record;
import hann.project.finamana.entities.RecordTable;

/**
 * Created by DUCVINH on 4/10/2017.
 */

public interface ManageRecord {
    public boolean addRecordToTable(Record record);
    public boolean removeRecordFromTable(int tableId, int RecordId);
    public boolean updateRecord(Record record);
    public void viewRecordDetails();
    public void sortByCategory();
    public void sortByDate();
}
