package com.tranquangdaine.applicationlotusyoga.services.categoryControllers;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.tranquangdaine.applicationlotusyoga.R;
import com.tranquangdaine.applicationlotusyoga.infrastructure.LotusYogaDbContext;
import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Category;
import com.tranquangdaine.applicationlotusyoga.infrastructure.daos.CategoryDao;

public class CreateCategoryActivity extends AppCompatActivity {

    private TextInputEditText editTextName, editTextDescription;
    private MaterialButton buttonSave;
    private LotusYogaDbContext dbContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_create_category);

        editTextName = findViewById(R.id.editTextCategoryName);
        editTextDescription = findViewById(R.id.editTextCategoryDescription);
        buttonSave = findViewById(R.id.buttonSaveCategory);

        buttonSave.setOnClickListener(v -> {
            String name = editTextName.getText() != null ? editTextName.getText().toString().trim() : "";
            String description = editTextDescription.getText() != null ? editTextDescription.getText().toString().trim() : "";

            if (TextUtils.isEmpty(name)) {
                Toast.makeText(this, "Name is required", Toast.LENGTH_SHORT).show();
                return;
            }

            Category category = new Category(name, description);

            // Insert category in a background thread
            new Thread(() -> {
                dbContext = LotusYogaDbContext.getInstance(getApplicationContext());
                CategoryDao categoryDao = dbContext.categoryDao();
                categoryDao.insertCategory(category);

                runOnUiThread(() -> {
                    Toast.makeText(this, "Category created", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }).start();
        });
    }
}