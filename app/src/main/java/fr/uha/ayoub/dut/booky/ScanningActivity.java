package fr.uha.ayoub.dut.booky;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanningActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{


    int MY_PERMISSIONS_REQUEST_CAMERA = 0;


    ZXingScannerView Scanv ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Scanv = new ZXingScannerView(this) ;
        setContentView(Scanv);

    }





    @Override
    public void handleResult(Result result) {
        String isbn = result.getText() ;
        Intent intent = new Intent();
        intent.putExtra("ISBN",isbn) ;
        setResult(RESULT_OK,intent);
        finish();

    }




    @Override
    protected void onPause() {
        super.onPause();
        Scanv.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Scanv.setResultHandler(this);
        Scanv.startCamera();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},MY_PERMISSIONS_REQUEST_CAMERA);
        }
        Scanv.setResultHandler(this);
        Scanv.startCamera();

    }
}
