package hann.project.finamana.controllers;

import android.content.Context;

import hann.project.finamana.entities.User;
import hann.project.finamana.utils.DBHelper;

/**
 * Created by DUCVINH on 7/10/2017.
 */

public class RegisterManager {
    DBHelper dbHelper;
    public RegisterManager(Context context){
        dbHelper = new DBHelper(context);
    }
    public boolean registerUser(User user){
        return dbHelper.registerUser(user);

    }
    public boolean checkUserExistence(User user){
        return dbHelper.checkUserExistence(user);
    }
}
