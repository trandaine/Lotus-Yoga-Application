package com.tranquangdaine.applicationlotusyoga.services.categoryControllers;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.tranquangdaine.applicationlotusyoga.R;
import com.tranquangdaine.applicationlotusyoga.infrastructure.LotusYogaDbContext;
import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Category;
import com.tranquangdaine.applicationlotusyoga.infrastructure.daos.CategoryDao;

public class EditCategoryActivity extends AppCompatActivity {

    private EditText editTextName, editTextDescription;
    private TextView textViewTitle;
    private Button buttonSave;
    private LotusYogaDbContext dbContext;
    private Category category;
    private CategoryDao categoryDao;

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
        textViewTitle = findViewById(R.id.textViewTitle);

        int categoryId = getIntent().getIntExtra("categoryId", -1);
        if (categoryId == -1) {
            Toast.makeText(this, "Invalid category", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        textViewTitle.setText("Edit Category");
        dbContext = LotusYogaDbContext.getInstance(getApplicationContext());
        categoryDao = dbContext.categoryDao();

        new Thread(() -> {
            category = null;
            for (Category c : categoryDao.getAllCategories()) {
                if (c.getId() == categoryId) {
                    category = c;
                    break;
                }
            }
            runOnUiThread(() -> {
                if (category != null) {
                    editTextName.setText(category.getName());
                    editTextDescription.setText(category.getDescription());
                } else {
                    Toast.makeText(this, "Category not found", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }).start();
        buttonSave.setText("Update Category");
        buttonSave.setOnClickListener(v -> updateCategory());


    }

    private void updateCategory() {
        String name = editTextName.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Name is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (category == null) return;

        category.setName(name);
        category.setDescription(description);

        new Thread(() -> {
            dbContext.categoryDao().updateCategory(category);
            runOnUiThread(() -> {
                Toast.makeText(this, "Category updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }
}