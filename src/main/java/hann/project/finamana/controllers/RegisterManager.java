package hann.project.finamana.controllers;

import android.content.Context;

import hann.project.finamana.entities.User;
import hann.project.finamana.utils.DBHelper;

/**
 * Created by DUCVINH on 7/10/2017.
 */

public class RegisterManager {
    public boolean registerUser(User user, Context context){
        DBHelper dbHelper = new DBHelper(context);
        return dbHelper.registerUser(user);

    }
    public boolean checkUserExistence(User user,Context context){
        DBHelper dbHelper = new DBHelper(context);
        return dbHelper.checkUserExistence(user);
    }
}
