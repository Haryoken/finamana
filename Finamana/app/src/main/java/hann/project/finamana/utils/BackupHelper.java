package hann.project.finamana.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by hann on 13/10/2017.
 */

public class BackupHelper {
    private Activity activity;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public BackupHelper(Activity activity) {
        this.activity = activity;
    }
    public boolean processImport(){
//1. GET SD Card PATH
        String dataBaseFileName = "FinamanaDB.db";
        File sdCard = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();

        String databasePath = "data/hann.project.finamana/databases"; // /databases
        File databaseDirectory = new File(data,databasePath);

        return copyFile(sdCard.getAbsolutePath()+"/"+dataBaseFileName,databaseDirectory);

    }
    public boolean processBackup(){
        //1. GET SD Card PATH
        String dataBaseFileName = "FinamanaDB.db";
        File sdCard = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();

        String dataPath = "data/hann.project.finamana/databases"; // /databases
        File dataDir = new File(data,dataPath);

        File[] fileList = dataDir.listFiles();
        File dbFile = null;
        for(File file:fileList){
            if(file.getName().equals(dataBaseFileName)){
                dbFile = file;
                return copyFile(dbFile.getAbsolutePath(),sdCard);
            }
        }
            return false;
    }
    private boolean copyFile(String sourcePath, File destination) {
        verifyStoragePermissions(activity);
        String dataFile = "FinamanaDB.db";
        File srcFile = new File(sourcePath);
        File desFile = new File(destination,dataFile);
        if(!desFile.exists()) {
            try {
                desFile.createNewFile();
            } catch (IOException e) {
                Log.d("BackUpHelper","IOException: "+e.getMessage());
            }
        }
        try{

            FileChannel srcChannel = new FileInputStream(srcFile).getChannel();
            FileChannel desChannel = new FileOutputStream(desFile).getChannel();
            try{
                desChannel.transferFrom(srcChannel,0,srcChannel.size());
                return true;
            }catch(IOException e){

            }finally {
                desChannel.close();
                srcChannel.close();
            }
        }catch(FileNotFoundException e) {
            Log.d("MainActivity", "copyFile: FileNotFoundException: "+e.getMessage());
            return false;

        }catch (IOException e){
            return false;
        }
        return false;
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        boolean permission = checkPermissions(activity);
        // Not have permission so prompt the user
        if(!permission){
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }

    public static boolean checkPermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }
}
