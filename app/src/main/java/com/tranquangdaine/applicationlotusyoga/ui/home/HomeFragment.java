package com.tranquangdaine.applicationlotusyoga.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.tranquangdaine.applicationlotusyoga.R;
import com.tranquangdaine.applicationlotusyoga.databinding.FragmentCourseBinding;
import com.tranquangdaine.applicationlotusyoga.databinding.FragmentHomeBinding;
import com.tranquangdaine.applicationlotusyoga.infrastructure.LotusYogaDbContext;
import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Course;
import com.tranquangdaine.applicationlotusyoga.services.homeControllers.UploadActivity;
import com.tranquangdaine.applicationlotusyoga.services.teacherControllers.CreateTeacherActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private LotusYogaDbContext dbContext;
    private HomeAdapter adapter;
    private List<Course> fullCourseList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        dbContext = LotusYogaDbContext.getInstance(requireContext());
//        SearchView searchView = view.findViewById(R.id.searchView);
//        RecyclerView recyclerView = view.findViewById(R.id.homeRecyclerView);
//
//        fullCourseList = dbContext.courseDao().getAllCourses();
//        adapter = new HomeAdapter(new ArrayList<>(fullCourseList));
//        recyclerView.setAdapter(adapter);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                filterCourses(query);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                filterCourses(newText);
//                return true;
//            }
//
//            private void filterCourses(String text) {
//                List<Course> filtered = new ArrayList<>();
//                for (Course course : fullCourseList) {
//                    if (course.getName().toLowerCase().contains(text.toLowerCase())) {
//                        filtered.add(course);
//                    }
//                }
//                adapter.setCourses(filtered);
//            }
//        });
//
//        binding.floatingactionbuttonUploadToCloud.setOnClickListener(v -> {
//            Intent intent = new Intent(requireContext(), UploadActivity.class);
//            startActivity(intent);
//        });
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbContext = LotusYogaDbContext.getInstance(requireContext());
        SearchView searchView = view.findViewById(R.id.searchView);
        RecyclerView recyclerView = view.findViewById(R.id.homeRecyclerView);

        adapter = new HomeAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        new Thread(() -> {
            List<Course> courses = dbContext.courseDao().getAllCourses();
            requireActivity().runOnUiThread(() -> {
                fullCourseList = courses;
                adapter.setCourses(new ArrayList<>(fullCourseList));
            });
        }).start();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterCourses(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterCourses(newText);
                return true;
            }

            private void filterCourses(String text) {
                List<Course> filtered = new ArrayList<>();
                for (Course course : fullCourseList) {
                    if (course.getName().toLowerCase().contains(text.toLowerCase())) {
                        filtered.add(course);
                    }
                }
                adapter.setCourses(filtered);
            }
        });

        binding.floatingactionbuttonUploadToCloud.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), UploadActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}