package hann.project.finamana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class RecordListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_details);

        Intent fromTableManagerIntent = getIntent();
        int tableId= fromTableManagerIntent.getExtras().getInt("tableId");
        Toast.makeText(this,"TableID: "+tableId,Toast.LENGTH_SHORT).show();
    }
}
