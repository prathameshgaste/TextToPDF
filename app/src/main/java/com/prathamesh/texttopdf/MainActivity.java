package com.prathamesh.texttopdf;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import java.io.File;
import java.io.FileOutputStream;


public class MainActivity extends AppCompatActivity {
    private EditText editText,fileName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        fileName = findViewById(R.id.fileName);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
    }

    @SuppressLint("SetTextI18n")
    public void createPDF(View view){

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder( 595,842,1).create();
        PdfDocument.Page myPage = document.startPage(myPageInfo);

        Paint myPaint = new Paint();
        String myString = editText.getText().toString();
        String nm = fileName.getText().toString();
        int x = 10, y=25;
        for (String line:myString.split("\n")){
            myPage.getCanvas().drawText(line, x, y, myPaint);
            y+=myPaint.descent()-myPaint.ascent();
        }

        document.finishPage(myPage);

        String myFilePath = Environment.getExternalStorageDirectory().getPath() + "/"+nm+".pdf";
        File myFile = new File(myFilePath);
        try {
            document.writeTo(new FileOutputStream(myFile));
            Toast.makeText(MainActivity.this, "Created Successfully as :"+myFilePath, Toast.LENGTH_LONG).show();
        }
        catch (Exception e){
            e.printStackTrace();
            editText.setText("ERROR");
        }

        document.close();
    }
}
