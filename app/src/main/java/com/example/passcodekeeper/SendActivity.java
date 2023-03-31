package com.example.passcodekeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SendActivity extends AppCompatActivity {

    private EditText phoneNumberEditText, emailEditText, emailSubjectEditText, descriptionEditText;
    private Button sendButton, viewExistingButton;
    private TextView existingPasswordsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        phoneNumberEditText = findViewById(R.id.phone_number_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        emailSubjectEditText = findViewById(R.id.email_subject_edit_text);
        descriptionEditText = findViewById(R.id.description_edit_text);
        sendButton = findViewById(R.id.send_button);
        viewExistingButton = findViewById(R.id.view_existing_button);
        existingPasswordsTextView = findViewById(R.id.view_existing_button);

        viewExistingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SendActivity.this, SavedPasswordsActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = phoneNumberEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String emailSubject = emailSubjectEditText.getText().toString().trim();
                String description = descriptionEditText.getText().toString().trim();

                if (TextUtils.isEmpty(phoneNumber) && TextUtils.isEmpty(email)) {
                    Toast.makeText(SendActivity.this, "Please enter a phone number or email.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!TextUtils.isEmpty(phoneNumber)) {
                    sendSMS(phoneNumber, description);
                }

                if (!TextUtils.isEmpty(email)) {
                    sendEmail(email, emailSubject, description);
                }

                Toast.makeText(SendActivity.this, "Message sent successfully!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void sendSMS(String phoneNumber, String message) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        } else {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "SMS permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "SMS permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void sendEmail(String email, String subject, String message) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);

        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(emailIntent, "Choose an email client:"));
        } else {
            Toast.makeText(SendActivity.this, "No email client found.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                PasswordItem passwordItem = data.getParcelableExtra("passwordItem");
                if (passwordItem != null) {
                    existingPasswordsTextView.setText("Password: " + passwordItem.getPassword() + "  Description: " + passwordItem.getDescription());
                }
            }
        }
    }
}
