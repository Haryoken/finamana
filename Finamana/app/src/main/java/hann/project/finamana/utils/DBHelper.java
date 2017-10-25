package hann.project.finamana.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import hann.project.finamana.entities.Record;
import hann.project.finamana.entities.RecordTable;
import hann.project.finamana.entities.User;

/**
 * Created by DUCVINH on 5/10/2017.
 */

public class DBHelper extends SQLiteOpenHelper{
    private static final String TABLE_USER="User";

    private static final String TABLE_RECORD="Record";
    public static final String RECORD_COLLUM_TABLEID="tableId";
    public static final String RECORD_COLLUM_RECORDID="recordId";
    public static final String RECORD_COLLUM_DESCRIPTION="description";
    public static final String RECORD_COLLUM_REVENUE="revenue";
    public static final String RECORD_COLLUM_EXPENSE="expense";
    public static final String RECORD_COLLUM_CATEGORY="category";
    public static final String RECORD_COLLUM_RECORDDATE="recordDate";


    private static final String TABLE_RECORDTABLE="RecordTable";
    public static final String RECORDTABLE_COLLUM_TABLEID="tableId";
    public static final String RECORDTABLE_COLLUM_MONTH="month";
    public static final String RECORDTABLE_COLLUM_YEAR="year";
    public static final String RECORDTABLE_COLLUM_ODD="odd";
    public static final String RECORDTABLE_COLLUM_CREATEDDATE="createdDate";
    public static final String RECORDTABLE_COLLUM_USERNAME="username";

    private static final String DB_NAME = "FinamanaDB.db";
    private static final String DB_PATH ="/data/data/hann.project.finamana/databases/";

