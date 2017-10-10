package hann.project.finamana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import hann.project.finamana.controllers.RegisterManager;
import hann.project.finamana.entities.User;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
    public void clickSignUp(View view){
        EditText txtUsername = (EditText)findViewById(R.id.txtUsername);
        EditText txtPassword = (EditText)findViewById(R.id.txtPassword);
        EditText txtConfirmPassword = (EditText)findViewById(R.id.txtConfirmPassword);
        EditText txtFullname = (EditText)findViewById(R.id.txtFullname);

        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        String confirmPassword = txtConfirmPassword.getText().toString();
        String fullname = txtFullname.getText().toString();

        User user = new User(username,password,fullname);

        RegisterManager rm = new RegisterManager();
        boolean isUserExisted = rm.checkUserExistence(user, this);

        if (validateUser(username, password,confirmPassword, fullname, isUserExisted)){

            if(rm.registerUser(user,this)){
                Toast.makeText(this,"Register Successfully.",Toast.LENGTH_SHORT).show();
                Intent toLoginIntent = new Intent(this, LoginActivity.class);
                startActivity(toLoginIntent);
            }else{
                Toast.makeText(this,"Register Failed.",Toast.LENGTH_SHORT).show();
            }

        }
    }
    private boolean validateUser(String username,String password,String confirmPassword,String fullname, boolean isUserExisted){
        TextView txtUsernameError = (TextView)findViewById(R.id.txtUsernameError);
        TextView txtPasswordError = (TextView)findViewById(R.id.txtPasswordError);
        TextView txtFullnameError = (TextView)findViewById(R.id.txtFullnameError);
        TextView txtConfirmPasswordError = (TextView)findViewById(R.id.txtConfirmPasswordError);

        RegisterManager rm = new RegisterManager();
        boolean result = true;

        //username validate
        if(username.equals("") || username.length() > 16 ||username.length() < 6){
            txtUsernameError.setText("Length from 6 to 16 characters.");
            result = false;
        }else {
            txtUsernameError.setText("");
        }
        //check user existence
        if(isUserExisted){
            txtUsernameError.setText("Username is existed.");
            result = false;
        }
        //password validate
        if(password.equals("") || username.length() > 16 ||username.length() < 6){
            txtPasswordError.setText("Length from 6 to 16 characters.");
            result = false;
        }else{
            txtPasswordError.setText("");
        }
        //confirm password validate
        if(!password.equals(confirmPassword)){
            txtConfirmPasswordError.setText("Confirm Password not matches.");
            result = false;
        }else{
            txtConfirmPasswordError.setText("");
        }
        //fullname validate
        if(fullname.length() > 30){
            txtFullnameError.setText("Length less than 30 characters.");
            result = false;
        }else{
            txtFullnameError.setText("");
        }
        return result;
    }
}
