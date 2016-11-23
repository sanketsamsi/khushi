package com.example.akshayk.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.beardedhen.androidbootstrap.TypefaceProvider;

public class Login extends AppCompatActivity implements  View.OnClickListener {
    private Button sign, back;
    private TextView signup;
    private EditText email, password;
    SQLiteDatabase mydatabase;
    TextView resultView;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String pow = "nameKey";
    public static final String Email = "emailKey";

    private String result;
    private URL url = null;
    private HttpURLConnection urlConnection = null;

    private String line = null;
    ArrayList<String> list;
    private String server_response;
    String urlResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_main);

        sign = (Button) findViewById(R.id.signin_log);
        signup = (TextView) findViewById(R.id.signup);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        sign.setOnClickListener(this);
        signup.setOnClickListener(this);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

    }

    @Override
    public void onClick(View v) {


        if (v == sign) {
            String uname = email.getText().toString().trim();
            String pword = password.getText().toString().trim();
            if (uname.equals("") && pword.equals("")) {
                Toast.makeText(this, "Please Enter Mandatory details", Toast.LENGTH_LONG).show();



            } else if (uname.equals("akshay.khadse@arrkgroup.com") && pword.equals("admin@123")) {

                //new JasonTask().execute("http://10.0.2.58/select.php?uid=1");

                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putString(Email, uname);
                editor.putString(pow, pword);

                editor.commit();


                    Intent intent = new Intent(Login.this, HomeScreen.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else if (uname.equals("super.user@arrkgroup.com") && pword.equals("admin@123")) {
                Intent intent = new Intent(Login.this, Question.class);
                startActivity(intent);
                finish();
            }
             else{
                    Toast.makeText(this, "Please Enter Valid Credentials", Toast.LENGTH_LONG).show();
                }
            }

            if (v == signup) {


              //  new JasonTask().execute("http://10.0.2.58/select.php?uid=1");
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }


        }

public class JasonTask extends AsyncTask<String,String,String>{

    @Override
    protected String doInBackground(String[] params) {

        URL url = null;
        try {
            url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            server_response = readStream(urlConnection.getInputStream());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        return server_response;
    }

    @Override
    protected void onPostExecute(String server_response) {
        super.onPostExecute(server_response);
        System.out.println(server_response);
        JSONObject JO = null;
        JSONObject J1=null;
        List l1= new ArrayList();
        try {
            JO = new JSONObject(server_response);
            JSONArray ja = JO.getJSONArray("info");
            System.out.println("JSONARRAy**************"+ja);
            for (int i = 0; i < ja.length(); i++) {

                J1 = (JSONObject) ja.get(i);
                l1.add(J1.get("name"));
                l1.add(J1.get("email"));

            }
            System.out.println("LIST##########"+l1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(l1);
        signup.setText(l1.toString());
       // signup.setText((CharSequence) l1);
    }
}

    private String readStream(InputStream inputStream) {

        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
                System.out.println("Inside readstream");


            }



        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();


    }



}

