package hann.project.finamana;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hann.project.finamana.controllers.TableListManager;
import hann.project.finamana.entities.RecordTable;

public class AddTableActivity extends AppCompatActivity {
    private static final String USERNAME_SP = "USERNAME_PREFERENCE";
    private Spinner spYear;
    private Spinner spMonth;
    TableListManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_table);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        manager = new TableListManager(this);
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
        String month = spMonth.getSelectedItem().toString();

        SharedPreferences sp = getSharedPreferences(USERNAME_SP, Context.MODE_PRIVATE);
        String username = sp.getString("USERNAME", "").toString();

        long createdDate = new java.util.Date().getTime();

        RecordTable table = new RecordTable(month,year,username,createdDate);
        if(manager.addTable(table)){
            Toast.makeText(this, "A table has successfully added.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Failed to add new table.", Toast.LENGTH_SHORT).show();
        }
        Intent toTableIntent = new Intent(this,FinancialManagementActivity.class);
        startActivity(toTableIntent);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
