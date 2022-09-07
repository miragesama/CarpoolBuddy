package com.example.PCCircuit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
/**
 * This activity lists three different ways to contact the company via external site
 *
 * @author adrianlee
 * @version 1.0
 */
public class CustomerContact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_contact);
    }

    public void igButton(View v)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com"));
        startActivity(intent);
    }

    public void linkedinButton(View v)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/company/67520159"));
        startActivity(intent);
    }

    public void discordButton(View v)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://discord.gg/55jFDWjnkh"));
        startActivity(intent);
    }
}