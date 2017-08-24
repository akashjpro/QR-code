package com.example.tmha.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private ImageView mImgPhoto;
    private TextView mTxtName, mTxtAddress;
    private Button mBtnScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImgPhoto = (ImageView) findViewById(R.id.img_hinh);
        mTxtAddress = (TextView) findViewById(R.id.txt_dia_chi);
        mTxtName    = (TextView) findViewById(R.id.txt_name);
        mBtnScan = (Button) findViewById(R.id.btn_scan);

        final IntentIntegrator integrator = new IntentIntegrator(this);

        mBtnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                integrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Picasso.with(this).load(result.getContents()).into(mImgPhoto);
                try {
                    JSONObject jsonObject = new JSONObject(result.getContents());
                    mTxtName.setText(jsonObject.getString("name"));
                    mTxtAddress.setText(jsonObject.getString("diachi"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onClick(View view) {
        startActivity(new Intent(MainActivity.this, ScanQRActivity.class));
    }
}
