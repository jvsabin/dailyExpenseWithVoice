package com.jvsabin.try3;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String ae="";
    String tvText="";
    int balan=0;
    dbhelper mydb;
    EditText e1,e2,e3,e4;
    Button btn1;
    ArrayList<String> listItem,listItem2;
    ArrayAdapter adapter,adapter2;
    ListView userlist, userlist2;
    String qq="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView tv=(TextView) findViewById(R.id.tv1);



        mydb =new dbhelper(this);
        ae=mydb.viewData2();


        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("General");
        categories.add("Food");
        categories.add("Transport");
        categories.add("Others");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);





        e1= (EditText) findViewById(R.id.et1);
        e2= (EditText) findViewById(R.id.et2);
        e3= (EditText) findViewById(R.id.et3);
        e4= (EditText) findViewById(R.id.et4);
        btn1= (Button) findViewById(R.id.submit);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveData();

            }
        });

        /*Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });*/

        //Cursor cursor=mydb.viewData();
        Button b1 = (Button) findViewById(R.id.button3);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveDataB();
            }
        });

        Button b2 = (Button) findViewById(R.id.button4);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity4();
            }
        });


        userlist=findViewById(R.id.tv);
        userlist2=findViewById(R.id.tvB);
        listItem=new ArrayList<>();
        listItem2=new ArrayList<>();
        viewData();
        userlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text=userlist.getItemAtPosition(i).toString();
                Toast.makeText(MainActivity.this,""+text,Toast.LENGTH_SHORT).show();
            }
        });

        Cursor cursor=mydb.viewDataB();
        if(cursor.getCount()==0){
            tvText="0";
        }
        else {
            while (cursor.moveToNext()){
                tvText+=cursor.getString(1);
                balan+=Integer.parseInt(tvText);
                tvText="";

            }
            tvText="";
        }
        Cursor cursor2=mydb.viewData();
        if(cursor2.getCount()==0){
            //ae="0";
        }
        else {
            while (cursor2.moveToNext()){
                tvText+=cursor2.getString(3);
                balan-=Integer.parseInt(tvText);
                tvText="";

            }
            tvText="";
        }



        tvText+="Current Balance: "+balan;
        tv.setText(tvText);


    }



    private void viewData() {
        Cursor cursor=mydb.viewData();
        if(cursor.getCount()==0){
            Toast.makeText(this,"none", Toast.LENGTH_SHORT).show();
        }
        else{
            while (cursor.moveToNext()){
                listItem.add(" "+cursor.getString(1)+"\n Amount: " +cursor.getString(3));
            }
            adapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1, listItem);
            userlist.setAdapter(adapter);

            Cursor cursor1=mydb.viewDataB();
            if(cursor1.getCount()==0){
                //Toast.makeText(this,"none", Toast.LENGTH_SHORT).show();
            }
            else{
                while (cursor1.moveToNext()){
                    //listItem.add("Balance Added "+cursor1.getString(1));
                    listItem2.add("Balance Added "+cursor1.getString(1));
                }
                //adapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1, listItem);
                //
                // userlist.setAdapter(adapter);
                adapter2= new ArrayAdapter(this,android.R.layout.simple_list_item_1, listItem2);
                userlist2.setAdapter(adapter2);
            }

        }
    }



    public void SaveDataB(){
        //String type=e1.getText().toString();
        //String desc=e2.getText().toString();
        String amount=e4.getText().toString();
        //int balance=0;
        int amnt=0;
        try {
             amnt = Integer.parseInt(amount);
        }
        catch (Exception e){

        }
        if(amnt>0){

            Boolean result= mydb.insertDataB(amnt);
            if (result==true){
                Toast.makeText(MainActivity.this, "Balance inserted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, "Balance not inserted", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(MainActivity.this, "ADDED Balance can not be zero", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }



    public void SaveData(){
        String type=qq;
        String desc=e2.getText().toString();
        String amount=e3.getText().toString();
        //int balance=0;
        int amnt=0;
        try {
            amnt = Integer.parseInt(amount);
        }
        catch (Exception e){

        }
        if(amnt>0){
            if (type.equals("Others")){//type
                type=e1.getText().toString();
            }
            else{
                type=qq;
            }
            if (desc.equals("")){
                desc="none";
            }
            Boolean result= mydb.insertData(type,desc,amnt);
            if (result==true){
                Toast.makeText(MainActivity.this, "DATA inserted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, "DATA not inserted", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(MainActivity.this, "Amount can not be zero", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }



   /* public void openActivity2() {
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }
    public void openActivity3() {
        Intent intent = new Intent(MainActivity.this, Balance.class);

        //intent.putExtra("q",ae);
        startActivity(intent);
    }*/
    public void openActivity4() {
        Intent intent = new Intent(MainActivity.this, Voice.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> av, View view, int i, long l) {
        String item = av.getItemAtPosition(i).toString();
        qq=item;

        // Showing selected spinner item
        //Toast.makeText(av.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
