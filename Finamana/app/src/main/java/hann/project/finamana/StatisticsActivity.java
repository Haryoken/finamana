package hann.project.finamana;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hann.project.finamana.controllers.TableListManager;
import hann.project.finamana.controllers.TableManager;
import hann.project.finamana.entities.Record;
import hann.project.finamana.entities.RecordTable;

public class StatisticsActivity extends AppCompatActivity {
    String username;
    TableListManager tblListManager;
    TableManager tblmanager;
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                Intent backToNavigatorIntent = new Intent(StatisticsActivity.this,NavigatorActivity.class);
                startActivity(backToNavigatorIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sp = getSharedPreferences("USERNAME_PREFERENCE", Context.MODE_PRIVATE);
        username = sp.getString("USERNAME", "").toString();
        tblListManager = new TableListManager(this);
        tblmanager = new TableManager(this);
        processTopIncome();
        processTopOutcome();
    }

    private boolean processTopIncome(){
        TextView txtTop1 = (TextView)findViewById(R.id.txtIncomeTop1);
        TextView txtTop2 = (TextView)findViewById(R.id.txtIncomeTop2);
        TextView txtTop3 = (TextView)findViewById(R.id.txtIncomeTop3);

        TextView txtTop1Count = (TextView)findViewById(R.id.txtIncomeTop1Count);
        TextView txtTop2Count = (TextView)findViewById(R.id.txtIncomeTop2Count);
        TextView txtTop3Count = (TextView)findViewById(R.id.txtIncomeTop3Count);

        Map<Record.CATEGORY,Integer> categories = new HashMap<>();
        final Record.CATEGORY[] earnCategories = {Record.CATEGORY.SALARY,Record.CATEGORY.OTHER,Record.CATEGORY.DEBT};


        List<RecordTable> tableList = tblListManager.getAllRecordTable(username);
        if(tableList != null){
            for(RecordTable table: tableList){
                List<Record> recordList = tblmanager.getAllRecordsByTableId(table.getTableId());
                if(recordList != null){
                    for(Record record:recordList){
                        Record.CATEGORY category = record.getCategory();
                        if(Record.isEarnCATEGORY(category)) {
                            if (categories.containsKey(category)) {
                                int count = categories.get(record.getCategory());
                                count++;
                                categories.put(record.getCategory(), count);
                            } else {
                                categories.put(record.getCategory(), 1);
                            }
                        }
                    }
                }
            }
            List<Map.Entry<Record.CATEGORY,Integer>> list = new ArrayList<Map.Entry<Record.CATEGORY, Integer>>(categories.entrySet());
            Collections.sort(list, new Comparator<Map.Entry<Record.CATEGORY, Integer>>() {
                @Override
                public int compare(Map.Entry<Record.CATEGORY, Integer> o1, Map.Entry<Record.CATEGORY, Integer> o2) {
                    if(o1.getValue() <  o2.getValue()){
                        return 1;
                    }
                    if(o1.getValue() > o2.getValue()){
                        return -1;
                    }
                    return 0;
                }
            });

            if(list.size() >=3){
                txtTop3.setText(list.get(2).getKey().toString());
                txtTop3Count.setText(list.get(2).getValue().toString() + " times");
            }
            if(list.size() >=2){
                txtTop2.setText(list.get(1).getKey().toString());
                txtTop2Count.setText(list.get(1).getValue().toString()+ " times");
            }
            if(list.size() >0){
                txtTop1.setText(list.get(0).getKey().toString());
                txtTop1Count.setText(list.get(0).getValue().toString()+ " times");
            }
            return true;
        }
        return false;
    }
    private boolean processTopOutcome(){
        TextView txtTop1 = (TextView)findViewById(R.id.txtOutcomeTop1);
        TextView txtTop2 = (TextView)findViewById(R.id.txtOutcomeTop2);
        TextView txtTop3 = (TextView)findViewById(R.id.txtOutcomeTop3);

        TextView txtTop1Count = (TextView)findViewById(R.id.txtOutcomeTop1Count);
        TextView txtTop2Count = (TextView)findViewById(R.id.txtOutcomeTop2Count);
        TextView txtTop3Count = (TextView)findViewById(R.id.txtOutcomeTop3Count);

        Map<Record.CATEGORY,Integer> categories = new HashMap<>();
        final Record.CATEGORY[] earnCategories = {Record.CATEGORY.SALARY,Record.CATEGORY.OTHER,Record.CATEGORY.DEBT};


        List<RecordTable> tableList = tblListManager.getAllRecordTable(username);
        if(tableList != null){
            for(RecordTable table: tableList){
                List<Record> recordList = tblmanager.getAllRecordsByTableId(table.getTableId());
                if(recordList != null){
                    for(Record record:recordList){
                        Record.CATEGORY category = record.getCategory();
                        if(!Record.isEarnCATEGORY(category)) {
                            if (categories.containsKey(category)) {
                                int count = categories.get(record.getCategory());
                                count++;
                                categories.put(record.getCategory(), count);
                            } else {
                                categories.put(record.getCategory(), 1);
                            }
                        }
                    }
                }
            }
            List<Map.Entry<Record.CATEGORY,Integer>> list = new ArrayList<Map.Entry<Record.CATEGORY, Integer>>(categories.entrySet());
            Collections.sort(list, new Comparator<Map.Entry<Record.CATEGORY, Integer>>() {
                @Override
                public int compare(Map.Entry<Record.CATEGORY, Integer> o1, Map.Entry<Record.CATEGORY, Integer> o2) {
                    if(o1.getValue() <  o2.getValue()){
                        return 1;
                    }
                    if(o1.getValue() > o2.getValue()){
                        return -1;
                    }
                    return 0;
                }
            });

            if(list.size() >=3){
                txtTop3.setText(list.get(2).getKey().toString());
                txtTop3Count.setText(list.get(2).getValue().toString() + " times");
            }
            if(list.size() >=2){
                txtTop2.setText(list.get(1).getKey().toString());
                txtTop2Count.setText(list.get(1).getValue().toString()+ " times");
            }
            if(list.size() >0){
                txtTop1.setText(list.get(0).getKey().toString());
                txtTop1Count.setText(list.get(0).getValue().toString()+ " times");
            }
            return true;
        }
        return false;
    }
}
