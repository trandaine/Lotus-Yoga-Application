package com.tranquangdaine.applicationlotusyoga.infrastructure.firestore;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.tranquangdaine.applicationlotusyoga.infrastructure.daos.CategoryDao;
import com.tranquangdaine.applicationlotusyoga.infrastructure.daos.CourseDao;
import com.tranquangdaine.applicationlotusyoga.infrastructure.daos.CustomerCourseCrossRefDao;
import com.tranquangdaine.applicationlotusyoga.infrastructure.daos.CustomerDao;
import com.tranquangdaine.applicationlotusyoga.infrastructure.daos.TeacherDao;
import com.tranquangdaine.applicationlotusyoga.infrastructure.daos.UserTransactionDao;
import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Course;
import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.CustomerCourseCrossRef;

import java.util.List;
import java.util.concurrent.Executors;

public class FirestoreService {
    private final CourseDao courseDao;
    private final CustomerDao customerDao;
    private final TeacherDao teacherDao;
    private final UserTransactionDao userTransactionDao;
    private final FirebaseFirestore firestore;
    private final CategoryDao categoryDao;
    private final CustomerCourseCrossRefDao customerCourseCrossRefDao;

    public FirestoreService(UserTransactionDao userTransactionDao, CourseDao courseDao, CustomerDao customerDao, TeacherDao teacherDao, CategoryDao categoryDao, CustomerCourseCrossRefDao customerCourseCrossRefDao) {
        this.customerCourseCrossRefDao = customerCourseCrossRefDao;
        this.categoryDao = categoryDao;
        this.courseDao = courseDao;
        this.customerDao = customerDao;
        this.teacherDao = teacherDao;
        this.userTransactionDao = userTransactionDao;
        firestore = FirebaseFirestore.getInstance();
    }

    public void syncLocalToCloud() {
        try {
            Executors.newSingleThreadExecutor().execute(() -> {
                syncCoursesToCloud();                // Sync courses to cloud
                syncCustomersToCloud();              // Sync customers to cloud
                syncTeachersToCloud();               // Sync teachers to cloud
                syncCategoriesToCloud();             // Sync categories to cloud
                syncUserTransactionsToCloud();       // Sync user transactions to cloud
                syncCourseCustomerCrossRefsToCloud(); // Sync course-customer cross-references to cloud
            });
        } catch (Exception e) {
            Log.e("FirebaseSyncService", "Error syncing local data to cloud", e);
        }
    }

