package hann.project.finamana.utils;

import android.content.Context;
import android.os.Environment;
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
    private Context context;

    public BackupHelper(Context context) {
        this.context = context;
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
        if(sdCard.canWrite()){
            copyFile(dbFile.getAbsolutePath(),sdCard);
            return true;
        }
        return false;
    }
    public void copyFile(String src, File destination){
        File srcFile = new File(src);
        File desFile = new File(destination,"FinamanaDB.db");
        try{
            FileChannel srcChannel = new FileInputStream(srcFile).getChannel();
            FileChannel desChannel = new FileInputStream(desFile).getChannel();
            try{
                desChannel.transferFrom(srcChannel,0,srcChannel.size());
            }catch(IOException e){

            }finally {
                desChannel.close();
                srcChannel.close();
            }
        }catch(FileNotFoundException e) {
            Log.d("MainActivity", "copyFile: FileNotFoundException: "+e.getMessage());

        }catch (IOException e){

        }
    }

}
