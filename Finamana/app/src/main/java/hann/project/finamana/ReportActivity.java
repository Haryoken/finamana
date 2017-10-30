package hann.project.finamana;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hann.project.finamana.controllers.TableListManager;
import hann.project.finamana.controllers.TableManager;
import hann.project.finamana.entities.Record;
import hann.project.finamana.entities.RecordTable;

public class ReportActivity extends AppCompatActivity {
    TableManager tblManager;
    TableListManager listManager;
    RecordTable monthTable;
    String username;

    TextView txtMonthTableName;
    TextView txtExpense;
    TextView txtRevenue;
    TextView txtNetIncome;
    TextView txtDebt;
    TextView txtBalance;
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                Intent backToNavigatorIntent = new Intent(ReportActivity.this,NavigatorActivity.class);
                startActivity(backToNavigatorIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sp = getSharedPreferences("USERNAME_PREFERENCE", Context.MODE_PRIVATE);
        username = sp.getString("USERNAME", "").toString();

        processCurrentMonth();
        processOverAll();



    }
    private boolean processCurrentMonth(){
        tblManager = new TableManager(this);

        SimpleDateFormat formatter = new SimpleDateFormat("MMMM_yyyy");
        String tableName =  formatter.format(new Date()).toString();
        double revenue=0;
        double expense=0;
        txtMonthTableName = (TextView)findViewById(R.id.txtMonthTableName) ;
        txtRevenue = (TextView)findViewById(R.id.txtMonthRevenue);
        txtExpense = (TextView)findViewById(R.id.txtMonthExpense);
        txtNetIncome=(TextView)findViewById(R.id.txtMonthNetIncome);
        txtDebt=(TextView)findViewById(R.id.txtMonthDebt);
        txtBalance=(TextView)findViewById(R.id.txtMonthBalance);


        monthTable = tblManager.findTableByMonthYear(tableName.split("_"),username);

        if(monthTable != null){

            txtMonthTableName.setText(monthTable.getMonth()+" "+monthTable.getYear());
            txtDebt.setText(String.format("%,.2f",monthTable.getDebt()));
            txtNetIncome.setText(String.format("%,.2f",monthTable.getOdd()));

            List<Record> recordList = tblManager.getAllRecordsByTableId(monthTable.getTableId());
            if(recordList != null){
                for(Record record: recordList){
                    revenue+=record.getRevenue();
                    expense+=record.getExpense();
                }

            }
            txtRevenue.setText(String.format("%,.2f",revenue));
            txtExpense.setText(String.format("%,.2f",expense));
            txtBalance.setText(String.format("%,.2f",monthTable.getOdd() - monthTable.getDebt()));

            //COLOR SETTING
            txtRevenue.setTextColor(Color.rgb(100,150,100));
            txtExpense.setTextColor(Color.RED);
            if(monthTable.getOdd() > 0){
                txtNetIncome.setTextColor(Color.rgb(100,150,100));
            }else{
                txtNetIncome.setTextColor(Color.RED);
            }
            txtDebt.setTextColor(Color.rgb(219,112,49));
            if(monthTable.getOdd() > monthTable.getDebt()){
                txtBalance.setTextColor(Color.rgb(100,150,100));
            }else{
                txtBalance.setTextColor(Color.RED);
            }
            return true;
        }
        return false;
    }
    private boolean processOverAll(){
        listManager = new TableListManager(this);


        double revenue=0;
        double expense=0;
        double netIncome = 0;
        double debt = 0;

        //VIEW INITIALIZING
        txtRevenue = (TextView)findViewById(R.id.txtAllRevenue);
        txtExpense = (TextView)findViewById(R.id.txtAllExpense);
        txtNetIncome=(TextView)findViewById(R.id.txtAllNetIncome);
        txtDebt=(TextView)findViewById(R.id.txtAllDebt);
        txtBalance=(TextView)findViewById(R.id.txtAllBalance);

        //VALUE CALCULATING
        List<RecordTable> tableList = listManager.getAllRecordTable(username);
        if(tableList!=null) {
            for (RecordTable table : tableList) {
                netIncome += table.getOdd();
                debt += table.getDebt();
                List<Record> recordList = tblManager.getAllRecordsByTableId(table.getTableId());
                if (recordList != null) {
                    for (Record record : recordList) {
                        revenue += record.getRevenue();
                        expense += record.getExpense();
                    }
                }
            }
            //VALUE SETTING
            txtRevenue.setText(String.format("%,.2f",revenue));
            txtExpense.setText(String.format("%,.2f",expense));
            txtNetIncome.setText(String.format("%,.2f",netIncome));
            txtDebt.setText(String.format("%,.2f",debt));
            txtBalance.setText(String.format("%,.2f",netIncome - debt));

            //COLOR SETTING
            txtRevenue.setTextColor(Color.rgb(100,150,100));
            txtExpense.setTextColor(Color.RED);
            if(netIncome > 0){
                txtNetIncome.setTextColor(Color.rgb(100,150,100));
            }else{
                txtNetIncome.setTextColor(Color.RED);
            }
            txtDebt.setTextColor(Color.rgb(219,112,49));
            if(netIncome > debt){
                txtBalance.setTextColor(Color.rgb(100,150,100));
            }else{
                txtBalance.setTextColor(Color.RED);
            }
            return true;
        }
        return false;
    }
}
