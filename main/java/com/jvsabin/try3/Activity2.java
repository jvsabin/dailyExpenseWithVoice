package com.jvsabin.try3;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Activity2 extends AppCompatActivity {

    ArrayList<String> listItem;
    ArrayAdapter adapter;
    dbhelper db;
    ListView userlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        userlist=findViewById(R.id.tv);
        listItem=new ArrayList<>();
        viewData();
        userlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text=userlist.getItemAtPosition(i).toString();
                Toast.makeText(Activity2.this,""+text,Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void viewData() {
        Cursor cursor=db.viewData();
        if(cursor.getCount()==0){
            Toast.makeText(this,"none", Toast.LENGTH_SHORT).show();
        }
        else{
            while (cursor.moveToNext()){
                listItem.add(cursor.getString(1));
            }
            adapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1, listItem);
            userlist.setAdapter(adapter);
        }
    }
}
