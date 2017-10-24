package hann.project.finamana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import hann.project.finamana.controllers.TableManager;
import hann.project.finamana.entities.Record;
import hann.project.finamana.utils.BackupHelper;

public class RecordDetailsActivity extends AppCompatActivity {
    EditText description;
    Spinner spinnerCategory;
    EditText moneyAmount;
    RadioGroup rdgMoney;
    RadioButton rdRevenue;
    RadioButton rdExpense;
    TableManager manager;
    Record record;
    EditText recordDate;
    TextView btnDeleteRecord;

    SimpleDateFormat dateFormatter;
    //private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent fromTableDetialsIntent = getIntent();
        record = (Record)fromTableDetialsIntent.getExtras().getSerializable("record");


        manager = new TableManager(this);
        //INIT SECTION
        description = (EditText)findViewById(R.id.txtDescription);
        spinnerCategory = (Spinner)findViewById(R.id.spinnerCategory);
        moneyAmount = (EditText)findViewById(R.id.txtMoneyAmount);
        rdgMoney = (RadioGroup)findViewById(R.id.rdgMoney);
        recordDate = (EditText)findViewById(R.id.txtRecordDate);
        rdRevenue = (RadioButton)findViewById(R.id.rdRevenue);
        rdExpense = (RadioButton)findViewById(R.id.rdExpense);
        btnDeleteRecord = (TextView)findViewById(R.id.btnDeleteRecord);
        //SETTING SECTION
        recordDate.setEnabled(false);

        btnDeleteRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Implement delete Record
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.btnSave:
                //TODO implement update record
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.record_details_menu, menu); //your file name
        return super.onCreateOptionsMenu(menu);
    }



}
