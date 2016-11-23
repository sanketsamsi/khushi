package com.example.akshayk.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.name;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private EditText fname, lname, email, password, cpassword;
    private Button back, signup;
    private TextView signin;
    private String result;
    private URL url = null;
    private HttpURLConnection urlConnection = null;

    private String line = null;
    ArrayList<String> list;
    private String server_response;
    String urlResult;
    String firstname;
    String email_add;
    String pword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        fname = (EditText) findViewById(R.id.first_name);
        email = (EditText) findViewById(R.id.emailaddress);
        password = (EditText) findViewById(R.id.password);
        signin = (TextView) findViewById(R.id.signin_reg);
        signup = (Button) findViewById(R.id.signup_reg);

        signup.setOnClickListener(this);
        signin.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if (v == signin) {
            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
        }

        if (v == signup) {


             firstname = fname.getText().toString().trim();

             pword = password.getText().toString().trim();

             email_add = email.getText().toString().trim();

            if (firstname.equals("") && pword.equals("") && email_add.equals("")) {
                Toast.makeText(this, "Please Enter Mandatory details", Toast.LENGTH_SHORT).show();


            } else {
                new JsonPost().execute("http://10.0.2.58/validate.php");

                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
                Toast.makeText(this, "Activation Email sent", Toast.LENGTH_SHORT).show();

            }


        }
    }


    public class JsonPost extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            URL url = null;
            try {
                url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                //urlConnection.setRequestProperty("Content-Type", "application/json");
               urlConnection.setRequestProperty("charset", "utf-8");
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                System.out.println("above post");
                urlConnection.setRequestMethod("POST");
                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());

                try {
                    JSONObject obj = new JSONObject();
                    obj.put("name", firstname);
                    obj.put("email",email_add);
                    obj.put("pwd",pword);


                    System.out.println("Inside put");
                    wr.writeBytes(obj.toString());
                    Log.e("JSON Input", obj.toString());


                    wr.flush();
                    wr.close();
                    server_response = readStream(urlConnection.getInputStream());
                    System.out.println(server_response);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } catch (ProtocolException e) {
                e.printStackTrace();
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
}


