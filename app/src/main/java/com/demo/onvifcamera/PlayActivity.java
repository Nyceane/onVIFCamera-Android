package com.demo.onvifcamera;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.potterhsu.rtsplibrary.NativeCallback;
import com.potterhsu.rtsplibrary.RtspClient;

public class PlayActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        if(this.getIntent().getData() == null)
        {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
            finish();
        }
        final String mFilePath = this.getIntent().getData().toString();

        final ImageView ivPreview = (ImageView) findViewById(R.id.ivPreview);
        final RtspClient rtspClient = new RtspClient(new NativeCallback() {
            @Override
            public void onFrame(final byte[] frame, final int nChannel, final int width, final int height) {
                ivPreview.post(new Runnable() {
                    @Override
                    public void run() {
                        int area = width * height;
                        int pixels[] = new int[area];
                        for (int i = 0; i < area; i++) {
                            int r = frame[3 * i];
                            int g = frame[3 * i + 1];
                            int b = frame[3 * i + 2];
                            if (r < 0) r += 255;
                            if (g < 0) g += 255;
                            if (b < 0) b += 255;
                            pixels[i] = Color.rgb(r, g, b);
                        }
                        Bitmap bmp = Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888);
                        ivPreview.setImageBitmap(bmp);
                    }
                });
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                rtspClient.play(mFilePath);
            }
        }).start();
    }
}
