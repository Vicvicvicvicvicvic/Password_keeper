package com.example.passcodekeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.security.SecureRandom;

public class PasswordGeneratorActivity extends AppCompatActivity {

    private Button generatePasswordButton;
    private TextView generatedPasswordTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_generator);

        generatePasswordButton = findViewById(R.id.generate_password_button);
        generatedPasswordTextView = findViewById(R.id.generated_password_text_view);

        generatePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String generatedPassword = generateRandomPassword(12);
                generatedPasswordTextView.setText(generatedPassword);
            }
        });
    }

    private String generateRandomPassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        SecureRandom random = new SecureRandom();
        StringBuilder passwordBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            passwordBuilder.append(characters.charAt(randomIndex));
        }

        return passwordBuilder.toString();
    }
}
