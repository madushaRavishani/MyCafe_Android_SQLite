package com.example.mycafe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName,editDescription,editPrice, editId;
    Button btnAddData;
    Button btnViewAll;
    Button btnUpdate;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editName = (EditText) findViewById(R.id.editTextName);
        editDescription = (EditText) findViewById(R.id.editTextDescription);
        editPrice = (EditText) findViewById(R.id.editTextPrice);
        editId = (EditText) findViewById(R.id.editTextId);
        btnAddData = (Button)findViewById(R.id.button_add);
        btnViewAll = (Button)findViewById(R.id.button_view);
        btnUpdate= (Button)findViewById(R.id.button_update);
        btnDelete = (Button)findViewById(R.id.button_delete);
        AddData();
        viewAll();
        updateData();
        deleteData();
    }

    public void AddData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        boolean isInserted= myDb.insertData(editName.getText().toString(),
                                editDescription.getText().toString(),
                                editPrice.getText().toString()
                        );
                            if(isInserted= true)
                                Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(MainActivity.this, "Data not inserted", Toast.LENGTH_SHORT).show();
                    }
                }


        );
    }

    public void viewAll(){
        btnViewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor res = myDb.getAllData();
                            if(res.getCount() == 0){
                                //show message
                                showMessage("Error","Nothing found");
                                return;
                            }
                        StringBuffer buffer = new StringBuffer();
                            while (res.moveToNext()){
                                buffer.append("Id:"+res.getString(0)+"\n");
                                buffer.append("Name:"+res.getString(1)+"\n");
                                buffer.append("Description:"+res.getString(2)+"\n");
                                buffer.append("Price:"+res.getString(3)+"\n");
                            }
                            //show all data
                            showMessage("Data", buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void updateData(){
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isUpdate = myDb.updateData(editId.getText().toString(),
                                editName.getText().toString(),
                                editDescription.getText().toString(),
                                editPrice.getText().toString());
                        if(isUpdate == true) {
                            Toast.makeText(MainActivity.this, "Data updated", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(MainActivity.this, "Data not updated", Toast.LENGTH_SHORT).show();
                        }

                }


        );

    }

    public void deleteData(){
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Integer deleteRows = myDb.deleteData(editId.getText().toString());
                        if(deleteRows > 0) {
                            Toast.makeText(MainActivity.this, "Data deleted", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(MainActivity.this, "Data not deleted", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }



}