package com.demo.onvifcamera;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText txtIP;
    EditText txtPort;
    EditText txtLogin;
    EditText txtPassword;
    Button btnPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtIP = (EditText)findViewById(R.id.txtIP);
        txtPort = (EditText)findViewById(R.id.txtPort);
        txtLogin = (EditText)findViewById(R.id.txtLogin);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        btnPlay = (Button)findViewById(R.id.btnPlay);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtIP.getText().toString().isEmpty() || txtPort.getText().toString().isEmpty())
                {
                    Toast.makeText(MainActivity.this, "IP and port can not be empty", Toast.LENGTH_LONG).show();
                }
                String VIFUrl = Util.getVIFString(txtLogin.getText().toString(), txtPassword.getText().toString(), txtIP.getText().toString(), Integer.valueOf(txtPort.getText().toString()));
                if(!VIFUrl.isEmpty())
                {
                    Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                    intent.setData(Uri.parse(VIFUrl));
                    MainActivity.this.startActivity(intent);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "invalid url", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}
