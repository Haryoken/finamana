package hann.project.finamana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import hann.project.finamana.controllers.TableManager;
import hann.project.finamana.entities.Record;

public class AddRecordActivity extends AppCompatActivity {
    EditText description;
    Spinner spinnerCategory;
    EditText moneyAmount;
    RadioGroup rdgMoney;
    TableManager manager;
    int tableId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent fromTableDetialsIntent = getIntent();
        tableId = fromTableDetialsIntent.getExtras().getInt("tableId");

        manager = new TableManager(this);

        description = (EditText)findViewById(R.id.txtDescription);
        spinnerCategory = (Spinner)findViewById(R.id.spinnerCategory);
        moneyAmount = (EditText)findViewById(R.id.txtMoneyAmount);
        rdgMoney = (RadioGroup)findViewById(R.id.rdgMoney);

        rdgMoney.check(R.id.rdRevenue);
        Record.CATEGORY[] categoryList = {Record.CATEGORY.FOOD,Record.CATEGORY.BEVERAGE,Record.CATEGORY.FUEL
                                         ,Record.CATEGORY.OUTFIT,Record.CATEGORY.SHOPPING,Record.CATEGORY.ENTERTAINMENT
                                         ,Record.CATEGORY.SALARY,Record.CATEGORY.OTHER,Record.CATEGORY.DEBT};
        //String[] categories = {"FOOD","BEVERAGE","FUEL","OUTFIT","SHOPPING","ENTERTAINMENT","SALARY","OTHER","DEBT"};
        spinnerCategory.setAdapter(new ArrayAdapter<Record.CATEGORY>(this,R.layout.support_simple_spinner_dropdown_item,categoryList));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_record_menu, menu); //your file name
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnDone:
                Record record = prepareRecord();
                if(manager.addRecordToTable(record)){
                    Toast.makeText(this,"Successfully added a new record to table.",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"Failed to add a new record to table.",Toast.LENGTH_SHORT).show();
                }
                Intent backToTableDetailsIntent = new Intent(AddRecordActivity.this,TableDetailsActivity.class);
                backToTableDetailsIntent.putExtra("tableId",tableId);
                startActivity(backToTableDetailsIntent);
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public Record prepareRecord(){
        //TODO add Date picker
        //TODO validate the input
        Record record;
        long dateInLong = new Date().getTime();
        String description = this.description.getText().toString();
        Double moneyAmount = Double.parseDouble(this.moneyAmount.getText().toString());
        Record.CATEGORY category = (Record.CATEGORY) spinnerCategory.getSelectedItem();
        record = new Record(dateInLong,description,tableId,category);

        int checkedId = rdgMoney.getCheckedRadioButtonId();
        switch (checkedId){
            case R.id.rdExpense:
                record.setExpense(moneyAmount);
                break;
            case R.id.rdRevenue:
                record.setRevenue(moneyAmount);
                break;
            default:
                break;
        }
        return record;
    }
}
