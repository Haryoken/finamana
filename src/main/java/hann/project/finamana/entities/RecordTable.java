package hann.project.finamana.entities;

import java.util.List;

/**
 * Created by DUCVINH on 4/10/2017.
 */

public class RecordTable {
    enum MONTH {JAN,FEB,MAR,APR,MAY,JUN,JUL,AUG,SEP,OCT,NOV,DEC}

   //FIELDS
    private int tableId;
    private RecordTable.MONTH month;
    private int year;
    private String username;//Foreign Key
}
