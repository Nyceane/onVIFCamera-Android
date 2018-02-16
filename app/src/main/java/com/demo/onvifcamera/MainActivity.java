package com.demo.onvifcamera;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public String TAG = "MainActivity";

    EditText txtIP;
    EditText txtPort;
    EditText txtLogin;
    EditText txtPassword;
    Button btnPlay;
    Button btnVIFInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtIP = (EditText) findViewById(R.id.txtIP);
        txtPort = (EditText) findViewById(R.id.txtPort);
        txtLogin = (EditText) findViewById(R.id.txtLogin);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnVIFInfo = (Button) findViewById(R.id.btnVIFInfo);

        txtIP.setText("61.164.52.166");
        //txtPort.setText("80");
        txtLogin.setText("admin");
        txtPassword.setText("Uniview2018");

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtIP.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "IP can not be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                String VIFUrl = Util.getVIFString(txtLogin.getText().toString(), txtPassword.getText().toString(), txtIP.getText().toString(), txtPort.getText().toString());
                if (!VIFUrl.isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                    intent.setData(Uri.parse(VIFUrl));
                    MainActivity.this.startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "invalid url", Toast.LENGTH_LONG).show();

                }
            }
        });


        btnVIFInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtIP.getText().toString().isEmpty() || txtPort.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "IP and port can not be empty", Toast.LENGTH_LONG).show();
                }
                new CamInfoTask().execute("");

            }
        });
    }

    private class CamInfoTask extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... urls) {


            return "";
        }

        protected void onPostExecute(String result) {
            Log.d(TAG, "Done");
        }
    }
}