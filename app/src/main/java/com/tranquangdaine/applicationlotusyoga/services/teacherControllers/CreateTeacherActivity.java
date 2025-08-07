package com.tranquangdaine.applicationlotusyoga.services.teacherControllers;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.tranquangdaine.applicationlotusyoga.R;
import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Teacher;
import com.tranquangdaine.applicationlotusyoga.infrastructure.daos.TeacherDao;
import com.tranquangdaine.applicationlotusyoga.infrastructure.LotusYogaDbContext;

public class CreateTeacherActivity extends AppCompatActivity {
    private EditText editTeacherName, editTeacherBio, editTeacherImageURL;
    private Button btnAddTeacher;
    private TeacherDao teacherDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_create_teacher);

        editTeacherName = findViewById(R.id.editTeacherName);
        editTeacherBio = findViewById(R.id.editTeacherBio);
        btnAddTeacher = findViewById(R.id.btnAddTeacher);
        editTeacherImageURL = findViewById(R.id.editTeacherImageUrl);

        teacherDao = LotusYogaDbContext.getInstance(getApplicationContext()).teacherDao();

        btnAddTeacher.setOnClickListener(v -> addTeacher());
    }

    private void addTeacher() {
        String name = editTeacherName.getText().toString().trim();
        String bio = editTeacherBio.getText().toString().trim();
        String imageUrl = editTeacherImageURL.getText().toString().trim();

        if (name.isEmpty() || bio.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Teacher teacher = new Teacher(name, bio, imageUrl);

        new Thread(() -> {
            long id = teacherDao.insertTeacher(teacher);
            runOnUiThread(() -> {
                if (id > 0) {
                    Toast.makeText(this, "Teacher added!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Failed to add teacher", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }
}