    // FirestoreService.java
    public void syncCoursesToCloud() {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                List<Course> localCourses = courseDao.getAllCourses();
                if (localCourses.isEmpty()) {
                    Log.w("FirebaseSyncService", "Local courses empty, skipping cloud deletion.");
                    return;
                }
                List<String> localIds = new java.util.ArrayList<>();
                for (Course c : localCourses) {
                    localIds.add(String.valueOf(c.courseId));
                    firestore.collection("courses")
                            .document(String.valueOf(c.courseId))
                            .set(c)
                            .addOnFailureListener(e -> Log.e("FirebaseSyncService", "Failed to sync course: " + c.courseId, e));
                }
                firestore.collection("courses").get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            for (var document : queryDocumentSnapshots) {
                                String cloudId = document.getId();
                                if (!localIds.contains(cloudId)) {
                                    firestore.collection("courses").document(cloudId).delete()
                                            .addOnSuccessListener(aVoid -> Log.d("FirebaseSyncService", "Deleted cloud course: " + cloudId))
                                            .addOnFailureListener(e -> Log.e("FirebaseSyncService", "Failed to delete cloud course: " + cloudId, e));
                                }
                            }
                        })
                        .addOnFailureListener(e -> Log.e("FirebaseSyncService", "Failed to fetch cloud courses", e));
            } catch (Exception e) {
                Log.e("FirebaseSyncService", "Error syncing courses to cloud", e);
            }
        });
    }

    public void syncCustomersToCloud() {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                List<com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Customer> localCustomers = customerDao.getAllCustomers();
                if (localCustomers.isEmpty()) {
                    Log.w("FirebaseSyncService", "Local customers empty, skipping cloud deletion.");
                    return;
                }
                List<String> localIds = new java.util.ArrayList<>();
                for (com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Customer c : localCustomers) {
                    localIds.add(String.valueOf(c.customerId));
                    firestore.collection("customers")
                            .document(String.valueOf(c.customerId))
                            .set(c)
                            .addOnFailureListener(e -> Log.e("FirebaseSyncService", "Failed to sync customer: " + c.customerId, e));
                }

                firestore.collection("customers").get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            for (var document : queryDocumentSnapshots) {
                                String cloudId = document.getId();
                                if (!localIds.contains(cloudId)) {
                                    firestore.collection("customers").document(cloudId).delete()
                                            .addOnSuccessListener(aVoid -> Log.d("FirebaseSyncService", "Deleted cloud customer: " + cloudId))
                                            .addOnFailureListener(e -> Log.e("FirebaseSyncService", "Failed to delete cloud customer: " + cloudId, e));
                                }
                            }
                        })
                        .addOnFailureListener(e -> Log.e("FirebaseSyncService", "Failed to fetch cloud customers", e));
            } catch (Exception e) {
                Log.e("FirebaseSyncService", "Error syncing customers to cloud", e);
            }
        });
    }

    public void syncTeachersToCloud() {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                List<com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Teacher> localTeachers = teacherDao.getAllTeachers();
                if (localTeachers.isEmpty()) {
                    Log.w("FirebaseSyncService", "Local teachers empty, skipping cloud deletion.");
                    return;
                }
                List<String> localIds = new java.util.ArrayList<>();
                for (com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Teacher t : localTeachers) {
                    localIds.add(String.valueOf(t.teacherId));
                    firestore.collection("teachers")
                            .document(String.valueOf(t.teacherId))
                            .set(t)
                            .addOnFailureListener(e -> Log.e("FirebaseSyncService", "Failed to sync teacher: " + t.teacherId, e));
                }

                firestore.collection("teachers").get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            for (var document : queryDocumentSnapshots) {
                                String cloudId = document.getId();
                                if (!localIds.contains(cloudId)) {
                                    firestore.collection("teachers").document(cloudId).delete()
                                            .addOnSuccessListener(aVoid -> Log.d("FirebaseSyncService", "Deleted cloud teacher: " + cloudId))
                                            .addOnFailureListener(e -> Log.e("FirebaseSyncService", "Failed to delete cloud teacher: " + cloudId, e));
                                }
                            }
                        })
                        .addOnFailureListener(e -> Log.e("FirebaseSyncService", "Failed to fetch cloud teachers", e));
            } catch (Exception e) {
                Log.e("FirebaseSyncService", "Error syncing teachers to cloud", e);
            }
        });
    }

    public void syncCategoriesToCloud() {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                List<com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Category> localCategories = categoryDao.getAllCategories();
                if (localCategories.isEmpty()) {
                    Log.w("FirebaseSyncService", "Local categories empty, skipping cloud deletion.");
                    return;
                }
                List<String> localIds = new java.util.ArrayList<>();
                for (com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Category c : localCategories) {
                    localIds.add(String.valueOf(c.categoryId));
                    firestore.collection("categories")
                            .document(String.valueOf(c.categoryId))
                            .set(c)
                            .addOnFailureListener(e -> Log.e("FirebaseSyncService", "Failed to sync category: " + c.categoryId, e));
                }

                firestore.collection("categories").get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            for (var document : queryDocumentSnapshots) {
                                String cloudId = document.getId();
                                if (!localIds.contains(cloudId)) {
                                    firestore.collection("categories").document(cloudId).delete()
                                            .addOnSuccessListener(aVoid -> Log.d("FirebaseSyncService", "Deleted cloud category: " + cloudId))
                                            .addOnFailureListener(e -> Log.e("FirebaseSyncService", "Failed to delete cloud category: " + cloudId, e));
                                }
                            }
                        })
                        .addOnFailureListener(e -> Log.e("FirebaseSyncService", "Failed to fetch cloud categories", e));
            } catch (Exception e) {
                Log.e("FirebaseSyncService", "Error syncing categories to cloud", e);
            }
        });
    }

    public void syncUserTransactionsToCloud() {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                List<com.tranquangdaine.applicationlotusyoga.infrastructure.entities.UserTransaction> localTransactions = userTransactionDao.getAllUserTransactions();
                if (localTransactions.isEmpty()) {
                    Log.w("FirebaseSyncService", "Local user transactions empty, skipping cloud deletion.");
                    return;
                }
                List<String> localIds = new java.util.ArrayList<>();
                for (com.tranquangdaine.applicationlotusyoga.infrastructure.entities.UserTransaction ut : localTransactions) {
                    localIds.add(String.valueOf(ut.transactionId));
                    firestore.collection("user_transactions")
                            .document(String.valueOf(ut.transactionId))
                            .set(ut)
                            .addOnFailureListener(e -> Log.e("FirebaseSyncService", "Failed to sync user transaction: " + ut.transactionId, e));
                }

                firestore.collection("user_transactions").get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            for (var document : queryDocumentSnapshots) {
                                String cloudId = document.getId();
                                if (!localIds.contains(cloudId)) {
                                    firestore.collection("user_transactions").document(cloudId).delete()
                                            .addOnSuccessListener(aVoid -> Log.d("FirebaseSyncService", "Deleted cloud user transaction: " + cloudId))
                                            .addOnFailureListener(e -> Log.e("FirebaseSyncService", "Failed to delete cloud user transaction: " + cloudId, e));
                                }
                            }
                        })
                        .addOnFailureListener(e -> Log.e("FirebaseSyncService", "Failed to fetch cloud user transactions", e));
            } catch (Exception e) {
                Log.e("FirebaseSyncService", "Error syncing user transactions to cloud", e);
            }
        });
    }

    public void syncCourseCustomerCrossRefsToCloud() {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                List<CustomerCourseCrossRef> crossRefs = customerCourseCrossRefDao.getAllRefs();
                for (CustomerCourseCrossRef ref : crossRefs) {
                    String docId = ref.customerId + "_" + ref.courseId;
                    firestore.collection("customer_course_cross_ref")
                            .document(docId)
                            .set(ref)
                            .addOnFailureListener(e -> {
                                Log.e("FirebaseSyncService", "Failed to sync cross-ref: " + docId, e);
                            });
                }
            } catch (Exception e) {
                Log.e("FirebaseSyncService", "Error syncing course-customer cross-refs to cloud", e);
            }
        });
    }
}
