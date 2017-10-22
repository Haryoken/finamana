package hann.project.finamana;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hann.project.finamana.controllers.NavigatorManager;
import hann.project.finamana.entities.MenuItem;
import hann.project.finamana.utils.BackupHelper;
import hann.project.finamana.utils.FeatureGridAdapter;

public class NavigatorActivity extends AppCompatActivity {
    private final String TABLE_MANAGER="Financial Management";
    private final String FINANCIAL_REPORT="Financial Report";
    private final String STATITICS="Statitics";
    enum ImageName {backup,credit_card,drone
                    ,ic_launcher,ic_launcher_round,money
                    ,music_note,umbrealla
                    ,money_green,chart};

    private SharedPreferences sp;
    private final String USERNAME_SP="USERNAME_PREFERENCE";
    GridView grid;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu); //your file name
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSetting:
                //
                return true;
            case R.id.menuBackup:
                BackupHelper buHelper = new BackupHelper(NavigatorActivity.this);
                if(buHelper.processBackup()){
                    Toast.makeText(this,"Your Data has been Sucessfully Backup.",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"Backup failed.",Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.menuLogout:
                this.logout();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

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
                    case TABLE_MANAGER:
                        Intent toTableIntent = new Intent(NavigatorActivity.this,FinancialManagementActivity.class);
                        startActivity(toTableIntent);
                        break;
                    case FINANCIAL_REPORT:
                        Intent toReportIntent = new Intent(NavigatorActivity.this,ReportActivity.class);
                        startActivity(toReportIntent);
                        break;
                    case STATITICS:
                        Intent toStatiticsIntent = new Intent(NavigatorActivity.this,StatiticsActivity.class);
                        startActivity(toStatiticsIntent);
                        break;
                }
            }

        });

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

        MenuItem tableList = new MenuItem(TABLE_MANAGER,ImageName.credit_card.toString());
        MenuItem financialReport = new MenuItem(FINANCIAL_REPORT,ImageName.money_green.toString());
        MenuItem statitics = new MenuItem(STATITICS,ImageName.money_green.chart.toString());

        list.add(tableList);
        list.add(financialReport);
        list.add(statitics);


        return list;
    }
    private void logout(){
        sp = getSharedPreferences(USERNAME_SP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();

        Intent toLoginIntent = new Intent(this, LoginActivity.class);
        startActivity(toLoginIntent);
    }
}
