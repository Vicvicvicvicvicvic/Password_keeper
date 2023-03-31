package com.example.passcodekeeper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.content.SharedPreferences;
import com.google.gson.Gson;


public class SavedPasswordsAdapter extends RecyclerView.Adapter<SavedPasswordsAdapter.SavedPasswordsViewHolder> {

    private List<PasswordItem> passwordItems;
    private Context context;
    private PasswordManager passwordManager;

    public SavedPasswordsAdapter(Context context, List<PasswordItem> passwordItems, PasswordManager passwordManager) {
        this.context = context;
        this.passwordItems = passwordItems;
        this.passwordManager = passwordManager;
    }

    @NonNull
    @Override
    public SavedPasswordsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.password_item, parent, false);
        return new SavedPasswordsViewHolder(view);
    }

    private void removeItem(int position) {
        passwordItems.remove(position);
        passwordManager.savePasswordsToSharedPreferences(passwordItems);
        notifyItemRemoved(position);
    }

    private void savePasswordsToSharedPreferences() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(passwordItems);
        editor.putString("password_list", json);
        editor.apply();
    }

    @Override
    public void onBindViewHolder(@NonNull SavedPasswordsViewHolder holder, int position) {
        PasswordItem currentItem = passwordItems.get(position);
        //add
        holder.descriptionTextView.setText(currentItem.getDescription());
        holder.passwordTextView.setText(currentItem.getPassword());
        //remove
        holder.removebutton.setOnClickListener(view -> {
            int currentPosition = holder.getAdapterPosition();
            removeItem(currentPosition);
        });
    }

    @Override
    public int getItemCount() {
        return passwordItems.size();
    }

    public static class SavedPasswordsViewHolder extends RecyclerView.ViewHolder {
        TextView descriptionTextView;
        TextView passwordTextView;
        Button removebutton;


        public SavedPasswordsViewHolder(@NonNull View itemView) {
            super(itemView);
            descriptionTextView = itemView.findViewById(R.id.description_text_view);
            passwordTextView = itemView.findViewById(R.id.password_text_view);
            removebutton = itemView.findViewById(R.id.remove_button);
        }
    }
}

