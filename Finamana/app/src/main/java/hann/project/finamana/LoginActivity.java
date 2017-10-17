package hann.project.finamana;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import hann.project.finamana.controllers.LoginManager;
import hann.project.finamana.utils.DBHelper;

public class LoginActivity extends AppCompatActivity {

    private final String spName = "USERNAME_PREFERENCE";
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //DBHelper dbHelper = new DBHelper(this);
//        try {
//            dbHelper.copydatabase();
//            Log.d("LOGIN ACTIVITY", "Finish Copying");
//        }catch(IOException e){
//            Log.d("LOGIN ACTIVITY", e.getMessage());
//        }
    }
    public void clickSignIn(View view){
        EditText txtUsername = (EditText)findViewById(R.id.txtUsername);
        EditText txtPassword = (EditText)findViewById(R.id.txtPassword);
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        LoginManager lm = new LoginManager();
        if(lm.checkLogin(username,password,this)){
            Toast.makeText(this, "LOGIN SUCCESS", Toast.LENGTH_SHORT).show();
            sp = getSharedPreferences(spName, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("USERNAME",username);
            editor.commit();
            Intent toNavigatorIntent = new Intent(this, NavigatorActivity.class);
            startActivity(toNavigatorIntent);
        }else{
            Toast.makeText(this, "Incorrect Username or Password.", Toast.LENGTH_LONG).show();
        }
//        DBHelper dbHelper = new DBHelper(this);
//        dbHelper.checkLogin(username,password);
    }
    public void clickSignUp(View view){
        Intent toRegisterIntent = new Intent(this, RegisterActivity.class);
        startActivity(toRegisterIntent);
    }
}
