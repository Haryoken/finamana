package hann.project.finamana.controllers;

import android.content.Context;

import hann.project.finamana.utils.DBHelper;

/**
 * Created by DUCVINH on 7/10/2017.
 */

public class NavigatorManager {
    public String getFullname(String username, Context context){
        DBHelper dbHelper = new DBHelper(context);
        return dbHelper.findFullnameByUsername(username);
    }
}
