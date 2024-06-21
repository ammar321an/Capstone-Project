package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class registration extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        TextView name = (TextView)findViewById(R.id.inputname);
        TextView email =(TextView)findViewById(R.id.inputemail);
        TextView pass = (TextView)findViewById(R.id.inputpassword);
        TextView repass = (TextView)findViewById(R.id.inputrepassword);
        Button btn1 = (Button)findViewById(R.id.btnregister);
        Button btn = (Button) findViewById(R.id.btn2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connection connection = connectionclass();
                try{
                    if(connection !=null) {

                        String names = name.getText().toString();
                        String emails = email.getText().toString();
                        String pasS = pass.getText().toString();
                        String repasS = repass.getText().toString();

                        if (names.isEmpty()) {
                            Toast.makeText(getBaseContext(), "Please insert your name.", Toast.LENGTH_SHORT).show();
                        }
                        else if (emails.isEmpty()) {
                            Toast.makeText(getBaseContext(), "Please insert your email.", Toast.LENGTH_SHORT).show();
                        }
                        else if (pasS.isEmpty()) {
                            Toast.makeText(getBaseContext(), "Please insert your password.", Toast.LENGTH_SHORT).show();
                        }
                        else if (repasS.isEmpty()) {
                            Toast.makeText(getBaseContext(), "Please insert your confirm password.", Toast.LENGTH_SHORT).show();
                        }
                        else if (!pasS.equals(repasS)) {
                            Toast.makeText(getBaseContext(), "Password and Confirm Password do not match.", Toast.LENGTH_SHORT).show();
                        } else {
                            String sqlinsert = "Insert into registration (name,email,password,repassword,access,dateTime) values" +
                                    "('" + names + "','" + emails + "'," +
                                    "'" + pasS + "','" + repasS + "', 'NoAccess', GETDATE())";
                            Statement st = connection.createStatement();
                            Toast.makeText(getBaseContext(), "Done Register", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(registration.this, MainActivity.class);
                            startActivity(intent);
                            ResultSet rs = st.executeQuery(sqlinsert);
                        }
                    }
                }
                catch (Exception exception){
                    Log.e( "Error",exception.getMessage() );

                }
            }
        });
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(registration.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("NewApi")
    public Connection connectionclass() {
        Connection con = null;
        String ip = "172.20.10.7", port = "1433", username = "ammarproject",
                password = "Ammar0135253103", databasename = "Application";
        StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            String connectionUrl = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";databasename=" +
                    databasename + ";User=" + username + ";password=" + password + ";";
            con = DriverManager.getConnection(connectionUrl);
        }
        catch (Exception exception){
            Log.e( "Error",exception.getMessage() );
        }
        return con;
    }

}