package hann.project.finamana.controllers;

import android.content.Context;

import hann.project.finamana.utils.DBHelper;

/**
 * Created by DUCVINH on 4/10/2017.
 */

public class LoginManager {
    //call query to entity User and check username password
    public boolean checkLogin(String username, String password, Context context){
        DBHelper dbHelper = new DBHelper(context);
        return dbHelper.checkLogin(username,password);

    }

}
