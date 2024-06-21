package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.login.ipcalibration.ip_address;

public class uirobot extends AppCompatActivity {

    private static Button buttononn, buttonoff;
    Handler handler = new Handler();
    boolean statusdevice = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uirobot);

        buttononn = (Button) findViewById(R.id.onbtn);
        buttonoff = (Button) findViewById(R.id.offbtn);

        handler.postDelayed(status_data,0);

        buttononn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    request_to_url("ledon");
                }

            }
        });
        buttonoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    request_to_url("ledoff");
                }
            }
        });

        Button btnout = (Button) findViewById(R.id.btnlogout);
        btnout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(uirobot.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(getBaseContext(), "Successfully Log out.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Runnable status_data = new Runnable() {
        @Override
        public void run() {
            if (statusdevice) {
                request_to_url("");
                handler.postDelayed(this, 2000);
                Log.d("Status", "Connectivity_esp32");
            }else {
                handler.removeCallbacks(status_data);
                Log.d("Status","Finalizado");
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy(); 
        statusdevice = false;
    }

    public void request_to_url (String command) {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()) {
            Log.d("Request", "Sending command: " + command);
            new uirobot.request_data().execute("http://" + ip_address + "/" + command);

        }else {
            Toast.makeText(uirobot.this, "Not connected  ", Toast.LENGTH_LONG).show();

        }
    }

    private class request_data extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {
            Log.d("Request", "URL: " + url[0]);
            return connectivity.geturl(url[0]);

        }

    }
}