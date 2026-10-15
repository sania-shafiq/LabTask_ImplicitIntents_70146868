package com.example.uolhelpdesk;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RequestActivity extends AppCompatActivity {

    TextView tvGreeting;
    EditText etPhone, etLink;
    Button btnCallNumber, btnOpenLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        tvGreeting = findViewById(R.id.tvGreeting);
        etPhone = findViewById(R.id.etPhone);
        etLink = findViewById(R.id.etLink);
        btnCallNumber = findViewById(R.id.btnCallNumber);
        btnOpenLink = findViewById(R.id.btnOpenLink);

        // Name receive karna
        String name = getIntent().getStringExtra(MainActivity.EXTRA_NAME);
        if (name == null || name.trim().isEmpty()) {
            name = "Student";
        }
        tvGreeting.setText("Hello, " + name + "! How can we help you today?");

        // Call This Number button
        btnCallNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = etPhone.getText().toString().trim();

                // Sirf 11 digits aur 03 se shuru
                if (!number.matches("03[0-9]{9}")) {
                    etPhone.setError("Enter 11 digits starting with 03");
                } else {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
                    MainActivity.openIntent(RequestActivity.this, intent);
                }
            }
        });

        // Open Link button
        btnOpenLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = etLink.getText().toString().trim();

                if (link.isEmpty()) {
                    etLink.setError("Please enter a link");
                } else {
                    // Agar http:// ya https:// nahi hai to https:// laga do
                    if (!link.startsWith("http://") && !link.startsWith("https://")) {
                        link = "https://" + link;
                    }
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    MainActivity.openIntent(RequestActivity.this, intent);
                }
            }
        });
    }
}