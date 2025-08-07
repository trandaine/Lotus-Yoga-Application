package com.tranquangdaine.applicationlotusyoga.services.homeControllers;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tranquangdaine.applicationlotusyoga.R;
import com.tranquangdaine.applicationlotusyoga.infrastructure.LotusYogaDbContext;
import com.tranquangdaine.applicationlotusyoga.infrastructure.firestore.FirestoreService;

public class UploadActivity extends AppCompatActivity {

    private Button uploadCourses, uploadCategories, uploadTeachers, uploadCustomers, uploadTransactions, uploadSuperKey, uploadAll;
    private LotusYogaDbContext dbContext;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_sync_data);

        uploadCourses = findViewById(R.id.btnUploadCourses);
        uploadCategories = findViewById(R.id.btnUploadCategories);
        uploadTeachers = findViewById(R.id.btnUploadTeachers);
        uploadCustomers = findViewById(R.id.btnUploadCustomers);
        uploadTransactions = findViewById(R.id.btnUploadUsersTransactions);
        uploadSuperKey = findViewById(R.id.btnUploadCrossRef);
        uploadAll = findViewById(R.id.btnUploadAllData);

        // Get your database instance (adjust as needed for your project)
        dbContext = LotusYogaDbContext.getInstance(getApplicationContext());

        // Create FirestoreService with DAOs
        FirestoreService firestoreService = new FirestoreService(
                dbContext.userTransactionDao(),
                dbContext.courseDao(),
                dbContext.customerDao(),
                dbContext.teacherDao(),
                dbContext.categoryDao(),
                dbContext.customerCourseCrossRefDao()
        );

        // Set button listeners
        uploadCourses.setOnClickListener(v -> firestoreService.syncCoursesToCloud());
        uploadCategories.setOnClickListener(v -> firestoreService.syncCategoriesToCloud());
        uploadTeachers.setOnClickListener(v -> firestoreService.syncTeachersToCloud());
        uploadCustomers.setOnClickListener(v -> firestoreService.syncCustomersToCloud());
        uploadTransactions.setOnClickListener(v -> firestoreService.syncUserTransactionsToCloud());
        uploadSuperKey.setOnClickListener(v -> firestoreService.syncCourseCustomerCrossRefsToCloud());
        uploadAll.setOnClickListener(v -> firestoreService.syncLocalToCloud());
    }
}
