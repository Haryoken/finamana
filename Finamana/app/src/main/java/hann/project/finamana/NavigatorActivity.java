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

import java.util.ArrayList;
import java.util.List;

import hann.project.finamana.controllers.ImageAdapter;
import hann.project.finamana.controllers.LoginManager;
import hann.project.finamana.controllers.NavigatorManager;
import hann.project.finamana.entities.MenuItem;
import hann.project.finamana.utils.FeatureGridAdapter;

public class NavigatorActivity extends AppCompatActivity {
    SharedPreferences sp;
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

        MenuItem tableList = new MenuItem("Table List","drone");
        MenuItem financialReport = new MenuItem("Financial Report","money");
        MenuItem statitics = new MenuItem("Statitics","music_note");
        MenuItem payAlarm = new MenuItem("Pay Alarm","umbrealla");
        MenuItem backupData = new MenuItem("Backup Data", "backup");

        list.add(tableList);
        list.add(financialReport);
        list.add(statitics);
        list.add(payAlarm);
        list.add(backupData);

        return list;
    }
}