    private Context mycontext;
    public SQLiteDatabase myDataBase;
    public static final int DB_VERISON = 5;


    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERISON);

        this.mycontext = context;

        boolean dbexist = checkdatabase();
        if (dbexist) {
            Log.d("DBHelper", "Database file exist, open");

            opendatabase();
        } else {
            Log.d("DBHelper", "Database file not exist, create new one");
            try {
                createdatabase();
            }catch (IOException e){
                Log.e("DBHelper","IOException: "+e.getMessage());
            }
        }
    }


    //USER METHOD:


    //4. TABLE MANAGEMENT:
    public boolean updateRecord(Record record){
        ContentValues values = new ContentValues();
        values.put("description",record.getDescription());
        values.put("revenue",record.getRevenue());
        values.put("expense",record.getExpense());
        values.put("category",record.getCategory().toString());
        values.put("tableId",record.getTableId());
        values.put("recordDate",record.getRecordDate());
        int i = myDataBase.update(TABLE_RECORD,values,
                RECORD_COLLUM_RECORDID+"=?" //TODO fix bug here
                ,new String[]{String.valueOf(record.getRecordId())});
        if(i >0){
            return true;
        }

        return false;
    }
    public boolean removeRecordFromTable(Record record){
        int result = myDataBase.delete(TABLE_RECORD
                                      ,RECORD_COLLUM_RECORDID+"=?"
                                      ,new String[]{String.valueOf(record.getRecordId())});
        return result >0;

    }

    public RecordTable findTableById(int tableId){
        String query = "SELECT * FROM " + TABLE_RECORDTABLE + " WHERE " +RECORD_COLLUM_TABLEID+"=?";
        String[] agrs = {String.valueOf(tableId)};
        Cursor cursor = myDataBase.rawQuery(query,agrs);
        if(cursor !=null){
            if(cursor.moveToNext()){


                String month = cursor.getString(cursor.getColumnIndex(RECORDTABLE_COLLUM_MONTH));

                int year = cursor.getInt(cursor.getColumnIndex(RECORDTABLE_COLLUM_YEAR));
                double odd = cursor.getDouble(cursor.getColumnIndex(RECORDTABLE_COLLUM_ODD)) ;
                long createdDate = cursor.getLong(cursor.getColumnIndex(RECORDTABLE_COLLUM_CREATEDDATE));

                RecordTable table = new RecordTable(tableId,month,year);
                table.setOdd(odd);
                table.setCreatedDate(createdDate);
                return table;
            }
        }
        return null;
    }
    public boolean addRecordToTable(Record record){
        ContentValues values = new ContentValues();
        values.put("description",record.getDescription());
        values.put("revenue",record.getRevenue());
        values.put("expense",record.getExpense());
        values.put("category",record.getCategory().toString());
        values.put("tableId",record.getTableId());
        values.put("recordDate",record.getRecordDate());
        if(myDataBase.insert(TABLE_RECORD,null,values)>-1){
            return true;
        }

        return false;
    }

    public List<Record> getAllRecordsByTableId(int tableId){
        String query = "SELECT * FROM " +TABLE_RECORD + " WHERE " + RECORD_COLLUM_TABLEID+ "=?";
        Cursor cursor = myDataBase.rawQuery(query,new String[]{String.valueOf(tableId)});

        List<Record> recordList = new ArrayList<Record>();
        if(cursor!=null){
            for(;cursor.moveToNext();){
                int recordId =cursor.getInt(cursor.getColumnIndex(RECORD_COLLUM_RECORDID));
                String description =cursor.getString(cursor.getColumnIndex(RECORD_COLLUM_DESCRIPTION));
                double revenue=cursor.getDouble(cursor.getColumnIndex(RECORD_COLLUM_REVENUE));
                double expense=cursor.getDouble(cursor.getColumnIndex(RECORD_COLLUM_EXPENSE));

                String cateString = cursor.getString(cursor.getColumnIndex(RECORD_COLLUM_CATEGORY));
                Record.CATEGORY  category = Record.parseCATEGORY(cateString);
                long recordDate = cursor.getLong(cursor.getColumnIndex(RECORD_COLLUM_RECORDDATE));

                Record record = new Record(recordDate,recordId,description,tableId,category);
                if(expense != 0){
                    record.setExpense(expense);
                }
                if(revenue != 0){
                    record.setRevenue(revenue);
                }

                recordList.add(record);
            }
            return recordList;
        }
        return null;
    }

    //3. TABLE LIST MANAGEMENT:
    public boolean removeTable(RecordTable table){

        int result = myDataBase.delete(TABLE_RECORDTABLE,RECORDTABLE_COLLUM_TABLEID+"=?",new String[]{String.valueOf(table.getTableId())});
        return result >0;
    }
    public boolean addTable(RecordTable table){
        ContentValues values = new ContentValues();
        values.put("month",table.getMonth());//bug here
        values.put("year",table.getYear());
        values.put("username",table.getUsername());
        values.put("createdDate",table.getCreatedDate());
        if(myDataBase.insert(TABLE_RECORDTABLE,RECORDTABLE_COLLUM_ODD,values)>-1){
            return true;
        }
        return false;
    }
    public List<RecordTable> getAllRecordTableByUser(String username){
        String query = "SELECT * FROM "+TABLE_RECORDTABLE + " WHERE "+RECORDTABLE_COLLUM_USERNAME+"=?";
        String[] selectionAgrs = {username};
        Cursor cursor = myDataBase.rawQuery(query,selectionAgrs);

        List<RecordTable> tableList = new ArrayList<RecordTable>();
        if(cursor != null){
           for(;cursor.moveToNext();){


                   int tableId = cursor.getInt(cursor.getColumnIndex(RECORDTABLE_COLLUM_TABLEID));

                   String month = cursor.getString(cursor.getColumnIndex(RECORDTABLE_COLLUM_MONTH));

                   int year = cursor.getInt(cursor.getColumnIndex(RECORDTABLE_COLLUM_YEAR));
                   double odd = cursor.getDouble(cursor.getColumnIndex(RECORDTABLE_COLLUM_ODD)) ;
                   long createdDate = cursor.getLong(cursor.getColumnIndex(RECORDTABLE_COLLUM_CREATEDDATE));

                   RecordTable table = new RecordTable(tableId,month,year,username);
                   table.setOdd(odd);
                   table.setCreatedDate(createdDate);
                   tableList.add(table);


           }
           return tableList;
        }
            return null;
    }

    //2. LOGIN MODULE:
    public boolean checkLogin(String username, String password){
        String query = "SELECT * FROM "+ TABLE_USER +" WHERE username=? and password=?";
        String[] selectionAgrs = {username,password};
        Cursor cursor = myDataBase.rawQuery(query,selectionAgrs);
        if(cursor.moveToFirst()){
            return true;
        }
        return false;
    }

    public boolean registerUser(User user){

        ContentValues values = new ContentValues();
        values.put("username",user.getUsername());
        values.put("password",user.getPassword());
        values.put("fullname",user.getFullname());
        if(myDataBase.insert(TABLE_USER,null,values) > -1){
            return true;
        }
        return false;
    }

    public boolean checkUserExistence(User user){
        String query = "SELECT * FROM "+ TABLE_USER +" WHERE username=?";
        String[] selectionAgrs = {user.getUsername()};
        Cursor cursor = myDataBase.rawQuery(query,selectionAgrs);
        if(cursor.moveToFirst()){
            return true;
        }
        return false;
    }
    public String findFullnameByUsername(String username) {
        String query = "SELECT * FROM " + TABLE_USER + " WHERE username =?";
        Cursor cursor = myDataBase.rawQuery(query, new String[]{username});
        if(cursor.moveToFirst()){
            return cursor.getString(cursor.getColumnIndex("fullname"));
        }
        return "";
    }


    //1. DATABASE INSIDE PROCESS:
    public void createdatabase() throws IOException {
        boolean dbexist = checkdatabase();
        if(!dbexist) {
            Log.d("DBHelper", "createdatabase: Copying database ...");
            copydatabase();
            Log.d("DBHelper", "createdatabase: Finish database");
        }
        opendatabase();
    }
    //Used by createdatabase
    private boolean checkdatabase() {
        boolean checkdb = false;
        try {
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        } catch(SQLiteException e) {
            Log.d("Open_Database", "checkdatabase: " + "Database not exists.");
        }
        return checkdb;
    }
    //Used by createdatabase
    public void copydatabase() throws IOException {
        InputStream myinput = mycontext.getAssets().open(DB_NAME);
        String outfilename = DB_PATH + DB_NAME;
        File dbdir = new File(DB_PATH);
        if(!dbdir.exists()){
            dbdir.mkdir();
        }
        FileOutputStream myoutput = new FileOutputStream(outfilename);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer))>0) {
            myoutput.write(buffer,0,length);
        }
        myoutput.flush();
        myoutput.close();
        myinput.close();
    }



    //Used by createdatabase
    public void opendatabase() throws SQLException {
        //Open the database
        String mypath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
        //myDataBase.close();
    }

    public synchronized void close() {
        if(myDataBase != null) {
            myDataBase.close();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE [User] (\n" +
                "  [username] NVARCHAR(16) NOT NULL ON CONFLICT ROLLBACK PRIMARY KEY, \n" +
                "  [password] CHAR(16) NOT NULL ON CONFLICT ROLLBACK, \n" +
                "  [fullname] NVARCHAR(30));\n" +
                "\n" +
                "\n" +
                "CREATE TABLE [RecordTable] (\n" +
                "  [tableId] INT(3) NOT NULL ON CONFLICT FAIL PRIMARY KEY AUTOINCREMENT, \n" +
                "  [month] NVARCHAR(3) NOT NULL ON CONFLICT FAIL, \n" +
                "  [year] INT(4) NOT NULL ON CONFLICT FAIL, \n" +
                "  [username] NVARCHAR(16) NOT NULL ON CONFLICT FAIL CONSTRAINT [username] REFERENCES [User]([username]) ON DELETE SET NULL);\n" +
                "\n" +
                "\n" +
                "CREATE TABLE [Record] (\n" +
                "  [recordId] INT(5) NOT NULL ON CONFLICT FAIL PRIMARY KEY AUTOINCREMENT, \n" +
                "  [date] DATE(20) NOT NULL ON CONFLICT FAIL, \n" +
                "  [description] NVARCHAR(50), \n" +
                "  [revenue] FLOAT, \n" +
                "  [expense] FLOAT, \n" +
                "  [category] NVARCHAR(20) NOT NULL ON CONFLICT FAIL, \n" +
                "  [tableId] INT(3) NOT NULL ON CONFLICT FAIL CONSTRAINT [tableId] REFERENCES [Table]([tableId]) ON DELETE SET NULL);");
        myDataBase = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("ONUPGRADE", newVersion+"");
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS RecordTable");
        db.execSQL("DROP TABLE IF EXISTS Record");
        onCreate(db);
    }
}
