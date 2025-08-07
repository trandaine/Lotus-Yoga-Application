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

public class EditCourseActivty extends AppCompatActivity {

    private LotusYogaDbContext dbContext;
    private List<Category> categories;
    private List<Teacher> teachers;
    private Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.activity_create_course);

        dbContext = LotusYogaDbContext.getInstance(getApplicationContext());

        int courseId = getIntent().getIntExtra("courseId", 0);
        if (courseId == 0) {
            Toast.makeText(this, "Invalid course ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        AutoCompleteTextView categoryDropdown = findViewById(R.id.autoCompleteCourseCategory);
        AutoCompleteTextView teacherDropdown = findViewById(R.id.autoCompleteCourseTeacher);
        EditText courseName = findViewById(R.id.editTextCourseName);
        EditText courseDescription = findViewById(R.id.editDescription);
        EditText courseImageUrl = findViewById(R.id.editImageUrl);
        AutoCompleteTextView courseLevel = findViewById(R.id.autoCompleteLevel);
        AutoCompleteTextView courseRoom = findViewById(R.id.autoCompleteRoom);
        EditText courseDuration = findViewById(R.id.editTextDuration);
        EditText coursePrice = findViewById(R.id.editTextPrice);

        new Thread(() -> {
            categories = dbContext.categoryDao().getAllCategories();
            teachers = dbContext.teacherDao().getAllTeachers();
            course = dbContext.courseDao().getCourseById(courseId);

            runOnUiThread(() -> {
                ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_dropdown_item_1line,
                        categories.stream().map(Category::getName).toArray(String[]::new));
                categoryDropdown.setAdapter(categoryAdapter);

                ArrayAdapter<String> teacherAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_dropdown_item_1line,
                        teachers.stream().map(Teacher::getName).toArray(String[]::new));
                teacherDropdown.setAdapter(teacherAdapter);

                String[] levels = {"Beginner", "Intermediate", "Advanced", "VIP"};
                ArrayAdapter<String> levelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, levels);
                courseLevel.setAdapter(levelAdapter);

                String[] rooms = {"F.201", "F.202", "F.203", "F.214"};
                ArrayAdapter<String> roomAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, rooms);
                courseRoom.setAdapter(roomAdapter);

                if (course != null) {
                    courseName.setText(course.getName());
                    courseDescription.setText(course.getDescription());
                    courseImageUrl.setText(course.getImageUrl());
                    courseLevel.setText(course.getLevel(), false);
                    courseRoom.setText(course.getRoom(), false);
                    courseDuration.setText(String.valueOf(course.getDuration()));
                    coursePrice.setText(String.valueOf(course.getPrice()));

                    Category selectedCategory = categories.stream()
                            .filter(c -> c.getId() == course.getCategoryId())
                            .findFirst().orElse(null);
                    if (selectedCategory != null)
                        categoryDropdown.setText(selectedCategory.getName(), false);

                    Teacher selectedTeacher = teachers.stream()
                            .filter(t -> t.getId() == course.getTeacherId())
                            .findFirst().orElse(null);
                    if (selectedTeacher != null)
                        teacherDropdown.setText(selectedTeacher.getName(), false);
                }
            });
        }).start();

        Button btnSave = findViewById(R.id.btnSaveClass);
        btnSave.setText("Save Changes");
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

            if (name.isEmpty() || description.isEmpty() || imageUrl.isEmpty() || level.isEmpty() || room.isEmpty() ||
                    durationStr.isEmpty() || priceStr.isEmpty() || selectedCategory.isEmpty() || selectedTeacher.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                return;
            }

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

            new Thread(() -> {
                course.setName(name);
                course.setDescription(description);
                course.setImageUrl(imageUrl);
                course.setLevel(level);
                course.setRoom(room);
                course.setDuration(duration);
                course.setPrice(price);
                course.setCategoryId(categoryId);
                course.setTeacherId(teacherId);

                dbContext.courseDao().updateCourse(course);
                runOnUiThread(() -> Toast.makeText(this, "Course updated!", Toast.LENGTH_SHORT).show());
                finish();
            }).start();
        });
    }
}