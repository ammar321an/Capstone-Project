package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ipcalibration extends AppCompatActivity {
    private EditText ip;
    private Button enter;
    public static String ip_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipcalibration);

        ip = (EditText) findViewById(R.id.ipaddress);
        enter = (Button) findViewById(R.id.ipbutton);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ip_address = ip.getText().toString();
                Intent intent = new Intent(ipcalibration.this, uirobot.class);
                startActivity(intent);


            }

        });

    }

}

