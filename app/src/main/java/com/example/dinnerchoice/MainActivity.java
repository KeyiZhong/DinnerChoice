package com.example.dinnerchoice;

import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dinnerchoice.datastructures.concrete.KVPair;
import com.example.dinnerchoice.datastructures.concrete.dictionaries.ArrayDictionary;

import java.util.Iterator;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editText_name, editText_weight;
    Button btnAddData;
    Button btnGetResult;
    Button btnDelData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editText_name = (EditText)findViewById(R.id.editText_name);
        editText_weight = (EditText)findViewById(R.id.editText_weight);
        btnAddData = (Button)findViewById(R.id.button_add);
        AddData();
        btnGetResult = (Button)findViewById(R.id.button_get);
        GetData();
        btnDelData = (Button)findViewById(R.id.button_del);
        DeleteData();
    }
    public void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(editText_name.getText().toString(),
                                editText_weight.getText().toString());
                        if(isInserted) {
                            Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(MainActivity.this, "Data not inserted", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public void GetData() {
        btnGetResult.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor cursor = myDb.retrieve();
                        if(cursor.getCount() == 0) {
                            showMessage("Error", "Nothing Found");
                        }else {
                            ArrayDictionary<String, String> dict = new ArrayDictionary<>();
                            while(cursor.moveToNext()) {
                                dict.put(cursor.getString(0), cursor.getString(1));
                            }
                            int size = dict.size();
                            int weightedSize = 0;
                            Iterator<KVPair<String, String>> itr = dict.iterator();
                            String[] list = new String[size];
                            int ind = 0;
                            while(itr.hasNext()) {
                                KVPair<String, String> pair = itr.next();
                                weightedSize += Integer.parseInt(pair.getValue());
                                list[ind] = pair.getKey();
                                ind ++;
                            }
                            Random rand = new Random();
                            int index = rand.nextInt(weightedSize);
                            ind = 0;
                            while(index > 0) {
                                index -= Integer.parseInt(dict.get(list[ind]));
                                ind++;
                            }
                            StringBuffer buffer = new StringBuffer();
                            buffer.append(list[ind]);
                            showMessage("Your Dinenr", buffer.toString());
                        }
                    }
                }
        );
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void DeleteData() {
        btnDelData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDb.deleteData();
                    }
                }
        );
    }
}
