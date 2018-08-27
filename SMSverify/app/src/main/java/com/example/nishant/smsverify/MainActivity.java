package com.example.nishant.smsverify;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText pn,text;

    int PERMISSION_REQUEST_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pn=(EditText)findViewById(R.id.pn);
        text=(EditText)findViewById(R.id.text);
    }

    public void maketext(final View view) {

        String phone=pn.getText().toString();
        String textsend = text.getText().toString();

        pn.setText("");
        text.setText("");

        Toast.makeText(this, "SMS Sent", Toast.LENGTH_SHORT).show();

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

                if (shouldShowRequestPermissionRationale(Manifest.permission.SEND_SMS)) {

                    new AlertDialog.Builder(MainActivity.this)

                            .setTitle("Text Permission")

                            .setMessage("How am I supposed to make text without your permission? Please give the text permission.")

                            .setPositiveButton("Okay", new DialogInterface.OnClickListener() {

                                @Override

                                public void onClick(DialogInterface dialogInterface, int i) {

                                    requestPermissions(new String[]
                                            {Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_CODE);

                                }

                            })

                            .setNegativeButton("No thanks", new DialogInterface.OnClickListener() {

                                @Override

                                public void onClick(DialogInterface dialogInterface, int i) {

                                    Toast.makeText(MainActivity.this, "Please grant the permission!", Toast.LENGTH_SHORT).show();

                                }

                            }).show();

                } else {

                    requestPermissions(new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_CODE);

                }

            } else {

                text(phone,textsend);

            }

        } else {

          text(phone,textsend);

        }
    }

    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        String phone=pn.getText().toString();
        String textsend = text.getText().toString();

        if (requestCode == PERMISSION_REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                text(phone,textsend);

            } else {

                Toast.makeText(this, "Please grant the permission!", Toast.LENGTH_SHORT).show();

            }

        }

    }

    private void text(String n,String text) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(n, null, text, null, null);

    }
}
