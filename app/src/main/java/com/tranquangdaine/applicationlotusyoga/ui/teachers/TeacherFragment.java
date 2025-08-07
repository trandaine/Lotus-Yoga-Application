package com.tranquangdaine.applicationlotusyoga.ui.teachers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tranquangdaine.applicationlotusyoga.databinding.FragmentTeacherBinding;
import com.tranquangdaine.applicationlotusyoga.infrastructure.LotusYogaDbContext;
import com.tranquangdaine.applicationlotusyoga.infrastructure.entities.Teacher;
import com.tranquangdaine.applicationlotusyoga.services.teacherControllers.CreateTeacherActivity;
import com.tranquangdaine.applicationlotusyoga.services.teacherControllers.EditTeacherActivity;

import java.util.ArrayList;
import java.util.List;

public class TeacherFragment extends Fragment {
    private FragmentTeacherBinding binding;
    private TeacherAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTeacherBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.floatingActionButtonCreateTeacher.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), CreateTeacherActivity.class);
            startActivity(intent);
        });

        adapter = new TeacherAdapter(new ArrayList<>()); // Initialize the adapter with an empty list, if not the app will crash on a null object reference
        adapter.setOnTeacherEditListener(teacher -> {
            Intent intent = new Intent(requireContext(), EditTeacherActivity.class);
            intent.putExtra("teacherId", teacher.teacherId);
            startActivity(intent);
        });
        // In TeacherFragment.java, inside onViewCreated
        adapter.setOnTeacherDeleteListener(teacher -> {
            new Thread(() -> {
                try {
                    LotusYogaDbContext.getInstance(requireContext()).teacherDao().deleteTeacher(teacher);
                    List<Teacher> teachers = LotusYogaDbContext.getInstance(requireContext())
                            .teacherDao().getAllTeachers();
                    requireActivity().runOnUiThread(() -> adapter.setTeachers(teachers));
                }catch (Exception e) {
                    requireActivity().runOnUiThread(() ->
                        // Handle the error, e.g., show a Toast or Snackbar
                        Toast.makeText(requireContext(), "Error deleting teacher: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                }
            }).start();
        });
        binding.teacherRecyclerView.setAdapter(adapter);
        binding.teacherRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        new Thread(() -> {
            List<Teacher> teachers = LotusYogaDbContext.getInstance(requireContext())
                    .teacherDao().getAllTeachers();
            requireActivity().runOnUiThread(() -> adapter.setTeachers(teachers));
        }).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}