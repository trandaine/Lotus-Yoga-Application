package com.tranquangdaine.applicationlotusyoga.services.courseControllers;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.tranquangdaine.applicationlotusyoga.R;
import com.tranquangdaine.applicationlotusyoga.infrastructure.LotusYogaDbContext;
import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Course;
import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Category;
import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Teacher;
import java.util.List;

public class CreateCourseActivity extends AppCompatActivity {

    private LotusYogaDbContext DbContext;
    private List<Category> categories;
    private List<Teacher> teachers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_create_course);

        DbContext = LotusYogaDbContext.getInstance(getApplicationContext());

        AutoCompleteTextView categoryDropdown = findViewById(R.id.autoCompleteCourseCategory);
        AutoCompleteTextView teacherDropdown = findViewById(R.id.autoCompleteCourseTeacher);
        EditText courseName = findViewById(R.id.editTextCourseName);
        EditText courseDescription = findViewById(R.id.editDescription);
        EditText courseImageUrl = findViewById(R.id.editImageUrl);
        AutoCompleteTextView courseLevel = findViewById(R.id.autoCompleteLevel);
        AutoCompleteTextView courseRoom = findViewById(R.id.autoCompleteRoom);
        EditText courseDuration = findViewById(R.id.editTextDuration);
        EditText coursePrice = findViewById(R.id.editTextPrice);
        AutoCompleteTextView courseAutoCompleteLevel = findViewById(R.id.autoCompleteLevel);
        AutoCompleteTextView courseAutoCompleteRoom = findViewById(R.id.autoCompleteRoom);


        // Fetch categories and teachers from DB (should be done in background thread)
        new Thread(() -> {
            categories = DbContext.categoryDao().getAllCategories();
            teachers = DbContext.teacherDao().getAllTeachers();

            runOnUiThread(() -> {
                ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_dropdown_item_1line,
                        categories.stream().map(Category::getName).toArray(String[]::new));
                categoryDropdown.setAdapter(categoryAdapter);

                String[] levels = {"Beginner", "Intermediate", "Advanced", "VIP"};
                ArrayAdapter<String> level = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, levels);
                courseAutoCompleteLevel.setAdapter(level);
                courseAutoCompleteLevel.setText("", false);
                String[] rooms = {"F.201", "F.202", "F.203", "F.214"};
                ArrayAdapter<String> room = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, rooms);
                courseAutoCompleteRoom.setAdapter(room);
                courseAutoCompleteRoom.setText("", false);

                ArrayAdapter<String> teacherAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_dropdown_item_1line,
                        teachers.stream().map(Teacher::getName).toArray(String[]::new));
                teacherDropdown.setAdapter(teacherAdapter);
            });
        }).start();

        Button btnSave = findViewById(R.id.btnSaveClass);
        btnSave.setOnClickListener(v -> {
            String name = courseName.getText().toString().trim();
            String description = courseDescription.getText().toString().trim();
            String imageUrl = courseImageUrl.getText().toString().trim();
            String level = courseLevel.getText().toString().trim();
            String room = courseRoom.getText().toString().trim();
            String durationStr = courseDuration.getText().toString().trim();
            String priceStr = coursePrice.getText().toString().trim();
            String selectedCategory = categoryDropdown.getText().toString().trim();
            String selectedTeacher = teacherDropdown.getText().toString().trim();

            // Validate required fields
            if (name.isEmpty() || description.isEmpty() || imageUrl.isEmpty() || level.isEmpty() || room.isEmpty() ||
                    durationStr.isEmpty() || priceStr.isEmpty() || selectedCategory.isEmpty() || selectedTeacher.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate numeric fields
            int duration;
            double price;
            try {
                duration = Integer.parseInt(durationStr);
                if (duration <= 0) {
                    Toast.makeText(this, "Duration must be greater than 0.", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid duration value.", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                price = Double.parseDouble(priceStr);
                if (price < 0) {
                    Toast.makeText(this, "Price cannot be negative.", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid price value.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate category and teacher selection
            int categoryId = categories.stream()
                    .filter(c -> c.getName().equals(selectedCategory))
                    .findFirst().map(Category::getId).orElse(0);
            if (categoryId == 0) {
                Toast.makeText(this, "Invalid category selected.", Toast.LENGTH_SHORT).show();
                return;
            }

            int teacherId = teachers.stream()
                    .filter(t -> t.getName().equals(selectedTeacher))
                    .findFirst().map(Teacher::getId).orElse(0);
            if (teacherId == 0) {
                Toast.makeText(this, "Invalid teacher selected.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create and save the course
            Course course = new Course(name, description, imageUrl, teacherId, categoryId, duration, price, level, room);

            new Thread(() -> {
                DbContext.courseDao().insertCourse(course);
                runOnUiThread(() -> Toast.makeText(this, "Course created!", Toast.LENGTH_SHORT).show());
                finish();
            }).start();
        });
    }
}