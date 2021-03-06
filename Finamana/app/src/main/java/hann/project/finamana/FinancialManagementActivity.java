package hann.project.finamana;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

import hann.project.finamana.controllers.TableListManager;
import hann.project.finamana.entities.RecordTable;
import hann.project.finamana.utils.TableItemAdapter;

public class FinancialManagementActivity extends AppCompatActivity {
    private final String USERNAME_SP="USERNAME_PREFERENCE";

    private GridView tableGrid;
    private TableListManager manager;
    private List<RecordTable> recordTableList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_management);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        

        SharedPreferences sp = getSharedPreferences(USERNAME_SP, Context.MODE_PRIVATE);
        String username = sp.getString("USERNAME", "").toString();
        manager = new TableListManager(this);
        tableGrid = (GridView) findViewById(R.id.gridRecordTable);
        recordTableList = manager.getAllRecordTable(username);
        if(recordTableList!=null) {
            tableGrid.setAdapter(new TableItemAdapter(this, recordTableList));
            tableGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                   Intent toTableDetailsIntent = new Intent(FinancialManagementActivity.this,TableDetailsActivity.class);
                    toTableDetailsIntent.putExtra("tableId",recordTableList.get(position).getTableId());
                    //toTableDetailsIntent.putExtra("table",recordTableList.get(position));
                    startActivity(toTableDetailsIntent);
                }
            });
        }else {
            Toast.makeText(this,"No Item In List",Toast.LENGTH_SHORT);
        }
    }

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
                Intent toAddTableIntent = new Intent(FinancialManagementActivity.this,AddTableActivity.class);
                startActivity(toAddTableIntent);
                return true;
            case android.R.id.home:
                Intent backToFinaManaIntent = new Intent(FinancialManagementActivity.this,NavigatorActivity.class);
                startActivity(backToFinaManaIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
