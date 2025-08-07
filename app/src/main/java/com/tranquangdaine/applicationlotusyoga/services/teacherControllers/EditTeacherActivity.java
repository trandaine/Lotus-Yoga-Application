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

public class EditTeacherActivity extends AppCompatActivity {
    private EditText editTeacherName, editTeacherBio, editTeacherImageURL;
    private Button btnUpdateTeacher;
    private TeacherDao teacherDao;
    private Teacher teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.activity_create_teacher);

        editTeacherName = findViewById(R.id.editTeacherName);
        editTeacherBio = findViewById(R.id.editTeacherBio);
        btnUpdateTeacher = findViewById(R.id.btnAddTeacher);
        editTeacherImageURL = findViewById(R.id.editTeacherImageUrl);

        teacherDao = LotusYogaDbContext.getInstance(getApplicationContext()).teacherDao();

        int teacherId = getIntent().getIntExtra("teacherId", -1);
        if (teacherId == -1) {
            Toast.makeText(this, "Invalid teacher", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        new Thread(() -> {
            for (Teacher t : teacherDao.getAllTeachers()) {
                if (t.teacherId == teacherId) {
                    teacher = t;
                    break;
                }
            }
            runOnUiThread(() -> {
                if (teacher != null) {
                    editTeacherName.setText(teacher.name);
                    editTeacherBio.setText(teacher.bio);
                    editTeacherImageURL.setText(teacher.imageUrl);
                }
            });
        }).start();

        btnUpdateTeacher.setText("Update");
        btnUpdateTeacher.setOnClickListener(v -> updateTeacher());
    }

    private void updateTeacher() {
        String name = editTeacherName.getText().toString().trim();
        String bio = editTeacherBio.getText().toString().trim();
        String imageUrl = editTeacherImageURL.getText().toString().trim();

        if (name.isEmpty() || bio.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        teacher.name = name;
        teacher.bio = bio;
        teacher.imageUrl = imageUrl;

        new Thread(() -> {
            teacherDao.updateTeacher(teacher);
            runOnUiThread(() -> {
                Toast.makeText(this, "Teacher updated!", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }
}