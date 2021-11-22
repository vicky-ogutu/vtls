package com.example.vitalis;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener {
    DatabaseHelper mydb;
    EditText editname, editphone, editid, editfee;
    Spinner spinner;
    Button btnsave, btndelete, btnupdate, btnview;
    String[] coursez ={"Single room", "BedSitter", "One bedroom"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DatabaseHelper(this);
        editid = findViewById(R.id.id);
        editname = findViewById(R.id.nem);
        editphone = findViewById(R.id.tel);
        editfee = findViewById(R.id.Fee);
        spinner = findViewById(R.id.spina);
        btnsave = findViewById(R.id.sev);
        btndelete = findViewById(R.id.del);
        btnupdate = findViewById(R.id.updet);
        btnview = findViewById(R.id.viu);

        btnsave.setOnClickListener(this);
        btnview.setOnClickListener(this);
        btnupdate.setOnClickListener(this);
        btndelete.setOnClickListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,coursez);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (position==0){
                    editfee.setText("Pay Ksh 7,000");

                }else if (position==1){
                    editfee.setText("Pay Ksh 15,000");

                }else if(position==2){
                    editfee.setText("Pay Ksh 20,000");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sev:
                String name = editname.getText().toString();
                String phone = editphone.getText().toString();
                //String spinnerValue = spinner.getSelectedItem().toString();
                String y = String.valueOf(spinner.getSelectedItem().toString());
                String fees = editfee.getText().toString();

                if (TextUtils.isEmpty(name) | TextUtils.isEmpty(phone)| TextUtils.isEmpty(fees)) {
                    Toast.makeText(MainActivity.this, "Enter name, phone number, room and Amount", Toast.LENGTH_SHORT).show();
                }
                boolean x = mydb.insertData(name, phone, y, fees);
                if (x == true) {
                    editname.setText("");
                    editphone.setText("");
                    editfee.setText("");
                    Toast.makeText(MainActivity.this, "saved", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "cant save", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.del:

                String ID = editid.getText().toString();

                if (ID.equals("")) {
                    Toast.makeText(getApplicationContext(), "fill Id", Toast.LENGTH_SHORT).show();
                }else{
                    mydb.deleteData(ID);
                    editid.setText("");
                    editname.setText("");
                    editphone.setText("");
                    editfee.setText("");

                    Toast.makeText(getApplicationContext(), "deleted successful", Toast.LENGTH_SHORT).show();

                }
                break;

            case R.id.updet:
                String IDs = editid.getText().toString();
                String names = editname.getText().toString();
                String phones = editphone.getText().toString();
                String spinnerValues = spinner.getSelectedItem().toString();
                String feess = editfee.getText().toString();

                if (IDs.equals("") | names.equals("") | phones.equals("")| spinnerValues.equals("") | feess.equals("")){
                    Toast.makeText(getApplicationContext(), "fill details", Toast.LENGTH_SHORT).show();
                }
                else {
                    mydb.updateData(IDs, names, phones, spinnerValues, feess);
                    editphone.setText("");
                    editphone.setText("");
                    editname.setText("");
                    editfee.setText("");


                    Toast.makeText(getApplicationContext(), "updated successful", Toast.LENGTH_SHORT).show();
                }




            case R.id.viu:


                Cursor res = mydb.getAllData();
                if (res.getCount() == 0) {
                    showMessage("Alert", "Nothing found");
                }
                StringBuffer buffer = new StringBuffer();

                while(res.moveToNext()) {
                    buffer.append("ID:" + res.getString(0) + "\n");
                    buffer.append("Name:" + res.getString(1) + "\n");
                    buffer.append("Phone:" + res.getString(2) + "\n");
                    buffer.append("Room:" + res.getString(3) + "\n");
                    buffer.append("Amount:" + res.getString(4) + "\n");

                }

                showMessage("Data", buffer.toString());
        }
    }




    public void showMessage (String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


    }
