package hann.project.finamana;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        FloatingActionButton btnAdd = (FloatingActionButton) findViewById(R.id.fab);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view,"Clicked",Snackbar.LENGTH_SHORT).show();
                Intent toAddTableIntent = new Intent(TableActivity.this,AddTableActivity.class);
                startActivity(toAddTableIntent);
            }
        });
    }
}
