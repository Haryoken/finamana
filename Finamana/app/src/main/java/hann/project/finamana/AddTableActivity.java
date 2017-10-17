package hann.project.finamana;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import hann.project.finamana.entities.RecordTable;

public class AddTableActivity extends AppCompatActivity {
    private static final String USERNAME_SP = "USERNAME_PREFERENCE";
    private Spinner spYear;
    private Spinner spMonth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_table);

        spYear = (Spinner)findViewById(R.id.spinYear);
        spMonth = (Spinner)findViewById(R.id.spinMonth);

        List<Integer> yearList = new ArrayList<Integer>();

        for(int i = 1990; i<= 2017;i++){
            yearList.add(i);
        }
        String[] monthList = {"January","February","March","April","May","June","July","August","September","October","November","December"};

        spYear.setAdapter(new ArrayAdapter<Integer>(this,R.layout.support_simple_spinner_dropdown_item,yearList));
        spMonth.setAdapter(new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,monthList));
    }
    public void clickDone(View view){
        int year = Integer.valueOf(spYear.getSelectedItem().toString());
        RecordTable.MONTH month = RecordTable.parseMONTH(spMonth.getSelectedItem().toString());

        SharedPreferences sp = getSharedPreferences(USERNAME_SP, Context.MODE_PRIVATE);
        String username = sp.getString("USERNAME", "").toString();

        long createdDate = new java.util.Date().getTime();

        RecordTable table = new RecordTable(month,year,username,createdDate);

    }
}
