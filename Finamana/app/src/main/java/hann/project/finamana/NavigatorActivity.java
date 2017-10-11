package hann.project.finamana;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import hann.project.finamana.controllers.LoginManager;
import hann.project.finamana.controllers.NavigatorManager;

public class NavigatorActivity extends AppCompatActivity {
    SharedPreferences sp;
    private final String USERNAME_SP="USERNAME_PREFERENCE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigator);

        sp = getSharedPreferences(USERNAME_SP, Context.MODE_PRIVATE);
        String username = sp.getString("USERNAME","").toString();

        TextView txtName = (TextView)findViewById(R.id.txtNavFullname);

        NavigatorManager navMana = new NavigatorManager();
        String fullname = navMana.getFullname(username,this);
        if(fullname.equals("")){
            txtName.setText(username);
        }else{
            txtName.setText(fullname);
        }

    }
    public void clickLogout(View view){
        sp = getSharedPreferences(USERNAME_SP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();

        Intent toLoginIntent = new Intent(this, LoginActivity.class);
        startActivity(toLoginIntent);
    }
}
