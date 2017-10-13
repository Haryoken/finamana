package hann.project.finamana;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hann.project.finamana.controllers.ImageAdapter;
import hann.project.finamana.controllers.LoginManager;
import hann.project.finamana.controllers.NavigatorManager;
import hann.project.finamana.entities.MenuItem;
import hann.project.finamana.utils.BackupHelper;
import hann.project.finamana.utils.FeatureGridAdapter;

public class NavigatorActivity extends AppCompatActivity {
    private final String TABLE_LIST="Table List";
    private final String FINANCIAL_REPORT="Financial Report";
    private final String BACKUP_DATA="Backup Data";
    private final String STATITICS="Statitics";


    private SharedPreferences sp;
    private final String USERNAME_SP="USERNAME_PREFERENCE";
    GridView grid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigator);

        sp = getSharedPreferences(USERNAME_SP, Context.MODE_PRIVATE);
        String username = sp.getString("USERNAME", "").toString();

        TextView txtName = (TextView) findViewById(R.id.txtNavFullname);

        NavigatorManager navMana = new NavigatorManager();
        String fullname = navMana.getFullname(username, this);
        if (fullname.equals("")) {
            txtName.setText(username);
        } else {
            txtName.setText(fullname);
        }
//        String[] arrNameCity = {"London","Paris","Tokyo","Hanoi","Penang","Seoul"};
        grid = (GridView) findViewById(R.id.gridFeatures);
        List<MenuItem> menuItemList = this.getMenuItemList();

        grid.setAdapter(new FeatureGridAdapter(this,menuItemList));
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object obj = grid.getItemAtPosition(i);
                MenuItem item = (MenuItem) obj;
                switch (item.getName()){
                    default:
                        break;
                    case TABLE_LIST:
                        Intent toTableIntent = new Intent(NavigatorActivity.this,TableActivity.class);
                        startActivity(toTableIntent);
                    case FINANCIAL_REPORT:
                        Intent toReportIntent = new Intent(NavigatorActivity.this,ReportActivity.class);
                        startActivity(toReportIntent);
                    case STATITICS:
                        Intent toStatiticsIntent = new Intent(NavigatorActivity.this,StatiticsActivity.class);
                        startActivity(toStatiticsIntent);
                    case BACKUP_DATA:
                        //DO BACK UP PROCESS
                        BackupHelper buHelper = new BackupHelper(NavigatorActivity.this);
                        buHelper.processBackup();
                }
            }

        });
//        grid.setAdapter(new ImageAdapter(this));
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_list_item_1, arrNameCity);
//        grid.setAdapter(adapter);
    }
    public void clickLogout(View view){
        sp = getSharedPreferences(USERNAME_SP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();

        Intent toLoginIntent = new Intent(this, LoginActivity.class);
        startActivity(toLoginIntent);
    }
    private List<MenuItem> getMenuItemList(){
        List<MenuItem> list = new ArrayList<MenuItem>();

        MenuItem tableList = new MenuItem(TABLE_LIST,"drone");
        MenuItem financialReport = new MenuItem(FINANCIAL_REPORT,"money");
        MenuItem statitics = new MenuItem(STATITICS,"music_note");
        MenuItem payAlarm = new MenuItem("Pay Alarm","umbrealla"); //Temporary
        MenuItem backupData = new MenuItem(BACKUP_DATA, "backup");

        list.add(tableList);
        list.add(financialReport);
        list.add(statitics);
        list.add(payAlarm);
        list.add(backupData);

        return list;
    }
}
