package com.example.passcodekeeper;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPasswordActivity extends AppCompatActivity {

    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private EditText descriptionEditText;
    private Button addButton, viewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);

        passwordEditText = findViewById(R.id.password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);
        descriptionEditText = findViewById(R.id.description_edit_text);
        addButton = findViewById(R.id.add_button);
        viewButton = findViewById(R.id.view_button);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passwordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();
                String description = descriptionEditText.getText().toString().trim();

                // Check if passwords meet requirements
                if (TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword) || TextUtils.isEmpty(description)) {
                    Toast.makeText(AddPasswordActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 8 || password.length() > 16) {
                    Toast.makeText(AddPasswordActivity.this, "Password must be between 8 and 16 characters.", Toast.LENGTH_SHORT).show();
                } else if (!password.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,16}$")) {
                    Toast.makeText(AddPasswordActivity.this, "Password must contain at least one number, one letter, one special character, and no whitespace.", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(AddPasswordActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                } else {
                    new PasswordManager(AddPasswordActivity.this).savePassword(new PasswordItem(password, description));
                    Intent intent = new Intent(AddPasswordActivity.this, SavedPasswordsActivity.class);
                    startActivity(intent);
                }
            }
        });

        viewButton.setOnClickListener(view -> {
            Intent intent = new Intent(AddPasswordActivity.this, SavedPasswordsActivity.class);
            startActivity(intent);
        });
    }
}




