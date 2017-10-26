package hann.project.finamana;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hann.project.finamana.controllers.LoginManager;
import hann.project.finamana.controllers.NavigatorManager;
import hann.project.finamana.controllers.TableListManager;
import hann.project.finamana.controllers.TableManager;
import hann.project.finamana.entities.Record;
import hann.project.finamana.entities.RecordTable;
import hann.project.finamana.utils.BackupHelper;
import hann.project.finamana.utils.RecordAdapter;

public class TableDetailsActivity extends AppCompatActivity {
    private TableManager tblManager;
    private ListView lvRecord;
    List<Record> recordList;
    RecordTable table;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.table_details_menu, menu); //your file name
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnAddRecord:
                Intent toAddRecordIntent = new Intent(TableDetailsActivity.this,AddRecordActivity.class);
                toAddRecordIntent.putExtra("tableId",table.getTableId());
                startActivity(toAddRecordIntent);
                return true;
            case android.R.id.home:
                Intent backToFinaManaIntent = new Intent(TableDetailsActivity.this,FinancialManagementActivity.class);
                startActivity(backToFinaManaIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //INITIALIZING AREA
        tblManager = new TableManager(this);


        SharedPreferences sp = getSharedPreferences("USERNAME_PREFERENCE", Context.MODE_PRIVATE);
        String username = sp.getString("USERNAME", "").toString();
        //GET table from previous Activity
        Intent fromTableManagerIntent = getIntent();
        int tableId= fromTableManagerIntent.getExtras().getInt("tableId");


        //table = (RecordTable)fromTableManagerIntent.getExtras().getSerializable("table");
        table = tblManager.findTableById(tableId);


        TextView owner = (TextView)findViewById(R.id.txtTableOwner);
        String fullname = new NavigatorManager().getFullname(username,this);
        owner.setText(fullname);

        TextView title = (TextView)findViewById(R.id.txtTableTitle);
        title.setText(table.getMonth() + "_" + table.getYear());

        TextView createdDate = (TextView)findViewById(R.id.txtTableCreatedDate);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss  EEE, MMM-dd-yyyy");
        String strDate = formatter.format(new Date(table.getCreatedDate()));
        createdDate.setText(strDate);

        recordList = tblManager.getAllRecordsByTableId(table.getTableId());
        double[] totals = tblManager.calculateTotal(recordList);

        TextView totalRevenue = (TextView)findViewById(R.id.txtTotalRevenues);
        totalRevenue.setText(String.format("%,.2f",totals[0]));
        totalRevenue.setTextColor(Color.rgb(100,150,100));

        TextView totalExpense = (TextView)findViewById(R.id.txtTotalExpense);
        totalExpense.setText(String.format("%,.2f",totals[1]));
        totalExpense.setTextColor(Color.RED);

        TextView totalOdd = (TextView)findViewById(R.id.txtTotalOdd);
        totalOdd.setText(String.format("%,.2f",totals[2]));
        if(totals[2] > 0){
            totalOdd.setTextColor(Color.rgb(100,150,100));
        }else{
            totalOdd.setTextColor(Color.RED);
        }
        table.setOdd(totals[2]);
        boolean result =tblManager.updateTableOdd(table);

        if(recordList!= null) {
            lvRecord = (ListView) findViewById(R.id.listRecords);
            lvRecord.setAdapter(new RecordAdapter(this, recordList));
            lvRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Intent toRecordListActivity = new Intent(TableDetailsActivity.this,RecordDetailsActivity.class);
                    toRecordListActivity.putExtra("record",recordList.get(position));

                    startActivity(toRecordListActivity);
                }
            });
        }

        TextView btnDeleteTable = (TextView)findViewById(R.id.btnDeleteTable);
        btnDeleteTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(TableDetailsActivity.this);
                builder.setMessage("Are you sure to delete this Table ?");
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        TableListManager manager = new TableListManager(TableDetailsActivity.this);
                        if(manager.removeTableFromList(table)){
                            Intent toTableListIntent = new Intent(TableDetailsActivity.this, FinancialManagementActivity.class);
                            startActivity(toTableListIntent);
                            Toast.makeText(TableDetailsActivity.this," Table has been successfully removed.",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(TableDetailsActivity.this,"Remove failed.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

}
