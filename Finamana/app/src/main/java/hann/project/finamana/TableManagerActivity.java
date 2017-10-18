package hann.project.finamana;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

import hann.project.finamana.controllers.TableManager;
import hann.project.finamana.entities.RecordTable;
import hann.project.finamana.utils.TableItemAdapter;

public class TableManagerActivity extends AppCompatActivity {
    private final String USERNAME_SP="USERNAME_PREFERENCE";

    private GridView tableGrid;
    private TableManager manager;
    private List<RecordTable> recordTableList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_manager);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton btnAdd = (FloatingActionButton) findViewById(R.id.fab);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view,"Clicked",Snackbar.LENGTH_SHORT).show();
                Intent toAddTableIntent = new Intent(TableManagerActivity.this,AddTableActivity.class);
                startActivity(toAddTableIntent);
            }
        });

        SharedPreferences sp = getSharedPreferences(USERNAME_SP, Context.MODE_PRIVATE);
        String username = sp.getString("USERNAME", "").toString();
        manager = new TableManager(this);
        tableGrid = (GridView) findViewById(R.id.gridRecordTable);
        recordTableList = manager.getAllRecordTable(username);
        if(recordTableList!=null) {
            tableGrid.setAdapter(new TableItemAdapter(this, recordTableList));
            tableGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                   Intent toRecordListIntent = new Intent(TableManagerActivity.this,RecordListActivity.class);
                    toRecordListIntent.putExtra("tableId",recordTableList.get(position).getTableId());
                    startActivity(toRecordListIntent);
                }
            });
        }else {
            Toast.makeText(this,"No Item In List",Toast.LENGTH_SHORT);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
