//package com.tranquangdaine.applicationlotusyoga.ui.courses;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.recyclerview.widget.LinearLayoutManager;
//
//import com.tranquangdaine.applicationlotusyoga.databinding.FragmentCourseBinding;
//import com.tranquangdaine.applicationlotusyoga.infrastructure.LotusYogaDbContext;
//import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Category;
//import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Course;
//import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Teacher;
//import com.tranquangdaine.applicationlotusyoga.services.courseControllers.CreateCourseActivity;
//import com.tranquangdaine.applicationlotusyoga.services.courseControllers.EditCourseActivty;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class CourseFragment extends Fragment {
//
//    private FragmentCourseBinding binding;
//    private CourseAdapter adapter;
//    private LotusYogaDbContext dbContext;
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        binding = FragmentCourseBinding.inflate(inflater, container, false);
//        return binding.getRoot();
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        dbContext = LotusYogaDbContext.getInstance(requireContext());
//        binding.floatingActionButtonAddCourse.setOnClickListener(v -> {
//            Intent intent = new Intent(requireContext(), CreateCourseActivity.class);
//            startActivity(intent);
//        });
//        adapter = new CourseAdapter(new ArrayList<>());
//        adapter.setOnEditClickListener(course -> {
//            Intent intent = new Intent(requireContext(), EditCourseActivty.class);
//            intent.putExtra("courseId", course.courseId);
//            startActivity(intent);
//        });
//        adapter.setOnDeleteClickListener((course) -> {
//            new Thread(() -> {
//                dbContext.courseDao().deleteCourse(course);
//                List<Course> courses = dbContext.courseDao().getAllCourses();
//                requireActivity().runOnUiThread(() -> {
//                    adapter = new CourseAdapter(courses);
//                    // Re-set category and teacher lists if needed
//                    adapter.setCategoryList(dbContext.categoryDao().getAllCategories());
//                    adapter.setTeacherList(dbContext.teacherDao().getAllTeachers());
//                    binding.coursesRecyclerView.setAdapter(adapter);
//                });
//            }).start();
//        });
//        binding.coursesRecyclerView.setAdapter(adapter);
//        binding.coursesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//
//
//        new Thread(() -> {
//            List<Course> courses = dbContext.courseDao().getAllCourses();
//            List<Category> categories = dbContext.categoryDao().getAllCategories();
//            List<Teacher> teachers = dbContext.teacherDao().getAllTeachers();
//
//            requireActivity().runOnUiThread(() -> {
//                adapter = new CourseAdapter(courses);
//                adapter.setCategoryList(categories);
//                adapter.setTeacherList(teachers);
//                binding.coursesRecyclerView.setAdapter(adapter);
//            });
//        }).start();
//
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
//}

package com.tranquangdaine.applicationlotusyoga.ui.courses;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tranquangdaine.applicationlotusyoga.databinding.FragmentCourseBinding;
import com.tranquangdaine.applicationlotusyoga.infrastructure.LotusYogaDbContext;
import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Category;
import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Course;
import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Teacher;
import com.tranquangdaine.applicationlotusyoga.services.courseControllers.CreateCourseActivity;
import com.tranquangdaine.applicationlotusyoga.services.courseControllers.EditCourseActivty;

import java.util.ArrayList;
import java.util.List;

public class CourseFragment extends Fragment {

    private FragmentCourseBinding binding;
    private CourseAdapter adapter;
    private LotusYogaDbContext dbContext;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCourseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbContext = LotusYogaDbContext.getInstance(requireContext());

        // Initialize adapter with empty list
        adapter = new CourseAdapter(new ArrayList<>());
        binding.coursesRecyclerView.setAdapter(adapter);
        binding.coursesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Add course button
        binding.floatingActionButtonAddCourse.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), CreateCourseActivity.class);
            startActivity(intent);
        });

        // Set up listeners
        adapter.setOnEditClickListener(course -> {
            Intent intent = new Intent(requireContext(), EditCourseActivty.class);
            intent.putExtra("courseId", course.courseId);
            startActivity(intent);
        });

        adapter.setOnDeleteClickListener(course -> {
            new Thread(() -> {
                dbContext.courseDao().deleteCourse(course);
                List<Course> updatedCourses = dbContext.courseDao().getAllCourses();
                requireActivity().runOnUiThread(() -> adapter.setCourses(updatedCourses));
            }).start();
        });

        // Load initial data in background
        new Thread(() -> {
            List<Course> courses = dbContext.courseDao().getAllCourses();
            List<Category> categories = dbContext.categoryDao().getAllCategories();
            List<Teacher> teachers = dbContext.teacherDao().getAllTeachers();

            requireActivity().runOnUiThread(() -> {
                adapter.setCategoryList(categories);
                adapter.setTeacherList(teachers);
                adapter.setCourses(courses);
            });
        }).start();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
