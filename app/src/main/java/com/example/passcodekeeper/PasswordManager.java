package com.example.passcodekeeper;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class PasswordManager {

    private SharedPreferences sharedPreferences;
    private Gson gson;

    public PasswordManager(Context context) {
        try {
            MasterKey masterKey = new MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            sharedPreferences = EncryptedSharedPreferences.create(
                    context,
                    "password_keeper",
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
        gson = new Gson();
    }

    public void savePassword(PasswordItem passwordItem) {
        String json = sharedPreferences.getString(" passwords ", null);
        Type type = new TypeToken<List<PasswordItem>>() {}.getType();
        List<PasswordItem> passwordItems;

        if (json == null) {
            passwordItems = new ArrayList<>();
        } else {
            passwordItems = gson.fromJson(json, type);
        }

        passwordItems.add(passwordItem);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(" passwords ", gson.toJson(passwordItems));
        editor.apply();
    }

    public List<PasswordItem> getPasswords() {
        String json = sharedPreferences.getString(" passwords ", null);
        Type type = new TypeToken<List<PasswordItem>>() {}.getType();
        return json == null ? new ArrayList<>() : gson.fromJson(json, type);
    }

    public void savePasswordsToSharedPreferences(List<PasswordItem> passwordItems) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(" passwords ", gson.toJson(passwordItems));
        editor.apply();
    }
}

