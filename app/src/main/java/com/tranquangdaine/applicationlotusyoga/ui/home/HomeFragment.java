package com.tranquangdaine.applicationlotusyoga.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.tranquangdaine.applicationlotusyoga.databinding.FragmentCourseBinding;
import com.tranquangdaine.applicationlotusyoga.databinding.FragmentHomeBinding;
import com.tranquangdaine.applicationlotusyoga.infrastructure.LotusYogaDbContext;
import com.tranquangdaine.applicationlotusyoga.services.homeControllers.UploadActivity;
import com.tranquangdaine.applicationlotusyoga.services.teacherControllers.CreateTeacherActivity;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private LotusYogaDbContext dbContext;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbContext = LotusYogaDbContext.getInstance(requireContext());

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