package com.jvsabin.try3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class Voice extends AppCompatActivity {

    private final int REQ_CODE = 100;
    TextView textView;
    String voice="";
    String[] b;
    dbhelper mydb;
    int amnt=0;
    int expense=0;
    int balance=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);



        mydb =new dbhelper(this);


        textView = findViewById(R.id.text);
        ImageView speak = findViewById(R.id.speak);
        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                        "Need to speak");
                try {
                    startActivityForResult(intent, REQ_CODE);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(),
                            "Sorry your device not supported",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    voice=result.get(0);
                    textView.setText(voice +" is added");




                    b=voice.split(" ");
                    for(int i=0; i<b.length; i++){
                        if(b[i].equals("expense")){
                            //System.out.println(b[i]);
                            expense=1;


                        }
                        else if(b[i].equals("balance")){
                            balance=1;
                        }
                        try{
                            // checking valid integer using parseInt() method
                            amnt=Integer.parseInt(b[i]);
                        }
                        catch (NumberFormatException e){

                        }
                    }


                    if(expense==1 && amnt>0){
                        String type="General";
                        String desc="none";
                        Boolean result1= mydb.insertData(type,desc,amnt);
                        if (result1==true){
                            Toast.makeText(Voice.this, "DATA inserted", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(Voice.this, "DATA not inserted", Toast.LENGTH_SHORT).show();
                        }
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                    }
                    else if(balance==1 && amnt>0){
                        Boolean result1= mydb.insertDataB(amnt);
                        if (result1==true){
                            Toast.makeText(Voice.this, "Balance inserted", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(Voice.this, "Balance not inserted", Toast.LENGTH_SHORT).show();
                        }
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                    }




                }
                break;
            }
        }
    }
}
