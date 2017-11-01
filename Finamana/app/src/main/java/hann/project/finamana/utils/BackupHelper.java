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
    public boolean processBackup(){
        //1. GET SD Card PATH
        File sdCard = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        String dataPath = "data/hann.project.finamana/databases"; // /databases
        File dataDir = new File(data,dataPath);
        File[] fileList = dataDir.listFiles();
        File dbFile = fileList[0];
        //File dbFile = new File(dataPath);


            return copyFile(dbFile.getAbsolutePath(),sdCard);



    }
    private boolean copyFile(String src, File destination) {
        String backUpFile = "FinamanaDB.db";
        File srcFile = new File(src);
        File desFile = new File(destination,backUpFile);
        if(!desFile.exists()) {
            try {
                desFile.createNewFile();
            } catch (IOException e) {
                Log.d("BackUpHelper","IOException: "+e.getMessage());
            }
        }
        boolean isdir2 = srcFile.isDirectory();
        boolean isdir = desFile.exists();
        try{

            FileChannel srcChannel = new FileInputStream(srcFile).getChannel();
            FileChannel desChannel = new FileInputStream(desFile).getChannel();
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
}
