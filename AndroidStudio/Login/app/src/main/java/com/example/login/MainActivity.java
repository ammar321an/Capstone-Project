package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = findViewById(R.id.btnnnlogin);
        EditText emaill = findViewById(R.id.emaillogin);
        EditText passs = findViewById(R.id.passwordlogin);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the email and password entered by the user
                String enteredEmail = emaill.getText().toString();
                String enteredPassword = passs.getText().toString();

                String sqlQuery = "SELECT * FROM registration WHERE email = '" + enteredEmail + "' AND password = '" + enteredPassword + "' AND access = 'Access'";
                String sqlAccess = "SELECT * FROM registration WHERE access = 'NoAccess'";


                Connection connection = connectionclass();

                try {
                    if (connection != null) {
                        Statement statement = connection.createStatement();
                        Statement statement1 = connection.createStatement();
                        ResultSet resultSet = statement.executeQuery(sqlQuery);
                        ResultSet resultSet1 = statement1.executeQuery(sqlAccess);


                        if (enteredEmail.isEmpty()) {
                            Toast.makeText(getBaseContext(), "Please insert your email.", Toast.LENGTH_SHORT).show();
                        } else if (enteredPassword.isEmpty()) {
                            Toast.makeText(getBaseContext(), "Please insert your password.", Toast.LENGTH_SHORT).show();
                        } else if (resultSet.next()) {
                            Intent intent = new Intent(MainActivity.this, ipcalibration.class);
                            startActivity(intent);
                            Toast.makeText(getBaseContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                        } else if (resultSet1.next()) {
                            Toast.makeText(getBaseContext(), "Login Failed. Please request access from the administrator.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getBaseContext(), "Login Failed. Please register.", Toast.LENGTH_SHORT).show();
                        }
                        resultSet.close();
                        resultSet1.close();
                        statement.close();
                        statement1.close();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Error", e.getMessage());
                }
            }
        });




        TextView btn=findViewById(R.id.textsignup);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, registration.class);
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



