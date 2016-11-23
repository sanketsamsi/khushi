package com.example.akshayk.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener {

    private EditText fname,lname,email,password,cpassword;
    private Button back,signup;
    private TextView user;


    private ImageButton happy,sad,blank;
    String line = null;
    ArrayList<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        happy=(ImageButton)findViewById(R.id.imageButton1);
        sad=(ImageButton)findViewById(R.id.imageButton2);
        blank=(ImageButton)findViewById(R.id.imageButton3);
        user=(TextView)findViewById(R.id.textView2);
        happy.setOnClickListener(this);
        sad.setOnClickListener(this);
        blank.setOnClickListener(this);
            }

    @Override
    public void onClick(View v) {

        if (v==happy) {

        new GetDataFromWebservice().execute("http://10.0.2.58/select.php?uid=1");

            Intent intent = new Intent(HomeScreen.this, happy.class);
            startActivity(intent);
        }
        else if (v==sad){

            Intent intent = new Intent(HomeScreen.this, sad.class);
            startActivity(intent);
        }
        else if (v==blank){

            Intent intent = new Intent(HomeScreen.this, blank.class);
            startActivity(intent);
        }

    }

    private void readStream(String finalResult) {
        try {

            JSONObject jsonobject = new JSONObject(finalResult);
            JSONArray ja = jsonobject.getJSONArray("info");
            JSONObject jsonObject1;

            for (int i = 0; i < ja.length(); i++) {
                jsonObject1 = (JSONObject) ja.get(i);
                //And then read attributes like
                String name = jsonObject1.getString("name");
                String phone = jsonObject1.getString("email");
                String id = jsonObject1.getString("status");
                System.out.println("Array name===>>>>>>>>>>" + name);
                System.out.println("Array phone===>>>>>>>>>>" + phone);
                System.out.println("Array id===>>>>>>>>>>" + id);
            }
        } catch (Exception e) {
            Log.e("Webservice 3", e.toString());
        }
    }
}


