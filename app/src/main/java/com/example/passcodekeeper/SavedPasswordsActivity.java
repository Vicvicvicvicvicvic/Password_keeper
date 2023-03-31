package com.example.passcodekeeper;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SavedPasswordsActivity extends AppCompatActivity {

    private RecyclerView savedPasswordsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_passwords);

        savedPasswordsRecyclerView = findViewById(R.id.saved_passwords_recycler_view);
        savedPasswordsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        PasswordManager passwordManager = new PasswordManager(this);
        List<PasswordItem> passwordItems = passwordManager.getPasswords();

        if (passwordItems.isEmpty()) {
            Toast.makeText(this, "No savedpasswords found.", Toast.LENGTH_SHORT).show();
        } else {
            SavedPasswordsAdapter savedPasswordsAdapter = new SavedPasswordsAdapter(this, passwordItems, passwordManager);
            savedPasswordsRecyclerView.setAdapter(savedPasswordsAdapter);
        }


    }
}




